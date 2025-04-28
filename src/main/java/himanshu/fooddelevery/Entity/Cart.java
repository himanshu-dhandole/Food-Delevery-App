package himanshu.fooddelevery.Entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "cart")
public class Cart {
    @Id
    private String id;
    private String userId ;
    private Map<String , Integer> items = new HashMap<>();

    public Cart (String id , Map<String , Integer> items ) {
        this.id = id;
        this.items = items ;
    }
}
