package com.cvte.waimai.utils;

import com.cvte.waimai.service.FailsOrderService;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@DependsOn("springBeanUtils")
public class OrderMqUtils {

    @Autowired
    private FailsOrderService failsOrderService;
    static String TOPIC = "waimai";
    static String PRODUCER_GROUP = "cvte_waimai";
    static String NAMESRV_ADDR = "localhost:9876";
    static String CONSUMER_GROUP = "cvte_waimai";
    static String CONSUMER_GROUP_DLQ = "cvte_waimai_dlq";
    static DefaultMQProducer producer;
    static DefaultMQPushConsumer consumer;
    static DefaultMQPushConsumer consumerDLQ;

    public void sendMessage(String messageBody, String orderId) {
        try {
            Message msg = new Message(TOPIC,
                    "*",
                    orderId,
                    messageBody.getBytes()
            );
            //异步发送消息
            producer.send(msg, new SendCallbackHandler(messageBody));
        } catch (Exception e) {
            e.printStackTrace();
            this.failsOrderService.addFailsOrder(messageBody);
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
        consumer.registerMessageListener(new MessageListenerConcurrentlyHandler());
        consumer.start();
        //死信订单消费者
        consumerDLQ = new DefaultMQPushConsumer();
        consumerDLQ.setConsumerGroup(CONSUMER_GROUP_DLQ);
        consumerDLQ.setMessageModel(MessageModel.CLUSTERING);
        consumerDLQ.setNamesrvAddr(NAMESRV_ADDR);
        consumerDLQ.setMessageListener(new MessageListenerConcurrentlyHandlerDLQ());
        consumerDLQ.subscribe("%DLQ%" + CONSUMER_GROUP,"*");
        consumerDLQ.start();
    }
}
