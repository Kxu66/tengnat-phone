package com.tengnat.elasticsearch.utils;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Configuration
public class InitElasticsearch {

    private static RestHighLevelClient client;



    @Bean
    public RestHighLevelClient initESClient() {
        // 创建client
        // 初始化 RestClient, hostName 和 port 填写集群的内网 VIP 地址与端口
        RestClientBuilder builder = RestClient.builder(new HttpHost("172.17.48.3", 9200, "http"));
        // 设置超时时间
        builder.setMaxRetryTimeoutMillis(10000);
//        RestClient restClient = builder.build();
        // 由Low Level Client构造High Level Client
        client = new RestHighLevelClient(builder);
        System.out.println("连接成功！");
        return client;
    }
    public List<Map<String, Object>> getSearchResponse(SearchRequest searchRequest){
        SearchResponse response = null;
        try {
            response = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = response.getHits();
            List<Map<String, Object>> list = new ArrayList<>();
            for (SearchHit hit: hits) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                list.add(sourceAsMap);
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //添加数据
    public void addIndexTypeData(IndexRequest indexRequest){
        try {
            InitElasticsearch.client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //查询数据的总数
    public long getTotalHits(SearchRequest searchRequest){
        SearchResponse response = null;
        try {
            response = InitElasticsearch.client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = response.getHits();
            return hits.getTotalHits();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
    //ES查询clientId
    public String findClientIdByToOrFromAccount(SearchRequest searchRequest){
        SearchResponse response = null;
        try {
            response = InitElasticsearch.client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = response.getHits();
            String clientId = null;
            for (SearchHit hit: hits) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                Map<String, Object> order = (Map<String, Object>) sourceAsMap.get("order");
                clientId = String.valueOf(order.get("clientId"));
                System.out.println("********" + clientId);
            }
            return clientId;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
