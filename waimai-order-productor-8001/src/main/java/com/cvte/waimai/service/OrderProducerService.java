package com.cvte.waimai.service;

import com.cvte.waimai.utils.MsgUtils;
import pojo.Order;

public interface OrderProducerService {

    MsgUtils order(Order order);

    MsgUtils getOrderById(String orderID);

}
