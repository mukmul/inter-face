package mukmul.inter_face.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mukmul.inter_face.news.NewsEntity;
import mukmul.inter_face.news.NewsRepository;
import mukmul.inter_face.news.NewsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@Service
public class NewsService
{
    @Value("${newsApi.api.key}")
    private String apiKey;

    @Autowired
    private  NewsRepository newsRepository;
    private final String BASE_URL = "https://newsapi.org/v2/everything";

    public ArrayList<NewsEntity> getItNews()
    {
        String url = String.format("%s?q=IT&language=ko&apiKey=%s", BASE_URL, apiKey);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String jsonResponse = response.getBody();
        ArrayList<NewsEntity> newsArray=new ArrayList<>();

        try
        {
            ObjectMapper objectMapper = new ObjectMapper();
            NewsResponse newsResponse = objectMapper.readValue(jsonResponse, NewsResponse.class);
            if (!newsResponse.getStatus().equals("error")) {
                for(NewsResponse.Article a :newsResponse.getArticles())
                    newsArray.add(saveNewsArticles(a));
                return newsArray;
            }
        } catch (JsonProcessingException e)
        {
            return newsArray;
        }
        return newsArray;
    }

    public NewsEntity saveNewsArticles(NewsResponse.Article article)
    {
        NewsEntity newsEntity= NewsEntity.builder()
                .newsAuthor(article.getAuthor())
                .newsContent(article.getDescription())
                .newsTitle(article.getTitle())
                .newsSource(article.getSource().getName())
                .newsCreatedAt(NewsEntity.formatCreatedAt(article.getPublishedAt()))
                .build();
        return newsRepository.save(newsEntity);
    }

    public NewsEntity getNewsById(Long newsId)
    {
        return newsRepository.findById(newsId)
                .orElseThrow(()-> new NoSuchElementException(newsId+"아이디의 뉴스는 없습니다"));
    }

    public void increaseNewsViews(Long newsId)
    {
        NewsEntity news =  getNewsById(newsId);
        news.increaseNewsViews();
    }

    public void increaseNewsBlocks(Long newsId)
    {
        NewsEntity news =  getNewsById(newsId);
        news.increaseNewsBlocks();
    }


}
