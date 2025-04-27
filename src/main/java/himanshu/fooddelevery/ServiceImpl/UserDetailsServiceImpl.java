package himanshu.fooddelevery.ServiceImpl;

import himanshu.fooddelevery.Entity.User;
import himanshu.fooddelevery.Repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepo ;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User nonAuthUser = userRepo.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found with email: " + email));
        return new org.springframework.security.core.userdetails.User(nonAuthUser.getEmail() , nonAuthUser.getPassword() , Collections.emptyList());
    }
}
