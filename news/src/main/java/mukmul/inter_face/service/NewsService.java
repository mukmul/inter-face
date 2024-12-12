package mukmul.inter_face.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

@Service
public class NewsService
{
    @Value("${newsApi.api.key}")
    private String apiKey;

    private final String BASE_URL = "https://newsapi.org/v2/everything";

    public String getItNews(){
        String url = String.format("%s?q=IT&language=ko&apiKey=%s", BASE_URL, apiKey);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }
}
