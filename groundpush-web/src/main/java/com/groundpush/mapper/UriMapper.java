package com.groundpush.mapper;

import com.groundpush.core.model.Uri;

import java.util.List;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-19 下午4:58
 */
public interface UriMapper {
    List<Uri> queryUriByUserId();
}
