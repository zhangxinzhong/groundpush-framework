package com.groundpush.core.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.groundpush.core.condition.CashOutLogQueryCondition;
import com.groundpush.core.mapper.CashOutLogMapper;
import com.groundpush.core.model.CashOutLog;
import com.groundpush.core.service.CashOutLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Page<CashOutLog> queryCashOutLog(CashOutLogQueryCondition cashOutLogQueryCondition) {
        PageHelper.startPage(cashOutLogQueryCondition.getPageNumber(), cashOutLogQueryCondition.getPageSize());
        return cashOutLogMapper.queryCashOutLog(cashOutLogQueryCondition);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createCashOutLog(CashOutLog build) {
        cashOutLogMapper.createCashOutLog(build);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCashOutLog(CashOutLog cashOutLog) {
        cashOutLogMapper.updateCashOutLog(cashOutLog);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCashOutLogByOutBizNo(CashOutLog build) {
        cashOutLogMapper.updateCashOutLogByOutBizNo(build);
    }



    @Override
    public Page<CashOutLog> findAll(Integer pageNumber, Integer pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        return cashOutLogMapper.queryAll();
    }
}