package com.groundpush.core.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.groundpush.core.common.View;

import java.util.List;

/**
 * TaskTeamList
 *
 * @author hss
 * @date 2019/10/11 16:22
 */
public class TaskTeamList {

    @JsonView(View.class)
    List teams;


    @JsonView(View.class)
    List tasks;

    public TaskTeamList(List teams,List tasks){
        this.teams = teams;
        this.tasks = tasks;
    }
}
