package mukmul.inter_face.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import mukmul.inter_face.dto.NewsApiResponse;
import mukmul.inter_face.dto.request.NewsSearchRequest;
import mukmul.inter_face.dto.response.NewsResponse;
import mukmul.inter_face.news.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
//restTemplate 빈으로 등록하기, 예외 클래스 만들기
@Service
public class NewsService
{
    @Value("${newsApi.api.key}")
    private String apiKey;

    @Autowired
    private  NewsRepository newsRepository;
    private final String BASE_URL = "https://newsapi.org/v2/everything";

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void getItNews()
    {
        String url = String.format("%s?q=IT&language=ko&apiKey=%s", BASE_URL, apiKey);
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            String jsonResponse = response.getBody();

            if (jsonResponse != null) {
                processNewsResponse(jsonResponse);
            }
        }  catch (Exception e) {
            // 예외 로그 추가

        }
    }

    private void processNewsResponse(String jsonResponse)
    {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            NewsApiResponse newsResponse = objectMapper.readValue(jsonResponse, NewsApiResponse.class);
            if (!newsResponse.getStatus().equals("error"))
            {
                for(NewsApiResponse.Article a :newsResponse.getArticles())
                    saveNewsArticles(a);
            }
        } catch (JsonProcessingException e)
        {

        }
    }

    private void saveNewsArticles(NewsApiResponse.Article article)
    {
        try {
            NewsEntity newsEntity = NewsEntity.builder()
                    .newsAuthor(NewsEntity.formatAuthor(article.getAuthor()))
                    .newsContent(article.getDescription())
                    .newsTitle(article.getTitle())
                    .newsSource(article.getSource().getName())
                    .newsCreatedAt(NewsEntity.formatCreatedAt(article.getPublishedAt()))
                    .build();
            newsRepository.save(newsEntity);
        } catch (Exception e) {

        }

    }

    public ArrayList<NewsResponse> getAllNews()
    {
        ArrayList<NewsEntity> newsArray = fetchAllNews();
        return convertToDtoList(newsArray);
    }

    private ArrayList<NewsResponse> convertToDtoList(List<NewsEntity> newsArray) {
        ArrayList<NewsResponse> newsResponseArray = new ArrayList<>();
        for (NewsEntity newsEntity : newsArray) {
            newsResponseArray.add(NewsResponse.fromEntity(newsEntity));
        }
        return newsResponseArray;
    }
    //반환용
    public NewsResponse getNewsDtoById(Long newsId)
    {
        NewsEntity newsEntity = newsRepository.findById(newsId)
                .orElseThrow(() -> new NoSuchElementException(newsId + " 아이디의 뉴스는 없습니다"));

        return NewsResponse.fromEntity(newsEntity);
    }

    private ArrayList<NewsEntity> fetchAllNews() {
        try {
            return new ArrayList<>(newsRepository.findAll());
        }catch(DataAccessException e)
        {
            return new ArrayList<>();
        }

    }


    private NewsEntity fetchNewsById(Long newsId)
    {
        return newsRepository.findById(newsId)
                .orElseThrow(() -> new NoSuchElementException(newsId + " 아이디의 뉴스는 없습니다"));
    }

    @Transactional
    public boolean increaseNewsViews(Long newsId)
    {
        try{
                NewsEntity news =  fetchNewsById(newsId);
                news.increaseNewsViews();
                return true;
        }catch(NoSuchElementException e) {
                return false;
        } catch(DataAccessException e) {
                return false;
        }
    }
    @Transactional
    public boolean increaseNewsBlocks(Long newsId)
    {
        try{
                NewsEntity news =  fetchNewsById(newsId);
                news.increaseNewsBlocks();
                return true;
        } catch(NoSuchElementException e) {
                return false;
        } catch(DataAccessException e) {
                return false;
        }
    }

    public ArrayList<NewsResponse> searchNews(NewsSearchRequest newsSearchRequest)
    {

        ArrayList<NewsEntity> searchResults = newsRepository.findNewsByNewsSearchRequest
                (newsSearchRequest.getNewsTitle(),newsSearchRequest.getNewsContent(),newsSearchRequest.getNewsSource(),newsSearchRequest.getNewsAuthor());
        return convertToDtoList(searchResults);
    }
}
