package com.example.wanted.domain.user;

import com.example.wanted.exception.user.UserNameNullException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserTest {

    private static Stream<String> blankOrNullAndEmptyStrings() {
        return Stream.of(null, "", " ", "   ", "\t", "\n");
    }

    @Test
    @DisplayName("Builder 를 이용하여 User 생성")
    void createUserWithBuilder() {
        // when, then
        assertThatCode(() -> User.builder()
            .id(1L)
            .name("wanted")
            .build()
        ).doesNotThrowAnyException();
    }


    @DisplayName("name 이 Null, Blank, Empty 이면 User 객체 생성 실패")
    @ParameterizedTest
    @MethodSource("blankOrNullAndEmptyStrings")
    void failNameIsNullBlankEmpty(String text) {
        // when, then
        assertThatThrownBy(() -> User.builder()
            .id(1L)
            .name(text)
            .build()
        ).isInstanceOf(UserNameNullException.class);
    }


}