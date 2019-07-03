package com.gmail.shinch.report.controller.api.keyword;

import com.gmail.shinch.report.authentication.Permission;
import com.gmail.shinch.report.authentication.UserInfoVo;
import com.gmail.shinch.report.controller.api.response.PageResponseV1;
import com.gmail.shinch.report.mapper.HistoryMapper;
import com.gmail.shinch.report.mapper.TopMapper;
import com.gmail.shinch.report.service.history.HistoryDto;
import com.gmail.shinch.report.service.history.HistoryService;
import com.gmail.shinch.report.service.model.PageResultDto;
import com.gmail.shinch.report.service.top.TopKeywordDto;
import com.gmail.shinch.report.service.top.TopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@Validated
@RequestMapping("/api/keyword")
@Api(tags = "01-01.Keyeord 관련 APIs", description = "/api/keyword", produces = "application/json", consumes = "application/json")
public class KeywordController {

    private final TopService topService;
    private final TopMapper topMapper;
    private final HistoryService historyService;
    private final HistoryMapper historyMapper;

    @Autowired
    public KeywordController(
            TopService topService,
            HistoryService historyService,
            TopMapper topMapper,
            HistoryMapper historyMapper ) {
        this.topService = topService;
        this.historyService = historyService;
        this.topMapper = topMapper;
        this.historyMapper = historyMapper;
    }

    @Permission
    @ApiOperation(value = "회원 검색 목록 API", notes = "회원 검색 목록을 반환한다.")
    @GetMapping(value = "/my/{page}", headers = {"Accept=application/vnd.shinch.api.report-V1+json"})
    @ResponseBody
    public PageResponseV1<MyHistoryResponseV1> myKeywordV1(
            @ApiParam(value = "요청 페이지", required = true, example = "1")
            @NotNull(message = "요청 페이지는 필수 값입니다.")
            @Min(value = 1, message = "요청 페이지 최소 1이상 이어야 합니다.")
            @PathVariable("page") Integer page,
            UserInfoVo uesrInfo ) {
        PageResultDto<HistoryDto> histories = historyService.searchMyHistory(uesrInfo.getUserId(), page, 5);
        return new PageResponseV1<>(historyMapper.toMyHistoryResponseV1s(histories.getContent()), histories);
    }

    @Permission
    @ApiOperation(value = "인기 KEYWORD 조회 API", notes = "인기 키워드 상위 10개를 반환한다.")
    @GetMapping(value = "/top", headers = {"Accept=application/vnd.shinch.api.report-V1+json"})
    @ResponseBody
    public List<TopKeywordResponseV1> topV1() {
        List<TopKeywordDto> topKeywords = topService.getTopKeyword();
        return topMapper.toTopKeywordResponseV1s(topKeywords);
    }
}
