package mukmul.inter_face.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsSearchRequest
{
    @Size(max = 200, message = "뉴스 제목은 200자를 초과할 수 없습니다.")
    private String newsTitle;
    @Size(max = 1000, message = "뉴스 내용은 1000자를 초과할 수 없습니다.")
    private String newsContent;
    @Size(max = 20, message = "뉴스 작성자는 20자를 초과할 수 없습니다.")
    private String newsAuthor;
    @Size(max = 20, message = "뉴스 출처는 255자를 초과할 수 없습니다.")
    private String newsSource;
}
