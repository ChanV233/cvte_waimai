package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order implements Serializable {
    private String order_id;
    List<OrderDetail> orderDetailList;
    private Address address;
    private double price;
    private long user_id;
    private int state;          //0. 未处理 1.处理成功 2.处理失败
    private Date created_time;
}
