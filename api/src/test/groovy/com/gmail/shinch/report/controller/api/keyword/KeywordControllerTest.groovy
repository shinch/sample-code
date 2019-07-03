package com.gmail.shinch.report.controller.api.keyword

import com.gmail.shinch.report.authentication.UserInfoVo
import com.gmail.shinch.report.controller.api.authentication.AuthenticationController
import com.gmail.shinch.report.mapper.HistoryMapper
import com.gmail.shinch.report.mapper.TopMapper
import com.gmail.shinch.report.service.history.HistoryDto
import com.gmail.shinch.report.service.history.HistoryService
import com.gmail.shinch.report.service.top.TopKeywordDto
import com.gmail.shinch.report.service.top.TopService
import com.gmail.shinch.report.service.model.PageResultDto
import org.assertj.core.util.Lists
import spock.lang.Specification

import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.executable.ExecutableValidator
import java.lang.reflect.Method

class KeywordControllerTest extends Specification {
    KeywordController testController
    TopService topService
    TopMapper topMapper
    HistoryService historyService
    HistoryMapper historyMapper

    ExecutableValidator executableValidator

    def "setup"() {
        topService = Mock(TopService)
        topMapper = Mock(TopMapper)
        historyService = Mock(HistoryService)
        historyMapper = Mock(HistoryMapper)
        testController = new KeywordController(topService, historyService, topMapper, historyMapper)

        executableValidator = Validation.buildDefaultValidatorFactory().getValidator().forExecutables()
    }

    def "[V1]회원 검색 목록 API Spec"() {
        given:
        int page = 1
        String userId = "userId"
        UserInfoVo userInfoVo = new UserInfoVo()
        userInfoVo.setUserId(userId)
        PageResultDto<HistoryDto> pageResultDto = Mock(PageResultDto)
        List<HistoryDto> contents = Lists.newArrayList()
        List<MyHistoryResponseV1> myHistoryResponseV1s = Lists.newArrayList()
        when:
        testController.myKeywordV1(page, userInfoVo)
        then:
        1 * historyService.searchMyHistory(userId, page, 5) >> pageResultDto
        1 * pageResultDto.getContent() >> contents
        2 * pageResultDto.getNowPage() >> 1
        1 * pageResultDto.getLinePerPage() >> 1
        3 * pageResultDto.getTotalElements() >> 1
        1 * historyMapper.toMyHistoryResponseV1s(contents) >> myHistoryResponseV1s
    }

    def "[V1]회원 검색 목록 API Validation spec"() {
        given:
        UserInfoVo userInfoVo = new UserInfoVo()
        expect:
        Method testMethod = testController.getClass().getMethod("myKeywordV1", Integer.class, UserInfoVo.class)
        Object[] params = [size, userInfoVo]
        Set<ConstraintViolation<AuthenticationController>> violations = executableValidator.validateParameters(testController, testMethod, params)
        validSize == violations.size()
        where:
        size||validSize
        null||1
        0||1
        1||0
    }

    def "[V1]인기 KEYWORD 조회 API Spec"() {
        given:
        List<TopKeywordDto> topKeywrds = Lists.newArrayList()
        when:
        testController.topV1()
        then:
        1 * topService.getTopKeyword() >> topKeywrds
        1 * topMapper.toTopKeywordResponseV1s(topKeywrds)
    }

}
