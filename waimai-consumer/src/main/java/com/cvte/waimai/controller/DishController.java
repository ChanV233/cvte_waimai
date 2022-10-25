package com.cvte.waimai.controller;


import com.cvte.waimai.exception.AppException;
import com.cvte.waimai.exception.AppExceptionCodeMsg;
import com.cvte.waimai.utils.MsgUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pojo.Dish;
import service.DishesManagementService;

@Api(tags = {"菜品管理"})
@RestController
public class DishController {

    @Autowired
    private DishesManagementService dishesManagementService;

    @ApiOperation("增加菜品")
    @PostMapping(value = "/dish/add")
    public MsgUtils addDish(@RequestBody Dish dish) {
        return this.dishesManagementService.addDish(dish);
    }

    @ApiOperation("删除菜品")
    @PostMapping(value = "/dish/delete")
    public MsgUtils deleteDish(int dishId) {
        if (dishId == 0) {
            throw new AppException(AppExceptionCodeMsg.ILLEGAL_DISH);
        }
        return this.dishesManagementService.deleteDish(dishId);
    }

    @ApiOperation("更新菜品")
    @PostMapping(value = "/dish/update")
    public MsgUtils updateDish(@RequestBody Dish dish) {
        if (dish.getDishId() == 0) {
            throw new AppException(AppExceptionCodeMsg.ILLEGAL_DISH);
        }
        return this.dishesManagementService.updateDish(dish);
    }

    @ApiOperation("根据id查询菜品")
    @GetMapping(value = "/dish/getById")
    public MsgUtils getDishById(int dishId) {
        return this.dishesManagementService.getDishById(dishId);
    }

    @ApiOperation("分页查询菜品")
    @GetMapping(value = "/dish/query")
    public MsgUtils getDishes(int curPage, int pageSize) {
        return this.dishesManagementService.getDishes(curPage, pageSize);
    }
}
