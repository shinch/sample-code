package com.gmail.shinch.report.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

@Slf4j
public class AsyncHandlingExecutor implements AsyncTaskExecutor {
    private AsyncTaskExecutor executor;
    private String threadGroupName;

    public AsyncHandlingExecutor(AsyncTaskExecutor executor, String threadGroupName) {
        this.executor = executor;
        this.threadGroupName = threadGroupName;
    }

    @Override
    public void execute(Runnable task) {
        executor.execute(createWrappedRunnable(task));
    }

    @Override
    public void execute(Runnable task, long startTimeout) {
        executor.execute(createWrappedRunnable(task), startTimeout);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return executor.submit(createWrappedRunnable(task));
    }

    @Override
    public <T> Future<T> submit(final Callable<T> task) {
        return executor.submit(createCallable(task));
    }

    private <T> Callable<T> createCallable(final Callable<T> task) {
        return () -> {
            try {
                return task.call();
            } catch (Exception ex) {
                handle(ex);
                throw ex;
            }
        };
    }

    private Runnable createWrappedRunnable(final Runnable task) {
        return () -> {
            try {
                task.run();
            } catch (Exception ex) {
                handle(ex);
            }
        };
    }

    private void handle(Exception ex) {
        log.error("ASYNC EXCEPTION : {}/{}/{}", threadGroupName, ex.getMessage(), ex);
    }

    public void destroy() {
        if(executor instanceof ThreadPoolTaskExecutor){
            ((ThreadPoolTaskExecutor) executor).shutdown();
        }
    }
}
