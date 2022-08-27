package com.itheima.reggie.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

//菜品口味
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishFlavor implements Serializable {

    private Long id;//主键

    @TableField(value="dish_id")
    private Long dishId;//所属菜品id

    private String name;//口味名称

    private String value;//口味数据list

    //是否删除
    @TableField(value="is_deleted")
    private Integer isDeleted;

    @TableField(value="create_time",fill = FieldFill.INSERT)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField(value="update_time",fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @TableField(value="create_user",fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(value="update_user",fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;


}