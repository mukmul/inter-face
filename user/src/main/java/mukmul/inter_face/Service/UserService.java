package mukmul.inter_face.Service;

import lombok.RequiredArgsConstructor;
import mukmul.inter_face.user.UserEntity;
import mukmul.inter_face.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getUserById(Long Id) {
        return userRepository.findById(Id).orElseThrow(() ->
                new IllegalArgumentException("User Not Found"+Id));
    }

    public void CreateUser(UserEntity user) {
        userRepository.save(user);
    }

}
