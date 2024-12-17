package mukmul.inter_face.news;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
public class NewsResponse {
    private String status;
    private int totalResults;
    private List<Article> articles;

    @Getter
    @Setter
    public static class Article {
        private Source source; //
        private String title;
        private String author;
        private String description;
        private String url;
        private String urlToImage;
        private String publishedAt;
        private String content;
        @Getter
        @Setter
        public static class Source {
            private String id;
            private String name;

            // getters and setters
        }
    }

}

