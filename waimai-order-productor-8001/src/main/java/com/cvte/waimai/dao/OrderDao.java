package com.cvte.waimai.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import pojo.OrderDB;

@Mapper
@Repository
public interface OrderDao {

    @Select("SELECT order_id, order_detail_list, address, price, " +
            "user_id, state, created_time FROM wm_order WHERE order_id = #{orderId}")
    OrderDB getOrderById(@Param("orderId") String orderId);

}
