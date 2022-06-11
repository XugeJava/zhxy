package com.xuge.zhxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xuge.zhxy.bean.Admin;
import com.xuge.zhxy.bean.Student;
import com.xuge.zhxy.bean.Teacher;
import com.xuge.zhxy.service.AdminService;
import com.xuge.zhxy.service.StudentService;
import com.xuge.zhxy.service.TeacherService;
import com.xuge.zhxy.utils.JwtHelper;
import com.xuge.zhxy.utils.MD5;
import com.xuge.zhxy.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.annotation.WebFilter;
import java.util.List;

/**
 * (TbAdmin)表控制层
 *
 * @author makejava
 * @since 2022-06-11 13:51:13
 */
@RestController
@RequestMapping("sms/adminController")
public class AdminController {
  @Autowired
  private AdminService adService;
  @Autowired
  private StudentService studentService;
  @Autowired
  private TeacherService teacherService;

  @ApiOperation("分页获取所有Admin信息【带条件】")
  @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
  public Result getAllAdmin(@PathVariable Integer pageNo,
                            @PathVariable Integer pageSize,
                            String adminName){
    Page<Admin> page = new Page<>(pageNo,pageSize);
    QueryWrapper<Admin> qw = new QueryWrapper<>();
    qw.like(adminName!=null,"name",adminName);
    adService.page(page,qw);
    return Result.ok(page);
  }

  @ApiOperation("添加或修改Admin信息")
  @PostMapping("/saveOrUpdateAdmin")
  public Result saveOrUpdateAdmin(@RequestBody Admin admin){
    if (!Strings.isEmpty(admin.getPassword())) {
      admin.setPassword(MD5.encrypt(admin.getPassword()));
    }
    adService.saveOrUpdate(admin);
    return Result.ok();
  }

  @ApiOperation("删除Admin信息")
  @DeleteMapping("/deleteAdmin")
  public Result deleteAdmin(@RequestBody List<Integer> ids){
    adService.removeByIds(ids);
    return Result.ok();
  }

  @ApiOperation("修改密码")
  @PostMapping("/updatePwd/{oldPwd}/{newPwd}")
  public Result updatePwd(@RequestHeader("token") String token,
                          @PathVariable("oldPwd") String oldPwd,
                          @PathVariable("newPwd") String newPwd){
    boolean yOn = JwtHelper.isExpiration(token);
    if(yOn){
      //token过期
      return Result.fail().message("token失效!");
    }
    //通过token获取当前登录的用户id
    Long userId = JwtHelper.getUserId(token);
    //通过token获取当前登录的用户类型
    Integer userType = JwtHelper.getUserType(token);
    // 将明文密码转换为暗文
    oldPwd=MD5.encrypt(oldPwd);
    newPwd= MD5.encrypt(newPwd);
    if(userType == 1){
      QueryWrapper<Admin> queryWrapper=new QueryWrapper<>();
      queryWrapper.eq("id",userId.intValue()).eq("password",oldPwd);
      Admin admin = adService.getOne(queryWrapper);
      if (null!=admin) {
        admin.setPassword(newPwd);
        adService.saveOrUpdate(admin);
      }else{
        return Result.fail().message("原密码输入有误！");
      }
    }else if(userType == 2){
      QueryWrapper<Student> queryWrapper=new QueryWrapper<>();
      queryWrapper.eq("id",userId.intValue()).eq("password",oldPwd);

      Student student = studentService.getOne(queryWrapper);
      if (null!=student) {
        student.setPassword(newPwd);
        studentService.saveOrUpdate(student);
      }else{
        return Result.fail().message("原密码输入有误！");
      }
    }
    else if(userType == 3){
      QueryWrapper<Teacher> queryWrapper=new QueryWrapper<>();
      queryWrapper.eq("id",userId.intValue()).eq("password",oldPwd);
      Teacher teacher = teacherService.getOne(queryWrapper);
      if (null!=teacher) {
        teacher.setPassword(newPwd);
        teacherService.saveOrUpdate(teacher);
      }else{
        return Result.fail().message("原密码输入有误！");
      }
    }
    return Result.ok();
  }

}

