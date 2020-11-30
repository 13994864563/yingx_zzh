package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baizhi.entity.City;
import com.baizhi.entity.Mc;
import com.baizhi.entity.Sc;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.util.AliyunUtil;
import com.baizhi.util.ImageCodeUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("queryAll")
    @ResponseBody
    public Map<String,Object> queryAll(Integer page, Integer rows){
        HashMap<String, Object> map = new HashMap<>();

        //分页查询的结果
        List<User> users = userService.queryAll(page, rows);
        //总条数
        Integer queryCount = userService.queryCount();
        //总页数
        Integer totalPage= queryCount%rows==0?queryCount/rows:queryCount/rows+1;

        map.put("page",page);   //当前页
        map.put("total",totalPage);  //总页数
        map.put("records",queryCount); //总条数
        map.put("rows",users); //当前页显示的记录数

        return map;
    }

    @RequestMapping("edit")
    @ResponseBody
    public String edit(User user,String oper){
        String id=null;
        if(oper.equals("edit")){
            userService.update(user);
        }
        if (oper.equals("add")){
           id = userService.add(user);
        }
        return id;
    }

    //发送验证码
    @RequestMapping("phoneCode")
    @ResponseBody
    public Map<String,Object> phoneCode(String phone){

        String phoneMsg = null;
        HashMap<String, Object> map = null;
        try {
            //随机获取验证码
            String code = ImageCodeUtil.getSecurityCode();
            //调工具类发送验证码
            phoneMsg = AliyunUtil.sendPhoneMsg(phone, code);

            map = new HashMap<>();

            map.put("message",phoneMsg);
            map.put("status",200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("message",phoneMsg);
            map.put("status",201);
        }
        return map;
    }

    //上传到aliyun和数据库
    @RequestMapping("uploadAliyun")
    public void uploadAliyun(MultipartFile picImg, String id){
        userService.uploadAliyun(picImg,id);
    }

    @RequestMapping("poiexport")
    @ResponseBody
    public void poiexport(){
        List<User> users = userService.queryAllPoiexport();

        ExportParams exportParams = new ExportParams("用户信息表","用户");

        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, User.class, users);

        try {
            workbook.write(new FileOutputStream(new File("D:\\Easypois.xls")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("importExcel")
    @ResponseBody
    public void importExcel(){

        ImportParams importParams = new ImportParams();
        importParams.setTitleRows(1);  //标题所占行
        importParams.setHeadRows(1);   //表头所占行

        List<User> users = ExcelImportUtil.importExcel(new File("D:\\Easypois.xls"), User.class, importParams);

        users.forEach(user -> System.out.println(user));
    }

    @RequestMapping("queryMouth")
    @ResponseBody
    public HashMap<String,Object> queryMouth(){
        HashMap<String, Object> map = new HashMap<>();
        ArrayList<Object> mouth = new ArrayList<>();
        ArrayList<Object> boy = new ArrayList<>();
        ArrayList<Object> girl = new ArrayList<>();
        for (int i=1;i<=12;i++){
            mouth.add(i+"月");
            List<Mc> boys = userService.queryMouth("男", i);
            Integer counts=null;
            for (Mc mc : boys) {
                 counts = mc.getCounts();
            }
            boy.add(counts);
            Integer count=null;
            List<Mc> mcs = userService.queryMouth("女", i);
            for (Mc mc : mcs) {
                count = mc.getCounts();
            }
            girl.add(count);
        }
        map.put("month",mouth);
        map.put("boys",boy);
        map.put("girls",girl);

        return map;
    }

    @RequestMapping("queryCity")
    @ResponseBody
    public ArrayList<Sc> queryCity(){
        ArrayList<Sc> list = new ArrayList<>();
        List<City> boys = userService.queryCity("男");
        List<City> girls = userService.queryCity("女");
        list.add(new Sc("小男孩",boys));
        list.add(new Sc("小女孩",girls));

        return list;
    }
}
