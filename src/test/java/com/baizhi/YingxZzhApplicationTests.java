package com.baizhi;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Category;
import com.baizhi.entity.User;
import com.baizhi.entity.Video;
import com.baizhi.service.CategoryService;
import com.baizhi.service.UserService;
import com.baizhi.service.VideoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@SpringBootTest
class YingxZzhApplicationTests {

    @Resource
    private AdminDao adminDao;

    @Resource
    private UserService userService;

    @Resource
    private CategoryService categoryService;
    @Test
    void contextLoads() {
        Admin admin = adminDao.queryByUsername("1");
        System.out.println(admin);

    }

   /* @Test
    public void test(){
            System.out.println(users);

        List<User> users = userService.queryAll(1, 3);

    }*/
   /* @Test
    public void test1(){
        User user = new User();
        user.setId("1");
        user.setStatus("12");
        userService.update(user);

    }*/

   @Test
    public void test2(){
       List<Category> categories = categoryService.queryAllOneCate(1, 2);

       System.out.println(categories);
   }
   @Test
    public void Test3(){
       Integer integer = categoryService.queryCountTwo("1");
       System.out.println(integer);
   }

    @Test
    public void Test4(){
        List<Category> categories = categoryService.queryAllTwoCate(1, 2, "1");
        System.out.println(categories);
    }
    @Test
    public void Test(){
        Category category = new Category();
        category.setCateName("迪迦迪迦");
        category.setId("1");

        categoryService.updateOneCate(category);
    }
    //创建Bucket列表
    @Test
    public void ossTest(){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4G4eCDv8w9Xs4ZR61FQz";
        String accessKeySecret = "AhdmzcSqfSEmHzyJtpHjXAO0qh68Su";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建CreateBucketRequest对象。
        CreateBucketRequest createBucketRequest = new CreateBucketRequest("java-205");

        // 创建存储空间。
        ossClient.createBucket(createBucketRequest);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
    //上传文件
    @Test
    public void addFile(){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4G4eCDv8w9Xs4ZR61FQz";
        String accessKeySecret = "AhdmzcSqfSEmHzyJtpHjXAO0qh68Su";

        String bucketName = "java-2005";   //存储空间名  java-2005
        String objectName = "迪丽热巴.jpg";            //保存文件名
        String localFile = "D:\\第一阶段\\图片\\1.jpg";             //本地文件位置

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, new File(localFile));

        // 上传文件。
        ossClient.putObject(putObjectRequest);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test
    void addVodic(){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = "LTAI4G4eCDv8w9Xs4ZR61FQz";
        String accessKeySecret = "AhdmzcSqfSEmHzyJtpHjXAO0qh68Su";
        String bucketName = "java-2005";   //存储空间名  java-2005
        String objectName = "video/haokan.mp4";            //保存文件名
        String localFile = "D:\\video\\好看.mp4";             //本地文件位置

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(localFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ossClient.putObject(bucketName, objectName, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Resource
    private VideoService videoService;

    @Test
    void query(){
        List<Video> videos = videoService.queryByPageVideo(1, 2);
        System.out.println(videos);


    }

    @Test
    void e(){
        List<Category> categories = categoryService.queryAllCategory();
        for (Category category : categories) {
            System.out.println(category);
        }
    }

    @Test
    void a(){
        User user = new User();
        user.setNickName("11");
        userService.add(user);
    }
    @Test
    void aaa(){
        int a = 1;
        int b = 2;
        System.out.println(a+b);
    }
}
