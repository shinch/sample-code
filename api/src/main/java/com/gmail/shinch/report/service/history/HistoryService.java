package com.gmail.shinch.report.service.history;

import com.gmail.shinch.report.dao.database.report.search_history.SearchHistoryEntity;
import com.gmail.shinch.report.dao.database.report.search_history.SearchHistoryRepository;
import com.gmail.shinch.report.dao.model.PageParamVo;
import com.gmail.shinch.report.mapper.HistoryMapper;
import com.gmail.shinch.report.service.model.PageResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class HistoryService {
    private final SearchHistoryRepository searchHistoryRepository;
    private final HistoryMapper historyMapper;

    @Autowired
    public HistoryService(
            SearchHistoryRepository searchHistoryRepository,
            HistoryMapper historyMapper ) {
        this.searchHistoryRepository = searchHistoryRepository;
        this.historyMapper = historyMapper;
    }

    public PageResultDto<HistoryDto> searchMyHistory(String userId, int nowPage, int linePerPage) {
        PageParamVo page = PageParamVo.builder().nowPage(nowPage).linePerPage(linePerPage).build();
        Pageable pageable = PageRequest.of(page.getNowPage()-1, page.getLinePerPage(), Sort.by(Sort.Order.desc("createAt")));
        Page<SearchHistoryEntity> searchHistoryEntities = searchHistoryRepository.findByCreateBy(userId, pageable);
        return new PageResultDto<>(historyMapper.toHistoryDtos(searchHistoryEntities.getContent()), page, Long.valueOf(searchHistoryEntities.getTotalElements()).intValue());
    }

    public void addMyHistory(String userId, String keyword, LocalDateTime searchAt) {
        SearchHistoryEntity searchHistory = SearchHistoryEntity.builder()
                .keyword(keyword)
                .createBy(userId)
                .createAt(searchAt)
                .build();
        searchHistoryRepository.save(searchHistory);
    }
}
