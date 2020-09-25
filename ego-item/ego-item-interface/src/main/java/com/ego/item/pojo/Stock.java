package com.ego.item.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @ClassName Stock
 * @Description 库存量单位表，代表库存，秒杀库存等信息
 * @Author luokun
 * @Date  2020/9/23 13:54
 * @Version 1.0
 **/
@Data
@TableName(value = "tb_stock")
public class Stock implements Serializable {
    /**
     * 库存对应的商品sku id
     */
    @TableId(value = "sku_id", type = IdType.INPUT)
    private Long skuId;

    /**
     * 可秒杀库存
     */
    @TableField(value = "seckill_stock")
    private Integer seckillStock;

    /**
     * 秒杀总数量
     */
    @TableField(value = "seckill_total")
    private Integer seckillTotal;

    /**
     * 库存数量
     */
    @TableField(value = "stock")
    private Integer stock;

    private static final long serialVersionUID = 1L;
}
