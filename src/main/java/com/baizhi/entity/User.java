package com.baizhi.entity;


import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

  @ExcelProperty(value={"用户","id"})
  private String id;

  @ExcelProperty(value={"用户","电话"})
  private String phone;

  @ExcelProperty(value={"用户","密码"})
  private String password;

  @ExcelProperty(value={"用户","盐值"})
  private String salt;

  @ExcelProperty(value={"用户","状态"})
  private String status;

  @ExcelProperty(value={"用户","图片"},converter = UserContenver.class)
  private String photo;

  @ExcelProperty(value={"用户","姓名"})
  private String name;

  @ExcelProperty(value={"用户","法名"})
  private String nickName;

  @ExcelProperty(value={"用户","性别"})
  private String sex;

  @ExcelProperty(value={"用户","签名"})
  private String sign;

  @ExcelProperty(value={"用户","位置"})
  private String location;

  @ExcelProperty(value={"用户","注册时间"})
  @JsonFormat(pattern = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date rigestDate;

  @ExcelProperty(value={"用户","最后登录时间"})
  @JsonFormat(pattern = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date lastLogin;

}
