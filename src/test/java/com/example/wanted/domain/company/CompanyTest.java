package com.example.wanted.domain.company;

import com.example.wanted.exception.company.CompanyCountryNullException;
import com.example.wanted.exception.company.CompanyNameNullException;
import com.example.wanted.exception.company.CompanyCityNullException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Company 도메인 테스트")
class CompanyTest {

    private static Stream<String> blankOrNullAndEmptyStrings() {
        return Stream.of(null, "", " ", "   ", "\t", "\n");
    }

    @Test
    @DisplayName("Builder 를 이용하여 Company 를 생성한다.")
    void createCompanyWithBuilder() {
        // when, then
        assertThatCode(() -> Company.builder()
            .id(1L)
            .name("원티드")
            .country("한국")
            .city("서울")
            .build()
        ).doesNotThrowAnyException();
    }

    @DisplayName("name 이 Null,Blank,Empty 이면 company 객체 생성 실패")
    @ParameterizedTest
    @MethodSource("blankOrNullAndEmptyStrings")
    void failWhenNameIsNullEmptyBlank(String text) {
        assertThatThrownBy(() -> Company.builder()
            .id(1L)
            .name(text)
            .country("한국")
            .city("서울")
            .build()
        ).isInstanceOf(CompanyNameNullException.class);
    }

    @DisplayName("nation 이 Null,Blank,Empty 이면 company 객체 생성 실패")
    @ParameterizedTest
    @MethodSource("blankOrNullAndEmptyStrings")
    void failWhenNationIsNullEmptyBlank(String text) {
        assertThatThrownBy(() -> Company.builder()
            .id(1L)
            .name("원티드")
            .country("한국")
            .city(text)
            .build()
        ).isInstanceOf(CompanyCityNullException.class);
    }

    @DisplayName("country 가 Null,Blank,Empty 이면 company 객체 생성 실패")
    @ParameterizedTest
    @MethodSource("blankOrNullAndEmptyStrings")
    void failWhenCountryIsNullEmptyBlank(String text) {
        assertThatThrownBy(() -> Company.builder()
            .id(1L)
            .name("원티드")
            .country(text)
            .city("서울")
            .build()
        ).isInstanceOf(CompanyCountryNullException.class);
    }
}