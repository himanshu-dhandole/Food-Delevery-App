package himanshu.fooddelevery.Entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Document(collection = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    private String id;
    private String userId;
    private String address;
    private String contact;
    private String email;
    private List<OrderItem> orderedItems;
    private double amount ;
    private String paymentStatus;
    private String razorPayOrderId;
    private String razorPaySignature;
    private String orderStatus;

}
