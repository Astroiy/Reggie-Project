package com.itheima.reggie.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("employee")
public class Employee implements Serializable {
    public static final Integer STATUS_DISABLE=0;
    public static final Integer STATUS_ENABLE=1;

    private Long id;
    private String username;
    private String name;
    private String password;
    private String phone;
    private String sex;
    @TableField("id_number")
    private String idNumber;
    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(value="create_time",fill = FieldFill.INSERT)
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(value="update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(value="create_user",fill = FieldFill.INSERT)
    private Long createUser;
    @TableField(value="update_user",fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
}
