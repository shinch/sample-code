package com.gmail.shinch.report.dao.api.kakao

import com.gmail.shinch.report.dao.model.PageParamVo
import groovy.util.logging.Slf4j
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

@Slf4j
class KeywordSerchApiTest extends Specification {
    RestTemplate restTemplate
    KeywordSerchApi keywordSerchApi

    def setup() {
        restTemplate = new RestTemplate()
        keywordSerchApi = new KeywordSerchApi("https://dapi.kakao.com", "b905c3938904dd33b8cde6756a967a16", restTemplate)
    }

    def "/v2/local/search/keyword.json send spec"() {
        given:
        String searchKeyword = "스타벅스"
        PageParamVo page = PageParamVo.builder().nowPage(1).linePerPage(15).build();
        when:
        KakaoPageResponseVo<PlaceVo> placesInfo = keywordSerchApi.getPlace(searchKeyword, page)
        log.info("result : {}", placesInfo)
        then:
        placesInfo != null
    }
}
