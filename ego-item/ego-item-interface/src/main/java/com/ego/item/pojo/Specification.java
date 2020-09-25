package com.ego.item.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @ClassName Specification
 * @Description 商品类规格参数表，json格式。
 * @Author luokun
 * @Date  2020/9/23 13:51
 * @Version 1.0
 **/
@Data
@TableName(value = "tb_specification")
public class Specification implements Serializable {
    /**
     * 规格模板所属商品分类id
     */
    @TableId(value = "category_id", type = IdType.INPUT)
    private Long categoryId;

    /**
     * 规格参数模板，json格式
     */
    @TableField(value = "specifications")
    private String specifications;

    private static final long serialVersionUID = 1L;
}
