package com.gmail.shinch.report.dao.api.base;

import com.gmail.shinch.report.exception.ApiException;

public interface ApiExecutable<R> {
    R execute() throws ApiException;
}
