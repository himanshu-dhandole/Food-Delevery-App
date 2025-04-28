package himanshu.fooddelevery.ServiceImpl;

import himanshu.fooddelevery.Entity.Cart;
import himanshu.fooddelevery.Repository.CartRepo;
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
    public void addToCart(String foodId) {
      String loggedEmail = registerService.findUserById();
      Optional<Cart> cartOptional = cartRepo.findByUserId(loggedEmail) ;
      Cart cart = cartOptional.orElseGet(()-> new Cart(loggedEmail , new HashMap<>()) ) ;
      Map<String , Integer> items  = cart.getItems() ;
      items.put(foodId , items.getOrDefault(foodId , 0) + 1) ;
      cart.setItems(items) ;
      cart = cartRepo.save(cart) ;

    }
}