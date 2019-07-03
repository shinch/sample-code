package com.gmail.shinch.report.facade.search;

import com.gmail.shinch.report.service.async.AsyncWrapper;
import com.gmail.shinch.report.service.model.PageResultDto;
import com.gmail.shinch.report.service.search.PlaceDto;
import com.gmail.shinch.report.service.search.SearchService;
import com.gmail.shinch.report.service.top.TopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
@Service
public class SearchFacade {
    private final SearchService searchService;
    private final AsyncWrapper asyncWrapper;
    private final TopService topService;

    @Autowired
    public SearchFacade(
            SearchService searchService,
            AsyncWrapper asyncWrapper,
            TopService topService ) {
        this.searchService = searchService;
        this.asyncWrapper = asyncWrapper;
        this.topService = topService;
    }

    public PageResultDto<PlaceDto> searchPlaces(String userId, String keyword, int nowPage) {
        PageResultDto<PlaceDto> pageResult = searchService.searchPlaces(keyword, nowPage);
        if ( pageResult.getContent().size() > 0 && nowPage == 1 ) {
            asyncWrapper.addMyHistory(userId, keyword, LocalDateTime.now());
            Future<Boolean> isClear = asyncWrapper.addSearchCount(keyword);
            try {
                if (isClear.get()) {
                    topService.clearTopKeywordCache();
                }
            } catch (InterruptedException|ExecutionException ex) {
                log.warn("add search count exception : {}", ex.getMessage());
            }
        }
        return pageResult;
    }
}
