package com.xuge.zhxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuge.zhxy.bean.Student;
import com.xuge.zhxy.service.StudentService;
import com.xuge.zhxy.utils.MD5;
import com.xuge.zhxy.utils.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TbStudent)表控制层
 *
 * @author makejava
 * @since 2022-06-11 13:51:13
 */
@RestController
@RequestMapping("/sms/studentController")
public class StudentController {
  @Autowired
  private StudentService studentService;

  @ApiOperation("删除一个或者多个学生信息")
  @DeleteMapping("/delStudentById")
  public Result delStudentById(
          @ApiParam("多个学生id的JSON") @RequestBody List<Integer> ids
  ){
    studentService.removeByIds(ids);
    return Result.ok();
  }


  @ApiOperation("增加学生信息")
  @PostMapping("/addOrUpdateStudent")
  public Result addOrUpdateStudent(@RequestBody Student student){
    //对学生的密码进行加密
    if (!Strings.isEmpty(student.getPassword())) {
      student.setPassword(MD5.encrypt(student.getPassword()));
    }
    //保存学生信息进入数据库
    studentService.saveOrUpdate(student);
    return Result.ok();
  }
  @ApiOperation("查询学生信息,分页带条件")
  @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
  public Result getStudentsByOpr(
          @ApiParam("页码数") @PathVariable("pageNo")Integer pageNo,
          @ApiParam("页大小") @PathVariable("pageSize")Integer pageSize,
          @ApiParam("查询条件转换后端数据模型") Student student
  ){
    // 准备分页信息封装的page对象
    Page<Student> page =new Page<>(pageNo,pageSize);
    // 获取分页的学生信息
    //条件构造
    QueryWrapper<Student> qw=new QueryWrapper<>();
    qw.eq(student.getClazzName()!=null,"clazz_name",student.getClazzName());
    qw.like(student.getName()!=null,"name",student.getName());
    studentService.page(page,qw);
    // 返回学生信息
    return Result.ok(page);
  }

}

