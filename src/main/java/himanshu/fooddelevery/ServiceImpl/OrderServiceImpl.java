package himanshu.fooddelevery.ServiceImpl;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import himanshu.fooddelevery.Entity.Order;
import himanshu.fooddelevery.Repository.CartRepo;
import himanshu.fooddelevery.Repository.OrderRepo;
import himanshu.fooddelevery.RequestDTO.OrderRequest;
import himanshu.fooddelevery.ResponseDTO.OrderResponse;
import himanshu.fooddelevery.Service.AuthenticationFacade;
import himanshu.fooddelevery.Service.OrderService;
import himanshu.fooddelevery.Service.RegisterService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private  OrderRepo orderRepo;
    @Autowired
    private  RegisterService registerService;
    @Autowired
    private CartRepo cartRepo;

    @Value("${razorpay.apiKey}")
    private String apiKey;
    @Value("${razorpay.secretKey}")
    private String secretKey;

    @Override
    public OrderResponse createOrderWithPayment(OrderRequest request) throws RazorpayException {
        Order order = convertToOrder(request);
        order = orderRepo.save(order);

        // new razorpay payment order
        RazorpayClient razorpayClient = new RazorpayClient(apiKey, secretKey);
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", (int)(order.getAmount() * 100));
        orderRequest.put("currency", "INR");
        orderRequest.put("payment_capture", 1);

        com.razorpay.Order razorPayOrder = razorpayClient.orders.create(orderRequest);
        String loggedEmail = registerService.findUserById();

        order.setRazorPayOrderId(razorPayOrder.get("id"));
        order.setUserId(loggedEmail);

        order = orderRepo.save(order);

        return convertToOrderResponse(order);
    }

    @Override
    public void verifyPayment(Map<String, String> paymentData, String status) {
        String orderId = paymentData.get("razorpay_order_id");
        Order existingOrder = orderRepo.findByRazorPayOrderId(orderId).orElseThrow(()-> new RuntimeException("Order not found"));
        existingOrder.setPaymentStatus(status);
        existingOrder.setRazorPaySignature(paymentData.get("razorpay_signature"));
        existingOrder.setRazorPayPaymentId(paymentData.get("razorpay_payment_id"));
        orderRepo.save(existingOrder);
        if ("paid".equalsIgnoreCase(status)) {
            cartRepo.deleteByUserId(existingOrder.getUserId());
        }
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        String loggedEmail = registerService.findUserById();
        List<Order> orders = orderRepo.findByUserId(loggedEmail) ;
        return orders.stream().map(this::convertToOrderResponse).collect(Collectors.toList()) ;
    }

    @Override
    public void deleterOrder(String id) {
        orderRepo.deleteById(id) ;
    }

    @Override
    public List<OrderResponse> getOrdersOfAllUsers() {
        List<Order> allOrders = orderRepo.findAll();
        return allOrders.stream().map(this::convertToOrderResponse).collect(Collectors.toList()) ;
    }

    @Override
    public void updateOrderStatus(String orderId, String status) {
        Order order = orderRepo.findById(orderId).orElseThrow(()-> new RuntimeException("Order not found"));
        order.setOrderStatus(status);
        orderRepo.save(order);
    }

    private OrderResponse convertToOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .address(order.getAddress())
                .amount(order.getAmount())
                .paymentStatus(order.getPaymentStatus())
                .razorPayOrderId(order.getRazorPayOrderId())
                .orderStatus(order.getOrderStatus())
                .email(order.getEmail())
                .contact(order.getContact())
                .orderedItems(order.getOrderedItems())
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
