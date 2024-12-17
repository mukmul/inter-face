package mukmul.inter_face;

import mukmul.inter_face.news.NewsEntity;
import mukmul.inter_face.news.NewsRepository;
import mukmul.inter_face.service.NewsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = NewsApplication.class) // Spring Boot context를 로드
public class NewsTests {

    @Mock
    private NewsRepository newsRepository; // Mock된 NewsRepository

    @InjectMocks
    private NewsService newsService; // Mock된 repository를 주입받는 NewsService

    private NewsEntity newsEntity;

    @BeforeEach
    void setUp() {
        // 테스트에 사용할 mock 객체 생성
        newsEntity = NewsEntity.builder()
                .newsAuthor("")
                .newsContent("")
                .newsTitle("")
                .newsSource("")
                .newsCreatedAt("")
                .build();

        // repository의 동작을 mock 설정 (뉴스를 찾는 메서드)
        when(newsRepository.findById(1L)).thenReturn(java.util.Optional.of(newsEntity));
        when(newsRepository.save(newsEntity)).thenReturn(newsEntity);
    }

    @Test
    void testIncreaseNewsViews() {
        // 실제 메서드 호출
        newsService.increaseNewsViews(1L);
        newsService.increaseNewsViews(1L);
        newsService.increaseNewsViews(1L);
        // 결과 출력 (이 부분은 주로 assert 구문으로 결과를 검증하는 방식으로 수정할 수 있음)
        System.out.println(newsEntity.getNewsViews());
    }

    @Test
    void testIncreaseNewsBlocks() {
        // 실제 메서드 호출
        newsService.increaseNewsBlocks(1L);
        newsService.increaseNewsBlocks(1L);
        newsService.increaseNewsBlocks(1L);
        // 결과 출력
        System.out.println(newsEntity.getNewsBlock());
    }
}