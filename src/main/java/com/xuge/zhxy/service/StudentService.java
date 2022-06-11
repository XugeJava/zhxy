package com.xuge.zhxy.service;

import com.xuge.zhxy.bean.LoginForm;
import com.xuge.zhxy.bean.Student;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author xuge
* @description 针对表【tb_student】的数据库操作Service
* @createDate 2022-06-11 13:29:27
*/
public interface StudentService extends IService<Student> {

  Student login(LoginForm loginForm);
}
