package com.cvte.waimai.dao;

import com.cvte.waimai.pojo.OrderDB;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface OrderDao {

    @Insert("INSERT IGNORE INTO wm_order (order_id, order_detail_list, address, price, user_id, state) " +
            "VALUES(#{order.order_id}, #{order.orderDetailList}, #{order.address}, #{order.price}, #{order.user_id}, #{order.state})")
    void addOrder(@Param("order") OrderDB order);

    @Insert("<script> " +
            "INSERT IGNORE INTO wm_order (order_id, order_detail_list, address, price, user_id, state) " +
            "VALUES " +
            "<foreach collection=\"orders\" item = \"order\" separator=\",\"> " +
            "(#{order.order_id}, #{order.orderDetailList}, #{order.address}, #{order.price}, #{order.user_id}, #{order.state}) " +
            "</foreach>" +
            "</script>"
        )
    void addOrderBatch(@Param("orders") List<OrderDB> orders);

}
