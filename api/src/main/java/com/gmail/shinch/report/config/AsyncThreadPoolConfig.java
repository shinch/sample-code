package com.gmail.shinch.report.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
@Configuration
@EnableAsync
public class AsyncThreadPoolConfig {
    @Bean("asyncTopKeywordExecutor")
    public TaskExecutor asyncTopKeywordExecutor() {
        String threadGroupName = "TOP-KEYWORD";
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setThreadNamePrefix(threadGroupName + "-");
        taskExecutor.initialize();
        return new AsyncHandlingExecutor(taskExecutor, threadGroupName);
    }


    @Bean("asyncHistoryExecutor")
    public TaskExecutor asyncHistoryExecutor() {
        String threadGroupName = "HISTORY";
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setThreadNamePrefix(threadGroupName + "-");
        taskExecutor.initialize();
        return new AsyncHandlingExecutor(taskExecutor, threadGroupName);
    }

}
