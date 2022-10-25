package com.cvte.waimai.controller;

import com.cvte.waimai.exception.AppException;
import com.cvte.waimai.exception.AppExceptionCodeMsg;
import com.cvte.waimai.utils.MsgUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pojo.Dish;
import pojo.Order;
import pojo.OrderDetail;
import service.DishesManagementService;
import service.OrderProducerService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"下单"})
@RestController
public class OrderController {

    @Autowired
    private OrderProducerService orderProducerService;

    @Autowired
    private DishesManagementService dishesManagementService;

    @ApiOperation("下单")
    @PostMapping(value = "/order/producer")
    public MsgUtils order(@RequestBody Order order) {
        if (order.getAddress() == null || order.getOrderDetailList().isEmpty()) {
            throw new AppException(AppExceptionCodeMsg.ILLEGAL_ORDER);
        }
        List<OrderDetail> illegalOrderList = order.getOrderDetailList().stream().filter((orderDetail -> {
            return orderDetail.getCount() == 0 || orderDetail.getDish().getPrice() == 0;
        })).collect(Collectors.toList());
        if (illegalOrderList.size() > 0) {
            throw new AppException(AppExceptionCodeMsg.ILLEGAL_ORDER);
        }
        double price = 0.0;
        for (OrderDetail orderDetail : order.getOrderDetailList()) {
            price += orderDetail.getCount() * orderDetail.getDish().getPrice();
        }
        order.setPrice(price);
        return this.orderProducerService.order(order);
    }


    @ApiOperation("根据id获取订单")
    @GetMapping(value = "/order/get")
    public MsgUtils getOrderById(String orderId) {
        if (orderId == null) {
            throw new AppException(AppExceptionCodeMsg.ILLEGAL_ORDER);
        }
        MsgUtils result = this.orderProducerService.getOrderById(orderId);
        return result;
    }
}
