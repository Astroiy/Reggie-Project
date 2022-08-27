package com.itheima.reggie.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("category")
public class Category {

    public static final Integer TYPE_DISH=1;
    public static final Integer TYPE_SETMEAL=2;

    private Long id;
    private Integer type;
    private String name;
    private Integer sort;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(value="create_time",fill = FieldFill.INSERT)
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(value="update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @TableField(value="create_user",fill = FieldFill.INSERT)
    private Long createUser;
    @TableField(value="update_user",fill=FieldFill.INSERT_UPDATE)
    private Long updateUser;
}
