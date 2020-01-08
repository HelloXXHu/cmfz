package com.baizhi.service;

import com.baizhi.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface UserService {

    public HashMap<String,Object> selectByPage(Integer page,Integer rows);

    public void insert(User user);

    public void delete(String id);

    //上传
    public void upload(MultipartFile photo, String id, HttpServletRequest request);

}
