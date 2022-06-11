package com.xuge.zhxy.service;

import com.xuge.zhxy.bean.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xuge.zhxy.bean.LoginForm;

/**
* @author xuge
* @description 针对表【tb_admin】的数据库操作Service
* @createDate 2022-06-11 13:29:26
*/
public interface AdminService extends IService<Admin> {

  Admin login(LoginForm loginForm);
}
