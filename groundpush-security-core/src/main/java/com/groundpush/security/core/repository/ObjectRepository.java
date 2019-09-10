package com.groundpush.security.core.repository;


import java.util.Optional;

/**
 * @description: 系统用户 CRUD 包括 Customer 和User
 * @author: zhangxinzhong
 * @date: 2019-09-10 下午3:13
 */
public interface ObjectRepository<T> {

    /**
     * 用于查询或这新增 Customer 和User
     *
     * @param id
     * @return
     */
    Optional<T> queryOrCreate(String id);

}
