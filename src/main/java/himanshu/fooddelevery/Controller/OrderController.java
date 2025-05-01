package himanshu.fooddelevery.Controller;


import himanshu.fooddelevery.Service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ordres")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")


}
