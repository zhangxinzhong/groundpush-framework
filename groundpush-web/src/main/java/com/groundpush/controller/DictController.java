package com.groundpush.controller;

import com.github.pagehelper.Page;
import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.model.Dict;
import com.groundpush.core.model.DictDetail;
import com.groundpush.core.model.PageResult;
import com.groundpush.service.DictDetailService;
import com.groundpush.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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

    @Resource
    private DictDetailService dictDetailService;

    /**
     * 跳转字典列表页
     *
     * @return
     */
    @RequestMapping("/toDictList")
    public String toDictList() {
        return "dict/dict";
    }

    /**
     * 根据条件获取字典列表信息
     *
     * @param page
     * @param limit
     * @return
     */
    @ResponseBody
    @GetMapping
    public JsonResp getDictList(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        Page<Dict> dicts = dictService.getDictList(page, limit);
        return JsonResp.success(new PageResult(dicts));

    }

    /**
     * 添加字典表信息
     *
     * @param dict
     * @return
     */
    @PostMapping
    @ResponseBody
    public JsonResp addDict(@Valid @RequestBody Dict dict, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        dictService.saveDict(dict);
        return JsonResp.success();
    }

    /**
     * 查看字典项信息
     *
     * @param dictId
     * @return
     */
    @GetMapping("/{dictId:\\d+}")
    @ResponseBody
    public JsonResp detailDict(@PathVariable Integer dictId) {
        Optional<Dict> dict = dictService.getById(dictId);
        return JsonResp.success(dict.isPresent() ? dict.get() : null);
    }

    /**
     * 删除字典项信息
     *
     * @param dictId
     * @return
     */
    @DeleteMapping
    @ResponseBody
    public JsonResp deleteDict(@RequestParam Integer dictId) {
        dictService.deleteDict(dictId);
        return JsonResp.success();
    }

    @PutMapping
    @ResponseBody
    public JsonResp updateDict(@Valid @RequestBody Dict dict, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        dictService.updateDict(dict);
        return JsonResp.success();
    }

    @ResponseBody
    @GetMapping("/{dictId:\\d+}/dictDetail")
    public JsonResp queryDictDetailByDict(@Valid @PathVariable @NotNull(message = "数据字典编号不可为空") Integer dictId, @RequestParam(name = "page") Integer page, @RequestParam(name = "limit") Integer limit) {
        Page<DictDetail> dictDetails = dictDetailService.queryDictDetailByDict(dictId, page, limit);
        return JsonResp.success(new PageResult(dictDetails));
    }
}
