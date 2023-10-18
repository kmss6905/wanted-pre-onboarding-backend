package com.example.wanted.domain.user;

import com.example.wanted.domain.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("User Repository 테스트")
public class UserRepositoryTest extends RepositoryTest {

    @Autowired
    UserRepository userRepository;

    User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
            .name("wanted")
            .build();
    }

    @Test
    @DisplayName("유저 저장 - 성공")
    void saveUser() {
        assertThatCode(() -> userRepository.save(user))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("유저 조회 - 성공")
    void findUser() {
        // given
        User save = userRepository.save(user);

        // when
        Optional<User> optionalUser = userRepository.findById(save.getId());

        // then
        assertThat(optionalUser.isPresent()).isTrue();
    }

    @Test
    @DisplayName("없는 유저 조회 - 실패")
    void findNoExistedUser() {
        // when
        Optional<User> optionalUser = userRepository.findById(1L);

        // then
        assertThat(optionalUser.isPresent()).isFalse();
    }

}
