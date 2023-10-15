package com.example.wanted.domain.company;

import com.example.wanted.domain.RepositoryTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Company Repository 테스트")
public class CompanyRepositoryTest extends RepositoryTest {

    @Autowired
    CompanyRepository companyRepository;

    Company company;

    @BeforeEach
    void setUp() {
        company = Company.builder()
            .id(1L)
            .name("원티드")
            .city("서울")
            .country("한국")
            .build();
    }

    @AfterEach
    void tearDown() {
        companyRepository.deleteAll();
    }

    @Test
    @DisplayName("Company 저장 - 성공")
    void saveCompany() {
        // when
        Company save = companyRepository.save(company);

        // then
        assertThat(save.getName()).isEqualTo(company.getName());
        assertThat(save.getCountry()).isEqualTo(company.getCountry());
        assertThat(save.getCity()).isEqualTo(company.getCity());
    }

    @Test
    @DisplayName("Company 조회 - 성공")
    void findCompany() {
        // given
        Company save = companyRepository.saveAndFlush(company);

        // when
        boolean present = companyRepository.findById(save.getId()).isPresent();

        // then
        assertThat(present).isTrue();
    }

    @Test
    @DisplayName("Company 조회 - 실패")
    void failFindCompany() {
        // when
        boolean present = companyRepository.findById(1L).isPresent();

        // then
        assertThat(present).isFalse();
    }

}
