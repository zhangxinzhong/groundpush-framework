package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.condition.SpreadQueryCondition;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.model.Order;
import com.groundpush.core.model.TaskUri;
import com.groundpush.core.service.OrderService;
import com.groundpush.core.service.SpecialTaskService;
import com.groundpush.core.service.TaskService;
import com.groundpush.core.service.TaskUriService;
import com.groundpush.core.utils.AesUtils;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.utils.RedisUtils;
import com.groundpush.core.vo.TaskPopCountVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;

/**
 * @description:推广任务
 * @author: hss
 * @date: 2019-09-17
 */
@Slf4j
@ApiModel(value = "产品推广")
@RequestMapping("/spread")
@RestController
public class SpreadController {
    @Resource
    private RedisUtils redisUtils;

    @Resource
    private AesUtils aesUtils;

    @Resource
    private OrderService orderService;

    @Resource
    private TaskUriService taskUriService;

    @Resource
    private TaskService taskService;

    @Resource
    private SpecialTaskService specialTaskService;

    @ApiOperation("页面跳转uri")
    @GetMapping
    public JsonResp toSpread(@Valid SpreadQueryCondition spreadQueryCondition, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        String key = aesUtils.dcodes(spreadQueryCondition.getKey(), Constants.APP_AES_KEY);
        try {

            log.info("跳转页面方法传参：用户id:{},任务id:{},任务类型:{},二维码key:{}", spreadQueryCondition.getCustomId(), spreadQueryCondition.getTaskId(), spreadQueryCondition.getType(), spreadQueryCondition.getKey());


            String obj = (String) redisUtils.get(key);

            if (StringUtils.isBlank(key) || StringUtils.isBlank(obj) || (StringUtils.isNotBlank(obj) && !obj.equalsIgnoreCase(key))) {
                log.info("跳转页面失败,key 不匹配");
                return JsonResp.failure(ExceptionEnum.TASK_QR_CODE_INVALID.getErrorCode(), ExceptionEnum.TASK_QR_CODE_INVALID.getErrorMessage());
            }

            //获取每日推广剩余次数 每人每日推广剩余次数
            Optional<TaskPopCountVo> optional = taskService.getSupTotalOrCustomCount(spreadQueryCondition.getCustomId(), spreadQueryCondition.getTaskId());
            if (optional.isPresent()) {
                log.info("每日推广剩余次数：{} 每人每日推广剩余次数：{}", optional.get().getSupTotal(), optional.get().getSupCustom());
                if (optional.get().getSupCustom() <= Constants.ZROE || optional.get().getSupTotal() <= Constants.ZROE) {
                    log.error("今日推广次数已达上限");
                    return JsonResp.failure(ExceptionEnum.TASK_SPREAD_MAX.getErrorCode(), ExceptionEnum.TASK_SPREAD_MAX.getErrorMessage());
                }
            }

            //获取任务uri
            Optional<TaskUri> taskUriOptional = taskUriService.queryTaskUriByTaskId(spreadQueryCondition.getTaskId());
            if (!taskUriOptional.isPresent()) {
                log.error("任务：{} 的URI 不存在", spreadQueryCondition.getTaskId());
                return JsonResp.failure(ExceptionEnum.TASK_NOT_URI.getErrorCode(), ExceptionEnum.TASK_NOT_URI.getErrorMessage());
            }

            // 如果任务类型为申请任务且订单未上传结果集不重复创建订单
            if (Constants.ONE.equals(spreadQueryCondition.getType())) {
                Optional<Order> orderOptional = orderService.checkOrderIsExistAndIsUploadResult(spreadQueryCondition.getCustomId(), spreadQueryCondition.getTaskId());
                if (orderOptional.isPresent()) {
                    return JsonResp.success(StringUtils.isBlank(orderOptional.get().getChannelUri()) ? taskUriOptional.get().getUri() : orderOptional.get().getChannelUri());
                }
            }

            //1.是否是特殊任务 且 是否是改任务的特殊用户
            //  还需验证当前用户上级是否是特殊用户
            Boolean isSpecialTask = specialTaskService.whetherSpecialTask(spreadQueryCondition.getTaskId());
            Order order = Order.builder().customerId(spreadQueryCondition.getCustomId()).type(spreadQueryCondition.getType()).taskId(spreadQueryCondition.getTaskId()).status(Constants.ORDER_STATUS_REVIEW).channelUri(taskUriOptional.get().getUri()).isSpecial(isSpecialTask).build();
            //2.创建用户订单
            orderService.createOrder(order);
            // 使用完url 后需要把最后修改时间改成今天
            taskUriService.updateTaskUri(taskUriOptional.get());

            return JsonResp.success(taskUriOptional.get().getUri());
        } catch (Exception e) {
            log.error(e.toString(), e);
            return JsonResp.failure(ExceptionEnum.EXCEPTION.getErrorCode(), ExceptionEnum.EXCEPTION.getErrorMessage());
        } finally {
            redisUtils.del(key);
        }
    }


}
