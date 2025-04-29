package himanshu.fooddelevery.ServiceImpl;

import himanshu.fooddelevery.Entity.Cart;
import himanshu.fooddelevery.Repository.CartRepo;
import himanshu.fooddelevery.RequestDTO.CartRequest;
import himanshu.fooddelevery.ResponseDTO.CartResponse;
import himanshu.fooddelevery.Service.CartService;
import himanshu.fooddelevery.Service.RegisterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepo cartRepo;
    private final RegisterService registerService;

    @Override
    public CartResponse addToCart(CartRequest request) {
            String loggedEmail = registerService.findUserById();

            Optional<Cart> cartOptional = cartRepo.findByUserId(loggedEmail);
            Cart cart = cartOptional.orElseGet(() -> new Cart(loggedEmail, new HashMap<>()));

            Map<String, Integer> items = cart.getItems();
            items.put(request.getFoodId(), items.getOrDefault(request.getFoodId(), 0) + 1);
            cart.setItems(items);

            cart = cartRepo.save(cart);
            return convertToResponse(cart);

    }

    @Override
    public CartResponse getCart() {
        String loggedEmail = registerService.findUserById();
        Cart cart = cartRepo.findByUserId(loggedEmail).orElse(new Cart(null , loggedEmail , new HashMap<>() ) ) ;
        return convertToResponse(cart);
    }

    @Override
    public void clearCart() {
        String loggedEmail = registerService.findUserById();
        cartRepo.deleteByUserId(loggedEmail);
    }

    @Override
    public CartResponse deleteItem(CartRequest request) {
        String loggedEmail = registerService.findUserById();
        Cart cart = cartRepo.findByUserId(loggedEmail).orElseThrow(()->new RuntimeException("Cart not found"));
        Map<String , Integer> items  = cart.getItems() ;
        if(items.containsKey(request.getFoodId())) {
            int quantity = items.get(request.getFoodId()) ;
            if(quantity > 1 ) {
                items.put(request.getFoodId(), quantity - 1) ;
            }
            else {
                items.remove(request.getFoodId()) ;
            }
            cart = cartRepo.save(cart) ;
        }
        return convertToResponse(cart);
    }

    private CartResponse convertToResponse(Cart cart) {
        return CartResponse.builder()
                .id(cart.getId())
                .userId(cart.getUserId())
                .items(cart.getItems())
                .build();
    }
}