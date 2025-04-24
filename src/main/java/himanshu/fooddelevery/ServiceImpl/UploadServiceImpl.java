package himanshu.fooddelevery.ServiceImpl;

import com.uploadcare.api.Client;
import com.uploadcare.upload.FileUploader;
import himanshu.fooddelevery.Entity.Food;
import himanshu.fooddelevery.Repository.FoodRepo;
import himanshu.fooddelevery.RequestDTO.FoodRequest;
import himanshu.fooddelevery.ResponseDTO.FoodResponse;
import himanshu.fooddelevery.Service.UploadService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UploadServiceImpl implements UploadService {

    private final Client uploadCareClient;
    private final FoodRepo foodRepo ;

    @Override
    public String uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        File tempFile = null;
        try {
            String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            tempFile = File.createTempFile("upload", null);
            file.transferTo(tempFile);
            FileUploader uploader = new FileUploader(uploadCareClient, tempFile);
            uploader.store(true);
            com.uploadcare.api.File uploadedFile = uploader.upload();

            return uploadedFile.getUrl().toString();


        } catch (IOException e) {
            throw new RuntimeException("File upload failed due to an I/O error", e);
        } catch (com.uploadcare.upload.UploadFailureException e) {
            throw new RuntimeException("File upload failed due to Uploadcare error", e);
        } finally {
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    @Override
    public FoodResponse addFood(FoodRequest request, MultipartFile file) {
        Food newFoodEntity = convertToFoodEntity(request) ;
        String url = uploadFile(file) ;
        newFoodEntity.setImageurl(url);
        newFoodEntity = foodRepo.save(newFoodEntity);
        return convertToFoodResponse(newFoodEntity) ;
    }
    private Food convertToFoodEntity (FoodRequest request) {
        return Food.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .category(request.getCategory())
                .build();
    }
    private FoodResponse convertToFoodResponse(Food food) {
        return FoodResponse.builder()
                .id(food.getId())
                .name(food.getName())
                .description(food.getDescription())
                .price(food.getPrice())
                .category(food.getCategory())
                .imageurl(food.getImageurl())
                .build() ;
    }
}