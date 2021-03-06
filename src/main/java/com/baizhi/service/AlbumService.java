package com.baizhi.service;

import com.baizhi.entity.Album;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public interface AlbumService {

    public void insert(Album album);

    public void delete(String id);

    public void update(Album album);

    public HashMap<String,Object> selectByPage(Integer page,Integer rows);

    //上传
    public void bannerUpload(MultipartFile src, String id, HttpServletRequest request);

}
