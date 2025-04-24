package himanshu.fooddelevery.Config;

import com.uploadcare.api.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UploadCareConfig {
    @Value("${uploadcare.publicKey}")
    private String accessKey;
    @Value("${uploadcare.secretKey}")
    private String secretKey;

    @Bean
    public Client uploadCareClient() {
        return new Client(accessKey, secretKey);
    }

}