package com.groundpush.controller;

import com.github.pagehelper.Page;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.model.Dict;
import com.groundpush.core.model.PageResult;
import com.groundpush.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @description:字典项
 * @author: hengquan
 * @date: 13:35 2019/9/4
 */
@Slf4j
@RequestMapping("/dict")
@Controller
public class DictController {
    @Resource
    private DictService dictService;

    /**
     * 跳转字典列表页
     *
     * @param model
     * @return
     */
    @RequestMapping("/toDictList")
    public String toDictList(Model model) {
        return "dict/dict";
    }

    /**
     * 根据条件获取字典列表信息
     *
     * @param dict
     * @param nowPage
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping("/getDictList")
    public JsonResp getDictList(Dict dict, @RequestParam(value = "nowPage", defaultValue = "1") Integer nowPage, @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        Page<Dict> dicts = dictService.getDictList(nowPage, pageSize);
        return JsonResp.success(new PageResult(dicts));

    }

    /**
     * 添加字典表信息
     *
     * @param dict
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public JsonResp addDict(@RequestBody Dict dict) {
        try {
            dictService.saveDict(dict);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    /**
     * 查看字典项信息
     *
     * @param dictId
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public JsonResp detailDict(Integer dictId) {
        try {
            Optional<Dict> dict = dictService.getById(dictId);
            return JsonResp.success(dict.get());
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
    }

    /**
     * 删除字典项信息
     *
     * @param dictId
     * @return
     */
    @RequestMapping("/del")
    @ResponseBody
    public JsonResp deleteMenu(Integer dictId) {
        try {
            if (dictId != null) {
                dictService.deleteDict(dictId);
                return JsonResp.success();
            }
        } catch (Exception e) {
            log.error(e.toString(), e);
            throw e;
        }
        return JsonResp.failure();
    }
}
