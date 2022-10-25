package com.cvte.waimai.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cvte.waimai.dao.OrderDao;
import com.cvte.waimai.exception.AppException;
import com.cvte.waimai.exception.AppExceptionCodeMsg;
import com.cvte.waimai.service.OrderProducerService;
import com.cvte.waimai.utils.*;
import io.micrometer.core.instrument.Meter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Order;
import pojo.OrderDB;

@Service
public class OrderProducerServiceImpl implements OrderProducerService {

    private static Logger logger = LogManager.getLogger(OrderProducerServiceImpl.class);

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderMqUtils orderMqUtils;

    @Override
    public MsgUtils order(Order order) {
        //fill orderId
        String orderId = idGenerator.nextId();
        order.setOrderId(orderId);
        String orderStr = JSONObject.toJSONString(order);
        this.orderMqUtils.sendMessage(orderStr, orderId);
//        logger.info(order.getOrder_id() + " order success");
        return MsgUtils.success(order);
    }

    @Override
    public MsgUtils getOrderById(String orderID) {
        OrderDB order = this.orderDao.getOrderById(orderID);
        if (order == null) {
            throw new AppException(AppExceptionCodeMsg.ORDER_NOT_FOUND);
        }
        return MsgUtils.success(order);
    }
}
