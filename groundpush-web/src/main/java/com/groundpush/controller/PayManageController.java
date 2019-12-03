package com.groundpush.controller;

import com.github.pagehelper.Page;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.condition.ExportWordCondition;
import com.groundpush.core.condition.OrderListQueryCondition;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.model.*;
import com.groundpush.core.service.*;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.vo.OrderPayVo;
import com.groundpush.core.vo.TaskOrderListVo;
import com.groundpush.utils.ExportUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * @description: 支付管理
 * @author: zhangxinzhong
 * @date: 2019-09-09 下午1:33
 */
@Slf4j
@Controller
@RequestMapping("/payManage")
public class PayManageController {

    @Resource
    private PayService payService;


    @Resource
    private AuditLogService auditLogService;

    @Resource
    private TaskService taskService;

    @Resource
    private OrderService orderService;

    @Resource
    private ExportUtils exportUtils;

    @PostMapping
    @ResponseBody
    public JsonResp pay(@Valid @RequestBody OrderPayVo orderPay, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        payService.pay(orderPay);
        return JsonResp.success();
    }


    @ApiOperation(value = "跳转支付管理")
    @RequestMapping("/toPay")
    public String toLabel() {

        return "pay/pay";
    }


    @ApiOperation(value = "获取所有任务列表")
    @RequestMapping(value = "/getTaskOrderlist", method = RequestMethod.GET)
    @ResponseBody
    public JsonResp getTaskOrderlist(Integer page, Integer limit) {

        try {
            Page<TaskOrderListVo> pageAudit = auditLogService.findAllPayTaskOrderList(page, limit);
            return JsonResp.success(new PageResult(pageAudit));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }





    @ApiOperation(value = "获取订单列表列表")
    @RequestMapping(value = "/queryOrderList", method = RequestMethod.GET)
    @ResponseBody
    public JsonResp queryOrderList(OrderListQueryCondition condition) {
        try {

            Page<OrderList> pageOrderList = auditLogService.queryOrderListByTaskIdAndOrderId(condition);
            return JsonResp.success(new PageResult(pageOrderList));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @RequestMapping(value = "/exportOrderWord",method = RequestMethod.GET)
    public void exportOrderWord(@Valid ExportWordCondition condition, BindingResult bindingResult, HttpServletResponse response) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }


        Optional<Task> optional = taskService.queryTaskByTaskId(condition.getTaskId());
        if(optional.isPresent()){
            response.setCharacterEncoding(Constants.ENCODING_UTF8);
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment;filename=" + new String(optional.get().getTitle().concat(".docx").getBytes("utf-8"), "ISO8859-1"));
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "No-cache");
            List<Order> orders = orderService.queryOrderLogOfOrder(condition);
            WordDataMap dataMap = WordDataMap.builder().taskName(optional.get().getTitle()).orders(orders).build();
            //获取orderLog订单记录中类型为图片的log
            orders.forEach(order ->{
                order.getOrderLogs().stream().filter(orderLog ->
                        Constants.ORDER_RESULT_TYPE_2.equals(orderLog.getOrderResultType())).forEach(orderLog -> {
                    try {
                        orderLog.setBinaryData(exportUtils.getByteArrayByImgUrl(orderLog.getOrderValue()));
                        orderLog.setFileExt(orderLog.getOrderValue().substring(orderLog.getOrderValue().lastIndexOf(".")+1));
                    } catch (Exception e) {
                        log.error("图片转换错误imgurl:{}",orderLog.getOrderValue());
                    }
                });
            });

            exportUtils.exportWord(Constants.TEMPLATE_FTL,dataMap,response.getOutputStream());
        }

    }

}
