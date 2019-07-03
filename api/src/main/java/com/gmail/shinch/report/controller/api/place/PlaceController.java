package com.gmail.shinch.report.controller.api.place;

import com.gmail.shinch.report.authentication.Permission;
import com.gmail.shinch.report.authentication.UserInfoVo;
import com.gmail.shinch.report.controller.api.response.PageResponseV1;
import com.gmail.shinch.report.facade.search.SearchFacade;
import com.gmail.shinch.report.mapper.PlaceMapper;
import com.gmail.shinch.report.service.model.PageResultDto;
import com.gmail.shinch.report.service.search.PlaceDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@Validated
@RequestMapping("/api/place")
@Api(tags = "01-00.장소 관련 APIs", description = "/api/place", produces = "application/json", consumes = "application/json")
public class PlaceController {
    private final SearchFacade searchFacade;
    private final PlaceMapper placeMapper;

    @Autowired
    public PlaceController(
            SearchFacade searchFacade,
            PlaceMapper placeMapper ) {
        this.searchFacade = searchFacade;
        this.placeMapper = placeMapper;
    }

    @Permission
    @ApiOperation(value = "KEYWORD 검색 API", notes = "kakao api를 통하여 장소를 검색한다.")
    @GetMapping(value = "/search/{keyword}/{page}", headers = {"Accept=application/vnd.shinch.api.report-V1+json"})
    @ResponseBody
    public PageResponseV1<PlaceResponseV1> searchV1(
            @ApiParam(value = "검색어", required = true, example = "스타벅스")
            @NotBlank(message = "검색어는 필수 값 입니다.")
            @PathVariable("keyword") String keyword,
            @ApiParam(value = "요청 페이지", required = true, example = "1")
            @NotNull(message = "요청 페이지는 필수 값입니다.")
            @Min(value = 1, message = "요청 페이지 최소 1이상 이어야 합니다.")
            @PathVariable("page") Integer page,
            UserInfoVo uesrInfo) {
        PageResultDto<PlaceDto> places = searchFacade.searchPlaces(uesrInfo.getUserId(), keyword, page);
        return new PageResponseV1<>(placeMapper.toPlaceResponseV1s(places.getContent()), places);
    }
}
