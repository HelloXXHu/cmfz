package com.baizhi.service;

import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
import com.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    AlbumDao albumDao;

    @Override
    public void insert(Album album) {
        album.setId(UUIDUtil.getUUID());
        albumDao.insert(album);
    }

    @Override
    public void delete(String id) {
        albumDao.delete(id);
    }

    @Override
    public void update(Album album) {
        albumDao.update(album);
    }

    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public HashMap<String, Object> selectByPage(Integer page, Integer rows) {
        HashMap<String,Object> map=new HashMap<>();
        //获取数据库中的起始条数
        Integer start=(page-1)*rows;
        //查询轮播图的列表
        List<Album> list=albumDao.findByPage(start,rows);
        //总条数
        Integer totalCount=albumDao.totalCount();
        //总页数
        Integer pageCount=0;
        if(totalCount%rows==0){
            pageCount=totalCount/rows;
        }else{
            pageCount=totalCount/rows+1;
        }
        map.put("total",pageCount);     //总页数
        map.put("records",totalCount);  //总条数
        map.put("rows",list);           //轮播图列表
        map.put("page",page);           //当前页
        return map;
    }

    @Override
    public void bannerUpload(MultipartFile src, String id, HttpServletRequest request) {
        //根据相对路径获取绝对路径
        String realPath = request.getServletContext().getRealPath("/upload/photo");
        //创建文件
        File file=new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        //获取文件名
        String fileName=src.getOriginalFilename();
        //防止图片发生覆盖，给图片另起一个名
        String newName=new Date().getTime()+"-"+fileName;
        //文件上传
        try{
            src.transferTo(new File(realPath,newName));
            Album album=new Album();
            album.setId(id);
            album.setSrc(newName);
            albumDao.update(album);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
