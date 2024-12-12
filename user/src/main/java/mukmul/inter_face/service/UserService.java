package mukmul.inter_face.service;

import lombok.RequiredArgsConstructor;
import mukmul.inter_face.user.UserEntity;
import mukmul.inter_face.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

}