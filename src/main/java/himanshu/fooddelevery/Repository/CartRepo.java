package himanshu.fooddelevery.Repository;

import himanshu.fooddelevery.Entity.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepo extends MongoRepository<Cart,String> {
    //finding cart by userId
    Optional<Cart> findByUserId(String userId);


    //deleting cart by userId
    void deleteByUserId(String userId);
}
