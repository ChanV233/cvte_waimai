package com.cvte.waimai.utils;

import com.alibaba.fastjson.JSONObject;
import com.cvte.waimai.pojo.Order;
import com.cvte.waimai.service.OrderService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
@DependsOn("springBeanUtils")
public class MessageListenerConcurrentlyHandler implements MessageListenerConcurrently {

    private OrderService orderService;

    private static Logger logger = LogManager.getLogger(MessageListenerConcurrentlyHandler.class);

    public MessageListenerConcurrentlyHandler() {
        this.orderService = SpringBeanUtils.getBean(OrderService.class);
    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

        try {
            List<Order> orders = new ArrayList<>();
            for (MessageExt msg : msgs) {
                Order order = JSONObject.parseObject(new String(msg.getBody(), StandardCharsets.UTF_8), Order.class);
                orders.add(order);
                this.orderService.addOrderBatch(orders);
                logger.info(order.getOrder_id() + " dump success");
            }
        }catch (Exception e) {
            //落表失败, 重试
            e.printStackTrace();
            logger.error("order dump fails");
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
