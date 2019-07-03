package com.gmail.shinch.report.service.top;

import com.gmail.shinch.report.dao.database.report.top_keyword.TopKeywordEntity;
import com.gmail.shinch.report.dao.database.report.top_keyword.TopKeywordRepository;
import com.gmail.shinch.report.mapper.TopMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TopService {
    private final TopKeywordRepository topKeywordRepository;
    private final TopMapper topMapper;

    public TopService(
            TopKeywordRepository topKeywordRepository,
            TopMapper topMapper ) {
        this.topKeywordRepository = topKeywordRepository;
        this.topMapper = topMapper;
    }

    @Cacheable(value="topKeyword")
    public List<TopKeywordDto> getTopKeyword() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("cnt")));
        Page<TopKeywordEntity> topKeywordEntities = topKeywordRepository.findAll(pageable);
        return topMapper.toTopKeywordDtos(topKeywordEntities.getContent());
    }

    public boolean addSearchCount(String keyword) {
        boolean isClearCache = false;
        Optional<TopKeywordEntity> optionalTopKeywordEntity = topKeywordRepository.findByKeyword(keyword);
        if ( optionalTopKeywordEntity.isPresent() ) {
            topKeywordRepository.updateByKeyword(keyword);
            if (hasKeyword(keyword)) {
                isClearCache = true;
            }
        } else {
            TopKeywordEntity topKeywordEntity = TopKeywordEntity.builder()
                    .keyword(keyword).cnt(1).build();
            topKeywordRepository.save(topKeywordEntity);
            if ( getTopKeyword().size() < 10 ) {
                isClearCache = true;
            }
        }
        return isClearCache;
    }

    @CacheEvict(value="topKeyword", allEntries = true)
    public void clearTopKeywordCache() {
        log.info("clear top keyword cache");
    }

    boolean hasKeyword(String keyword) {
        List<TopKeywordDto> topKeywords = getTopKeyword();
        for ( TopKeywordDto topKeyword : topKeywords) {
            if ( keyword.equals(topKeyword.getKeyword()) ) {
                return true;
            }
        }
        return false;
    }
}
