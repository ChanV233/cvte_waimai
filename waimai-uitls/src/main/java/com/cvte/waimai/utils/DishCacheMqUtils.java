package com.cvte.waimai.utils;


import com.mysql.cj.MessageBuilder;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.TopicConfig;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

@Component
@DependsOn("springBeanUtils")
public class DishCacheMqUtils {

    static String TOPIC = "waimai_dish_cache_remove";
    static String PRODUCER_GROUP = "cvte_waimai_dish_remove";
    static String NAMESRV_ADDR = "localhost:9876";
    static String CONSUMER_GROUP = "cvte_waimai_dish_remove";
    static DefaultMQProducer producer;
    static DefaultMQPushConsumer consumer;

    public void sendMessage(String cacheKey) {
        try {
            Message msg = new Message(TOPIC,
                    "*",
                    cacheKey,
                    cacheKey.getBytes(StandardCharsets.UTF_8));
            msg.setDelayTimeLevel(1);
            producer.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void init() throws MQClientException {
        producer = new DefaultMQProducer();
        producer.setProducerGroup(PRODUCER_GROUP);
        producer.setNamesrvAddr(NAMESRV_ADDR);
        producer.start();
        //订单消费者
        consumer = new DefaultMQPushConsumer();
        consumer.setConsumerGroup(CONSUMER_GROUP);
        consumer.setMessageModel(MessageModel.CLUSTERING);
        consumer.setNamesrvAddr(NAMESRV_ADDR);
        consumer.subscribe(TOPIC, "*");
        //设置最大重试次数
        consumer.setMaxReconsumeTimes(10);
        consumer.setSuspendCurrentQueueTimeMillis(3000);
        consumer.registerMessageListener(new MessageListenerConcurrentlyCacheHandler());
        consumer.start();
    }

}
