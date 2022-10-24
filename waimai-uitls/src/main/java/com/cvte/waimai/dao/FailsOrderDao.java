package com.cvte.waimai.dao;

import com.cvte.waimai.pojo.FailsOrderDB;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface FailsOrderDao {

    @Insert("INSERT IGNORE INTO wm_fails_order (order_id, order_detail_list, address, price, user_id, state) " +
            "VALUES(#{order.order_id}, #{order.orderDetailList}, #{order.address}, #{order.price}, #{order.user_id}, #{order.state})")
    void addFailsOrder(@Param("order") FailsOrderDB failsOrderDB);

}
