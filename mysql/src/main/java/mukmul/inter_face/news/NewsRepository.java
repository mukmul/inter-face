package mukmul.inter_face.news;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity,Long> {

    @Query("SELECT n FROM NewsEntity n " +
            "WHERE (:newsTitle IS NULL OR n.newsTitle LIKE %:newsTitle) " +
            "AND (:newsContent IS NULL OR n.newsContent LIKE %:newsContent) " +
            "AND (:newsSource IS NULL OR n.newsSource LIKE %:newsSource) " +
            "AND (:newsAuthor IS NULL OR n.newsAuthor LIKE %:newsAuthor)")
    ArrayList<NewsEntity> findNewsByNewsSearchRequest(@Param("newsTitle") String newsTitle,
                                                      @Param("newsContent") String newsContent,
                                                      @Param("newsSource") String newsSource,
                                                      @Param("newsAuthor") String newsAuthor);

}
