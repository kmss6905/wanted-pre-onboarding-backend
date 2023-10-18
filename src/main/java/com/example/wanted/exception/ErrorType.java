package com.example.wanted.exception;

import com.example.wanted.exception.apply.AlreadyResumeSubmittedException;
import com.example.wanted.exception.company.CompanyCityNullException;
import com.example.wanted.exception.company.CompanyCountryNullException;
import com.example.wanted.exception.company.CompanyNameNullException;
import com.example.wanted.exception.company.CompanyNotFoundException;
import com.example.wanted.exception.job.CompanyNullException;
import com.example.wanted.exception.job.InvalidJobContentException;
import com.example.wanted.exception.job.JobNotFoundException;
import com.example.wanted.exception.job.UpdateJobNullException;
import com.example.wanted.exception.user.UserNotFoundException;
import com.example.wanted.exception.user.UserNameNullException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum ErrorType {
    U001("U001", "찾을 수 없는 사용자 입니다.", UserNotFoundException.class, HttpStatus.NOT_FOUND),
    U002("U002", "사용자 이름은 필수입니다.", UserNameNullException.class, HttpStatus.BAD_REQUEST),
    J001("J001", "회사는 Null 이 될 수 없습니다.", CompanyNullException.class, HttpStatus.BAD_REQUEST),
    J002("J002", "유효하지 않는 채용 공고입니다.(기술, 포지션, 설명은 필수)", InvalidJobContentException.class, HttpStatus.BAD_REQUEST),
    J003("J003", "해당하는 채용공고를 찾을 수 없습니다.", JobNotFoundException.class, HttpStatus.NOT_FOUND),
    J004("J004", "채용공고를 수정할 수 없습니다.", UpdateJobNullException.class, HttpStatus.BAD_REQUEST),
    C001("C001", "찾을 수 없는 회사 입니다.", CompanyNotFoundException.class, HttpStatus.NOT_FOUND),
    C002("C002", "회사의 국가는 필수 입력값 입니다.", CompanyCountryNullException.class, HttpStatus.BAD_REQUEST),
    C003("C003", "회사의 이름은 필수 입력값 입니다.", CompanyNameNullException.class, HttpStatus.BAD_REQUEST),
    C004("C004", "회사의 도시는 필수 입력값 입니다.", CompanyCityNullException.class, HttpStatus.BAD_REQUEST),
    A001("A001", "이미 지원한 채용공고 입니다.", AlreadyResumeSubmittedException.class, HttpStatus.CONFLICT);

    private final String code;
    private final String message;
    private final Class<? extends WantedException> classType;
    private final HttpStatus httpStatus;
    private static final List<ErrorType> errorTypes = Arrays.stream(ErrorType.values()).toList();

    public static ErrorType of(Class<? extends WantedException> classType) {
        return errorTypes.stream()
            .filter(it -> it.classType.equals(classType))
            .findFirst()
            .orElseThrow(RuntimeException::new);
    }
}
