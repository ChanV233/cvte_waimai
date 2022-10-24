package com.cvte.waimai.utils;

import com.cvte.waimai.service.FailsOrderService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;

public class SendCallbackHandler implements SendCallback {

    private FailsOrderService failsOrderService;

    private String messageBody;

    private static Logger logger = LogManager.getLogger(SendCallbackHandler.class);

    public SendCallbackHandler(String messageBody) {
        this.failsOrderService = SpringBeanUtils.getBean(FailsOrderService.class);
        this.messageBody = messageBody;
    }

    @Override
    public void onSuccess(SendResult sendResult) {
        logger.info(sendResult.getMsgId() + " send success");
    }

    @Override
    public void onException(Throwable throwable) {
        logger.error(throwable.getMessage());
        try {
            this.failsOrderService.addFailsOrder(messageBody);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
