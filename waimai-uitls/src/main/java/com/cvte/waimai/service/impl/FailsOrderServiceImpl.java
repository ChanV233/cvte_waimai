package com.cvte.waimai.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cvte.waimai.dao.FailsOrderDao;
import com.cvte.waimai.pojo.FailsOrder;
import com.cvte.waimai.pojo.FailsOrderDB;
import com.cvte.waimai.service.FailsOrderService;
import com.cvte.waimai.utils.SendCallbackHandler;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class FailsOrderServiceImpl implements FailsOrderService {

    @Autowired
    private FailsOrderDao failsOrderDao;

    @Override
    public void addFailsOrder(String failsOrderStr) {
        FailsOrder failsOrder = JSONObject.parseObject(failsOrderStr, FailsOrder.class);
        String orderDetailList = JSONObject.toJSONString(failsOrder.getOrderDetailList());
        String address = JSONObject.toJSONString(failsOrder.getAddress());
        FailsOrderDB failsOrderDB = new FailsOrderDB(failsOrder.getOrder_id(), orderDetailList, address, failsOrder.getPrice(), failsOrder.getUser_id(), failsOrder.getState());
        this.failsOrderDao.addFailsOrder(failsOrderDB);
    }
}
