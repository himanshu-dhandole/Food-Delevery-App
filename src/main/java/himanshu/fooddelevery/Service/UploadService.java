package himanshu.fooddelevery.Service;

import himanshu.fooddelevery.RequestDTO.FoodRequest;
import himanshu.fooddelevery.ResponseDTO.FoodResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UploadService {
    String uploadFile(MultipartFile file) ;
    FoodResponse addFood(FoodRequest request , MultipartFile file) ;
}