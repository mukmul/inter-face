package mukmul.inter_face.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequestDto {
    private String userJob;
    private String userPhone;
    private String userNickname;
    private String userFavor;
}
