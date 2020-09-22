package com.ego.item.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassNameCategory
 * @Descripiotn 商品类实体
 * @Author luokun
 * @Date 2020/9/21 10:19
 * @Version 1.0
 **/
@Data
@TableName("tb_category")
public class Category {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Long parentId;
    private Boolean isParent;
    private Integer sort;
}
