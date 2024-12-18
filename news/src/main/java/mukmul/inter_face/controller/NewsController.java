package mukmul.inter_face.controller;

import jakarta.validation.Valid;
import mukmul.inter_face.dto.response.NewsResponse;
import mukmul.inter_face.dto.request.NewsSearchRequest;
import mukmul.inter_face.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController
{
    @Autowired
    private NewsService newsService;

    @GetMapping
    public ResponseEntity<List<NewsResponse>> getAllNews()
    {
        List<NewsResponse> newsList = newsService.getAllNews();
        return new ResponseEntity<>(newsList, HttpStatus.OK);
    }
    @GetMapping("/test")
    public void getNewsByNewsApi()
    {
        newsService.getItNews();
    }

    @GetMapping("/{newsId}")
    public ResponseEntity<NewsResponse> getNewsById(@PathVariable Long newsId)
    {
        NewsResponse newsResponse = newsService.getNewsDtoById(newsId);
        return new ResponseEntity<>(newsResponse, HttpStatus.OK);
    }


    @PostMapping("/{newsId}/increaseViews")
    public ResponseEntity<String> increaseNewsViews(@PathVariable("newsId") long newsId)
    {
        boolean success = newsService.increaseNewsViews(newsId);
        if (success) {
            return new ResponseEntity<>("Views increased successfully", HttpStatus.OK); // 성공
        } else {
            return new ResponseEntity<>("Failed to increase views", HttpStatus.BAD_REQUEST); // 실패
        }
    }

    @PostMapping("/{newsId}/increaseBlocks")
    public ResponseEntity<String> increaseNewsBlocks(@PathVariable("newsId") long newsId)
    {
        boolean success = newsService.increaseNewsBlocks(newsId);
        if (success) {
            return new ResponseEntity<>("Blocks increased successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to increase blocks", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<List<NewsResponse>> searchNews(@Valid @RequestBody NewsSearchRequest newsSearchRequest){
        List<NewsResponse> newsList = newsService.searchNews(newsSearchRequest);
        return new ResponseEntity<>(newsList, HttpStatus.OK);
    }
}
