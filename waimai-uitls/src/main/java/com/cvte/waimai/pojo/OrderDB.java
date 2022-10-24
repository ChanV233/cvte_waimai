package com.cvte.waimai.pojo;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDB {
    private String order_id;
    private String orderDetailList;
    private String address;
    private double price;
    private long user_id;
    private int state;          //0. 未处理 1.处理成功 2.处理失败
    private Date created_time;

    public OrderDB(String order_id, String orderDetailList, String address, double price, long user_id, int state) {
        this.order_id = order_id;
        this.orderDetailList = orderDetailList;
        this.address = address;
        this.price = price;
        this.user_id = user_id;
        this.state = state;
    }
}