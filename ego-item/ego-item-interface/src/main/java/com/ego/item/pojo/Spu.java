package com.ego.item.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;

/**
 * @ClassName Spu
 * @Description 标准产品单位表，该表描述的是一个抽象性的商品
 * @Author luokun
 * @Date  2020/9/23 13:51
 * @Version 1.0
 **/
@Data
@TableName(value = "tb_spu")
public class Spu implements Serializable {
    /**
     * spu id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 子标题
     */
    @TableField(value = "sub_title")
    private String subTitle;

    /**
     * 1级类目id
     */
    @TableField(value = "cid1")
    private Long cid1;

    /**
     * 2级类目id
     */
    @TableField(value = "cid2")
    private Long cid2;

    /**
     * 3级类目id
     */
    @TableField(value = "cid3")
    private Long cid3;

    /**
     * 商品所属品牌id
     */
    @TableField(value = "brand_id")
    private Long brandId;

    /**
     * 是否上架，0下架，1上架
     */
    @TableField(value = "saleable")
    private Boolean saleable;

    /**
     * 是否有效，0已删除，1有效
     */
    @TableField(value = "valid")
    private Boolean valid;

    /**
     * 添加时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 最后修改时间
     */
    @TableField(value = "last_update_time")
    private Date lastUpdateTime;

    private static final long serialVersionUID = 1L;
}
