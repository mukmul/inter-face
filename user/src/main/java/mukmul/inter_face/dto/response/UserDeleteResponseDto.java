package mukmul.inter_face.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class UserDeleteResponseDto {
    private String userEmail;
    private LocalDateTime deletedAt;
}
