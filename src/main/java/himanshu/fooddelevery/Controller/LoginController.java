package himanshu.fooddelevery.Controller;


import himanshu.fooddelevery.RequestDTO.LoginRequest;
import himanshu.fooddelevery.ResponseDTO.LoginResponse;
import himanshu.fooddelevery.Service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse userLogin(@RequestBody LoginRequest request) {
        return loginService.login(request);
    }

}
