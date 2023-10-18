package com.example.wanted.acceptance.job;

import com.example.wanted.acceptance.DomainAcceptanceTest;
import com.example.wanted.domain.job.Job;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("채용공고 '수정' 인수 테스트")
public class JobUpdateAcceptanceTest extends DomainAcceptanceTest {

    @Test
    @DisplayName("유효한 채용공고 수정 - 성공")
    void updateJob() throws Exception {
        // given
        saveJob();

        // when, then
        mockMvc.perform(patch("/api/v1/jobs/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "tech": "js",
                            "position": "프론트 개발자",
                            "desc": "채용안급구",
                            "compensateMoneyValue": 10000
                        }
                    """)
            )
            .andDo(print())
            .andExpect(status().isNoContent());

        // verify
        Job job = jobRepository.findById(1L).get();
        assertThat(job.getTech()).isEqualTo("js");
        assertThat(job.getDescription()).isEqualTo("채용안급구");
        assertThat(job.getMoney()).isEqualTo(10000);
        assertThat(job.getPosition()).isEqualTo("프론트 개발자");
    }

    @Test
    @DisplayName("존재하지 않는 채용공고를 수정하려고 하면 예외 던짐 - 실패")
    void throwExceptionWhenNotExistJob() throws Exception {
        // when, then
        mockMvc.perform(patch("/api/v1/jobs/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "tech": "js",
                            "position": "프론트 개발자",
                            "desc": "채용안급구",
                            "compensateMoneyValue": 10000
                        }
                    """)
            )
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @ParameterizedTest
    @DisplayName("유효하지 않은 입력값이 들어오거나 없을 경우 채용공고 수정 - 실패")
    @ValueSource(strings = {
        """
                {
                    "position": "벡엔드 개발자",
                    "desc": "채용급구",
                    "compensateMoneyValue": 10000
                }
            """,
        """
                {
                    "tech": " ",
                    "position": "벡엔드 개발자",
                    "desc": "채용급구",
                    "compensateMoneyValue": 10000
                }
            """,
        """
                {
                    "tech": "java",
                    "desc": "채용급구",
                    "compensateMoneyValue": 10000
                }
            """,
        """
                {
                    "tech": "java",
                    "position": " ",
                    "desc": "채용급구",
                    "compensateMoneyValue": 10000
                }
            """,
        """
                {
                    "tech": "java",
                    "position": "벡엔드 개발자",
                    "compensateMoneyValue": 10000
                }
            """,
        """
                {
                    "tech": "java",
                    "position": "벡엔드 개발자",
                    "desc": " ",
                    "compensateMoneyValue": 10000
                }
            """,
        """
                {
                    "companyId": 1,
                    "tech": "java",
                    "position": "벡엔드 개발자",
                }
            """
    })
    void throwExceptionWhenNotInvalidRequest(String text) throws Exception {
        // given
        saveJob();

        // when, then
        mockMvc.perform(patch("/api/v1/jobs/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(text)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("E001"));
    }
}
