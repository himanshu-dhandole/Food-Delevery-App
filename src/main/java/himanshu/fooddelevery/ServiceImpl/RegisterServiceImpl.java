package himanshu.fooddelevery.ServiceImpl;

import himanshu.fooddelevery.Entity.User;
import himanshu.fooddelevery.Repository.UserRepo;
import himanshu.fooddelevery.RequestDTO.RegisterRequest;
import himanshu.fooddelevery.ResponseDTO.RegisterResponse;
import himanshu.fooddelevery.Service.AuthenticationFacade;
import himanshu.fooddelevery.Service.RegisterService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final UserRepo userRepo ;
    private final BCryptPasswordEncoder passwordEncoder ;
    private final AuthenticationFacade authenticationFacade ;

    @Override
    public RegisterResponse registerUser(RegisterRequest request) {
        User newUser =  convertToUser(request); ;
        newUser = userRepo.save(newUser);
        return convertToRegisterResponse(newUser);
    }

    @Override
    public String findUserById() {
       String loggedEmail = authenticationFacade.getAuthentication().getName();
       User loggedUser = userRepo.findByEmail(loggedEmail).orElseThrow(()->new UsernameNotFoundException("User not found with email: "));
       return loggedUser.getId() ;
    }

    public User convertToUser(RegisterRequest request) {
        return User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
    }

    public RegisterResponse convertToRegisterResponse(User user) {
        return RegisterResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
