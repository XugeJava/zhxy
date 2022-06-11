package com.xuge.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuge.zhxy.bean.LoginForm;
import com.xuge.zhxy.bean.Teacher;
import com.xuge.zhxy.service.TeacherService;
import com.xuge.zhxy.mapper.TeacherMapper;
import com.xuge.zhxy.utils.MD5;
import org.springframework.stereotype.Service;

/**
* @author xuge
* @description 针对表【tb_teacher】的数据库操作Service实现
* @createDate 2022-06-11 13:29:27
*/
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>
    implements TeacherService{

  @Override
  public Teacher login(LoginForm loginForm) {
    QueryWrapper<Teacher> qw=new QueryWrapper<>();
    qw.eq("name",loginForm.getUsername())
            .eq("password", MD5.encrypt(loginForm.getPassword()));
    return baseMapper.selectOne(qw);
  }
}




