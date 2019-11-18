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
 *
 * 用于app返回 参数 data 或 list
 * @author hss
 * @date 2019/10/18 16:47
 */
@Data
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataResult {

    @JsonView(View.class)
    private List list;

    @JsonView(View.class)
    private Object data;

    public DataResult(Object data) {
        this.data = data;
    }

    public DataResult(List list) {
        this.list = list;
    }

    public DataResult( Object data,List list) {
        this.data = data;
        this.list = list;
    }
}
