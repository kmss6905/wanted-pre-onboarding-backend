package com.example.wanted.acceptance.job;

import com.example.wanted.acceptance.DomainAcceptanceTest;
import com.example.wanted.exception.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.MediaType;

import static com.example.wanted.exception.ErrorType.J003;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Job 검색 인수테스트")
class JobAcceptanceTest extends DomainAcceptanceTest {

    @Test
    @DisplayName("채용공고 리스트 검색하기 - 성공")
    @CsvSource(value = {
        "java, 3",
        "python, 3",
        "채용, 1"
    })
    void findJobsWithNoSearch() {
        // TODO
    }


    @Test
    @DisplayName("채용공고 찾기 - 성공")
    void findJob() throws Exception {
        // given
        saveJob();

        // when, then
        mockMvc.perform(get("/api/v1/jobs/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.companyName").value("원티드랩"))
            .andExpect(jsonPath("$.companyCountry").value("한국"))
            .andExpect(jsonPath("$.companyCity").value("서울"))
            .andExpect(jsonPath("$.position").value("벡엔드 개발자"))
            .andExpect(jsonPath("$.money").value(10_000L))
            .andExpect(jsonPath("$.tech").value("java"))
            .andExpect(jsonPath("$.description").value("채용 급구"))
            .andExpect(jsonPath("$.jobId").value(1L))
            .andExpect(jsonPath("$.anotherJobsId").exists());
    }

    @Test
    @DisplayName("채용공고 찾을 때 같은 회사가 올린 다른 채용공고 번호도 같이 조회한다. - 성공")
    void findJobsWithAnotherJobIds() throws Exception {
        // 5개 저장합니다.
        saveJobsWithCountBy(5);

        // when, then
        mockMvc.perform(get("/api/v1/jobs/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.companyName").value("원티드랩"))
            .andExpect(jsonPath("$.companyCountry").value("한국"))
            .andExpect(jsonPath("$.companyCity").value("서울"))
            .andExpect(jsonPath("$.position").value("벡엔드 개발자"))
            .andExpect(jsonPath("$.money").value(10_000L))
            .andExpect(jsonPath("$.tech").value("java"))
            .andExpect(jsonPath("$.description").value("채용 급구"))
            .andExpect(jsonPath("$.jobId").value(1L))
            .andExpect(jsonPath("$.anotherJobsId", hasItems(2, 3, 4, 5)));
    }

    @Test
    @DisplayName("존재하지 않는 채용공고 조회 시도 시 예외 던짐 - 실패")
    void throwExceptionWhenNoExistJob() throws Exception {
        // given
        saveJob();

        // when, then
        mockMvc.perform(get("/api/v1/jobs/{id}", 100L)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value(J003.getCode()))
            .andExpect(jsonPath("$.message").value(J003.getMessage()));
    }
}
