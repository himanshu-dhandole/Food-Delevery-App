package himanshu.fooddelevery.ResponseDTO;

import himanshu.fooddelevery.Entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderResponse {
    private String id;
    private String userId;
    private String address;
    private String contact;
    private String email;
    private double amount ;
    private String paymentStatus;
    private String RazorPayOrderId;
    private String OrderStatus;
}
