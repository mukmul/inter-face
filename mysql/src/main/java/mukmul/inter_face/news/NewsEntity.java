package mukmul.inter_face.news;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

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
    private long newsId;

    @Column(length = 50, nullable = false)
    private String newsTitle;

    @Column(nullable = false)
    private String newsContent;

    @Column(nullable = false)
    private String newsSource;

    @Column(nullable = false)
    private int newsViews=0;

    @Column(nullable = false)
    private int newsBlock=0;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public void increaseNewsViews(){
        this.newsViews++;
    }
    public void increaseNewsBlocks(){
        this.newsBlock++;
    }
}
