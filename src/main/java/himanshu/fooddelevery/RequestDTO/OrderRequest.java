package himanshu.fooddelevery.RequestDTO;

import himanshu.fooddelevery.Entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderRequest {
    private String address;
    private List<OrderItem> orderedItems;
    private double amount ;
    private String contact;
    private String email;
    private String orderStatus ;
}