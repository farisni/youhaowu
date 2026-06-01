package com.youhaowu.search.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youhaowu.common.vo.SkuEsModel;
import com.youhaowu.search.service.ProductSaveService;
import com.youhaowu.common.constant.KafkaTopicConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * SPU 上架 Kafka 消费者 —— 消费消息写入 ES
 */
@Slf4j
@Component
public class ProductUpConsumer {

    @Autowired
    private ProductSaveService productSaveService;

    /** 监听 youhaowu.product.up 主题 */
    @KafkaListener(topics = KafkaTopicConstants.TOPIC_PRODUCT_UP, groupId = "youhaowu-search-up")
    public void onMessage(String message) {
        log.info("收到商品上架消息，开始处理...");
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<SkuEsModel> models = mapper.readValue(message,
                    new TypeReference<List<SkuEsModel>>() {});
            boolean ok = productSaveService.productStatusUp(models);
            if (ok) {
                log.info("ES 索引写入完成，共 {} 条", models.size());
            } else {
                log.error("ES 索引写入失败");
            }
        } catch (Exception e) {
            log.error("消费上架消息异常: {}", e.getMessage(), e);
        }
    }
}
