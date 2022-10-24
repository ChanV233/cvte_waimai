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
    private int dish_id;
    private String dish_name;
    private String image_url;
    private double price;
    private int merchant_id;
    private int is_deleted;
    private Date created_time;
}
