package com.baizhi.service;

import com.baizhi.entity.Log;

import java.util.List;

public interface LogService {
    //添加log
    void add(Log log);
    //分页查数据
    List<Log> queryByPage(Integer page,Integer rows);
    //总条数
    Integer queryCount();
    //删除
    void delete(Log log);
}
