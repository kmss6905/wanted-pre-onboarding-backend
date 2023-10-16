package com.example.wanted;

import com.example.wanted.domain.company.Company;
import com.example.wanted.domain.company.CompanyRepository;
import com.example.wanted.domain.user.User;
import com.example.wanted.domain.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Component
@Transactional
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public DataLoader(UserRepository userRepository, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        saveUsers();
        saveCompany();
    }

    /**
     * 회사
     * | 회사번호 |  회사명   | 국가 | 지역 |
     * |:----:|:------:|:--:|:--:|
     * |  1   |  원티드랩  | 한국 | 서울 |
     * |  2   |  네이버   | 한국 | 판교 |
     * |  3   | 원티드코리아 | 한국 | 부산 |
     * |  4   |  카카오   | 한국 | 판교 |
     */
    void saveCompany() {
        Company company1 = Company.builder()
            .name("원티드랩")
            .country("한국")
            .city("서울")
            .build();

        Company company2 = Company.builder()
            .name("네이버")
            .country("한국")
            .city("판교")
            .build();

        Company company3 = Company.builder()
            .name("원티드코리아")
            .country("한국")
            .city("부산")
            .build();

        Company company4 = Company.builder()
            .name("카카오")
            .city("한국")
            .country("판교")
            .build();
        companyRepository.saveAll(List.of(company1, company2, company3, company4));
    }

    /**
     * 사용자
     * | 사용자번호 |   이름   |
     * |:-----:|:------:|
     * |   1   | wanted |
     * |   2   |  june  |
     */
    void saveUsers() {
        userRepository.saveAll(List.of(createUserWithName("wanted"), createUserWithName("june")));
    }

    private User createUserWithName(final String name) {
        return User.builder()
            .name(name)
            .build();
    }
}
