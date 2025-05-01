package himanshu.fooddelevery.Service;

import com.razorpay.RazorpayException;
import himanshu.fooddelevery.RequestDTO.OrderRequest;
import himanshu.fooddelevery.ResponseDTO.OrderResponse;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    OrderResponse createOrderWithPayment(OrderRequest request)throws RazorpayException;
}
