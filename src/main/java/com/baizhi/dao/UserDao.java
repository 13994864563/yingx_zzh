package com.baizhi.dao;

import com.baizhi.entity.City;
import com.baizhi.entity.Mc;
import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {

    //分页显示的记录数
    List<User> queryAll(@Param("begin") Integer begin,@Param("rows") Integer rows);
    //总条数
    Integer queryCount();
    //修改
    void update(User user);
    //查一个
    User queryOne(String id);


    //添加
    void addAliyun(User user);
    //查所有
    List<User> selectAllPoiexport();


    //查询用户在几月注册的数量
    List<Mc> queryMouth(@Param("sex") String sex,@Param("mouth") Integer mouth);
    //查询在地区用户创建的数量
    List<City> queryCity(String sex);
}
