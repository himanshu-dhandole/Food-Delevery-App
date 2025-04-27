//package himanshu.fooddelevery.ServiceImpl;
//
//import com.uploadcare.api.Client;
//import com.uploadcare.upload.FileUploader;
//import himanshu.fooddelevery.Entity.Food;
//import himanshu.fooddelevery.Repository.FoodRepo;
//import himanshu.fooddelevery.RequestDTO.FoodRequest;
//import himanshu.fooddelevery.ResponseDTO.FoodResponse;
//import himanshu.fooddelevery.Service.FoodService;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@Service
//@AllArgsConstructor
//public class FoodServiceImpl implements FoodService {
//
//    private final Client uploadCareClient;
//    private final FoodRepo foodRepo;
//
//    @Override
//    public String uploadFile(MultipartFile file) {
//        if (file.isEmpty()) {
//            throw new IllegalArgumentException("File is empty");
//        }
//
//        File tempFile = null;
//        try {
//            String originalFilename = file.getOriginalFilename();
//            String extension = "";
//
//            // Extract file extension
//            if (originalFilename != null && originalFilename.contains(".")) {
//                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
//            }
//
//            // Generate a unique temp file with the same extension
//            String uniqueFilename = UUID.randomUUID().toString() + extension;
//            tempFile = File.createTempFile("upload_", extension);
//            file.transferTo(tempFile);
//
//            // Upload using Uploadcare
//            FileUploader uploader = new FileUploader(uploadCareClient, tempFile);
//            uploader.store(true);
//            com.uploadcare.api.File uploadedFile = uploader.upload();
//
//            return uploadedFile.getUrl().toString();
//
//        } catch (IOException e) {
//            throw new RuntimeException("File upload failed due to an I/O error", e);
//        } catch (com.uploadcare.upload.UploadFailureException e) {
//            throw new RuntimeException("File upload failed due to Uploadcare error", e);
//        } finally {
//            if (tempFile != null && tempFile.exists()) {
//                tempFile.delete();
//            }
//        }
//    }
//
//    @Override
//    public FoodResponse addFood(FoodRequest request, MultipartFile file) {
//        Food newFoodEntity = convertToFoodEntity(request);
//        String imageUrl = uploadFile(file);
//        newFoodEntity.setImageurl(imageUrl);
//        newFoodEntity = foodRepo.save(newFoodEntity);
//        return convertToFoodResponse(newFoodEntity);
//    }
//
//    @Override
//    public List<FoodResponse> displayFoods() {
//
//        List<Food> responseList = foodRepo.findAll();
//        return responseList.stream()
//                .map(this::convertToFoodResponse)
//                .collect(Collectors.toList());
//
//    }
//
//    @Override
//    public FoodResponse displayFood(String id) {
//        Food food = foodRepo.findById(id).orElseThrow(() -> new RuntimeException("Food not found"));
//        return convertToFoodResponse(food);
//    }
//
//    // internal server error
//    @Override
//    public Boolean deleteFile(String fileUrl) {
//        try {
//            String fileUUID = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
//            com.uploadcare.api.File file = uploadCareClient.getFile(fileUUID);
//            file.delete();
//        } catch (Exception e) {
//            throw new RuntimeException("File deletion failed", e);
//        }
//        return true;
//    }
//
//    @Override
//    public Void deleteFood(String id) {
//        Food food = foodRepo.findById(id).orElseThrow(() -> new RuntimeException("Food not found"));
//        String imageUrl = food.getImageurl();
//        deleteFile(imageUrl);
//        // dont know
//        foodRepo.deleteById(id) ;
//        return null;
//    }
//
//    private Food convertToFoodEntity(FoodRequest request) {
//        return Food.builder()
//                .name(request.getName())
//                .description(request.getDescription())
//                .price(request.getPrice())
//                .category(request.getCategory())
//                .build();
//    }
//
//    private FoodResponse convertToFoodResponse(Food food) {
//        return FoodResponse.builder()
//                .id(food.getId())
//                .name(food.getName())
//                .description(food.getDescription())
//                .price(food.getPrice())
//                .category(food.getCategory())
//                .imageurl(food.getImageurl())
//                .build();
//    }
//}
package himanshu.fooddelevery.ServiceImpl;

import com.uploadcare.api.Client;
import com.uploadcare.upload.FileUploader;
import himanshu.fooddelevery.Entity.Food;
import himanshu.fooddelevery.Repository.FoodRepo;
import himanshu.fooddelevery.RequestDTO.FoodRequest;
import himanshu.fooddelevery.ResponseDTO.FoodResponse;
import himanshu.fooddelevery.Service.FoodService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final Client uploadCareClient;
    private final FoodRepo foodRepo;

    @Override
    public String uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        File tempFile = null;
        try {
            String originalFilename = file.getOriginalFilename();
            String extension = "";

            // Extract file extension
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            // Generate a unique temp file with the same extension
            String uniqueFilename = UUID.randomUUID().toString() + extension;
            tempFile = File.createTempFile("upload_", extension);
            file.transferTo(tempFile);

            // Upload using Uploadcare
            FileUploader uploader = new FileUploader(uploadCareClient, tempFile);
            uploader.store(true);
            com.uploadcare.api.File uploadedFile = uploader.upload();

            // ✅ Return the public CDN URL instead of API URL
            return "https://ucarecdn.com/" + uploadedFile.getFileId() + "/";

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
        Food newFoodEntity = convertToFoodEntity(request);
        String imageUrl = uploadFile(file);
        newFoodEntity.setImageurl(imageUrl);
        newFoodEntity = foodRepo.save(newFoodEntity);
        return convertToFoodResponse(newFoodEntity);
    }

    @Override
    public List<FoodResponse> displayFoods() {
        List<Food> responseList = foodRepo.findAll();
        return responseList.stream()
                .map(this::convertToFoodResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FoodResponse displayFood(String id) {
        Food food = foodRepo.findById(id).orElseThrow(() -> new RuntimeException("Food not found"));
        return convertToFoodResponse(food);
    }

    @Override
    public Boolean deleteFile(String fileUrl) {
        try {
            String fileUUID = fileUrl.substring(fileUrl.lastIndexOf("/") - 36, fileUrl.lastIndexOf("/"));
            com.uploadcare.api.File file = uploadCareClient.getFile(fileUUID);
            file.delete();
        } catch (Exception e) {
            throw new RuntimeException("File deletion failed", e);
        }
        return true;
    }

    @Override
    public Void deleteFood(String id) {
        Food food = foodRepo.findById(id).orElseThrow(() -> new RuntimeException("Food not found"));
        String imageUrl = food.getImageurl();
        deleteFile(imageUrl);
        foodRepo.deleteById(id);
        return null;
    }

    private Food convertToFoodEntity(FoodRequest request) {
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
                .build();
    }
}
