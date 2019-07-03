package com.gmail.shinch.report.service.history;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoryDto {
    private int id;
    private String keyword;
    private LocalDateTime searchAt;
}
