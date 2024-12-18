package mukmul.inter_face.news;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


import java.time.*;

@Entity
@Table(name = "news")
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@SuperBuilder
public class NewsEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long newsId;

    @Column(length =200, nullable = false)
    private String newsTitle;

    @Column(nullable = false, length = 1000)
    private String newsContent;

    @Column(nullable = false)
    private String newsSource;

    @Column(nullable = false)
    private String newsAuthor;

    @Column(length = 20,nullable = false)
    private LocalDateTime newsCreatedAt;

    @Column(nullable = false)
    private int newsViews=0;

    @Column(nullable = false)
    private int newsBlock=0;



    public void increaseNewsViews(){
        this.newsViews++;
    }
    public void increaseNewsBlocks(){
        this.newsBlock++;
    }

    public static LocalDateTime formatCreatedAt(String publishedAt) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(publishedAt);  // ISO 8601 형식으로 파싱 (Z는 UTC를 나타냄)
        return zonedDateTime.withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();  // UTC 타임존에 맞게 변환 후 LocalDateTime 반환
    }
}
