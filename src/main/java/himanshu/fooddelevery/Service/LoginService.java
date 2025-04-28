package himanshu.fooddelevery.Service;


import himanshu.fooddelevery.RequestDTO.LoginRequest;
import himanshu.fooddelevery.ResponseDTO.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    LoginResponse login(LoginRequest request) ;
}
