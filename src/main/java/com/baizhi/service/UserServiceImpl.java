package com.baizhi.service;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.Banner;
import com.baizhi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public HashMap<String, Object> selectByPage(Integer page, Integer rows) {
        HashMap<String,Object> map=new HashMap<>();
        Integer start=(page-1)*rows;
        List<User> list=userDao.findByPage(start,rows);
        Integer totalCount=userDao.totalCount();
        Integer pageCount=0;
        if(totalCount%rows==0){
            pageCount=totalCount/rows;
        }else{
            pageCount=totalCount/rows+1;
        }
        map.put("total",pageCount);     //总页数
        map.put("records",totalCount);  //总条数
        map.put("rows",list);           //列表
        map.put("page",page);           //当前页
        return map;
    }

    @Override
    public void insert(User user) {
        String uuid= UUID.randomUUID().toString().replace("-","");
        user.setId(uuid);
        userDao.insert(user);
    }

    @Override
    public void delete(String id) {
        userDao.delete(id);
    }

    @Override
    public void upload(MultipartFile photo, String id, HttpServletRequest request) {
        //根据相对路径获取绝对路径
        String realPath=request.getServletContext().getRealPath("/upload/photo");
        File file = new File(realPath);
        //创建文件
        if(!file.exists()){
            file.mkdir();
        }
        //获取文件名
        String filename=photo.getOriginalFilename();
        //防止图片发生覆盖，重新给图片命名
        String newName=new Date().getTime()+"-"+filename;
        //文件上传
        try {
            photo.transferTo(new File(realPath, newName));
            //修改轮播图得信息
            User user= new User();
            user.setId(id);
            user.setPhoto(newName);
        }catch(IOException e){
            e.printStackTrace();
        }

    }

}
