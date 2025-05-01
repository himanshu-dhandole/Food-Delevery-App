package himanshu.fooddelevery.Service;

import com.razorpay.RazorpayException;
import himanshu.fooddelevery.RequestDTO.OrderRequest;
import himanshu.fooddelevery.ResponseDTO.OrderResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface OrderService {
    OrderResponse createOrderWithPayment(OrderRequest request)throws RazorpayException;

    void verifyPayment(Map<String , String> paymentData , String status) ;

    List<OrderResponse> getAllOrders();

    void deleterOrder(String id);

    List<OrderResponse> getOrdersOfAllUsers() ;

    void updateOrderStatus(String orderId, String status);
}
