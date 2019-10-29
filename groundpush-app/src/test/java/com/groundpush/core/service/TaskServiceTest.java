package com.groundpush.core.service;

import com.groundpush.core.model.Task;
import com.groundpush.core.model.TaskAttribute;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * TaskServiceTest
 *
 * @author hss
 * @date 2019/10/29 10:14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;


    @Test
    public void save() {
        for(int i = 0; i < 4700; i ++){


            Task task = Task.builder().title("测试标题"+i)
                    .imgUri("http://test.oss.zhongdi001.com/task/1571292252303.jpg")
                    .status(1).type(1).source(5).amount(BigDecimal.valueOf(20+i))
                   .spreadTotal(20+i).handlerNum(20+i).auditDuration(20+i)
                   .expiredTime(LocalDateTime.now()).completeOdds(BigDecimal.valueOf(20+i))
                   .spreadRatio(BigDecimal.valueOf(20+i)).leaderRatio(BigDecimal.valueOf(20+i))
                   .createdBy(0).createdTime(LocalDateTime.now()).isResult(0)
                   .iconUri("http://test.oss.zhongdi001.com/task/1571292242412.jpg")
                   .briefTitle("测试简略标题"+i).taskTitle("介绍标题"+i)
                   .taskContent("1.介绍内容:\\n1.介绍内容:\\n1.介绍内容:\\n1.介绍内容:")
                   .labelIds("18,19,20").build();
            List<TaskAttribute> attributes = new ArrayList<>();
            attributes.add(TaskAttribute.builder().content("下载APP").type(2).seq(1).labelType(1).rowType(1).createUri(0).build());
            attributes.add(TaskAttribute.builder().content("https://storage.360buyimg.com/jdmobile/JDMALL-PC2.apk").type(2).seq(2).labelType(1).rowType(3).createUri(0).build());
            attributes.add(TaskAttribute.builder().content("开始任务").type(2).seq(1).labelType(2).rowType(1).createUri(0).build());
            attributes.add(TaskAttribute.builder().content("https://106.2.172.227:18443/spread").type(2).seq(2).labelType(2).rowType(3).createUri(1).build());
            attributes.add(TaskAttribute.builder().content("上传结果").type(2).seq(1).labelType(3).rowType(1).createUri(0).build());
            attributes.add(TaskAttribute.builder().content("http://test.oss.zhongdi001.com/task/1571883899064.png").type(2).seq(2).labelType(3).rowType(5).createUri(0).build());
            attributes.add(TaskAttribute.builder().content("请输入订单编号").type(3).seq(1).labelType(1).rowType(1).createUri(0).build());
            task.setSpreadTaskAttributes(attributes);
            taskService.save(task);
        }
    }
}