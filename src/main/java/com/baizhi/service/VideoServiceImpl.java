package com.baizhi.service;

import com.baizhi.annotation.AddLog;
import com.baizhi.annotation.DelCache;
import com.baizhi.dao.VideoMapper;
import com.baizhi.entity.Video;
import com.baizhi.entity.VideoExample;
import com.baizhi.po.VideoPO;
import com.baizhi.util.AliyunOSSUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("videoService")
@Transactional
public class VideoServiceImpl implements VideoService {


    @Resource
    private VideoMapper videoMapper;

    @Resource
    private HttpServletRequest request;

    //分页查询所有
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Video> queryByPageVideo(Integer page, Integer rows) {
        //条件
        VideoExample example = new VideoExample();
        //设置分页参数
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        //分页查询
        List<Video> videos = videoMapper.selectByExampleAndRowBounds(example, rowBounds);

        return videos;
    }

    @Override
    @Transactional(propagation =Propagation.SUPPORTS )
    public Integer queryCount() {
        Video video = new Video();
        return videoMapper.selectCount(video);
    }

    //添加
    @AddLog("添加返回ID")
    @Override
    @DelCache
    public String addVideo(Video video) {

        String uuId = UUID.randomUUID().toString();
        video.setId(uuId);
        video.setUploadTime(new Date());
        video.setStatus(1);
        video.setLikeCount(0);
        video.setPlayCount(0);

        videoMapper.insertSelective(video);
        return uuId;
    }
    //上传到数据库
    public void headUpload(MultipartFile videoPath,String id){
        //文件上传
        //根据相对路径获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("upload/photo");

        //判断上传文件夹是否存在
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdir();
        }
        //获取文件名
        String fileName = videoPath.getOriginalFilename();
        //给文件拼接时间戳
        String newName = new Date().getTime()+"-"+fileName;

        //文件上传
        try {
            videoPath.transferTo(new File(realPath,newName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //修改数据
        VideoExample example = new VideoExample();
        example.createCriteria().andIdEqualTo(id);

        Video video = new Video();
        video.setVideoPath(newName);

        videoMapper.updateByExampleSelective(video,example);
    }
    @AddLog("上传到aliyun和数据库")
    //上传到aliyun和数据库
    @DelCache
    public void headUploadAliyun(MultipartFile videoPath,String id){

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

        //5.数据修改
        Video video = new Video();
        video.setId(id);

        video.setVideoPath("https://java-2005.oss-cn-beijing.aliyuncs.com/"+videoName);
        video.setCoverPath("https://java-2005.oss-cn-beijing.aliyuncs.com/"+coverName);
        videoMapper.updateByPrimaryKeySelective(video);
    }

    //删除aliyun和数据库
    @AddLog("删除aliyun和数据库")
    @Override
    @DelCache
    public void deleteAliyunAndDB(Video video) {

        VideoExample videoExample = new VideoExample();
        videoExample.createCriteria().andIdEqualTo(video.getId());
        Video videos = videoMapper.selectOneByExample(videoExample);

        String videoPath = videos.getVideoPath().replace("https://java-2005.oss-cn-beijing.aliyuncs.com/", "");
        String coverPath = videos.getCoverPath().replace("https://java-2005.oss-cn-beijing.aliyuncs.com/", "");

        //删除视频
        AliyunOSSUtil.deleteFile("java-2005",videoPath);
        //删除封面
        AliyunOSSUtil.deleteFile("java-2005",coverPath);

        //删除数据库
        videoMapper.deleteByPrimaryKey(video.getId());
    }

    @AddLog("修改返回ID")
    @DelCache
    public String update(Video video){
        //查一个
        Video video1 = videoMapper.selectByPrimaryKey(video);
        //返回id
        String id = video1.getId();

        Video videos = new Video();
        videos.setTitle(video.getTitle());
        videos.setBrief(video.getBrief());
        //修改
        VideoExample example = new VideoExample();
        example.createCriteria().andIdEqualTo(video1.getId());
        videoMapper.updateByExampleSelective(videos,example);
        return id;

    }

    @Override
    @AddLog("修改aliyun和数据库")
    @DelCache
    public void updateAliyunAndDB(MultipartFile videoPath,String id) {

        if(videoPath.getSize()!=0){

            Video videos = videoMapper.selectByPrimaryKey(id);

            String videoPaths = videos.getVideoPath().replace("https://java-2005.oss-cn-beijing.aliyuncs.com/", "");
            String coverPath = videos.getCoverPath().replace("https://java-2005.oss-cn-beijing.aliyuncs.com/", "");

            //删除视频
            AliyunOSSUtil.deleteFile("java-2005",videoPaths);
            //删除封面
            AliyunOSSUtil.deleteFile("java-2005",coverPath);

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

            //5.数据修改
            Video video = new Video();
            video.setId(id);

            video.setVideoPath("https://java-2005.oss-cn-beijing.aliyuncs.com/"+videoName);
            video.setCoverPath("https://java-2005.oss-cn-beijing.aliyuncs.com/"+coverName);
            videoMapper.updateByPrimaryKeySelective(video);


        }
    }

    @Override
    public List<VideoPO> queryByReleaseTime() {
        List<VideoPO> videos = videoMapper.queryByReleaseTime();
        for (VideoPO video : videos) {
            //获取视频id
            String id = video.getId();

            //根据视频id redis  查询点赞数
            video.setLikeCount(8);

        }
        return videos;
    }

    @Override
    public List<VideoPO> queryByLikeVideoName(String content) {
        return videoMapper.queryByLikeVideoName(content);
    }
}
