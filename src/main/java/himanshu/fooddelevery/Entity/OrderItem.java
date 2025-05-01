package himanshu.fooddelevery.Entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItem {

    private String foodId ;
    private String name ;
    private int qunatity ;
    private double price ;
    private String category ;
    private String imageUrl ;
    private String description ;
}
