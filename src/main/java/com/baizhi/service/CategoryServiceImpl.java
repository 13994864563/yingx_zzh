package com.baizhi.service;

import com.baizhi.annotation.AddLog;
import com.baizhi.annotation.DelCache;
import com.baizhi.dao.CategoryDao;
import com.baizhi.entity.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service("categoryService")
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryDao categoryDao;


    //分页查询所有的一级类别
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Category> queryAllOneCate(Integer page, Integer rows) {
        Integer begin = (page-1)*rows;
        List<Category> categories = categoryDao.queryAllOneCate(begin, rows,1);
        return categories;
    }

    //查询总条数
    @Override
    public Integer queryCount() {
        return categoryDao.queryCount();
    }
    //查询二级类别
    @Override
    public List<Category> queryAllTwoCate(Integer page, Integer rows, String id) {
        Integer begin = (page-1)*rows;
        List<Category> categories = categoryDao.queryAllTwoCate(begin, rows,2,id);
        return categories;
    }

    //查询所有二级类别的数量
    @Override
    public Integer queryCountTwo(String id) {
        return categoryDao.queryCountTwo(id);
    }
    //添加一级类别

    @DelCache
    @AddLog("添加一级类别或二级类别")
    @Override
    public void addOneCate(Category category) {
        if(category.getParentId()!=null){
            category.setId(UUID.randomUUID().toString());
            category.setLevels(2);
            categoryDao.addTwoCate(category);
        }else {
            category.setId(UUID.randomUUID().toString());
            category.setParentId(null);
            category.setLevels(1);
            categoryDao.addOneCate(category);
        }
    }
    //查所有一级
    @Override
    public List<Category> queryAllOne() {
        List<Category> categories = categoryDao.queryAllOne(1);
        return categories;
    }

    //修改一级或二级类别
    @DelCache
    @AddLog("修改一级或二级类别")
    @Override
    public void updateOneCate(Category category) {
        categoryDao.updateOneCate(category);
    }

    //删除
    @DelCache
    @AddLog("删除类别")
    @Override
    public String delete(String id) {
        Category category = categoryDao.queryById(id);
        if(category.getLevels()==1){
            Integer integer = categoryDao.queryCountTwo(id);
            if(integer==0){
                categoryDao.delete(id);
                return "删除成功!!!";
            }
            return "该一级类别下有二级类别无法删除!!!";
        }else{
            if(true){
                categoryDao.delete(id);
                return "删除成功!!!";
            }else{
                return "该类别下有视频无法删除!!!";
            }
        }
    }

    @Override
    public List<Category> queryAllCategory() {
        List<Category> categorys = categoryDao.queryAllCategory();
        return categorys;
    }

//    @Override
//    public void addTwoCate(Category category) {
//        category.setId(UUID.randomUUID().toString());
//        category.setLevels(2);
//        categoryDao.addTwoCate(category);
//    }
}
