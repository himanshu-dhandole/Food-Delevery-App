package himanshu.fooddelevery.Service;

import himanshu.fooddelevery.RequestDTO.FoodRequest;
import himanshu.fooddelevery.ResponseDTO.FoodResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface FoodService {

    String uploadFile(MultipartFile file) ;

    FoodResponse addFood(FoodRequest request , MultipartFile file) ;

    List<FoodResponse> displayFoods() ;

    FoodResponse displayFood(String id) ;

    Boolean deleteFile(String fileUrl) ;

    Void deleteFood(String id) ;
}