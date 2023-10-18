package com.example.wanted.domain.user;

import com.example.wanted.domain.BaseEntity;
import com.example.wanted.domain.resume.Resume;
import com.example.wanted.exception.user.UserNameNullException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
public class User extends BaseEntity {

    private String name;

    @Builder
    private User(final Long id, final String name) {
        validateNullName(name);
        this.name = name;
        this.id = id;
    }

    private void validateNullName(String name) {
        if (Objects.isNull(name) || name.trim().isEmpty()) {
            throw new UserNameNullException();
        }
    }
}
