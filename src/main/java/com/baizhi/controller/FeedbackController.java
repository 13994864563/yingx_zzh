package com.baizhi.controller;

import com.baizhi.entity.Feedback;
import com.baizhi.service.FeedbackService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("feedback")
public class FeedbackController {

    @Resource
    private FeedbackService feedbackService;

    @RequestMapping("queryByPage")
    @ResponseBody
    public HashMap<String, Object> queryByPage(Integer page, Integer rows){
        HashMap<String, Object> map = new HashMap<>();
        //分页查的数据
        List<Feedback> feedbacks = feedbackService.queryByPage(page, rows);
        //总条数
        Integer queryCount = feedbackService.queryCount();
        //总页数
        Integer total=queryCount%rows==0?queryCount/rows:queryCount/rows+1;

        map.put("page",page);
        map.put("total",total);
        map.put("records",queryCount);
        map.put("rows",feedbacks);
        return map;
    }
}
