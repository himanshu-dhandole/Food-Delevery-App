package himanshu.fooddelevery.Entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderItem {

    private String foodId ;
    private String name ;
    private int quantity ;
    private double price ;
    private String category ;
    private String imageUrl ;
    private String description ;
    private List<OrderItem> orderedItems;
}
