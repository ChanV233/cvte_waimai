package com.cvte.waimai.service;

import com.cvte.waimai.utils.MsgUtils;
import pojo.Dish;

import java.util.List;

public interface DishService {

    MsgUtils addDish(Dish dish);

    MsgUtils deleteDish(int dishId) throws InterruptedException;

    MsgUtils updateDish(Dish dish) throws InterruptedException;

    MsgUtils getDishById(int dishId);

    MsgUtils getDishes(int curPage, int pageSize);
}
