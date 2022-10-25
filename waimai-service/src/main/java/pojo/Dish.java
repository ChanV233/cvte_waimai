package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Dish {
    private int dishId;
    private String dishName;
    private String imageUrl;
    private double price;
    private int merchantId;
    private int isDeleted;
    private Date createdTime;
}
