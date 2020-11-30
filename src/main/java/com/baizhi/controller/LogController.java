package com.baizhi.controller;


import com.alibaba.druid.util.StringUtils;
import com.baizhi.entity.Log;
import com.baizhi.service.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("log")
public class LogController {

    @Resource
    private LogService logService;

    @RequestMapping("queryByPage")
    @ResponseBody
    public HashMap<String, Object>  queryByPage(Integer page,Integer rows){
        HashMap<String, Object> map = new HashMap<>();
        //分页查的数据
        List<Log> logs = logService.queryByPage(page, rows);
        //总条数
        Integer queryCount = logService.queryCount();
        //总页数
        Integer total=queryCount%rows==0?queryCount/rows:queryCount/rows+1;

        map.put("page",page);
        map.put("total",total);
        map.put("records",queryCount);
        map.put("rows",logs);
        return map;
    }
    @RequestMapping("edit")
    @ResponseBody
    public void edit(Log log,String oper){
        if(StringUtils.equals("del",oper)){
            logService.delete(log);
        }
    }
}
