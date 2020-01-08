package com.baizhi.entity;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;

public class UserListener extends AnalysisEventListener<User> {
    ArrayList<User> list= new ArrayList<User>();
    @Override
    public void invoke(User user, AnalysisContext analysisContext){

        list.add(user);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        for (User user : list) {
            System.out.println(user+"-------------");
        }
    }

}
