package himanshu.fooddelevery.Repository;

import himanshu.fooddelevery.Entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepo extends MongoRepository<Order, String > {
    List <Order> findByUserId(String userId);
    Optional<Order> findByRazorPayOrderId(String razorPayOrderId);
}
