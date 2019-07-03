package com.gmail.shinch.report.controller.exception;

import com.gmail.shinch.report.exception.NoContentException;
import com.gmail.shinch.report.exception.NotImplementedException;
import com.gmail.shinch.report.exception.UnauthorizedException;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {

    //204
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(NoContentException.class)
    public void noContent(NoContentException ex) {
        log.warn("NO_CONTENT : {}", ex.getMessage());
    }

    //400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public List<String> badRequest(HttpMessageNotReadableException ex) {
        log.error("ERROR Handler", ex);
        return Lists.newArrayList(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public List<String> badRequest(MethodArgumentTypeMismatchException ex) {
        log.error("ERROR Handler", ex);
        List<String> errors = Lists.newArrayList();
        errors.add(ex.getName() + " 형식이 잘못 되었습니다.");
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public List<String> badRequest(MethodArgumentNotValidException ex) {
        List<String> errors = Lists.newArrayList();
        for ( ObjectError errorObj : ex.getBindingResult().getAllErrors() ) {
            errors.add(errorObj.getDefaultMessage());
        }
        log.error("ERROR Handler", ex);
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public List<String> badRequest(ConstraintViolationException ex) {
        log.error("ERROR Handler", ex);
        List<String> errors = Lists.newArrayList();
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations ) {
            errors.add(violation.getMessage());
        }
        return errors;
    }

    //401 Unauthorized
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public List<String> unauthorized(UnauthorizedException ex) {
        List<String> errors = Lists.newArrayList(ex.getMessage());
        return errors;
    }

    //402 Payment Required
    //403 Forbidden
    //404
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public List<String> notFound(HttpServletRequest request, NoHandlerFoundException ex) {
        List<String> errors = Lists.newArrayList();
        if ( request.getRequestURI().startsWith("/api/") ) {
            errors.add("요청한 API를 찾을 수 없습니다.");
            log.error("ERROR Handler", ex);
        }
        return errors;
    }
    //405
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public List<String> methodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        log.error("ERROR Handler", ex);
        return Lists.newArrayList(ex.getMessage());
    }
    //406 Not Acceptable
    //407 Proxy Authentication Required
    //408 Request Timeout
    //409 Conflict
    //410 Gone
    //411 Length Required
    //412 Precondition Failed
    //413 Request Entity Too Large
    //414 Request-URI Too Long
    //415
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public List<String> unsupportedMediaType(HttpMediaTypeNotSupportedException ex) {
        log.error("ERROR Handler", ex);
        return Lists.newArrayList(ex.getMessage());
    }

    //416 Requested range not satisfiable
    //417 Expectation Failed

    //500
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public List<String> internalServerError(Exception ex) {
        log.error("ERROR Handler", ex);
        return Lists.newArrayList(ex.getMessage());
    }

    //501 Not implemented
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    @ExceptionHandler(NotImplementedException.class)
    @ResponseBody
    public List<String> notImplemented(NotImplementedException ex) {
        log.error("ERROR Handler", ex);
        return Lists.newArrayList(ex.getMessage());
    }
    //502 Bad Gateway
    //503 Service Unavailable
    //504 Gateway Timeout
    //505 HTTP Version Not Supported

}
