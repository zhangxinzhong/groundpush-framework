package com.groundpush.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.pagehelper.Page;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.common.View;
import com.groundpush.core.model.Label;
import com.groundpush.core.model.PageResult;
import com.groundpush.core.model.TaskLabel;
import com.groundpush.service.LabelService;
import com.groundpush.service.TaskLabelService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/label")
public class LabelController {

    @Resource
    private LabelService labelService;
    @Resource
    private TaskLabelService taskLabelService;


    @RequestMapping("/toLabel")
    public String toLabel(Model model) {

        return "/label/label";
    }


    @ApiOperation(value = "获取所有标签列表")
    @RequestMapping(value = "/getLabelPage", method = RequestMethod.GET)
    @ResponseBody
    public JsonResp getLabelPage(Integer page, Integer limit) {
        try {
            Page<Label>  pageLabel  = labelService.queryAll(page,limit);
            return JsonResp.success(new PageResult(pageLabel));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }


    @ApiOperation(value = "添加标签")
    @RequestMapping(value = "/addLabel", method = RequestMethod.POST)
    @ResponseBody
    public JsonResp addLabel(@RequestBody Label label) {
        try {

            labelService.createLabel(label);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @ApiOperation(value = "修改标签")
    @RequestMapping(value = "/updateLabel", method = RequestMethod.POST)
    @ResponseBody
    public JsonResp updateLabel(@RequestBody Label label) {
        try {
            labelService.updateLabel(label);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @ApiOperation(value = "删除标签")
    @RequestMapping(value = "/delLabel", method = RequestMethod.POST)
    @ResponseBody
    public JsonResp delLabel(@RequestBody Label label) {
        try {
            List<TaskLabel> list = taskLabelService.getTaskLabelByLabelId(label);
            if(list.size() > 0){
                return JsonResp.failure("存在关联的任务，不可删除！");
            }

            labelService.delById(label);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }


    @ApiOperation(value = "删除标签")
    @RequestMapping(value = "/detailLabel", method = RequestMethod.GET)
    @ResponseBody
    public JsonResp detailLabel(Integer labelId) {
        try {
            Optional<Label> label = labelService.quueryById(labelId);
            return JsonResp.success(label.get());
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }
}
