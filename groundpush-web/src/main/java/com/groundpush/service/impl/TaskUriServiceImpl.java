package com.groundpush.service.impl;

import com.groundpush.core.model.TaskUri;
import com.groundpush.mapper.TaskUriMapper;
import com.groundpush.service.TaskUriService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @description:任务uri
 * @author: hss
 * @date: 2019/9/16 19:58
 */
@Service
public class TaskUriServiceImpl implements TaskUriService {

    @Resource
    private TaskUriMapper taskUriMapper;


    @Override
    public Boolean save(List<TaskUri> taskUris) {
        Boolean result = true;
        //保存
        if(taskUris!=null && taskUris.size()>0){
            result = taskUriMapper.insert(taskUris) > 0 ? true : false;
        }
        return result;
    }

    @Override
    public Boolean del(Integer taskId) {
        Boolean result = true;
        //保存
        result = taskUriMapper.del(taskId) > 0 ? true : false;
        return result;
    }
}
