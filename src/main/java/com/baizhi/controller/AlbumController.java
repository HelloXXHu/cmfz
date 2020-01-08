package com.baizhi.controller;


import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    AlbumService albumService;

    @RequestMapping("/selectByPage")
    public HashMap<String,Object> selectByPage(Integer page,Integer rows){
        HashMap<String,Object> map=albumService.selectByPage(page,rows);
        return map;
    }

    @RequestMapping("/edit")
    public String edit(Album album,String id,String oper){
        if(oper.equals("add")){
            albumService.insert(album);
        }
        if(oper.equals("del")){
            albumService.delete(id);
        }
        if(oper.equals("edit")){
            albumService.update(album);
        }
        return album.getId();
    }

    @RequestMapping("/upload")
    public void upload(MultipartFile src, String id, HttpServletRequest request){
        albumService.bannerUpload(src,id,request);
    }

}
