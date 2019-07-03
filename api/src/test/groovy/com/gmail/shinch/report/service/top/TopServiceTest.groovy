package com.gmail.shinch.report.service.top

import com.gmail.shinch.report.dao.database.report.top_keyword.TopKeywordEntity
import com.gmail.shinch.report.dao.database.report.top_keyword.TopKeywordRepository
import com.gmail.shinch.report.mapper.TopMapper
import org.assertj.core.util.Lists
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class TopServiceTest extends Specification {
    TopKeywordRepository topKeywordRepository
    TopMapper topMapper
    TopService testService
    TopService overrideService

    def "setup"() {
        topKeywordRepository = Mock(TopKeywordRepository)
        topMapper = Mock(TopMapper)
        testService = new TopService(topKeywordRepository, topMapper)
        overrideService = new TopService(topKeywordRepository, topMapper) {
            boolean hasKeyword(String keyword) { true }
        }
    }

    def "자주 찾은 검색어 목록 spec"() {
        given:
        Page<TopKeywordEntity> topKeywordEntities = Mock(Page)
        when:
        testService.getTopKeyword()
        then:
        1 * topKeywordRepository.findAll(_ as Pageable) >> topKeywordEntities
        1 * topKeywordEntities.getContent() >> Lists.newArrayList()
        1 * topMapper.toTopKeywordDtos(_ as List)
    }

    def "keyword 검색 횟수 추가 spec"() {
        given:
        String keyword = "keyword"
        Optional<TopKeywordEntity> optionalTopKeywordEntity = Optional.of(TopKeywordEntity.builder().build())
        when:
        overrideService.addSearchCount(keyword)
        then:
        1 * topKeywordRepository.findByKeyword(keyword) >> optionalTopKeywordEntity
        1 * topKeywordRepository.updateByKeyword(keyword)
    }

    def "keyword 검색 신규 추가 spec"() {
        given:
        String keyword = "keyword"
        Optional<TopKeywordEntity> optionalTopKeywordEntity = Optional.empty()
        Page<TopKeywordEntity> topKeywordEntities = Mock(Page)
        when:
        overrideService.addSearchCount(keyword)
        then:
        1 * topKeywordRepository.findByKeyword(keyword) >> optionalTopKeywordEntity
        1 * topKeywordRepository.findAll(_ as Pageable) >> topKeywordEntities
        1 * topKeywordEntities.getContent() >> Lists.newArrayList()
        1 * topKeywordRepository.save(_ as TopKeywordEntity)
        1 * topMapper.toTopKeywordDtos(_ as List) >> Lists.newArrayList()
    }
}
