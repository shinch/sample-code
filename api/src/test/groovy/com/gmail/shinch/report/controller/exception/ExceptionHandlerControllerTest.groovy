package com.gmail.shinch.report.controller.exception

import com.gmail.shinch.report.exception.NoContentException
import com.gmail.shinch.report.exception.NotImplementedException
import com.gmail.shinch.report.exception.UnauthorizedException
import com.google.common.collect.Sets
import org.assertj.core.util.Lists
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.NoHandlerFoundException
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException

class ExceptionHandlerControllerTest extends Specification {
    ExceptionHandlerController testController

    HttpServletResponse response
    HttpServletRequest request

    def "setup"() {
        response = Mock(HttpServletResponse)
        request = Mock(HttpServletRequest)
        testController = new ExceptionHandlerController()
    }

    def "NoContentException Handler spec"() {
        given:
        NoContentException ex = Mock(NoContentException)
        String message = "message"
        when:
        testController.noContent(ex)
        then:
        1 * ex.getMessage() >> message
    }

    def "HttpMessageNotReadableException Handler spec"() {
        given:
        HttpMessageNotReadableException ex = Mock(HttpMessageNotReadableException)
        String message = "message"
        when:
        List<String> results = testController.badRequest(ex)
        then:
        2 * ex.getMessage() >> message
        results.size() == 1
    }

    def "MethodArgumentTypeMismatchException Handler spec"() {
        given:
        MethodArgumentTypeMismatchException ex = Mock(MethodArgumentTypeMismatchException)
        String message = "message"
        when:
        List<String> results = testController.badRequest(ex)
        then:
        1 * ex.getMessage() >> message
        1 * ex.getName() >> ""
        results.size() == 1
    }

    def "MethodArgumentNotValidException Handler spec"() {
        given:
        BindingResult bindingResult = Mock(BindingResult)
        MethodArgumentNotValidException ex = Mock(MethodArgumentNotValidException)
        ObjectError objectError = Mock(ObjectError)
        List<ObjectError> errorList = Lists.newArrayList(objectError)
        when:
        List<String> results = testController.badRequest(ex)
        then:
        1 * ex.getBindingResult() >> bindingResult
        1 * bindingResult.getAllErrors() >> errorList
        1 * objectError.getDefaultMessage() >> ""
        1 * ex.getMessage()
        results.size() == 1
    }

    def "ConstraintViolationException Handler spec"() {
        given:
        ConstraintViolation<?> constraintViolation = Mock(ConstraintViolation)
        Set<ConstraintViolation<?>> constraintViolations = Sets.newHashSet(constraintViolation)
        ConstraintViolationException ex = Mock(ConstraintViolationException)
        when:
        List<String> results = testController.badRequest(ex)
        then:
        1 * ex.getConstraintViolations() >> constraintViolations
        1 * constraintViolation.getMessage() >> ""
        1 * ex.getMessage()
        results.size() == 1
    }

    def "UnauthorizedException Handler spec"() {
        given:
        UnauthorizedException ex = Mock(UnauthorizedException)
        when:
        List<String> results = testController.unauthorized(ex)
        then:
        1 * ex.getMessage()
        results.size() == 1
    }

    def "NoHandlerFoundException Handler spec"() {
        given:
        NoHandlerFoundException ex = Mock(NoHandlerFoundException)
        when:
        List<String> results = testController.notFound(request, ex)
        then:
        1 * request.getRequestURI() >> "/api/not-found"
        1 * ex.getMessage()
        results.size() == 1
    }

    def "HttpRequestMethodNotSupportedException Handler spec"() {
        given:
        HttpRequestMethodNotSupportedException ex = Mock(HttpRequestMethodNotSupportedException)
        when:
        List<String> results = testController.methodNotAllowed(ex)
        then:
        2 * ex.getMessage() >> "message"
        results.size() == 1
    }

    def "HttpMediaTypeNotSupportedException Handler spec"() {
        given:
        HttpMediaTypeNotSupportedException ex = Mock(HttpMediaTypeNotSupportedException)
        when:
        List<String> results = testController.unsupportedMediaType(ex)
        then:
        2 * ex.getMessage() >> "message"
        results.size() == 1
    }

    def "Exception Handler spec"() {
        given:
        Exception ex = Mock(Exception)
        when:
        List<String> results = testController.internalServerError(ex)
        then:
        2 * ex.getMessage() >> "message"
        results.size() == 1
    }

    def "NotImplementedException Handler spec"() {
        given:
        NotImplementedException ex = Mock(NotImplementedException)
        when:
        List<String> results = testController.notImplemented(ex)
        then:
        2 * ex.getMessage() >> "message"
        results.size() == 1
    }
}
