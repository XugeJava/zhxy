package com.xuge.zhxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuge.zhxy.bean.Clazz;
import com.xuge.zhxy.service.ClazzService;
import com.xuge.zhxy.utils.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * (TbClazz)表控制层
 *
 * @author makejava
 * @since 2022-06-11 13:51:13
 */
@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {
  @Autowired
  private ClazzService clazzService;
  @ApiOperation("获取所有班级的JSON")
  @GetMapping("/getClazzs")
  public Result getClazzs(){
    List<Clazz> clazzList = clazzService.list();
    return Result.ok(clazzList);
  }
  @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
  public Result getClazzsByOpr(
          @ApiParam("页码数") @PathVariable("pageNo") Integer pageNo,
          @ApiParam("页大小") @PathVariable("pageSize") Integer pageSize,
          @ApiParam("查询条件") Clazz clazz
  ) {
    Page<Clazz> page = new Page<>(pageNo, pageSize);
    //条件构造
    QueryWrapper<Clazz> qw=new QueryWrapper<>();
    qw.eq(clazz.getGradeName()!=null,"grade_name",clazz.getGradeName());
    qw.like(clazz.getName()!=null,"name",clazz.getName());
    qw.orderByDesc("id");
    qw.orderByAsc("name");
    clazzService.page(page,qw);
    return Result.ok(page);
  }
  @ApiOperation("保存或者修改班级信息")
  @PostMapping("/saveOrUpdateClazz")
  public Result saveOrUpdateClazz(
          @ApiParam("JSON转换后端Clazz数据模型") @RequestBody Clazz clazz
  ){
    clazzService.saveOrUpdate(clazz);
    return Result.ok();
  }
  @ApiOperation("删除一个或者多个班级信息")
  @DeleteMapping("/deleteClazz")
  public Result deleteClazzByIds(
          @ApiParam("多个班级id的JSON") @RequestBody List<Integer> ids
  ){
    clazzService.removeByIds(ids);
    return Result.ok();
  }
}



