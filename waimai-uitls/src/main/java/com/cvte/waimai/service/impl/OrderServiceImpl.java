package com.cvte.waimai.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cvte.waimai.dao.OrderDao;
import com.cvte.waimai.pojo.Order;
import com.cvte.waimai.pojo.OrderDB;
import com.cvte.waimai.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public void addOrder(Order order) {
        String orderDetailList = JSONObject.toJSONString(order.getOrderDetailList());
        String address = JSONObject.toJSONString(order.getAddress());
        OrderDB orderDB = new OrderDB(order.getOrder_id(), orderDetailList, address, order.getPrice(), order.getUser_id(), order.getState());
        this.orderDao.addOrder(orderDB);
    }

    @Override
    public void addOrderBatch(List<Order> orders) {
        List<OrderDB> list = new ArrayList<>();
        for (Order order : orders) {
            String orderDetailList = JSONObject.toJSONString(order.getOrderDetailList());
            String address = JSONObject.toJSONString(order.getAddress());
            OrderDB orderDB = new OrderDB(order.getOrder_id(), orderDetailList, address, order.getPrice(), order.getUser_id(), order.getState());
            list.add(orderDB);
        }
        this.orderDao.addOrderBatch(list);
    }
}