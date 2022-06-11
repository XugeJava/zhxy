package com.xuge.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuge.zhxy.bean.Admin;
import com.xuge.zhxy.bean.LoginForm;
import com.xuge.zhxy.service.AdminService;
import com.xuge.zhxy.mapper.AdminMapper;
import com.xuge.zhxy.utils.MD5;
import org.springframework.stereotype.Service;

/**
 * @author xuge
 * @description 针对表【tb_admin】的数据库操作Service实现
 * @createDate 2022-06-11 13:29:26
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

  @Override
  public Admin login(LoginForm loginForm) {
     //根据用户名和密码查询对象
    QueryWrapper<Admin> qw=new QueryWrapper<>();
    qw.eq("name",loginForm.getUsername());
    qw.eq("password", MD5.encrypt(loginForm.getPassword()));
    return baseMapper.selectOne(qw);
  }
}




