package com.baizhi.app;

import com.baizhi.common.CommonResult;
import com.baizhi.entity.Category;
import com.baizhi.po.VideoPO;
import com.baizhi.service.CategoryService;
import com.baizhi.service.VideoService;
import com.baizhi.util.AliyunUtil;
import com.baizhi.util.ImageCodeUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

//前台接口

@RestController     //全部返回的是json串
@RequestMapping("app")
public class AppController {

    @Resource
    private VideoService videoService;
    @Resource
    private CategoryService categoryService;

    @RequestMapping("getPhoneCode")
    public Object getPhoneCode(String phone){
        System.out.println(phone);
        //生成随机验证码
        String randomCode = ImageCodeUtil.getSecurityCode();
        System.out.println("手机验证码："+randomCode);

        String message =null;
        try {
            //发送手机验证码
            message = AliyunUtil.sendPhoneMsg(phone, randomCode);
            return new CommonResult().success(message, phone);
        } catch (Exception e) {
            return new CommonResult().failed(message);
        }
    }

    //查所有视频
    @RequestMapping("queryByReleaseTime")
    public CommonResult queryByReleaseTime(){

        try {
            List<VideoPO> videoPOS = videoService.queryByReleaseTime();
            return new CommonResult().success(videoPOS);
        } catch (Exception e) {
            return new CommonResult().failed();
        }
    }

    //查类别
    @RequestMapping("queryAllCategory")
    public CommonResult queryAllCategory(){
        try {
            List<Category> categoryPOS = categoryService.queryAllCategory();
            return new CommonResult().success(categoryPOS);
        } catch (Exception e) {
            return new CommonResult().failed();
        }
    }

    //搜索模糊查
    @RequestMapping("queryByLikeVideoName")
    public CommonResult queryByLikeVideoName(String content){
        try {
            List<VideoPO> videoPOS = videoService.queryByLikeVideoName(content);
            return new CommonResult().success(videoPOS);
        } catch (Exception e) {
            return new CommonResult().failed();
        }
    }
}
