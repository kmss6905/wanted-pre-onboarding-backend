package com.example.wanted.acceptance.job;

import com.example.wanted.acceptance.DomainAcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.MediaType;

import static com.example.wanted.exception.ErrorType.C001;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("채용공고 '추가' 인수 테스트")
public class JobAddAcceptanceTest extends DomainAcceptanceTest {

    @Test
    @DisplayName("유효한 채용공고 추가 - 성공")
    void createJob() throws Exception {
        // then
        mockMvc.perform(post("/api/v1/jobs")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {
                        "companyId": 1,
                        "tech": "java",
                        "position": "벡엔드 개발자",
                        "desc": "채용급구",
                        "compensateMoneyValue": 10000
                    }
                """)
        ).andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("존재하지 않는 회사가 채용공고 추가 시 예외 던짐 - 실패")
    void throwExceptionWhenNoExistCompanyAddJob() throws Exception {
        mockMvc.perform(post("/api/v1/jobs")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "companyId": 100,
                            "tech": "java",
                            "position": "벡엔드 개발자",
                            "desc": "채용급구",
                            "compensateMoneyValue": 10000
                        }
                    """)
            ).andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value(C001.getCode()))
            .andExpect(jsonPath("$.message").value(C001.getMessage()));
    }



    @DisplayName("유효하지 않은 입력값으로 채용공고 추가 시 예외 던짐 - 실패")
    @ParameterizedTest
    @ValueSource(strings = {
        """
                        {
                            "tech": "java",
                            "position": "벡엔드 개발자",
                            "desc": "채용급구",
                            "compensateMoneyValue": 10000
                        }
                    """,
        """
                            {
                                "companyId": 0,
                                "tech": "java",
                                "position": "벡엔드 개발자",
                                "desc": "채용급구",
                                "compensateMoneyValue": 10000
                            }
                        """,
        """
                            {
                                "companyId": 1,
                                "position": "벡엔드 개발자",
                                "desc": "채용급구",
                                "compensateMoneyValue": 10000
                            }
                        """,
        """
                            {
                                "companyId": 1,
                                "tech": " ",
                                "position": "벡엔드 개발자",
                                "desc": "채용급구",
                                "compensateMoneyValue": 10000
                            }
                        """,
        """
                            {
                                "companyId": 1,
                                "tech": "java",
                                "desc": "채용급구",
                                "compensateMoneyValue": 10000
                            }
                        """,
        """
                            {
                                "companyId": 1,
                                "tech": "java",
                                "position": " ",
                                "desc": "채용급구",
                                "compensateMoneyValue": 10000
                            }
                        """,
        """
                            {
                                "companyId": 1,
                                "tech": "java",
                                "position": "벡엔드 개발자",
                                "compensateMoneyValue": 10000
                            }
                        """,
        """
                            {
                                "companyId": 1,
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
                                "desc": "채용급구"
                            }
                        """
    })
    void throwExceptionWhenInvalidRequest(String text) throws Exception {
        mockMvc.perform(post("/api/v1/jobs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(text)
            ).andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("E001"));
    }


    @DisplayName("유효하지 않은 보상금액 범위로 채용공고 추가 시 예외 던짐 - 실패")
    @ParameterizedTest
    @ValueSource(
        strings = {
            """
                    {
                        "companyId": 1,
                        "tech": "java",
                        "position": "벡엔드 개발자",
                        "desc": "채용급구",
                        "compensateMoneyValue": 1
                    }
                """,
            """
                    {
                        "companyId": 1,
                        "tech": "java",
                        "position": "벡엔드 개발자",
                        "desc": "채용급구",
                        "compensateMoneyValue": 9999
                    }
                """,
            """
                    {
                        "companyId": 1,
                        "tech": "java",
                        "position": "벡엔드 개발자",
                        "desc": "채용급구",
                        "compensateMoneyValue": 10000001
                    }
                """
        }
    )
    void throwExceptionWhenNotValidMoneyValue(String text) throws Exception {
        mockMvc.perform(post("/api/v1/jobs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(text)
            ).andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("E001"));
    }
}
