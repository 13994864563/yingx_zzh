package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Excel(name = "ID")
    private String id;
    @Excel(name = "头像",type = 2)
    private String picImg; //头像
    @Excel(name = "昵称")
    private String nickName; //昵称
    @Excel(name = "手机号")
    private String phone;  //手机号
    @Excel(name = "简介")
    private String brief;  //简介
    @Excel(name = "创建日期",exportFormat = "yyyy-MM-dd",importFormat = "yyyy-MM-dd")
    private Date createDate;//创建日期
    @Excel(name = "状态")
    private String status;//状态
    @Excel(name = "学分")
    private String scoreId;   //学分
    @Excel(name = "性别")
    private String sex;

}
