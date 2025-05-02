package himanshu.fooddelevery.Controller;


import himanshu.fooddelevery.RequestDTO.CartRequest;
import himanshu.fooddelevery.ResponseDTO.CartResponse;
import himanshu.fooddelevery.Service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@AllArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;


    @PostMapping
    public CartResponse addToCart (@RequestBody CartRequest request) {
        String foodId = request.getFoodId() ;
        if(foodId == null || foodId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "Food ID is required") ;
        }
       return cartService.addToCart(request);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CartResponse getCart() {
        return cartService.getCart() ;
    }

    @DeleteMapping("/clear")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart () {
        cartService.clearCart();
    }

    @PostMapping("/remove")
    @ResponseStatus(HttpStatus.OK)
    public CartResponse deleteItem (@RequestBody CartRequest request) {
        String foodId = request.getFoodId() ;
        if(foodId == null || foodId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "Food ID is required") ;
        }
        return cartService.deleteItem(request);
    }

}
