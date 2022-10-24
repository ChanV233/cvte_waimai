package com.cvte.waimai.service;

import com.cvte.waimai.pojo.Order;

import java.util.List;

public interface OrderService {
    void addOrder(Order order);

    void addOrderBatch(List<Order> orders);
}