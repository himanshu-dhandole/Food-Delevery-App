package himanshu.fooddelevery.Repository;

import himanshu.fooddelevery.Entity.Food;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoodRepo extends MongoRepository<Food,String> {
}
