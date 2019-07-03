package com.gmail.shinch.report.facade.search

import com.gmail.shinch.report.service.async.AsyncWrapper
import com.gmail.shinch.report.service.model.PageResultDto
import com.gmail.shinch.report.service.search.PlaceDto
import com.gmail.shinch.report.service.search.SearchService
import com.gmail.shinch.report.service.top.TopService
import org.assertj.core.util.Lists
import org.springframework.scheduling.annotation.AsyncResult
import spock.lang.Specification

import java.time.LocalDateTime

class SearchFacadeTest extends Specification {
    SearchService searchService
    AsyncWrapper asyncWrapper
    TopService topService
    SearchFacade testFacade

    def "setup"() {
        searchService = Mock(SearchService)
        asyncWrapper = Mock(AsyncWrapper)
        topService = Mock(TopService)
        testFacade = new SearchFacade(searchService, asyncWrapper, topService)
    }

    def "장소 검색 결과가 있을때 spec"() {
        given:
        String userId = "userId"
        String keyword = "keyword"
        int nowPage = 1
        PageResultDto<PlaceDto> pageResult = Mock(PageResultDto)
        when:
        testFacade.searchPlaces(userId, keyword, nowPage)
        then:
        1 * searchService.searchPlaces(keyword, nowPage) >> pageResult
        1 * pageResult.getContent() >> Lists.newArrayList("test")
        1 * asyncWrapper.addMyHistory(userId, keyword, _ as LocalDateTime)
        1 * asyncWrapper.addSearchCount(keyword) >> new AsyncResult(true)
        1 * topService.clearTopKeywordCache()
    }

    def "장소 검색 결과가 옶을때 spec"() {
        given:
        String userId = "userId"
        String keyword = "keyword"
        int nowPage = 1
        PageResultDto<PlaceDto> pageResult = Mock(PageResultDto)
        when:
        testFacade.searchPlaces(userId, keyword, nowPage)
        then:
        1 * searchService.searchPlaces(keyword, nowPage) >> pageResult
        1 * pageResult.getContent() >> Lists.newArrayList()
        0 * asyncWrapper.addMyHistory(userId, keyword, _ as LocalDateTime)
        0 * asyncWrapper.addSearchCount(keyword)
    }
}
