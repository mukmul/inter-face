package mukmul.inter_face.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@SuperBuilder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long userId;

    @Column(length = 50, nullable = false)
    private String userEmail;

    @Column(length = 60, nullable = false)
    private String userPassword;

    @Column(length = 10, nullable = false)
    private String userName;

    @Column(length = 50, nullable = false)
    private String userJob;

    @Column(length = 11, nullable = false)
    private String userPhone;

    @Column(length = 7, nullable = false)
    private String userNickname;

    @Column(length = 50)
    private String userFavor;

    private LocalDateTime deletedAt;

}
