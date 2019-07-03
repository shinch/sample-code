package com.gmail.shinch.report.service.search

import com.gmail.shinch.report.dao.api.kakao.KakaoPageResponseVo
import com.gmail.shinch.report.dao.api.kakao.KeywordSerchApi
import com.gmail.shinch.report.dao.api.kakao.PlaceVo
import com.gmail.shinch.report.dao.model.PageParamVo
import com.gmail.shinch.report.mapper.PlaceMapper
import com.gmail.shinch.report.service.model.PageResultDto
import org.assertj.core.util.Lists
import spock.lang.Specification

class SearchServiceTest extends Specification {
    KeywordSerchApi keywordSerchApi
    PlaceMapper placeMapper
    SearchService searchService

    def "setup"() {
        keywordSerchApi = Mock(KeywordSerchApi)
        placeMapper = Mock(PlaceMapper)
        searchService = new SearchService(keywordSerchApi, placeMapper)
    }

    def "위치 검색 spec"() {
        given:
        String keyword = "keyword"
        int nowPage = 1
        KakaoPageResponseVo<PlaceVo> pagePlaces = Mock(KakaoPageResponseVo)
        when:
        searchService.searchPlaces(keyword, nowPage)
        then:
        1 * keywordSerchApi.getPlace(keyword, _ as PageParamVo) >> pagePlaces
        1 * pagePlaces.getDocuments() >> Lists.newArrayList()
        1 * pagePlaces.getTotalCount()
        1 * placeMapper.toPlaceDtos(_ as List)
    }
}
