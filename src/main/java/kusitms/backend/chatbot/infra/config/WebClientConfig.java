package kusitms.backend.chatbot.infra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${clova.api.url}")
    private String apiUrl;

    @Value("${clova.api.api-key}")
    private String apiKey;

    @Value("${clova.api.api-gateway-key}")
    private String apiGatewayKey;

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl(apiUrl)
                .defaultHeader("X-NCP-CLOVASTUDIO-API-KEY", apiKey)
                .defaultHeader("X-NCP-APIGW-API-KEY", apiGatewayKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
