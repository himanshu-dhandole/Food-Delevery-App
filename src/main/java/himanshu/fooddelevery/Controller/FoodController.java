package himanshu.fooddelevery.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import himanshu.fooddelevery.RequestDTO.FoodRequest;
import himanshu.fooddelevery.ResponseDTO.FoodResponse;
import himanshu.fooddelevery.Service.UploadService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/food")
@AllArgsConstructor
public class FoodController {

    private final UploadService uploadService ;

    @PostMapping
    public FoodResponse addFood(@RequestPart("food") String foodString ,
                                @RequestPart("file")MultipartFile file) {

        ObjectMapper objectMapper = new ObjectMapper();
        FoodRequest foodRequest = null;
        try{
            foodRequest = objectMapper.readValue(foodString, FoodRequest.class);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        FoodResponse foodResponse = uploadService.addFood(foodRequest, file);
        return foodResponse;
    }
}
