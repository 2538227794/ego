package com.ego.item.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @ClassName Sku
 * @Description 库存量单位表,该表表示具体的商品实体
 * @Author luokun
 * @Date  2020/9/23 13:50
 * @Version 1.0
 **/
@Data
@TableName(value = "tb_sku")
public class Sku implements Serializable {
    /**
     * sku id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * spu id
     */
    @TableField(value = "spu_id")
    private Long spuId;

    /**
     * 商品标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 商品的图片，多个图片以‘,’分割
     */
    @TableField(value = "images")
    private String images;

    /**
     * 销售价格，单位为分
     */
    @TableField(value = "price")
    private Long price;

    /**
     * 特有规格属性在spu属性模板中的对应下标组合
     */
    @TableField(value = "indexes")
    private String indexes;

    /**
     * sku的特有规格参数键值对，json格式，反序列化时请使用linkedHashMap，保证有序
     */
    @TableField(value = "own_spec")
    private String ownSpec;

    /**
     * 是否有效，0无效，1有效
     */
    @TableField(value = "enable")
    private Boolean enable;

    /**
     * 添加时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 最后修改时间
     */
    @TableField(value = "last_update_time")
    private LocalDateTime lastUpdateTime;

    private static final long serialVersionUID = 1L;

    /**
     * 非表字段
     */
    @TableField(exist = false)
    private Stock stock;// 库存
}
