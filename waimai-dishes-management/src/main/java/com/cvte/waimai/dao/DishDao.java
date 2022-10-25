package com.cvte.waimai.dao;


import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import pojo.Dish;

import java.util.List;

@Repository
@Mapper
public interface DishDao {


    /**
     * 增加菜品
     * @param dish
     */
    @Insert("INSERT INTO wm_dish (dish_name, image_url, price, merchant_id) " +
            "VALUES(#{dish.dish_name}, #{dish.image_url}, #{dish.price}, #{dish.merchant_id})")
    @Options(useGeneratedKeys = true, keyProperty = "dish_id", keyColumn = "dish_id")
    int addDish(@Param("dish") Dish dish);

    /**
     * 删除菜品（软删）
     * @param dishId
     */
    @Update("UPDATE wm_dish SET is_deleted = 1 WHERE dish_id = #{dishId}")
    void deleteDish(@Param("dishId") int dishId);

    /**
     * 更新菜品
     * @param dish
     */
    @Update("UPDATE wm_dish SET dish_name = #{dish.dish_name}, image_url = #{dish.image_url}, " +
            "price = #{dish.price}, merchant_id = #{dish.merchant_id}, is_deleted = #{dish.is_deleted} WHERE dish_id = #{dish.dish_id}")
    void updateDish(@Param("dish") Dish dish);

    /**
     * 根据id查询菜品
     * @param dishId
     * @return
     */
    @Select("SELECT * FROM wm_dish WHERE dish_id = #{dishId}")
    Dish getDishById(@Param("dishId") int dishId);

    /**
     * 分页查询菜品
     * @param curPage
     * @param pageSize
     * @return
     */
    @Select("SELECT * FROM wm_dish WHERE is_deleted = '0' LIMIT #{curPage}, #{pageSize}")
    List<Dish> getDishes(@Param("curPage") int curPage, @Param("pageSize") int pageSize);



}
