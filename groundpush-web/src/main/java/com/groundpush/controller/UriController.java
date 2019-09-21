package com.groundpush.controller;

import com.github.pagehelper.Page;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.model.PageResult;
import com.groundpush.core.model.Uri;
import com.groundpush.service.UriService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * @description:Uri
 * @author: hengquan
 * @date 9:24 2019/9/19
 */
@Slf4j
@ApiModel(value = "uri管理")
@RequestMapping("/uri")
@Controller
public class UriController {

    @Resource
    private UriService uriService;

    @RequestMapping("/toUriList")
    public String toUriList(Model model) {
        return "/uri/uri";
    }

    /**
     * 分页查询Uri
     */
    @GetMapping
    @ResponseBody
    @ApiOperation("Uri查询服务")
    @RequestMapping("/getUriPageList")
    public JsonResp queryTask(Uri uri, @RequestParam(value = "nowPage", defaultValue = "1") Integer nowPage, @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        try {
            Page<Uri> uris = uriService.queryTaskAll(uri, nowPage, pageSize);
            return JsonResp.success(new PageResult(uris));
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @ApiOperation("Uri保存服务")
    @RequestMapping("/save")
    @ResponseBody
    public JsonResp saveUri(Model model, @RequestBody Uri uri) {
        try {
            uriService.save(uri);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @ApiOperation("获取Uri")
    @ResponseBody
    @GetMapping("getUri/{id:\\d+}")
    public JsonResp getTask(@PathVariable Integer id) {
        try {
            //获取任务数据
            Optional<Uri> optionalUri = uriService.getUri(id);
            //获取任务编辑数据
            Uri uri = optionalUri.get();
            return JsonResp.success(optionalUri.isPresent() ? uri : null);
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    @ApiOperation("Uri删除")
    @RequestMapping("/del")
    @ResponseBody
    public JsonResp delUri(Model model, @RequestBody Uri uri) {
        try {
            uriService.del(uri.getUriId());
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    /**
     * 获取所有的URI
     */
    @GetMapping
    @ResponseBody
    @RequestMapping("/getUriALL")
    public Map<String,Object> getUriALL() {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try {
            List<Uri> uriList = uriService.getUriALL();
            if(uriList!=null && uriList.size()>0){
                resultMap.put("code","200");
                resultMap.put("dataList",uriList);
            }
        } catch (Exception e) {
            resultMap.put("code","500");
            resultMap.put("msg","获取URI失败请联系管理员！");
            log.error(e.toString(), e);
            throw e;
        }
        return resultMap;
    }

}
