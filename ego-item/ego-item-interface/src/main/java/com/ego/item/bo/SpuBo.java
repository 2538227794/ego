package com.ego.item.bo;

import com.ego.item.pojo.Sku;
import com.ego.item.pojo.Spu;
import com.ego.item.pojo.SpuDetail;
import lombok.Data;

import java.util.List;

/**
 * @ClassName SpuBo
 * @Descripiotn Spu业务类
 * @Author luokun
 * @Date 2020/9/23 17:06
 * @Version 1.0
 **/
@Data
public class SpuBo extends Spu {
    /**
     * 商品类名
     **/
    private String categoryNames;
    /**
     * 品牌名
     **/
    private String brandName;
    /**
     * 库存量
     **/
    private List<Sku> skus;
    /**
     *  标准单位详情
     **/
    private SpuDetail spuDetail;
}
