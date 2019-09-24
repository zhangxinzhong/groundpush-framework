package com.groundpush.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.pagehelper.Page;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.model.Channel;
import com.groundpush.core.model.ChannelData;
import com.groundpush.core.model.LoginUserInfo;
import com.groundpush.core.model.PageResult;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.utils.ExcelTools;
import com.groundpush.core.utils.ExcelUtils;
import com.groundpush.core.utils.SessionUtils;
import com.groundpush.service.ChannelDataService;
import com.groundpush.service.ChannelService;
import com.groundpush.service.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/channel")
public class ChannelController {

    @Value("${groundpush.channel.mapping_filed}")
    private String mappingFiled;

    @Resource
    private ChannelService channelService;

    @Resource
    private OrderService orderService;

    @Resource
    private ChannelDataService channelDataService;

    @Resource
    private SessionUtils sessionUtils;

    @RequestMapping("/toChannel")
    public String toChannel(Model model) {

        return "channel/channel";
    }


    @ApiOperation(value = "获取所有渠道列表")
    @RequestMapping(value = "/getChannelPage", method = RequestMethod.GET)
    @ResponseBody
    @JsonView(Channel.OutChannelView.class)
    public JsonResp getChannelPage(Integer page, Integer limit) {
        try {
            Page<Channel> pageLabel = channelService.queryAll(page, limit);
            return JsonResp.success(new PageResult(pageLabel));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }


    @ApiOperation(value = "添加渠道")
    @RequestMapping(value = "/addChannel", method = RequestMethod.POST)
    @ResponseBody
    public JsonResp addChannel(@Valid @RequestBody Channel channel, BindingResult bindingResult) {
        try {
            Optional<LoginUserInfo> user = sessionUtils.getLogin();
            channel.setCreatedBy(user.isPresent() ? user.get().getUser().getUserId() : null);
            channelService.createChannel(channel);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @ApiOperation(value = "修改渠道")
    @RequestMapping(value = "/updateChannel", method = RequestMethod.POST)
    @ResponseBody
    public JsonResp updateChannel(@Valid @RequestBody Channel channel, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
            }
            channelService.updateChannel(channel);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @ApiOperation(value = "删除标签")
    @RequestMapping(value = "/delChannel", method = RequestMethod.POST)
    @ResponseBody
    public JsonResp delChannel(@RequestBody Channel channel) {
        try {
            channel.setStatus(Constants.STATUS_LOSE);
            channelService.updateChannel(channel);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }


    @ApiOperation(value = "查询某个标签")
    @RequestMapping(value = "/detailChannel", method = RequestMethod.GET)
    @ResponseBody
    @JsonView(Channel.AllChannelView.class)
    public JsonResp detailChannel(Integer channelId) {
        try {
            Optional<Channel> label = channelService.queryById(channelId);
            return JsonResp.success(label.get());
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @ApiOperation(value = "获取excel的标题")
    @RequestMapping(value = "/getExcelTitle", method = RequestMethod.POST)
    @ResponseBody
    public JsonResp getExcelTitle(@RequestParam("file") MultipartFile file) throws Exception {
        try {
            return JsonResp.success(ExcelUtils.getInstance().parseExcel(file.getInputStream()).getJsonTitle());
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @ApiOperation(value = "导入数据")
    @RequestMapping(value = "/importExcelData", method = RequestMethod.POST)
    @ResponseBody
    public JsonResp importExcelData(@RequestParam("file") MultipartFile file, String mapping, Integer channelId, Integer taskId) throws Exception {
        try {
            ExcelTools excelTools = ExcelTools.getInstance();
            excelTools.openExcel(file.getInputStream());
            final Object[] title = excelTools.getExcelTitle();
            excelTools.setRowResult(100, (sheetName, countRow, resultCount, result) -> {
                List<ChannelData> cds = new ArrayList<>();
                result.stream().forEach(channel -> {
                    Object[] excelRowData = channel;
                    if (!StringUtils.equals(String.valueOf(excelRowData[0]), String.valueOf(title[0]))) {
                        Map<String, Object> analysisResult = analysisSingletData(channel, mapping);
                        String uniqueCode = String.valueOf(analysisResult.get("uniqueCode"));
                        String failureResult = String.valueOf(analysisResult.get("failureResult"));
                        //@author hss @date 2019-09-24 将xls中是否有效改为“是”=1 “否”或null=0
                        String isEffective = String.valueOf(analysisResult.get("isEffective"));
                        Integer resStatus = Constants.XLS_IS_EFFECTIVE_VAILD_TEXT.equals(isEffective)?Constants.XLS_IS_EFFECTIVE_VAILD:Constants.XLS_IS_EFFECTIVE_INVAILD;
                        boolean isExistOrder = true;
                        if (orderService.updateOrderByUniqueCode(uniqueCode, resStatus, failureResult) <= 0) {
                            isExistOrder = false;
                        }

                        ChannelData channelData = new ChannelData();
                        channelData.setChannelId(channelId);
                        channelData.setTaskId(taskId);
                        channelData.setUniqueCode(uniqueCode);
                        channelData.setExistOrder(isExistOrder);
                        channelData.setChannelTime(LocalDateTime.parse(String.valueOf(analysisResult.get("produceTime"))));
                        channelData.setEffective(Boolean.valueOf(String.valueOf(analysisResult.get("isEffective"))));
                        channelData.setDescription(String.valueOf(analysisResult.get("failureResult")));
                        cds.add(channelData);
                    }
                });
                channelDataService.createChannelData(cds);
            });
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @ApiOperation(value = "获取所有渠道列表")
    @RequestMapping(value = "/getChannelAll")
    @ResponseBody
    @JsonView(Channel.OutChannelView.class)
    public Map<String, Object> getChannelAll() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            List<Channel> channelList = channelService.getChannelAll();
            if (channelList != null && channelList.size() > 0) {
                resultMap.put("dataList", channelList);
                resultMap.put("code", "200");
            }
        } catch (Exception e) {
            resultMap.put("msg", "获取公司信息列表失败！");
            resultMap.put("code", "500");
            log.error(e.toString(), e);
            throw e;
        }
        return resultMap;
    }

    /**
     * 处理单条数据
     *
     * @param result  单条数据
     * @param mapping 映射关系
     * @return
     */
    private Map<String, Object> analysisSingletData(Object[] result, String mapping) {
        Map<String, Object> res = new LinkedHashMap<>();

        /** excel数据映射到系统内部关系 */
        JSONArray mappingRelation = new JSONArray(mapping);
        String[] mFiled = mappingFiled.split(",");
        mappingRelation.forEach(obj -> {
            JSONObject mappingObj = (JSONObject) obj;
            res.put(mFiled[mappingObj.getInt("sysType") - 1], result[mappingObj.getInt("excelType")]);
        });
        return res;
    }
}
