package com.baizhi.controller;

import com.alibaba.druid.util.StringUtils;
import com.baizhi.entity.Video;
import com.baizhi.service.VideoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("video")
public class VideoController {

    @Resource
    private VideoService videoService;

    //分页查询所有
    @RequestMapping("queryByPageVideo")
    @ResponseBody
    public Map<String, Object> queryByPageVideo(Integer page, Integer rows) {
        //分页数据
        List<Video> videos = videoService.queryByPageVideo(page, rows);
        HashMap<String, Object> map = new HashMap<>();
        //总条数
        Integer queryCount = videoService.queryCount();
        //总页数
        Integer total = queryCount % rows == 0 ? queryCount / rows : queryCount / rows + 1;

        map.put("page", page);   //当前页
        map.put("total", total); //总页数
        map.put("records", queryCount);     //总条数
        map.put("rows", videos);     //分页数据

        return map;
    }

    @RequestMapping("edit")
    @ResponseBody
    public String edit(Video video,String oper){
        String id=null;
        if(StringUtils.equals("add",oper)){
            id = videoService.addVideo(video);
        }
        if(StringUtils.equals("del",oper)){
            videoService.deleteAliyunAndDB(video);
        }
        if(StringUtils.equals("edit",oper)){
            id= videoService.update(video);
        }
        return id;
    }

    //上传到aliyun和数据库
    @RequestMapping("headUpload")
    public void headUpload(MultipartFile videoPath,String id){
        videoService.headUploadAliyun(videoPath,id);
    }

    @RequestMapping("updateUpload")
    public void updateUpload(MultipartFile videoPath,String id){
        videoService.updateAliyunAndDB(videoPath,id);
    }


}
