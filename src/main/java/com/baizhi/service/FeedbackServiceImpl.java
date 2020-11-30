package com.baizhi.service;

import com.baizhi.dao.FeedbackMapper;
import com.baizhi.entity.Feedback;
import com.baizhi.entity.FeedbackExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("feedbackService")
@Transactional
public class FeedbackServiceImpl implements FeedbackService{

    @Resource
    private FeedbackMapper feedbackMapper;

    @Override
    public List<Feedback> queryByPage(Integer page, Integer rows) {
        //条件
        FeedbackExample example = new FeedbackExample();
        //设置分页参数
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        //分页数据
        List<Feedback> feedbacks = feedbackMapper.selectByExampleAndRowBounds(example, rowBounds);

        return feedbacks;
    }

    @Override
    public Integer queryCount() {
        Feedback feedback = new Feedback();
        return feedbackMapper.selectCount(feedback);
    }
}
