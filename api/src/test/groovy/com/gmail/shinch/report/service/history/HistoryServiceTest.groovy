package com.gmail.shinch.report.service.history

import com.gmail.shinch.report.dao.database.report.search_history.SearchHistoryEntity
import com.gmail.shinch.report.dao.database.report.search_history.SearchHistoryRepository
import com.gmail.shinch.report.mapper.HistoryMapper
import org.assertj.core.util.Lists
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import spock.lang.Specification

import java.time.LocalDateTime

class HistoryServiceTest extends Specification {
    SearchHistoryRepository searchHistoryRepository
    HistoryMapper historyMapper
    HistoryService testService

    def "setup"() {
        searchHistoryRepository = Mock(SearchHistoryRepository)
        historyMapper = Mock(HistoryMapper)
        testService = new HistoryService(searchHistoryRepository, historyMapper)
    }

    def "검색 목록 가져오기 spec"() {
        given:
        String userId = "userId"
        int nowPage = 1
        int linePerPage = 20
        Page<SearchHistoryEntity> searchHistoryEntities = Mock(Page)
        List<SearchHistoryEntity> contents = Lists.newArrayList()
        when:
        testService.searchMyHistory(userId, nowPage, linePerPage)
        then:
        1 * searchHistoryRepository.findByCreateBy(userId, _ as Pageable) >> searchHistoryEntities
        1 * searchHistoryEntities.getContent() >> contents
        1 * historyMapper.toHistoryDtos(contents)
    }

    def "검색 항목 등록 spec"() {
        given:
        String userId = "userId"
        String keyword = "keyword"
        LocalDateTime searchAt = LocalDateTime.now()
        when:
        testService.addMyHistory(userId, keyword, searchAt)
        then:
        1 * searchHistoryRepository.save(_ as SearchHistoryEntity)
    }
}
