package mukmul.inter_face.controller;

import mukmul.inter_face.news.NewsEntity;
import mukmul.inter_face.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/news")
public class NewsController
{
    @Autowired
    private NewsService newsService;

    @GetMapping
    public ArrayList<NewsEntity> getItNews() {
        return newsService.getItNews();
    }

    @PostMapping("/{newsId}/increaseViews")
    public Boolean increaseNewsViews(@PathVariable("newsId") long newsId)
    {
        newsService.increaseNewsViews(newsId);
        return true;
    }

    @PostMapping("/{newsId}/increaseBlocks")
    public Boolean increaseNewsBlocks(@PathVariable("newsId") long newsId)
    {
        newsService.increaseNewsBlocks(newsId);
        return true;
    }
}
