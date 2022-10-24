package com.cvte.waimai.controller;

import com.cvte.waimai.service.DishService;
import com.cvte.waimai.utils.MsgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pojo.Dish;

@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public MsgUtils addDish(@RequestBody Dish dish) {
        return this.dishService.addDish(dish);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public MsgUtils deleteDish(int dishId) throws InterruptedException {
        return this.dishService.deleteDish(dishId);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public MsgUtils updateDish(@RequestBody Dish dish) throws InterruptedException {
        return this.dishService.updateDish(dish);
    }

    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    @ResponseBody
    public MsgUtils getDishById(int dishId) {
        return this.dishService.getDishById(dishId);
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public MsgUtils getDishes(int curPage, int pageSize) {
        return this.dishService.getDishes(curPage, pageSize);
    }



}
