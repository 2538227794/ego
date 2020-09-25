package com.ego.item.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
/**
 * @ClassName SpuDetail
 * @Description 标准产品单位详情表
 * @Author luokun
 * @Date  2020/9/23 13:52
 * @Version 1.0
 **/
@Data
@TableName(value = "tb_spu_detail")
public class SpuDetail implements Serializable {
    @TableId(value = "spu_id", type = IdType.INPUT)
    private Long spuId;

    /**
     * 商品描述信息
     */
    @TableField(value = "description")
    private String description;

    /**
     * 全部规格参数数据
     */
    @TableField(value = "specifications")
    private String specifications;

    /**
     * 特有规格参数及可选值信息，json格式
     */
    @TableField(value = "spec_template")
    private String specTemplate;

    /**
     * 包装清单
     */
    @TableField(value = "packing_list")
    private String packingList;

    /**
     * 售后服务
     */
    @TableField(value = "after_service")
    private String afterService;

    private static final long serialVersionUID = 1L;
}
