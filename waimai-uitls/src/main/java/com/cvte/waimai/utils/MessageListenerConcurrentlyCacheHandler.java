package com.cvte.waimai.utils;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@DependsOn("springBeanUtils")
public class MessageListenerConcurrentlyCacheHandler implements MessageListenerConcurrently {

    private RedisUtils redisUtils;

    public MessageListenerConcurrentlyCacheHandler() {
        this.redisUtils = SpringBeanUtils.getBean(RedisUtils.class);
    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        try {
            for (MessageExt msg : list) {
                String cacheKey = new String(msg.getBody());
                this.redisUtils.remove(cacheKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
