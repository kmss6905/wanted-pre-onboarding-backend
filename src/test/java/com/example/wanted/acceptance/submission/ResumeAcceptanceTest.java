package com.example.wanted.acceptance.submission;

import com.example.wanted.acceptance.DomainAcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.MediaType;

import static com.example.wanted.exception.ErrorType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Resume Submission 인수 테스트")
public class ResumeAcceptanceTest extends DomainAcceptanceTest {

    @Test
    @DisplayName("유효한 지원서 신청하기 - 성공")
    void apply() throws Exception {
        // given
        saveJob();

        // when
        mockMvc.perform(post("/api/v1/apply")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "userId": 1,
                    "jobId": 1
                }
                """)
        ).andExpect(status().isNoContent());
    }

    @ParameterizedTest
    @DisplayName("jobId 가 없거나 userId 가 없을 경우 예외를 던진다. - 실패")
    @ValueSource(
        strings = {
            """
                    {
                        "userId": 1
                    }
                    """,
            """
                    {
                        "jobId": 1
                    }
                    """
        }
    )
    void throwExceptionWhenRequestNotJobIdOrUserId(String text) throws Exception {
        // given
        addResume();

        // when, then
        mockMvc.perform(post("/api/v1/apply")
                .contentType(MediaType.APPLICATION_JSON)
                .content(text)
            ).andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("E001"));
    }

    @ParameterizedTest
    @DisplayName("jobId 또는 userId 가 0이면 예외를 던진다. - 실패")
    @ValueSource(
        strings = {
            """
                    {
                        "userId": 1,
                        "jobId": 0
                    }
                    """,
            """
                    {
                        "userId": 1,
                        "jobId": 0
                    }
                    """
        }
    )
    void throwExceptionWhenRequestJobIdOrUserIdZero(String text) throws Exception {
        // given
        addResume();

        // when, then
        mockMvc.perform(post("/api/v1/apply")
                .contentType(MediaType.APPLICATION_JSON)
                .content(text)
            ).andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("E001"));
    }

    @Test
    @DisplayName("같은 채용공고에 중복 신청하면 예외를 던진다. - 실패")
    void throwExceptionWhenApplySameJob() throws Exception {
        // given
        addResume();

        // when, then
        mockMvc.perform(post("/api/v1/apply")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "userId": 1,
                        "jobId": 1
                    }
                    """)
            ).andExpect(status().isConflict())
            .andExpect(jsonPath("$.code").value(A001.getCode()))
            .andExpect(jsonPath("$.message").value(A001.getMessage()));
    }

    @Test
    @DisplayName("존재하지 않는 채용공고에 지원서 신청하면 예외를 던진다. - 실패")
    void throwExceptionWhenApplyNotExistedJob() throws Exception {
        mockMvc.perform(post("/api/v1/apply")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "userId": 1,
                        "jobId": 1
                    }
                    """)
            ).andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value(J003.getCode()))
            .andExpect(jsonPath("$.message").value(J003.getMessage()));
    }

    @Test
    @DisplayName("존재하지 않는 사람이 지원서 신청하면 예외를 던진다. - 실패")
    void throwExceptionWhenNotExistedUserApplyResume() throws Exception {

        mockMvc.perform(post("/api/v1/apply")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "userId": 100,
                        "jobId": 1
                    }
                    """)
            ).andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value(U001.getCode()))
            .andExpect(jsonPath("$.message").value(U001.getMessage()));
    }
}