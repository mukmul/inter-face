package mukmul.inter_face.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mukmul.inter_face.user.UserEntity;
import mukmul.inter_face.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    /**
     * 1. DTO 생성 후 반환 타입 변경
     * 2. 커스텀 에러 생성 후 변경
     */
    public UserEntity findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("찾을 수 없는 사용자입니다."));
    }

    public UserEntity findByEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("찾을 수 없는 사용자입니다."));
    }

    @Transactional
    public UserEntity delete(Long userId) {
        UserEntity findUser = findById(userId);

        if (findUser.getDeletedAt() != null) {
            throw new RuntimeException("이미 삭제된 사용자입니다.");
        }

        findUser.delete();
        return findUser;
    }

}
