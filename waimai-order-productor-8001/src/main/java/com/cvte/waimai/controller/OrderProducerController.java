package com.cvte.waimai.controller;


import com.cvte.waimai.service.OrderProducerService;
import com.cvte.waimai.utils.MsgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pojo.Order;

@RestController
@RequestMapping("/order")
public class OrderProducerController {

    @Autowired
    private OrderProducerService orderProducerService;

    @RequestMapping(value = "/producer", method = RequestMethod.POST)
    @ResponseBody
    public MsgUtils order(@RequestBody Order order) {
        return this.orderProducerService.order(order);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public MsgUtils getOrderById(String orderId) {
        return this.orderProducerService.getOrderById(orderId);
    }

}
