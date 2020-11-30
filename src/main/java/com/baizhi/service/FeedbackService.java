package com.baizhi.service;

import com.baizhi.entity.Feedback;

import java.util.List;

public interface FeedbackService {
    //分页查数据
    List<Feedback> queryByPage(Integer page, Integer rows);
    //总条数
    Integer queryCount();
}
