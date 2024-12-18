package mukmul.inter_face.controller;

import mukmul.inter_face.news.NewsDto;
import mukmul.inter_face.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController
{
    @Autowired
    private NewsService newsService;

    @GetMapping
    public List<NewsDto> getAllNews()
    {
        return newsService.getAllNews();
    }

    @GetMapping("/{newsId}")
    public NewsDto getNewsById(@RequestParam("newsId") Long newsId)
    {
        return newsService.getNewsDtoById(newsId);
    }

    @PostMapping("/{newsId}/increaseViews")
    public void increaseNewsViews(@PathVariable("newsId") long newsId)
    {
        newsService.increaseNewsViews(newsId);
    }

    @PostMapping("/{newsId}/increaseBlocks")
    public void increaseNewsBlocks(@PathVariable("newsId") long newsId)
    {
        newsService.increaseNewsBlocks(newsId);
    }
}
