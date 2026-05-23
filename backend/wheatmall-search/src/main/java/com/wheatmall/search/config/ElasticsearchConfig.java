package com.wheatmall.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ES 配置
 * 1、导入依赖
 * 2、编写配置
 */
@Configuration
public class ElasticsearchConfig {

    @Value("${ES.host}")
    private String ESHost;
    @Value("${ES.port}")
    private Integer ESPort;

//     @Bean
//     public RestHighLevelClient esRestClient(){
//         RestHighLevelClient client = new RestHighLevelClient(
//                 RestClient.builder(new HttpHost("192.168.31.237", 9200, "http")));
//         return  client;
//     }

    public static final RequestOptions COMMON_OPTIONS;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        // builder.addHeader("Authorization", "Bearer " + TOKEN);
        // builder.setHttpAsyncResponseConsumerFactory(
        //         new HttpAsyncResponseConsumerFactory
        //                 .HeapBufferedResponseConsumerFactory(30 * 1024 * 1024 * 1024));
        COMMON_OPTIONS = builder.build();
    }

    @Bean
    public RestHighLevelClient esRestClient(){
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost(ESHost, ESPort, "http")));
        return  client;
    }

}
