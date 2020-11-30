package com.baizhi.service;

import com.baizhi.entity.Video;
import com.baizhi.po.VideoPO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {
    //分页查所有
    List<Video> queryByPageVideo(Integer page,Integer rows);
    //总条数
    Integer queryCount();

    //添加
    String addVideo(Video video);

    void headUpload(MultipartFile videoPath, String id);
    //上传aliyun
    void headUploadAliyun(MultipartFile videoPath,String id);
    //删除
    void deleteAliyunAndDB(Video video);

    String update(Video video);
    //修改
    void updateAliyunAndDB(MultipartFile videoPath,String id);
    //查所有
    List<VideoPO> queryByReleaseTime();
    //搜索模糊查询
    List<VideoPO> queryByLikeVideoName(String content);
}
