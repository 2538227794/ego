package com.ego.item.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassNameBrand
 * @Descripiotn 品牌实体
 * @Author luokun
 * @Date 2020/9/21 10:21
 * @Version 1.0
 **/
@Data
@TableName("tb_brand")
public class Brand {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String image;
    private Character letter;
}
