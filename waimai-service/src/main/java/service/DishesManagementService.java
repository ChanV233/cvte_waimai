package service;


import com.cvte.waimai.utils.MsgUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pojo.Dish;

@Component
@FeignClient(value = "WAIMAI-DISHES-MANAGEMENT")
public interface DishesManagementService {

    @PostMapping("/dish/add")
    MsgUtils addDish(Dish dish);

    @PostMapping("/dish/delete")
    MsgUtils deleteDish(@RequestParam(value = "dishId") int dishId);

    @PostMapping("/dish/update")
    MsgUtils updateDish(Dish dish);

    @GetMapping("/dish/getById")
    MsgUtils getDishById(@RequestParam(value = "dishId") int dishId);

    @GetMapping("/dish/query")
    MsgUtils getDishes(@RequestParam(value = "curPage") int curPage, @RequestParam(value = "pageSize") int pageSize);
}
