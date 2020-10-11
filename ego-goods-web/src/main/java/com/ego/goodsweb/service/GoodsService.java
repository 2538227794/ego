package com.ego.goodsweb.service;

import com.ego.commom.utils.JsonUtils;
import com.ego.goodsweb.client.BrandClient;
import com.ego.goodsweb.client.CategoryClient;
import com.ego.goodsweb.client.GoodsClient;
import com.ego.item.bo.SpuBo;
import com.ego.item.pojo.Brand;
import com.ego.item.pojo.Category;
import com.ego.item.pojo.Sku;
import com.ego.item.pojo.SpuDetail;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 〈〉
 *
 * @author coach tam
 * @email 327395128@qq.com
 * @create 2020/9/30
 * @since 1.0.0
 * 〈坚持灵活 灵活坚持〉
 */
@Service
public class GoodsService {
    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Resource
    private TemplateEngine templateEngine;
    /**
     * 初始化一个固定数量线程池
     */
    private ExecutorService pool = Executors.newFixedThreadPool(10);

    public Map<String, Object> loadModel(Long spuId) {

        //查询商品信息
        SpuBo spuBo = this.goodsClient.queryGoodsById(spuId);

        //查询商品详情
        SpuDetail spuDetail = spuBo.getSpuDetail();

        //查询skus
        List<Sku> skuList = spuBo.getSkus();

        //查询分类信息
        List<Long> ids = new ArrayList<>();
        ids.add(spuBo.getCid1());
        ids.add(spuBo.getCid2());
        ids.add(spuBo.getCid3());
        //查询分类列表
        List<Category> categoryList = this.categoryClient.getCategoryListByIds(ids).getBody();

        //查询品牌信息
        Brand brand = this.brandClient.getBrandListByIds(Collections.singletonList(spuBo.getBrandId())).getBody().get(0);


        /**
         * 对于规格属性的处理需要注意以下几点：
         *      1. 所有规格都保存为index和name形式
         *      2. 规格对应的值保存为index和value形式
         *      3. 都是map形式
         *      4. 将特有规格参数单独抽取
         */

        //获取所有规格参数，然后封装成为id和name形式的数据
        String allSpecJson = spuDetail.getSpecifications();
        List<Map<String,Object>> allSpecs = JsonUtils.nativeRead(allSpecJson, new TypeReference<List<Map<String, Object>>>() {
        });
        Map<Integer,String> specName = new HashMap<>();
        Map<Integer,Object> specValue = new HashMap<>();
        this.getAllSpecifications(allSpecs,specName,specValue);


        //获取特有规格参数
        String specTJson = spuDetail.getSpecTemplate();
        Map<String,String[]> specs = JsonUtils.nativeRead(specTJson, new TypeReference<Map<String, String[]>>() {});
        Map<Integer,String> specialParamName = new HashMap<>();
        Map<Integer,String[]> specialParamValue = new HashMap<>();
        this.getSpecialSpec(specs,specName,specValue,specialParamName,specialParamValue);

        //按照组构造规格参数
        List<Map<String,Object>> groups = this.getGroupsSpec(allSpecs,specName,specValue);


        Map<String,Object> map = new HashMap<>();
        map.put("spu",spuBo);
        map.put("spuDetail",spuDetail);
        map.put("skus",skuList);
        map.put("brand",brand);
        map.put("categories",categoryList);
        map.put("specName",specName);
        map.put("specValue",specValue);
        map.put("groups",groups);
        map.put("specialParamName",specialParamName);
        map.put("specialParamValue",specialParamValue);

        return map;
    }



    private List<Map<String, Object>> getGroupsSpec(List<Map<String, Object>> allSpecs, Map<Integer, String> specName, Map<Integer, Object> specValue) {
        List<Map<String, Object>> groups = new ArrayList<>();
        int i = 0;
        int j = 0;
        for (Map<String,Object> spec :allSpecs){
            List<Map<String, Object>> params = (List<Map<String, Object>>) spec.get("params");
            List<Map<String,Object>> temp = new ArrayList<>();
            for (Map<String,Object> param :params) {
                for (Map.Entry<Integer, String> entry : specName.entrySet()) {
                    if (entry.getValue().equals(param.get("k").toString())) {
                        String value = specValue.get(entry.getKey()) != null ? specValue.get(entry.getKey()).toString() : "无";
                        Map<String, Object> temp3 = new HashMap<>(16);
                        temp3.put("id", ++j);
                        temp3.put("name", entry.getValue());
                        temp3.put("value", value);
                        temp.add(temp3);
                    }
                }
            }
            Map<String,Object> temp2 = new HashMap<>(16);
            temp2.put("params",temp);
            temp2.put("id",++i);
            temp2.put("name",spec.get("group"));
            groups.add(temp2);
        }
        return groups;
    }

    private void getSpecialSpec(Map<String, String[]> specs, Map<Integer, String> specName, Map<Integer, Object> specValue, Map<Integer, String> specialParamName, Map<Integer, String[]> specialParamValue) {
        if (specs != null) {
            for (Map.Entry<String, String[]> entry : specs.entrySet()) {
                String key = entry.getKey();
                for (Map.Entry<Integer,String> e : specName.entrySet()) {
                    if (e.getValue().equals(key)){
                        specialParamName.put(e.getKey(),e.getValue());
                        //因为是放在数组里面，所以要先去除两个方括号，然后再以逗号分割成数组
                        String  s = specValue.get(e.getKey()).toString();
                        String result = StringUtils.substring(s,1,s.length()-1);
                        specialParamValue.put(e.getKey(), result.split(","));
                    }
                }
            }
        }
    }

    private void getAllSpecifications(List<Map<String, Object>> allSpecs, Map<Integer, String> specName, Map<Integer, Object> specValue) {
        String k = "k";
        String v = "v";
        String unit = "unit";
        String numerical = "numerical";
        String options ="options";
        int i = 0;
        if (allSpecs != null){
            for (Map<String,Object> s : allSpecs){
                List<Map<String, Object>> params = (List<Map<String, Object>>) s.get("params");
                for (Map<String,Object> param :params){
                    String result;
                    if (param.get(v) == null){
                        result = "无";
                    }else{
                        result = param.get(v).toString();
                    }
                    if (param.containsKey(numerical) && (boolean) param.get(numerical)) {
                        if (result.contains(".")){
                            Double d = Double.valueOf(result);
                            if (d.intValue() == d){
                                result = d.intValue()+"";
                            }
                        }
                        i++;
                        specName.put(i,param.get(k).toString());
                        specValue.put(i,result+param.get(unit).toString());
                    } else if (param.containsKey(options)){
                        i++;
                        specName.put(i,param.get(k).toString());
                        specValue.put(i,param.get(options));
                    }else {
                        i++;
                        specName.put(i,param.get(k).toString());
                        specValue.put(i,param.get(v));
                    }
                }
            }
        }
    }

    public void buildStaticHtml(Long spuId, Map<String, Object> modelMap) {
        pool.submit(()->{
            try {
                //利用thymeleaf api创建静态页面
                //封装模型数据
                Context context = new Context();
                context.setVariables(modelMap);
                //文件输出流
                FileWriter fileWriter = null;
                fileWriter = new FileWriter("D:\\ide\\nginx-1.17.6\\html\\item\\"+spuId+".html");
                templateEngine.process("item",context,fileWriter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}