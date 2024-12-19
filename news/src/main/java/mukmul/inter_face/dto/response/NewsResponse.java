package mukmul.inter_face.dto.response;


import lombok.Builder;
import lombok.Getter;
import mukmul.inter_face.news.NewsEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class NewsResponse
{
    private Long newsId;
    private String newsTitle;
    private String newsContent;
    private String newsSource;
    private String newsAuthor;
    private LocalDateTime newsCreatedAt;
    private int newsViews;
    private int newsBlock;

    public static NewsResponse fromEntity(NewsEntity newsEntity) {
        return NewsResponse.builder()
                .newsId(newsEntity.getNewsId())
                .newsTitle(newsEntity.getNewsTitle())
                .newsContent(newsEntity.getNewsContent())
                .newsSource(newsEntity.getNewsSource())
                .newsAuthor(newsEntity.getNewsAuthor())
                .newsCreatedAt(newsEntity.getCreatedAt())
                .newsViews(newsEntity.getNewsViews())
                .newsBlock(newsEntity.getNewsBlocks())
                .build();
    }
}
