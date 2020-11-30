package com.baizhi.dao;

import com.baizhi.entity.Video;
import com.baizhi.po.VideoPO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface VideoMapper extends Mapper<Video> {
    //查所有视频
    List<VideoPO> queryByReleaseTime();
    //搜索模糊查询
    List<VideoPO> queryByLikeVideoName(String content);

}