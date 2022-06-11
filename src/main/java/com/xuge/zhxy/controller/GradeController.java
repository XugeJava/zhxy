package com.xuge.zhxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuge.zhxy.bean.Grade;
import com.xuge.zhxy.service.GradeService;
import com.xuge.zhxy.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * (TbGrade)表控制层
 *
 * @author makejava
 * @since 2022-06-11 13:51:13
 */
@RestController
@Api(tags = "年级控制器")
@RequestMapping("/sms/gradeController")
public class GradeController {
  @Autowired
  private GradeService gradeService;
  @ApiOperation("获取所有Grade信息")
  @GetMapping("/getGrades")
  public Result getGrades(){
    List<Grade> grades = gradeService.list();
    return Result.ok(grades);
  }
  @ApiOperation("添加或者修改年级")
  @PostMapping("saveOrUpdateGrades")
  public Result saveOrUpdateGrades(@ApiParam("JSON的grade对象转换后台数据模型")
                                   @RequestBody Grade grade){
    //调用服务层方法，实现修改或者添加
    gradeService.saveOrUpdate(grade);
    return Result.ok();
  }

  @ApiOperation("删除一个或者多个grade信息")
  @DeleteMapping("/deleteGrade")
  public Result deleteGradeById(
          @ApiParam("JSON的年级id集合,映射为后台List<Integer>")@RequestBody List<Integer> ids
  )
  {
    gradeService.removeByIds(ids);
    return Result.ok();
  }

  @ApiOperation("年级分页")
  @GetMapping("getGrades/{pageNo}/{pageSize}")
  public Result getGradesPage(@ApiParam("分页查询页码数") @PathVariable(value = "pageNo") Integer pageNo, // 页码数
                              @ApiParam("分页查询页大小") @PathVariable(value = "pageSize") Integer pageSize, // 页大小
                              @ApiParam("分页查询模糊匹配班级名") String gradeName) {
    Page<Grade> page = new Page<>(pageNo, pageSize);

    //构造条件
    QueryWrapper<Grade> qw = new QueryWrapper<>();
    qw.eq(gradeName != null, "name", gradeName);
    gradeService.page(page, qw);
    // 设置排序规则
    qw.orderByDesc("id");
    qw.orderByAsc("name");
    return Result.ok(page);
  }
}

