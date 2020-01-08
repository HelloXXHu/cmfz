package com.baizhi.dao;

import com.baizhi.entity.User;
import com.baizhi.entity.UserDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {

    public List<User> findByPage(@Param("start")Integer start,@Param("rows")Integer rows);

    public Integer totalCount();

    public List<User> queryAll();

    public void insert(User user);

    public void delete(String id);

    //按性别和天数查
    public Integer queryUserByTime(@Param("sex") String sex,@Param("day") Integer day);

    //按性别和地区查
    public List<UserDto> selectByLocation(String sex);

    public void updateUserMessage(User user);


}
