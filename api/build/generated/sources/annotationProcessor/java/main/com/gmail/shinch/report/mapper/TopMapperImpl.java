package com.gmail.shinch.report.mapper;

import com.gmail.shinch.report.controller.api.keyword.TopKeywordResponseV1;
import com.gmail.shinch.report.dao.database.report.top_keyword.TopKeywordEntity;
import com.gmail.shinch.report.service.top.TopKeywordDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-07-03T16:34:15+0900",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_171 (Oracle Corporation)"
)
@Component
public class TopMapperImpl implements TopMapper {

    @Override
    public TopKeywordDto toTopKeywordDto(TopKeywordEntity inEntity) {
        if ( inEntity == null ) {
            return null;
        }

        TopKeywordDto topKeywordDto = new TopKeywordDto();

        topKeywordDto.setCnt( inEntity.getCnt() );
        topKeywordDto.setKeyword( inEntity.getKeyword() );

        return topKeywordDto;
    }

    @Override
    public List<TopKeywordDto> toTopKeywordDtos(List<TopKeywordEntity> inEntities) {
        if ( inEntities == null ) {
            return null;
        }

        List<TopKeywordDto> list = new ArrayList<TopKeywordDto>( inEntities.size() );
        for ( TopKeywordEntity topKeywordEntity : inEntities ) {
            list.add( toTopKeywordDto( topKeywordEntity ) );
        }

        return list;
    }

    @Override
    public TopKeywordResponseV1 toTopKeywordResponseV1(TopKeywordDto inDto) {
        if ( inDto == null ) {
            return null;
        }

        TopKeywordResponseV1 topKeywordResponseV1 = new TopKeywordResponseV1();

        topKeywordResponseV1.setCnt( inDto.getCnt() );
        topKeywordResponseV1.setKeyword( inDto.getKeyword() );

        return topKeywordResponseV1;
    }

    @Override
    public List<TopKeywordResponseV1> toTopKeywordResponseV1s(List<TopKeywordDto> inDtos) {
        if ( inDtos == null ) {
            return null;
        }

        List<TopKeywordResponseV1> list = new ArrayList<TopKeywordResponseV1>( inDtos.size() );
        for ( TopKeywordDto topKeywordDto : inDtos ) {
            list.add( toTopKeywordResponseV1( topKeywordDto ) );
        }

        return list;
    }
}
