package mukmul.inter_face.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    @Email(message = "유효하지 않은 이메일 형식입니다.")
    @NotBlank(message = "이메일은 필수 입력값입니다.")

    private String userEmail;
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$",
            message = "비밀번호는 8~20자, 영문과 숫자를 조합해야 합니다."
    )
    private String userPassword;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String userName;

    @NotBlank(message = "직업은 필수 입력값입니다.")
    private String userJob;

    @NotBlank(message = "전화번호는 필수 입력값입니다.")
    private String userPhone;

    @NotBlank(message = "닉네임은 필수 입력값입니다.")
    private String userNickname;

    private String userFavor;

}
