package com.xuge.zhxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuge.zhxy.bean.Teacher;
import com.xuge.zhxy.service.TeacherService;
import com.xuge.zhxy.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * (TbTeacher)表控制层
 *
 * @author makejava
 * @since 2022-06-11 13:51:13
 */
@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {
@Autowired
private TeacherService teacherService;
  @ApiOperation("获取教师信息,分页带条件")
  @GetMapping("/getTeachers/{pageNo}/{pageSize}")
  public Result getTeachers(
          @PathVariable("pageNo") Integer pageNo,
          @PathVariable("pageSize") Integer pageSize,
          Teacher teacher
  ){
    Page<Teacher> page = new Page<>(pageNo, pageSize);
    //构造条件
    QueryWrapper<Teacher> qw = new QueryWrapper<>();
    qw.eq(teacher.getClazzName()!=null,"clazz_name",teacher.getClazzName())
            .like(teacher.getName()!=null,"name",teacher.getName());
    teacherService.page(page,qw);
    return Result.ok(page);

  }

  @ApiOperation("添加和修改教师信息")
  @PostMapping("/saveOrUpdateTeacher")
  public Result saveOrUpdateTeacher(
          @RequestBody Teacher teacher
  ){
    teacherService.saveOrUpdate(teacher);
    return Result.ok();
  }
  @ApiOperation("删除一个或者多个教师信息")
  @DeleteMapping("/deleteTeacher")
  public Result deleteTeacher(
          @RequestBody List<Integer> ids
  ){
    teacherService.removeByIds(ids);
    return Result.ok();
  }

}

