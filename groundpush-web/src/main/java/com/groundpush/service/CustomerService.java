package com.groundpush.service;

import com.groundpush.core.condition.CustomerQueryCondition;
import com.groundpush.core.model.Customer;
import com.groundpush.vo.CustomerVo;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @description:客户管理
 * @author: zhangxinzhong
 * @date: 2019-08-28 下午12:54
 */
public interface CustomerService {

    /**
     * 获取客户信息
     * @param customerId
     * @return
     */
    Optional<Customer> getCustomer(Integer customerId);
}
