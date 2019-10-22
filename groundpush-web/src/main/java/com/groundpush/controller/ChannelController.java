package com.groundpush.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.pagehelper.Page;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.model.Channel;
import com.groundpush.core.model.LoginUserInfo;
import com.groundpush.core.model.PageResult;
import com.groundpush.core.utils.Constants;
import com.groundpush.core.utils.DateUtils;
import com.groundpush.core.utils.ExcelUtils;
import com.groundpush.service.ChannelService;
import com.groundpush.utils.SessionUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;

/**
 * @description:渠道管理
 * @author: zhangxinzhong
 * @date: 2019-08-29 下午12:02
 */
@Slf4j
@Controller
@RequestMapping("/channel")
public class ChannelController {

    @Resource
    private ChannelService channelService;

    @Resource
    private SessionUtils sessionUtils;

    @Resource
    private DateUtils dateUtils;


    @RequestMapping("/toChannel")
    public String toChannel() {

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

    @ApiOperation(value = "删除渠道")
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


    @ApiOperation(value = "查询某个渠道详情 ")
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
    public JsonResp importExcelData(@RequestParam("file") MultipartFile file, String mapping, Integer channelId, Integer taskId, String channelTime) throws Exception {
        try {
            return JsonResp.success(channelService.addChannelData(channelId, taskId, dateUtils.transToLocalDateTime(channelTime, "yyyy-MM-dd"), file.getOriginalFilename(), mapping, file.getInputStream()));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @ApiOperation(value = "获取所有渠道列表")
    @RequestMapping(value = "/getChannelAll")
    @ResponseBody
    @JsonView(Channel.OutChannelView.class)
    public JsonResp getChannelAll() {
        try {
            return JsonResp.success(channelService.getChannelAll());
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }
}
