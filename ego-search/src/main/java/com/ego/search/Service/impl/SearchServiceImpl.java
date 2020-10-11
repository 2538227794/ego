package com.ego.search.Service.impl;

import com.ego.commom.PageResult;
import com.ego.commom.enums.ExceptionEnum;
import com.ego.commom.exception.EgoException;
import com.ego.commom.utils.JsonUtils;
import com.ego.commom.utils.NumberUtils;
import com.ego.item.bo.SpuBo;
import com.ego.item.pojo.Brand;
import com.ego.item.pojo.Category;
import com.ego.item.pojo.Sku;
import com.ego.item.pojo.SpuDetail;
import com.ego.search.Service.SearchService;
import com.ego.search.bo.SearchRequest;
import com.ego.search.bo.SearchResult;
import com.ego.search.client.BrandClient;
import com.ego.search.client.CategoryClient;
import com.ego.search.client.SpecificationClient;
import com.ego.search.pojo.Goods;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.ParsedHistogram;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedDoubleTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.ParsedStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @ClassName SearchServiceImpl
 * @Descripiotn //TODO
 * @Author luokun
 * @Date 2020/9/27 2:38
 * @Version 1.0
 **/
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private SpecificationClient specificationClient;

    @Autowired
    private BrandClient brandClient;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Goods buildGoods(SpuBo spuBo) {
        try {
            List<Sku> skus = spuBo.getSkus();
            SpuDetail spuDetail = spuBo.getSpuDetail();
            String specifications = spuDetail.getSpecifications();
            List<Map<String,Object>> groupList = objectMapper.readValue(specifications, new TypeReference<List<Map<String, Object>>>() {
            });

            Map<String, Object> specs = new HashMap<>();
            //循环遍历groupList
            groupList.forEach(group->{
                List<Map<String,Object>> params = (List<Map<String, Object>>) group.get("params");
                params.forEach(param->{
                    //判断里面每个元素是否可搜索
                    Boolean searchable = (Boolean) param.get("searchable");
                    if(searchable!=null&&searchable){
                        String k = (String) param.get("k");
                        Object v = param.get("v");
                        //如果没有v，那么就存options
                        if (v != null) {
                            specs.put(k, v);
                        }
                        else
                        {
                            specs.put(k, param.get("options"));
                        }
                    }
                });
            });

            Goods result = Goods.builder()
                    .id(spuBo.getId())
                    .cid1(spuBo.getCid1())
                    .cid2(spuBo.getCid2())
                    .cid3(spuBo.getCid3())
                    .brandId(spuBo.getBrandId())
                    .all(spuBo.getTitle() + " " + spuBo.getCategoryNames() + " " + spuBo.getBrandName())
                    .createTime(spuBo.getCreateTime())
                    .subTitle(spuBo.getSubTitle())
                    .skus(objectMapper.writeValueAsString(skus))
                    .specs(specs)
                    .price(skus.stream().map(sku -> sku.getPrice()).collect(Collectors.toList()))
                    .build();
            return result;
        } catch (JsonProcessingException e) {
            log.error("SpuBo转化Goods异常:{}",e);
            throw new EgoException(ExceptionEnum.BUILD_GOODS_EXCEPTION);
        }
    }

    @Override
    public SearchResult page(SearchRequest searchRequest) {
        SearchResult searchResult=new SearchResult();
        String key = searchRequest.getKey();
        Integer pageNo = searchRequest.getPageNo();
        //关键字为null，不能查询
        if(StringUtils.isBlank(key)){
            return null;
        }
        //无pageNo，默认为第一页
        if(pageNo==null){
            pageNo=1;
        }
        //自定义查询
        NativeSearchQueryBuilder nativeSearchQueryBuilder=new NativeSearchQueryBuilder();
        //指定字段查询
        nativeSearchQueryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id", "title", "subTitle", "skus"}, null));
        //关键字查询
        QueryBuilder basicQuery =getBasicQuery(searchRequest);
        nativeSearchQueryBuilder.withQuery(basicQuery);
        //分页查询
        nativeSearchQueryBuilder.withPageable(PageRequest.of(pageNo-1,20));
        //添加cid3，brandId聚合
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("分类").field("cid3"));
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("品牌").field("brandId"));
        //执行查询
        SearchHits<Goods> goodsSearchHits = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), Goods.class);
        //将搜索返回的SearchHit转化为Goods
        List<Goods> goods = goodsSearchHits.getSearchHits().stream().map(goodsSearchHit -> goodsSearchHit.getContent()).collect(Collectors.toList());
        searchResult.setItems(goods);
        long total = goodsSearchHits.getTotalHits();
        searchResult.setTotal(total);
        searchResult.setTotalPage(total%20==0?total/20:total/20+1);
        //获取cid3，品牌聚合数据
        List<Category> categories=getCategoryAgg(goodsSearchHits);
        List<Brand> brands=getBrandAgg(goodsSearchHits);
        searchResult.setBrands(brands);
        searchResult.setCategories(categories);
        if (CollectionUtils.isNotEmpty(categories)) {
            //查询第一个分类的所有过滤条件
            List<Map<String,Object>> specs = getSpecs(categories.get(0).getId(),basicQuery);
            searchResult.setSpecs(specs);
        }
        return searchResult;
    }

    /**
     * @Author luokun
     * @Description 获取基础查询对象
     * @Date  2020/10/8 16:48
     * @Param [searchRequest]
     * @return org.elasticsearch.index.query.QueryBuilder
     **/
    private QueryBuilder getBasicQuery(SearchRequest searchRequest) {
        //构建一个布尔查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //添加must条件
        boolQueryBuilder.must(QueryBuilders.matchQuery("all", searchRequest.getKey()));
        //添加filter
        BoolQueryBuilder filterQueryBuilder = QueryBuilders.boolQuery();
        Map<String, String> filter = searchRequest.getFilter();
        filter.forEach((field,value)->{
            //判断是否特殊字段  分类 品牌
            if("分类".equals(field)){
                field = "cid3";
                filterQueryBuilder.must(QueryBuilders.termQuery(field, value));
            }
            else if("品牌".equals(field)){
                field = "brandId";
                filterQueryBuilder.must(QueryBuilders.termQuery(field, value));
            }
            else
            {
                //判断是什么类型的字段，
                //1200.00-1500.00
                String reg = "^(\\d+\\.?\\d*)-(\\d+\\.?\\d*)$";
                if (Pattern.matches(reg, value)) {
                    //如果是数字型的，采用range查询
                    String[] split = value.split("-");
                    filterQueryBuilder.must(QueryBuilders.rangeQuery("specs."+field).gte(split[0]).lt(split[1]));
                }
                else{
                    //如果是字符型的，采用term查询
                    filterQueryBuilder.must(QueryBuilders.termQuery("specs." + field + ".keyword", value));
                }
            }
        });
        boolQueryBuilder.filter(filterQueryBuilder);
        return boolQueryBuilder;
    }

    /**
     * @Author luokun
     * @Description 查询其他过滤条件
     * @Date  2020/10/8 13:51
     * @Param [id, basicQuery]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    private List<Map<String, Object>> getSpecs(Long cid, QueryBuilder basicQuery) {
        try {
            List<Map<String, Object>> result = null;
            //1.根据类别id，查询到所有规格参数
            String specJson = specificationClient.querySpecifications(cid).getBody();
            //将json转成对象，方便解析
            List<Map<String,Object>> specs = JsonUtils.nativeRead(specJson, new TypeReference<List<Map<String, Object>>>() {});
            //2.区分数字型&字符型
            //key:名字  value:单位
            Map<String, String> numSpecs = new HashMap<>();
            List<String> strSpecs = new ArrayList<>();
            //遍历所有规格参数
            specs.forEach(group->
                    ((List<Map<String,Object>>)group.get("params")).forEach(param->{
                        //区分可搜索
                        boolean searchable = (boolean) param.get("searchable");
                        if(searchable){
                            //区分数字&字符型
                            Object numerical = param.get("numerical");
                            String k = (String) param.get("k");
                            if(numerical!=null&&(boolean)numerical){
                                String unit = (String) param.get("unit");
                                numSpecs.put(k,unit);
                            }
                            else{
                                strSpecs.add(k);
                            }
                        }
                    }));
            //3.查询到对应数字型参数的间隔
            Map<String,Double> intervalMap = getIntervalMap(numSpecs,basicQuery);
            //4.执行聚合查询
            NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
            //添加基础查询
            queryBuilder.withQuery(basicQuery);
            //添加聚合条件
            strSpecs.forEach(spec->queryBuilder.addAggregation(AggregationBuilders.terms(spec).field("specs."+spec+".keyword")));
            numSpecs.forEach((k,v)->{
                //间隔必须大于零
                if(intervalMap.get(k)>0){
                    queryBuilder.addAggregation(AggregationBuilders.histogram(k).interval(intervalMap.get(k)).field("specs."+k));
                }
            });
            SearchHits<Goods> searchHits = elasticsearchRestTemplate.search(queryBuilder.build(), Goods.class);
            //5.解析结果集(区分数字型&字符型)
            result = parseAgg(searchHits.getAggregations(),strSpecs,numSpecs,intervalMap);
            return result;
        } catch (Exception e) {
            log.error("查询其他过滤条件异常",e);
            throw new EgoException(ExceptionEnum.QUERY_SPECS_EXCEPTION);
        }
    }

    private List<Map<String, Object>> parseAgg(Aggregations aggregations, List<String> strSpecs, Map<String, String> numSpecs, Map<String, Double> intervalMap) {
        List<Map<String, Object>> result = new ArrayList<>();
        //解析数字型
        numSpecs.forEach((k, unit) -> {
            ParsedHistogram agg = aggregations.get(k);
            if(agg!=null){
                Map<String, Object> map = new HashMap<>();
                List<String> options = agg.getBuckets().stream().map(bucket -> {

                    String option = null;
                    try {
                        Double begin = (Double) bucket.getKey();
                        Double end = begin + intervalMap.get(k);
                        //判断是否整形,是就去除小数点，反之就取1位小数点
                        if (NumberUtils.isInt(begin) && NumberUtils.isInt(end)) {
                            option = begin.intValue() + "-" + end.intValue();
                        } else {
                            option = NumberUtils.scale(begin, 1) + "-" + NumberUtils.scale(end, 1);
                        }
                    } catch (NullPointerException e) {
                        log.error("", e);
                    }
                    return option;
                }).collect(Collectors.toList());
                map.put("options", options);
                map.put("name",k);
                map.put("unit",unit);
                result.add(map);
            }
        });
        //解析字符型
        strSpecs.forEach(k->{
            ParsedStringTerms aggregation = aggregations.get(k);
            if (aggregation != null) {
                Map<String, Object> map = new HashMap<>();
                List<String> options = aggregation.getBuckets().stream()
                        .map(bucket -> bucket.getKeyAsString())
                        .filter(option->StringUtils.isNotBlank(option))
                        .collect(Collectors.toList());
                if(options.size()>1)
                {
                    map.put("options", options);
                    map.put("name", k);
                    result.add(map);
                }
            }
        });
        return result;
    }

    /**
     * @ClassName
     * @Description 获取对应数字型参数间隔
     * @Author luokun
     * @Date  2020/10/8 14:08
     * @Version 1.0
     **/
    private Map<String, Double> getIntervalMap(Map<String, String> numSpecs, QueryBuilder basicQuery) {
        Map<String, Double> result = new HashMap<>(numSpecs.size());

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //基础查询
        queryBuilder.withQuery(basicQuery);
        //不要数据
        queryBuilder.withPageable(PageRequest.of(0,1));
        //添加聚合条件
        numSpecs.forEach((k,unit)->{
            queryBuilder.addAggregation(AggregationBuilders.stats(k).field("specs."+k));
        });
        //执行查询
        SearchHits<Goods> searchHits = elasticsearchRestTemplate.search(queryBuilder.build(), Goods.class);
        //获取聚合结果，计算出每个数字型参数的间隔
        numSpecs.forEach((k,unit)->{
            ParsedStats agg = searchHits.getAggregations().get(k);
            Double interval = NumberUtils.getInterval(agg.getMin(), agg.getMax(), agg.getSum());
            result.put(k, interval);
        });
        return result;
    }

    /**
     * @Author luokun
     * @Description 获取品牌聚合数据
     * @Date  2020/10/8 12:48
     * @Param [goodsSearchHits]
     * @return java.util.List<com.ego.item.pojo.Brand>
     **/
    private List<Brand> getBrandAgg(SearchHits<Goods> goodsSearchHits) {
        ParsedLongTerms agg=goodsSearchHits.getAggregations().get("品牌");
        List<Long> idList = agg.getBuckets().stream().map(bucket -> bucket.getKeyAsNumber().longValue()).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(idList)){
            return null;
        }
        //根据idList批量查询到对应的categoryList
        List<Brand> brands= brandClient.getBrandListByIds(idList).getBody();
        return brands;
    }

    /**
     * @Author luokun
     * @Description 解析分类聚合数据
     * @Date  2020/10/8 12:06
     * @Param [goodsSearchHits]
     * @return java.util.List<com.ego.item.pojo.Category>
     **/
    private List<Category> getCategoryAgg(SearchHits<Goods> goodsSearchHits) {
        ParsedLongTerms agg=goodsSearchHits.getAggregations().get("分类");
        List<Long> idList = agg.getBuckets().stream().map(bucket -> bucket.getKeyAsNumber().longValue()).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(idList)){
            return null;
        }
        //根据idList批量查询到对应的categoryList
        List<Category> categories=categoryClient.getCategoryListByIds(idList).getBody();
        return categories;
    }

}
