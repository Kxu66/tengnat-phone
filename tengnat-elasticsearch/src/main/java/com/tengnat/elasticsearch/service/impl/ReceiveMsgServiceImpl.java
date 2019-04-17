package com.tengnat.elasticsearch.service.impl;

import com.tengnat.elasticsearch.from.ReceiveMsgFrom;
import com.tengnat.elasticsearch.service.ReceiveMsgService;
import com.tengnat.elasticsearch.utils.InitElasticsearch;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ReceiveMsgServiceImpl implements ReceiveMsgService {
    @Autowired
    private InitElasticsearch initElasticsearch;


    @Override
    public void addIndex(String title,String content){

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("user", "laimailai");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "trying out Elasticsearch");
        IndexRequest indexRequest = new IndexRequest("ste", "type", content)
                .source(jsonMap);
        this.initElasticsearch.addIndexTypeData(indexRequest);
    }

    @Override
    public void addReceive(ReceiveMsgFrom receiveMsg)  {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("eventType", receiveMsg.getEventType());
        jsonMap.put("convType", receiveMsg.getConvType());
        jsonMap.put("to", receiveMsg.getTo());
        jsonMap.put("fromAccount", receiveMsg.getFromAccount());
        jsonMap.put("fromClientType", receiveMsg.getFromClientType());
        jsonMap.put("fromDeviceId", receiveMsg.getFromDeviceId());
        jsonMap.put("fromNick", receiveMsg.getFromNick());
        jsonMap.put("msgTimestamp", receiveMsg.getMsgTimestamp());
        jsonMap.put("msgType", receiveMsg.getMsgType());
        jsonMap.put("body", receiveMsg.getBody());
        jsonMap.put("attach", receiveMsg.getAttach());
        jsonMap.put("msgidClient",receiveMsg.getMsgidClient());
        jsonMap.put("msgidServer", receiveMsg.getMsgidServer());
        jsonMap.put("resendFlag", receiveMsg.getResendFlag());
        jsonMap.put("customSafeFlag", receiveMsg.getCustomSafeFlag());
        jsonMap.put("tMembers", receiveMsg.getTMembers());
        jsonMap.put("ext", receiveMsg.getExt());
        jsonMap.put("antispam", receiveMsg.getAntispam());
        jsonMap.put("yidunRes",receiveMsg.getYidunRes());
        jsonMap.put("blacklist",receiveMsg.getBlacklist());
        jsonMap.put("orderId1", receiveMsg.getOrderId1());
        jsonMap.put("orderId2", receiveMsg.getOrderId2());
        jsonMap.put("ediOrderId", receiveMsg.getEdiOrderId());
        IndexRequest indexRequest = new IndexRequest("receives", "receive",receiveMsg.getId()+"")
                .source(jsonMap);
        this.initElasticsearch.addIndexTypeData(indexRequest);
    }

    @Override
    public List<Map<String, Object>>  findByEdiOrderIdOrToOrFromAccount(String orderId1,String orderId2, Long msgTimestamp, String msgType, int size) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(0);
        sourceBuilder.size(size);
        //查询
        QueryBuilder should = QueryBuilders.boolQuery().should(QueryBuilders.termQuery("orderId1", orderId1)).should(QueryBuilders.termQuery("orderId2", orderId2)).minimumShouldMatch(2);
        QueryBuilder should1 = QueryBuilders.boolQuery().should(QueryBuilders.termQuery("orderId1", orderId2)).should(QueryBuilders.termQuery("orderId2", orderId1)).minimumShouldMatch(2);
        QueryBuilder fromQ = QueryBuilders.boolQuery().should(should).should(should1).minimumShouldMatch(1);

        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        if(!StringUtils.isEmpty(msgType)){
            QueryBuilder msgTypeQ = QueryBuilders.termQuery("msgType", msgType);
            boolBuilder.must(msgTypeQ);
        }
        boolBuilder.filter(fromQ).filter(QueryBuilders.rangeQuery("msgTimestamp").lt(msgTimestamp));
        sourceBuilder.query(boolBuilder);
        //排序
        FieldSortBuilder fieldSortBuilder = new FieldSortBuilder("msgTimestamp");
        fieldSortBuilder.order(SortOrder.DESC);
        sourceBuilder.sort(fieldSortBuilder);
        SearchRequest searchRequest = new SearchRequest("receives");
        searchRequest.types("receive");
        searchRequest.source(sourceBuilder);
        return this.initElasticsearch.getSearchResponse(searchRequest);
    }
}
