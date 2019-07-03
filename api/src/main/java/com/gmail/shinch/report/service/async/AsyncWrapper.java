package com.gmail.shinch.report.service.async;

import com.gmail.shinch.report.service.history.HistoryService;
import com.gmail.shinch.report.service.top.TopService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.Future;

@Service
public class AsyncWrapper {
    HistoryService historyService;
    TopService topService;

    public AsyncWrapper(
            HistoryService historyService,
            TopService topService ) {
        this.historyService = historyService;
        this.topService = topService;
    }

    @Async("asyncHistoryExecutor")
    @Transactional
    public void addMyHistory(String userId, String Keyword, LocalDateTime searchAt) {
        historyService.addMyHistory(userId, Keyword, searchAt);
    }

    @Async("asyncTopKeywordExecutor")
    @Transactional
    public Future<Boolean> addSearchCount(String keyword) {
        Boolean isClearCache = topService.addSearchCount(keyword);
        return new AsyncResult<>(isClearCache);
    }
}
