package com.baizhi.service;

import com.baizhi.entity.City;
import com.baizhi.entity.Mc;
import com.baizhi.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    List<User> queryAll(Integer page,Integer rows);

    Integer queryCount();
    //修改
    void update(User user);
    //查一个
    User queryOne(String id);

    //添加
    String add(User user);
    //上传aliyun
    void uploadAliyun(MultipartFile videoPath, String id);

    //查所有
    List<User> queryAllPoiexport();
    //查询用户在几月注册的数量
    List<Mc> queryMouth(String sex,Integer mouth);
    //查询在地区用户创建的数量
    List<City> queryCity(String sex);
}
