package com.gmail.shinch.report.dao.database.report.search_history;

import com.gmail.shinch.report.dao.database.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchHistoryRepository extends BaseRepository<SearchHistoryEntity, Integer> {
    Page<SearchHistoryEntity> findByCreateBy(String createBy, Pageable pageable);
}
