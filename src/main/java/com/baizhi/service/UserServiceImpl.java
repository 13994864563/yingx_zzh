package com.baizhi.service;

import com.baizhi.annotation.AddLog;
import com.baizhi.annotation.DelCache;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.City;
import com.baizhi.entity.Mc;
import com.baizhi.entity.User;
import com.baizhi.util.AliyunOSSUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {


    @Resource
    private UserDao userDao;

    //分页查询
    @Override                   //当前页          当前页显示的总记录数
    public List<User> queryAll(Integer page, Integer rows) {
        Integer begin = (page-1)*rows;
        return userDao.queryAll(begin,rows);
    }

    //查询总条数
    @Override
    public Integer queryCount() {
        return userDao.queryCount();
    }

    //修改
    @Override
    @AddLog("修改状态")
    @DelCache
    public void update(User user) {
        User user1 = userDao.queryOne(user.getId());
        if(user1.getStatus().equals("0")){
            user1.setStatus("1");
            userDao.update(user1);
        }else{
            user1.setStatus("0");
            userDao.update(user1);
        }
    }

    @Override
    public User queryOne(String id) {
        return userDao.queryOne(id);
    }

    @Override
    @DelCache
    public String add(User user) {
        System.out.println(user);
        String uuId=UUID.randomUUID().toString();
        User user1 = new User();
        user1.setId(uuId);
        user1.setStatus("1");
        user1.setCreateDate(new Date());

        userDao.addAliyun(user1);
        return uuId;
    }

    @Override
    @DelCache
    public void uploadAliyun(MultipartFile videoPath, String id) {
        //获取文件名
        String filename = videoPath.getOriginalFilename();
        //拼接时间戳
        String newName=new Date().getTime()+"-"+filename;

        //拼接视频文件夹
        String videoName="video/"+newName;

        /*
            上传视频至阿里云
            参数:
         *   videoPath: MultipartFile类型的文件
         *   bucketName:存储空间名
         *   objectName:文件名
         */
        AliyunOSSUtil.uploadFileByte(videoPath,"java-2005",videoName);

        //截取文件名
        String[] split = newName.split("\\.");
        //拼接图片名
        String coverName="cover/"+split[0]+".jpg";

        /*
         * 截取视频第一帧
         *   参数：
         *    bucketName:存储空间名
         *    videoName:视频名  文件夹
         *    coverName:封面名
         * */
        AliyunOSSUtil.interceptVideoCover("java-2005",videoName,coverName);

    }

    @Override
    public List<User> queryAllPoiexport() {
        List<User> users = userDao.selectAllPoiexport();
        return users;
    }

    @Override
    public List<Mc> queryMouth(String sex, Integer mouth) {
        List<Mc> mcs = userDao.queryMouth(sex, mouth);
        return mcs;
    }

    @Override
    public List<City> queryCity(String sex) {
        return userDao.queryCity(sex);
    }

}
