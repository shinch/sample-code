package com.gmail.shinch.report.controller.api.place

import com.gmail.shinch.report.authentication.UserInfoVo
import com.gmail.shinch.report.controller.api.authentication.AuthenticationController
import com.gmail.shinch.report.controller.api.keyword.MyHistoryResponseV1
import com.gmail.shinch.report.facade.search.SearchFacade
import com.gmail.shinch.report.mapper.PlaceMapper
import com.gmail.shinch.report.service.history.HistoryDto
import com.gmail.shinch.report.service.model.PageResultDto
import com.gmail.shinch.report.service.search.PlaceDto
import org.assertj.core.util.Lists
import spock.lang.Specification

import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.executable.ExecutableValidator
import java.lang.reflect.Method

class PlaceControllerTest extends Specification {
    PlaceController testController
    SearchFacade searchFacade
    PlaceMapper placeMapper

    ExecutableValidator executableValidator

    def "setup"() {
        searchFacade = Mock(SearchFacade)
        placeMapper = Mock(PlaceMapper)
        testController = new PlaceController(searchFacade, placeMapper)

        executableValidator = Validation.buildDefaultValidatorFactory().getValidator().forExecutables()
    }

    def "[V1]KEYWORD 검색 API spec"() {
        given:
        String keyword = "keyword"
        Integer page = 1
        String userId = "userId"
        UserInfoVo userInfoVo = new UserInfoVo()
        userInfoVo.setUserId(userId)
        PageResultDto<PlaceDto> pageResultDto = Mock(PageResultDto)
        List<PlaceDto> contents = Lists.newArrayList()
        List<PlaceResponseV1> placeResponseV1s = Lists.newArrayList()
        when:
        testController.searchV1(keyword, page, userInfoVo)
        then:
        searchFacade.searchPlaces(userId, keyword, page) >> pageResultDto
        1 * pageResultDto.getContent() >> contents
        2 * pageResultDto.getNowPage() >> 1
        1 * pageResultDto.getLinePerPage() >> 1
        3 * pageResultDto.getTotalElements() >> 1
        1 * placeMapper.toPlaceResponseV1s(contents) >> placeResponseV1s
    }

    def "[V1]KEYWORD 검색 API Validation spec"() {
        given:
        UserInfoVo userInfoVo = new UserInfoVo()
        expect:
        Method testMethod = testController.getClass().getMethod("searchV1", String.class, Integer.class, UserInfoVo.class)
        Object[] params = [keyword, page, userInfoVo]
        Set<ConstraintViolation<AuthenticationController>> violations = executableValidator.validateParameters(testController, testMethod, params)
        validSize == violations.size()
        where:
        keyword|page||validSize
        null|null||2
        " "|0||2
        "keyword"|1||0
    }
}
