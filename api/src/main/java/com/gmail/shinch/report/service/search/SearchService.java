package com.gmail.shinch.report.service.search;

import com.gmail.shinch.report.dao.api.kakao.KakaoPageResponseVo;
import com.gmail.shinch.report.dao.api.kakao.KeywordSerchApi;
import com.gmail.shinch.report.dao.api.kakao.PlaceVo;
import com.gmail.shinch.report.dao.model.PageParamVo;
import com.gmail.shinch.report.mapper.PlaceMapper;
import com.gmail.shinch.report.service.model.PageResultDto;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    private final KeywordSerchApi keywordSerchApi;
    private final PlaceMapper placeMapper;

    public SearchService(
            KeywordSerchApi keywordSerchApi,
            PlaceMapper placeMapper ) {
        this.keywordSerchApi = keywordSerchApi;
        this.placeMapper = placeMapper;
    }

    public PageResultDto<PlaceDto> searchPlaces(String keyword, int nowPage) {
        PageParamVo page = PageParamVo.builder().nowPage(nowPage).linePerPage(KeywordSerchApi.PLACE_PER_PAGE).build();
        KakaoPageResponseVo<PlaceVo> pagePlaces = keywordSerchApi.getPlace(keyword, page);
        return new PageResultDto<>(placeMapper.toPlaceDtos(pagePlaces.getDocuments()), page, pagePlaces.getTotalCount());
    }
}
