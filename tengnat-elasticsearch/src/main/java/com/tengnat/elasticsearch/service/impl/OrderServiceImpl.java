package com.tengnat.elasticsearch.service.impl;

import com.alibaba.fastjson.JSON;
import com.tengnat.elasticsearch.from.FirstOrderIMFrom;
import com.tengnat.elasticsearch.from.OrderDataFrom;
import com.tengnat.elasticsearch.service.OrderService;
import com.tengnat.elasticsearch.utils.InitElasticsearch;
import com.tengnat.elasticsearch.utils.data.properties.EdiProperties;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private InitElasticsearch initElasticsearch;
    @Autowired
    private EdiProperties ediProperties;


    @Override
    public void addOrder(FirstOrderIMFrom orderIMFrom) {
        boolean isOrderExist = getIsOrderExist(orderIMFrom.getOrderFrom().getData());
        if (isOrderExist){
            Map<String, Object> map = new HashMap<>();
            map.put("fromAccount", orderIMFrom.getFromAccount());
            map.put("to", orderIMFrom.getTo());
            map.put("date", new Date());
            map.put("order", JSON.toJSON(orderIMFrom.getOrderFrom()));
            map.put("orderUUID", UUID.randomUUID().toString());
            IndexRequest indexRequest = new IndexRequest("orders", "first")
                    .source(map);
            this.initElasticsearch.addIndexTypeData(indexRequest);
        }
    }
    //查询订单是否存在
    private boolean getIsOrderExist(OrderDataFrom orderDataFrom){
        SearchRequest searchRequest = new SearchRequest("orders");
        searchRequest.types("first");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder sourceOrderIdFilter = QueryBuilders.boolQuery().filter(QueryBuilders.termQuery("order.data.sourceOrderId", orderDataFrom.getSourceOrderId()));
        BoolQueryBuilder targetOrderIdFilter = QueryBuilders.boolQuery().filter(QueryBuilders.termQuery("order.data.targetOrderId", orderDataFrom.getTargetOrderId()));
        searchSourceBuilder.query(QueryBuilders.boolQuery().must(sourceOrderIdFilter).must(targetOrderIdFilter));
        searchRequest.source(searchSourceBuilder);
        long totalHits = this.initElasticsearch.getTotalHits(searchRequest);
        return totalHits == 0;
    }

    @Override
    public List findByToOrFromAccount(String fromAccount, String to) {
        SearchRequest searchRequest = new SearchRequest("orders");
        searchRequest.types("first");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(this.getQueryByToOrFromAccount(fromAccount,to));
        searchRequest.source(searchSourceBuilder);
        return this.initElasticsearch.getSearchResponse(searchRequest);
    }

    @Deprecated
    @Override
    public String findClientIdByToOrFromAccount(String fromAccount, String to) {
        SearchRequest searchRequest = new SearchRequest("orders");
        searchRequest.types("first");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(this.getQueryByToOrFromAccount(fromAccount,to)).from(0).size(1).fetchSource("order.clientId",null).sort("date", SortOrder.DESC);
        searchRequest.source(searchSourceBuilder);
        return this.initElasticsearch.findClientIdByToOrFromAccount(searchRequest);
    }

    @Override
    public String findEDIClientId() {
        return this.ediProperties.getClientId();
    }

    private BoolQueryBuilder getQueryByToOrFromAccount(String fromAccount, String to){
        QueryBuilder should = QueryBuilders.boolQuery().should(QueryBuilders.termQuery("fromAccount", fromAccount)).should(QueryBuilders.termQuery("to", to)).minimumShouldMatch(2);
        QueryBuilder should1 = QueryBuilders.boolQuery().should(QueryBuilders.termQuery("fromAccount", to)).should(QueryBuilders.termQuery("to", fromAccount)).minimumShouldMatch(2);
        QueryBuilder fromQ = QueryBuilders.boolQuery().should(should).should(should1).minimumShouldMatch(1);
       return QueryBuilders.boolQuery().must(fromQ);
    }
}
