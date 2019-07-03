package com.gmail.shinch.report.dao.database.report.top_keyword;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Slf4j
@Entity
@Table(name = "top_keyword")
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "keyword")
public class TopKeywordEntity {
    @Id
    @Column(name = "keyword", nullable = false)
    private String keyword;
    @Column(name = "cnt", nullable = false)
    private int cnt;
}
/*
CREATE TABLE top_keyword (
    keyword VARCHAR(100) PRIMARY KEY,
    cnt INT NOT NULL
);

 */