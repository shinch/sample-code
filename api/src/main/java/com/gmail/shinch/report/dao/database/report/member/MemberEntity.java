package com.gmail.shinch.report.dao.database.report.member;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Slf4j
@Entity
@Table(name = "member")
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class MemberEntity {
    @Id
    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "token", nullable = false)
    private String token;
    @Column(name = "token_expire", nullable = false)
    private LocalDateTime tokenExpire;

    public void setToken(String token) {
        this.token = token;
        this.tokenExpire = LocalDateTime.now().plusHours(12);
    }

    public void increaseExpirationTime() {
        this.tokenExpire = LocalDateTime.now().plusHours(12);
    }
}
/*
CREATE TABLE member (
    id VARCHAR(20) PRIMARY KEY,
    password VARCHAR(44) NOT NULL,
    token VARCHAR(44) NOT NULL,
    token_expire TIMESTAMP NOT NULL
);
 */