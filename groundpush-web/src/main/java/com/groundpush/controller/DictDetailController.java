package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.model.DictDetail;
import com.groundpush.service.DictDetailService;
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
@RequestMapping("/dictDetail")
@Controller
public class DictDetailController {
    @Resource
    private DictDetailService dictDetailService;

    /**
     * 添加字典表信息
     *
     * @param dictDetail
     * @return
     */
    @PostMapping
    @ResponseBody
    public JsonResp addDictDetail(@Valid @RequestBody DictDetail dictDetail, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new GroundPushMethodArgumentNotValidException(bindingResult.getFieldErrors());
        }
        return JsonResp.success(dictDetailService.insertDictDetail(dictDetail));
    }

    /**
     * 查看字典项信息
     *
     * @param detailId
     * @return
     */
    @GetMapping("/{detailId:\\d+}")
    @ResponseBody
    public JsonResp detailDict(@PathVariable(name = "detailId") Integer detailId) {
        Optional<DictDetail> dictDetail = dictDetailService.getById(detailId);
        return JsonResp.success(dictDetail.isPresent() ? dictDetail.get() : null);
    }

    /**
     * 更新字典项信息
     *
     * @param dictDetail
     * @return
     */
    @PutMapping
    @ResponseBody
    public JsonResp updateDictDetail(@RequestBody DictDetail dictDetail) {
        dictDetailService.updateDictDetail(dictDetail);
        return JsonResp.success();
    }

    /**
     * 删除字典项信息
     *
     * @param detailId
     * @return
     */
    @ResponseBody
    @DeleteMapping
    public JsonResp deleteDictDetail(@RequestParam Integer detailId) {
        dictDetailService.deleteDictDetail(detailId);
        return JsonResp.success();
    }
}
