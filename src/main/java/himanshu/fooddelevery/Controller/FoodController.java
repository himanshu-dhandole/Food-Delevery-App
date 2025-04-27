package himanshu.fooddelevery.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import himanshu.fooddelevery.Repository.FoodRepo;
import himanshu.fooddelevery.RequestDTO.FoodRequest;
import himanshu.fooddelevery.ResponseDTO.FoodResponse;
import himanshu.fooddelevery.Service.FoodService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/food")
@AllArgsConstructor
@CrossOrigin("*")
public class FoodController {

    private final FoodService uploadService ;
    private final FoodRepo foodRepo;

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


    @GetMapping
    public List<FoodResponse> displayFoods() {
        return uploadService.displayFoods() ;
    }

    @GetMapping("/{id}")
    public FoodResponse displayFoodById(@PathVariable String id) {
        return uploadService.displayFood(id) ;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFood(@PathVariable String id) {
        uploadService.deleteFood(id) ;
    }
}
