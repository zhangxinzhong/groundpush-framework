package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.pagehelper.Page;
import com.groundpush.core.common.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 分页对象
 * @author: zhangxinzhong
 * @date: 2019-08-23 下午4:50
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo implements Serializable {

    /**
     * 上传path
     */
    @JsonView(View.class)
    private String filePath;

    /**
     * 上传文件名
     */
    @JsonView(View.class)
    private String fileName;

    /**
     * 图像识别唯一标识
     */
    @JsonView(View.class)
    private String unqiueCode;

    public FileInfo(String filePath,String fileName){
        this.filePath = filePath;
        this.fileName = fileName;
    }

    public FileInfo(String unqiueCode){
        this.unqiueCode = unqiueCode;
    }


}
