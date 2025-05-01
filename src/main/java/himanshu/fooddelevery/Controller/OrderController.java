package himanshu.fooddelevery.Controller;


import com.razorpay.RazorpayException;
import himanshu.fooddelevery.RequestDTO.OrderRequest;
import himanshu.fooddelevery.ResponseDTO.OrderResponse;
import himanshu.fooddelevery.Service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    OrderResponse createOrderWithPayment(@RequestBody OrderRequest request) throws RazorpayException {
        return orderService.createOrderWithPayment(request);
    }

    @PostMapping("/verify")
    public void verifyPayment (@RequestBody Map<String , String> paymentData ) {
        orderService.verifyPayment(paymentData , "paid");
    }

    @GetMapping
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders() ;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable String id) {
        orderService.deleterOrder(id) ;
    }

    @GetMapping("/all")
    public List<OrderResponse> getOrdersOfAllUsers() {
        return orderService.getOrdersOfAllUsers() ;
    }

    @PatchMapping("/update/{orderId}")
    public void updateOrderStatus(@PathVariable String orderId , @RequestParam String status) {
        orderService.updateOrderStatus(orderId , status) ;
    }
}
