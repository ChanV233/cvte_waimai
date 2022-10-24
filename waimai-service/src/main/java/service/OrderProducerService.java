package service;


import com.cvte.waimai.utils.MsgUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pojo.Order;

import javax.servlet.http.HttpServletRequest;

@Component
@FeignClient(value = "WAIMAI-ORDER-PRODUCER")
public interface OrderProducerService {

    @PostMapping("/order/producer")
    MsgUtils order(Order order);

    @GetMapping("/order/get")
    MsgUtils getOrderById(@RequestParam(value = "orderId") String orderId);
}
