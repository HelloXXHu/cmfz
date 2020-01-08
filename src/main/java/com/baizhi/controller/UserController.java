package com.baizhi.controller;

import com.alibaba.excel.EasyExcel;
import com.baizhi.dao.AdminDao;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import com.baizhi.entity.UserDto;
import com.baizhi.entity.UserListener;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;
    @Autowired
    AdminDao adminDao;

    @RequestMapping("/selectByPage")
    public HashMap<String,Object> selectByPage(Integer page,Integer rows){
        HashMap<String, Object> map = userService.selectByPage(page, rows);
        return map;
    }

    //导出
    @RequestMapping("/imageUpload")
    public void imageUpload(){
        List<User> users = userDao.queryAll();
        String url="D:\\办公\\java第三阶段\\后期项目\\示例\\"+new Date().getTime()+".xls";
        EasyExcel.write(url, User.class)
                .sheet("用户")
                .doWrite(users);
    }

    //导入
    @RequestMapping("/leadExcel")
    public void leadExcel(){
        String url = "D:\\办公\\java第三阶段\\后期项目\\示例\\1578365517628.xls";
        EasyExcel.read(url,User.class,new UserListener()).sheet().doRead();
    }

    @RequestMapping("/edit")
    public String  edit(User user, String id, String oper){
        if(oper.equals("add")){
            userService.insert(user);
        }
        if(oper.equals("del")){
            userService.delete(id);
        }
        return user.getId();
    }

    //上传
    @RequestMapping("/upload")
    public void upload(MultipartFile photo, String id, HttpServletRequest request){
        userService.upload(photo,id,request);
    }

    //柱状图
    @RequestMapping("/showUserTime")
    public Map showUserTime(){
        HashMap hashMap = new HashMap();
        ArrayList manList = new ArrayList();
        manList.add(userDao.queryUserByTime("0",1));
        manList.add(userDao.queryUserByTime("0",7));
        manList.add(userDao.queryUserByTime("0",30));
        manList.add(userDao.queryUserByTime("0",365));
        ArrayList womenList = new ArrayList();
        womenList.add(userDao.queryUserByTime("1",1));
        womenList.add(userDao.queryUserByTime("1",7));
        womenList.add(userDao.queryUserByTime("1",30));
        womenList.add(userDao.queryUserByTime("1",365));
        hashMap.put("man",manList);
        hashMap.put("women",womenList);
        return hashMap;
    }

    //地图
    @RequestMapping("/showUserLocation")
    public Map showUserLocation(){
        HashMap hashMap = new HashMap();

        List<UserDto> manDto = userDao.selectByLocation("0");
        System.out.println(manDto);
        List<UserDto> womenDto = userDao.selectByLocation("1");
        System.out.println(womenDto);
        hashMap.put("man",manDto);
        hashMap.put("women",womenDto);


        return hashMap;
    }

}
