package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.groundpush.core.common.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * TaskAttrResult
 * 用于app返回 任务属性list
 * @author hss
 * @date 2019/10/18 16:47
 */
@Data
@Builder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataResult {

    @JsonView(View.class)
    private List list;

    public DataResult(List list){
         this.list = list;
    }
}
