package mukmul.inter_face.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mukmul.inter_face.dto.request.UserUpdateRequestDto;
import mukmul.inter_face.dto.response.UserDeleteResponseDto;
import mukmul.inter_face.dto.request.UserRequestDto;
import mukmul.inter_face.dto.response.UserResponseDto;
import mukmul.inter_face.dto.response.UserUpdateResponseDto;
import mukmul.inter_face.mapper.UserMapper;
import mukmul.inter_face.user.UserEntity;
import mukmul.inter_face.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserResponseDto save(UserRequestDto requestDto) {

        if (userRepository.findByUserEmail(requestDto.getUserEmail()).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다: " + requestDto.getUserEmail());
        }

        UserEntity userEntity = userMapper.toEntity(requestDto);
        return userMapper.toDto(userRepository.save(userEntity));
    }

    /**
     * 1. DTO 생성 후 반환 타입 변경
     * 2. 커스텀 에러 생성 후 변경
     */
    public UserResponseDto findById(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("찾을 수 없는 사용자입니다."));

        return userMapper.toDto(userEntity);
    }

    public UserResponseDto findByEmail(String userEmail) {
        UserEntity userEntity = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("찾을 수 없는 사용자입니다."));

        return userMapper.toDto(userEntity);
   }


    public List<UserResponseDto> findAll() {
        List<UserEntity> userEntities = userRepository.findAll();

        if (userEntities.isEmpty()) {
            throw new RuntimeException("등록된 사용자가 없습니다.");
        }

        return userEntities.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDeleteResponseDto delete(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다. ID: " + userId));

        if (user.getDeletedAt() != null) {
            throw new RuntimeException("이미 삭제된 사용자입니다. ID: " + userId);
        }

        user.delete();

        return UserDeleteResponseDto.builder()
                .userEmail(user.getUserEmail())
                .deletedAt(user.getDeletedAt())
                .build();
    }

    @Transactional
    public UserUpdateResponseDto update(Long userId, UserUpdateRequestDto requestDto) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다. ID: " + userId));

        userMapper.updateUserFromDto(requestDto, userEntity);

        return UserUpdateResponseDto.builder()
                .userId(userEntity.getUserId())
                .userEmail(userEntity.getUserEmail())
                .userJob(userEntity.getUserJob())
                .userPhone(userEntity.getUserPhone())
                .userNickname(userEntity.getUserNickname())
                .userFavor(userEntity.getUserFavor())
                .build();
    }

}
