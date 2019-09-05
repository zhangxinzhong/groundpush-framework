package com.groundpush.controller;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.model.DictDetail;
import com.groundpush.service.DictDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public JsonResp addDictDetail(@RequestBody DictDetail dictDetail) {
        try {
            dictDetailService.insertDictDetail(dictDetail);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
        return JsonResp.failure();

    }

    /**
     * 查看字典项信息
     *
     * @param dictDetailId
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public JsonResp detailDict(Integer dictDetailId) {
        try {
            Optional<DictDetail> dictDetail = dictDetailService.getById(dictDetailId);
            return JsonResp.success(dictDetail.get());
        } catch (Exception e) {
            log.error(e.toString(), e);
            return JsonResp.failure();
        }
    }

    /**
     * 更新字典项信息
     *
     * @param dictDetail
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public JsonResp updateDictDetail(@RequestBody DictDetail dictDetail) {
        try {
            dictDetailService.updateDictDetail(dictDetail);
            return JsonResp.success();
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
        return JsonResp.failure();
    }

    /**
     * 删除字典项信息
     *
     * @param dictDetailId
     * @return
     */
    @RequestMapping("/del")
    @ResponseBody
    public JsonResp deleteDictDetail(Integer dictDetailId) {
        try {
            if (dictDetailId != null) {
                dictDetailService.deleteDictDetail(dictDetailId);
                return JsonResp.success();
            }
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
        return JsonResp.failure();
    }
}
