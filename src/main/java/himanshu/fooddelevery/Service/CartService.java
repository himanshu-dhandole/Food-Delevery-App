package himanshu.fooddelevery.Service;

import org.springframework.stereotype.Service;

@Service
public interface CartService {
    void addToCart(String foodId) ;
}
