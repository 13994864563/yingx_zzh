package com.baizhi.service;

import com.baizhi.annotation.DelCache;
import com.baizhi.dao.LogMapper;
import com.baizhi.entity.Log;
import com.baizhi.entity.LogExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("logService")
@Transactional
public class LogServiceImpl implements LogService {

    @Resource
    private LogMapper logMapper;

    @Override
    public void add(Log log) {
        logMapper.insert(log);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Log> queryByPage(Integer page, Integer rows) {
        //条件
        LogExample example = new LogExample();
        //设置分页参数
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        //分页数据
        List<Log> logs = logMapper.selectByExampleAndRowBounds(example, rowBounds);

        return logs;
    }

    @Override
    public Integer queryCount() {
        Log log = new Log();
        return logMapper.selectCount(log);

    }

    @Override
    @DelCache
    public void delete(Log log) {
        logMapper.deleteByPrimaryKey(log.getId());
    }
}
