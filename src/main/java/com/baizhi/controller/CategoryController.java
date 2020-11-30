package com.baizhi.controller;


import com.baizhi.entity.Category;
import com.baizhi.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;



    //分页查询所有的一级类别
    @RequestMapping("queryAllOneCate")
    @ResponseBody
    public Map<String,Object> queryAllOneCate(Integer page,Integer rows){
        HashMap<String, Object> map = new HashMap<>();

        //分页查询所有的一级类别
        List<Category> categories = categoryService.queryAllOneCate(page, rows);
        //总条数
        Integer count = categoryService.queryCount();
        //总页数
        Integer total= count%rows==0?count/rows:count/rows+1;

        map.put("page",page);
        map.put("total",total);
        map.put("records",count);
        map.put("rows",categories);
        return map;
    }

    //分页查询二级类别下的二级类别
    @RequestMapping("queryAllTwoCate")
    @ResponseBody
    public Map<String,Object> queryAllTwoCate(Integer page,Integer rows,String id){
        HashMap<String, Object> map = new HashMap<>();

        //分页查询所有的一级类别
        List<Category> categories = categoryService.queryAllTwoCate(page, rows,id);
        //总条数
        Integer count = categoryService.queryCountTwo(id);
        //总页数
        Integer total= count%rows==0?count/rows:count/rows+1;

        map.put("page",page);
        map.put("total",total);
        map.put("records",count);
        map.put("rows",categories);
        return map;
    }

    //添加一级类别
    @RequestMapping("edit")
    @ResponseBody
    public Map<String,Object> edit(Category category,String oper){
        HashMap<String, Object> map = new HashMap<>();
        if(oper.equals("del")){
            String message = categoryService.delete(category.getId());
            map.put("message",message);
            map.put("status",200);
        }
        if(oper.equals("edit")){
            categoryService.updateOneCate(category);
        }
        if(oper.equals("add")){
            categoryService.addOneCate(category);
        }
        return map;
    }
    
    //查询所有一级
    @RequestMapping("queryAllOne")
    public void queryAllOne(HttpServletResponse response){
        //查询所有的一级类别
        List<Category> categories = categoryService.queryAllOne();
        //html <select><option value=1>奥斯</option>...</select>
        StringBuilder builder = new StringBuilder();
        builder.append("<select>");
        //遍历  构建option标签
        categories.forEach(category -> {
            builder.append("<option value="+category.getId()+">"+category.getCateName()+"</option>");
        });
        builder.append("</select>");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        try {
            response.getWriter().print(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
