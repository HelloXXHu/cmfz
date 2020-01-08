package com.baizhi.dao;

import com.baizhi.entity.Counter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CounterDao {

    public List<Counter> selectByCourseId(String id);

    //添加计数器
    public void insertCounter(Counter counter);

    //删除计数器
    public void deleteCounter(String id);

    //变更计数器
    public void updateCounter(@Param("id")String id,@Param("count")Integer count);

}
