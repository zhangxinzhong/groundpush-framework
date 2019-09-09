package com.groundpush.controller;


import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.model.Label;
import com.groundpush.core.utils.Constants;
import com.groundpush.mapper.LabelMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/label")
public class LabelController {

    @Resource
    private LabelMapper labelMapper;

    @GetMapping
    public JsonResp getLabelByType() {
        try {
            List<Label> list  = labelMapper.getLabelByType(Constants.TYPE_ONE);
            return JsonResp.success(list);
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}
