package com.baizhi.dao;

import com.baizhi.entity.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryDao {
    //查询所有一级类别
    List<Category> queryAllOneCate(@Param("begin") Integer begin,@Param("end") Integer end,@Param("levels") Integer levels);
    //查询总条数
    Integer queryCount();
    //查询所有的一级类别下二级类别
    List<Category> queryAllTwoCate(@Param("begin") Integer begin,@Param("end") Integer end,@Param("levels") Integer levels,@Param("id") String id);
    //查询二级类别的总条数
    Integer queryCountTwo(String id);

    //添加一级类别
    void addOneCate(Category category);
    //查询所有的一级
    List<Category> queryAllOne(Integer levels);
    //添加二级类别
    void addTwoCate(Category category);

    //修改一级类别
    void updateOneCate(Category category);
    //删除
    void delete(String id);
    //根据id查一个
    Category queryById(String id);

    //查询一级
    List<Category> queryAllCategory();
}
