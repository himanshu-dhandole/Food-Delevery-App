package himanshu.fooddelevery.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "food")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Food {
    private String id ;
    private String name;
    private String description;
    private double price ;
    private String category ;
    private String imageurl ;
}
