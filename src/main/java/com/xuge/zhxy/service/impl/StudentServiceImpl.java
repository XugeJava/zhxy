package com.xuge.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuge.zhxy.bean.LoginForm;
import com.xuge.zhxy.bean.Student;
import com.xuge.zhxy.service.StudentService;
import com.xuge.zhxy.mapper.StudentMapper;
import com.xuge.zhxy.utils.MD5;
import org.springframework.stereotype.Service;

/**
* @author xuge
* @description 针对表【tb_student】的数据库操作Service实现
* @createDate 2022-06-11 13:29:27
*/
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
    implements StudentService{

  @Override
  public Student login(LoginForm loginForm) {
    QueryWrapper<Student> qw=new QueryWrapper<>();
    qw.eq("name",loginForm.getUsername())
            .eq("password", MD5.encrypt(loginForm.getPassword()));

    return baseMapper.selectOne(qw);
  }
}




