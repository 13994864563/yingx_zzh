package com.baizhi.service;

import com.baizhi.entity.Category;

import java.util.List;

public interface CategoryService {
    //分页查询所有一级类别
    List<Category> queryAllOneCate(Integer page,Integer rows);
    //查询总条数
    Integer queryCount();
    //查询所有的一级类别下二级类别
    List<Category> queryAllTwoCate(Integer page, Integer rows, String id);
    //查询二级类别的总条数
    Integer queryCountTwo(String id);


    //添加一级类别
    void addOneCate(Category category);
    //查询所有的一级
    List<Category> queryAllOne();
//    //添加二级类别
//    void addTwoCate(Category category);

    //修改一级类别
    void updateOneCate(Category category);
    //删除
    String delete(String id);


    //查询
    List<Category> queryAllCategory();
}
