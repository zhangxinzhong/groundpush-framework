package com.groundpush.controller;


import com.groundpush.core.common.JsonResp;
import com.groundpush.core.model.Label;
import com.groundpush.core.service.LabelService;
import com.groundpush.core.utils.Constants;
import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@ApiModel("标签管理")
@RestController
@RequestMapping("/labelapp")
public class LabelController {

    @Resource
    private LabelService labelService;

    @GetMapping
    public JsonResp getLabelByType() {
        List<Label> list = labelService.getLabelByType(Constants.TYPE_ONE);
        return JsonResp.success(list);
    }
}
