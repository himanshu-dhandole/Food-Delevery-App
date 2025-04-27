package himanshu.fooddelevery.Controller;

import himanshu.fooddelevery.RequestDTO.RegisterRequest;
import himanshu.fooddelevery.ResponseDTO.RegisterResponse;
import himanshu.fooddelevery.Service.RegisterService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class RegisterController {
    private final RegisterService registerService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse registerNewUser (@RequestBody RegisterRequest request) {
        return registerService.registerUser(request);
    }
}
