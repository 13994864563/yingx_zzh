package com.baizhi.service;

import com.baizhi.entity.Admin;

import java.util.HashMap;

public interface AdminService {

    HashMap<String, Object> queryByUsername(Admin admin, String imgcode);
}
