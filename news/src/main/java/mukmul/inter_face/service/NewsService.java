package mukmul.inter_face.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mukmul.inter_face.news.NewsDto;
import mukmul.inter_face.news.NewsEntity;
import mukmul.inter_face.news.NewsRepository;
import mukmul.inter_face.news.NewsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
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
            NewsResponse newsResponse = objectMapper.readValue(jsonResponse, NewsResponse.class);
            if (!newsResponse.getStatus().equals("error"))
            {
                for(NewsResponse.Article a :newsResponse.getArticles())
                    saveNewsArticles(a);
            }
        } catch (JsonProcessingException e)
        {

        }
    }

    public void saveNewsArticles(NewsResponse.Article article)
    {
        try {
            NewsEntity newsEntity = NewsEntity.builder()
                    .newsAuthor(article.getAuthor())
                    .newsContent(article.getDescription())
                    .newsTitle(article.getTitle())
                    .newsSource(article.getSource().getName())
                    .newsCreatedAt(NewsEntity.formatCreatedAt(article.getPublishedAt()))
                    .build();
            newsRepository.save(newsEntity);
        } catch (Exception e) {

        }

    }

    public ArrayList<NewsDto> getAllNews()
    {
        try {
            ArrayList<NewsEntity> newsArray = new ArrayList<>(newsRepository.findAll());

            ArrayList<NewsDto> newsDtoArray=new ArrayList<>();

            for(NewsEntity newsEntity : newsArray)
                newsDtoArray.add(NewsDto.fromEntity(newsEntity));

            return newsDtoArray;
        }catch(DataAccessException e)
        {

        }

        return new ArrayList<>();
    }

    //반환용
    public NewsDto getNewsDtoById(Long newsId)
    {
        NewsEntity newsEntity = newsRepository.findById(newsId)
                .orElseThrow(() -> new NoSuchElementException(newsId + " 아이디의 뉴스는 없습니다"));

        return NewsDto.fromEntity(newsEntity);
    }




    //increase하는 함수에서만 사용함. 영속성 컨텍스트에 불러오기 위해서
    public NewsEntity getNewsById(Long newsId)
    {
        return newsRepository.findById(newsId)
                .orElseThrow(() -> new NoSuchElementException(newsId + " 아이디의 뉴스는 없습니다"));
    }


    public void increaseNewsViews(Long newsId)
    {
        try{
                NewsEntity news =  getNewsById(newsId);
                news.increaseNewsViews();
        }catch(NoSuchElementException e) {

        } catch(DataAccessException e) {

        }
    }

    public void increaseNewsBlocks(Long newsId)
    {
        try{
                NewsEntity news =  getNewsById(newsId);
                news.increaseNewsBlocks();
        } catch(NoSuchElementException e) {

        } catch(DataAccessException e) {

        }
    }


}
