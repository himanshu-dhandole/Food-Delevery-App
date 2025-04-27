package himanshu.fooddelevery.Service;

import himanshu.fooddelevery.RequestDTO.RegisterRequest;
import himanshu.fooddelevery.ResponseDTO.RegisterResponse;
import org.springframework.stereotype.Service;

@Service
public interface RegisterService {
   RegisterResponse registerUser(RegisterRequest request) ;
}
