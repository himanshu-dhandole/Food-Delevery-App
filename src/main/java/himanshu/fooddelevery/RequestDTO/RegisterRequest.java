package himanshu.fooddelevery.RequestDTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String name ;
    private String email ;
    private String password ;
}
