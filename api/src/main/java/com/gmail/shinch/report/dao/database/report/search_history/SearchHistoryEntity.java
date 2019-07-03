package com.gmail.shinch.report.dao.database.report.search_history;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;

@Slf4j
@Entity
@Table(name = "search_history")
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class SearchHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "keyword", nullable = false)
    private String keyword;
    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;
    @Column(name = "create_by", nullable = false)
    private String createBy;
}
/*
CREATE TABLE search_history (
    id INT AUTO_INCREMENT PRIMARY KEY,
    keyword VARCHAR(100) NOT NULL,
    create_at TIMESTAMP NOT NULL,
    create_by VARCHAR(20) NOT NULL
);
 */