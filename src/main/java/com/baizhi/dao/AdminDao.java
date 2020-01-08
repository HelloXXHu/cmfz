package com.baizhi.dao;

import com.baizhi.entity.Admin;
import org.apache.ibatis.annotations.Param;

public interface AdminDao {
    public Admin queryOne(@Param("username") String username);

    public Admin selectOne(@Param("username")String username,@Param("password")String password);
}
