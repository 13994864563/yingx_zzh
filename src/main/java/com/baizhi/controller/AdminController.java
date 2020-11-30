package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import com.baizhi.util.ImageCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    private static final Logger log = LoggerFactory.getLogger(AdminService.class);

    //安全退出
    @RequestMapping("logout")
    public String logout(HttpServletRequest request){

        HttpSession session = request.getSession();
        session.removeAttribute("admin1");
        session.removeAttribute("message");
        return "redirect:/login/login.jsp";
    }

    //验证码
    @RequestMapping("getImageCode")
    public String getImageCode(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //获得随机字符
        String code = ImageCodeUtil.getSecurityCode();
        System.out.println(code);
        //生成图片
        BufferedImage bufferedImage = ImageCodeUtil.createImage(code);

        ServletOutputStream out = response.getOutputStream();
        //使用响应输出流  把bufferedImage 输出到client
        ImageIO.write(bufferedImage,"gif",out);
        HttpSession session = request.getSession();
        session.setAttribute("ServerCode",code);
        return null;
    }


    @RequestMapping("login")
    @ResponseBody
    public HashMap<String, Object> login(Admin admin,String enCode){
        HashMap<String, Object> map = adminService.queryByUsername(admin, enCode);
        return map;
    }

}
