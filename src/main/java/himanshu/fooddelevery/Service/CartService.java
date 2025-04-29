package himanshu.fooddelevery.Service;

import himanshu.fooddelevery.RequestDTO.CartRequest;
import himanshu.fooddelevery.ResponseDTO.CartResponse;
import org.springframework.stereotype.Service;

@Service
public interface CartService {
    CartResponse addToCart(CartRequest request) ;

    CartResponse getCart( ) ;

    void clearCart() ;

    CartResponse deleteItem(CartRequest request) ;
}
