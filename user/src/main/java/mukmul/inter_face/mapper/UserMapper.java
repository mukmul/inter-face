package mukmul.inter_face.mapper;

import mukmul.inter_face.dto.request.UserRequestDto;
import mukmul.inter_face.dto.request.UserUpdateRequestDto;
import mukmul.inter_face.dto.response.UserResponseDto;
import mukmul.inter_face.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "userId", ignore = true)
    UserEntity toEntity(UserRequestDto dto);

    UserResponseDto toDto(UserEntity entity);

    void updateUserFromDto(UserUpdateRequestDto dto, @MappingTarget UserEntity entity);
}
