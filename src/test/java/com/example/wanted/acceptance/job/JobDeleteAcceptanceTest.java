package com.example.wanted.acceptance.job;

import com.example.wanted.acceptance.DomainAcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.example.wanted.exception.ErrorType.J003;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("채용공고 '삭제' 인수 테스트")
public class JobDeleteAcceptanceTest extends DomainAcceptanceTest {

    @Test
    @DisplayName("채용공고 삭제 - 성공")
    void deleteJob() throws Exception {
        // given
        saveJob();

        // when
        mockMvc.perform(delete("/api/v1/jobs/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNoContent());

        // assert
        boolean present = jobRepository.findById(1L).isPresent();
        assertThat(present).isFalse();
    }

    @Test
    @DisplayName("존재하지 않거나 이미 삭제된 채용공고 삭제 시 예외 던짐 - 실패")
    void throwExceptionWhenDeleteNoExistedOrDeletedJob() throws Exception {
        // when, then
        mockMvc.perform(delete("/api/v1/jobs/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value(J003.getCode()))
            .andExpect(jsonPath("$.message").value(J003.getMessage()));
    }
}
