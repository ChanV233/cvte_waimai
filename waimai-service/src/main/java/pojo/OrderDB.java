package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDB {
    private String orderId;
    private String orderDetailList;
    private String address;
    private double price;
    private long userId;
    private int state;          //0. 未处理 1.处理成功 2.处理失败
    private Date createdTime;

    public OrderDB(String order_id, String orderDetailList, String address, double price, long user_id, int state) {
        this.orderId = order_id;
        this.orderDetailList = orderDetailList;
        this.address = address;
        this.price = price;
        this.userId = user_id;
        this.state = state;
    }
}
