package com.gmail.shinch.report.dao.database.report.top_keyword;

import com.gmail.shinch.report.dao.database.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TopKeywordRepository extends BaseRepository<TopKeywordEntity, Integer> {
    Page<TopKeywordEntity> findAll(Pageable pageable);
    Optional<TopKeywordEntity> findByKeyword(String keyword);
    @Modifying
    @Query(value = "UPDATE top_keyword SET cnt = cnt + 1 WHERE keyword = :keyword", nativeQuery = true)
    void updateByKeyword(@Param("keyword") String keyword);
}
