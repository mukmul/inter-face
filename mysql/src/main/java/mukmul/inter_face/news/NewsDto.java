package mukmul.inter_face.news;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NewsDto
{
    private Long newsId;
    private String newsTitle;
    private String newsContent;
    private String newsSource;
    private String newsAuthor;
    private LocalDateTime newsCreatedAt;
    private int newsViews;
    private int newsBlock;

    public static NewsDto fromEntity(NewsEntity newsEntity) {
        return NewsDto.builder()
                .newsId(newsEntity.getNewsId())
                .newsTitle(newsEntity.getNewsTitle())
                .newsContent(newsEntity.getNewsContent())
                .newsSource(newsEntity.getNewsSource())
                .newsAuthor(newsEntity.getNewsAuthor())
                .newsCreatedAt(newsEntity.getNewsCreatedAt())
                .newsViews(newsEntity.getNewsViews())
                .newsBlock(newsEntity.getNewsBlock())
                .build();
    }
}
