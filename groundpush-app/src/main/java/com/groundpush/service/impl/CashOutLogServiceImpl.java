package com.groundpush.service.impl;

import com.github.pagehelper.PageHelper;
import com.groundpush.condition.CashOutLogQueryCondition;
import com.groundpush.mapper.CashOutLogMapper;
import com.groundpush.core.model.CashOutLog;
import com.groundpush.service.CashOutLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:提现记录
 * @author: zhangxinzhong
 * @date: 2019-08-29 下午12:50
 */
@Slf4j
@Service
public class CashOutLogServiceImpl implements CashOutLogService {

    @Resource
    private CashOutLogMapper cashOutLogMapper;

    @Override
    public List<CashOutLog> queryCashOutLog(CashOutLogQueryCondition cashOutLogQueryCondition, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        return cashOutLogMapper.queryCashOutLog(cashOutLogQueryCondition);
    }
}
