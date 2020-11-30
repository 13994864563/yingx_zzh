package com.baizhi.service;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Service("adminService")
@Transactional
public class AdminServiceImpl implements AdminService {
    
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private HttpSession session;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public HashMap<String, Object> queryByUsername(Admin admin,String encode) {

        HashMap<String, Object> map = new HashMap<>();
        String serverCode = (String) session.getAttribute("ServerCode");

        if(serverCode.equals(encode)){
            Admin admin1 = adminDao.queryByUsername(admin.getUsername());
            if(admin1!=null){
                if(admin.getUsername().equals(admin1.getUsername())){
                    session.setAttribute("admin1",admin1);
                    map.put("message","登录成功");
                    map.put("status","200");
                }else{
                    map.put("message","密码不正确!!!");
                    map.put("status","201");
                }
            }else{
                map.put("message","此用户不存在!!!");
                map.put("status","201");
            }
        }else{
            map.put("message","验证码错误!!!");
            map.put("status","201");
        }
        return map;
    }
}
