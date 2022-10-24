package com.cvte.waimai.conjob;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FailsOrderConJob {
    @Scheduled(cron ="0 0/1 * * * *")
    public void FailsOrderHandler() {
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        System.out.println(format + ": 处理失败订单");
    }
}
