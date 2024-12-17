package mukmul.inter_face.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserUpdateResponseDto {
    private Long userId;
    private String userEmail;
    private String userJob;
    private String userPhone;
    private String userNickname;
    private String userFavor;
}