package mukmul.inter_face.news;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.loader.LoaderLogging;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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

    @Column(length =200, nullable = false)
    private String newsTitle;

    @Column(nullable = false, length = 1000)
    private String newsContent;

    @Column(nullable = false)
    private String newsSource;

    @Column(nullable = false)
    private String newsAuthor;

    @Column(length = 20,nullable = false)
    private String newsCreatedAt;

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

    public static String formatCreatedAt(String input)
    {
        Instant instant = Instant.parse(input);
        LocalDateTime localDateTime =instant.atZone(ZoneId.of("UTC")).toLocalDateTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return localDateTime.format(formatter);
    }
}
