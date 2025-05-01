package himanshu.fooddelevery.ServiceImpl;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import himanshu.fooddelevery.Entity.Order;
import himanshu.fooddelevery.Repository.OrderRepo;
import himanshu.fooddelevery.RequestDTO.OrderRequest;
import himanshu.fooddelevery.ResponseDTO.OrderResponse;
import himanshu.fooddelevery.Service.AuthenticationFacade;
import himanshu.fooddelevery.Service.OrderService;
import himanshu.fooddelevery.Service.RegisterService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final RegisterService registerService;

    @Value("${razorpay_apiKey}")
    private String RAZORPAY_API_KEY;
    @Value("${razorpay_secretKey}")
    private String RAZORPAY_SECRET_KEY;

    @Override
    public OrderResponse createOrderWithPayment(OrderRequest request) throws RazorpayException {
        Order order = convertToOrder(request);
        order = orderRepo.save(order);

        // new razorpay payment order
        RazorpayClient razorpayClient = new RazorpayClient(RAZORPAY_API_KEY, RAZORPAY_SECRET_KEY);
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", order.getAmount());
        orderRequest.put("currency", "INR");
        orderRequest.put("payment_capture", 1);

        com.razorpay.Order razorPayOrder = razorpayClient.orders.create(orderRequest);
        String loggedEmail = registerService.findUserById();

        order.setRazorPayOrderId(razorPayOrder.get("id"));
        order.setUserId(loggedEmail);

        order = orderRepo.save(order);

        return convertToOrderResponse(order);
    }

    private OrderResponse convertToOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .address(order.getAddress())
                .amount(order.getAmount())
                .paymentStatus(order.getPaymentStatus())
                .RazorPayOrderId(order.getRazorPayOrderId())
                .OrderStatus(order.getOrderStatus())
                .email(order.getEmail())
                .contact(order.getContact())
                .build();
    }

    private Order convertToOrder(OrderRequest request) {
        return Order.builder()
                .orderedItems(request.getOrderedItems())
                .amount(request.getAmount())
                .email(request.getEmail())
                .contact(request.getContact())
                .address(request.getAddress())
                .orderStatus(request.getOrderStatus())
                .build();
    }
}
