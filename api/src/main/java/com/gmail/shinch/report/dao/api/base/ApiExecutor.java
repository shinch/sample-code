package com.gmail.shinch.report.dao.api.base;

import com.gmail.shinch.report.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

@Slf4j
public abstract class ApiExecutor {
    private ApiExecutor() {}
    public static <R> R execute(ApiExecutable<R> executable) {
        try {
            return executable.execute();
        } catch ( HttpStatusCodeException ex ) {
            // CHECK : 2xx 가 아닐때 처리
            throw ex;
        } catch ( RestClientException ex ) {
            log.error("ERROR", ex);
            throw new ApiException(ex.getMessage());
        }
    }
    public static <R> R getBody(ResponseEntity<R> responseEntity, String hostName) {
        if ( !responseEntity.getStatusCode().is2xxSuccessful() ) {
            throw new ApiException(hostName + " API 결과가 " + responseEntity.getStatusCodeValue() + " 입니다.");
        }
        return responseEntity.getBody();
    }
}
