package com.gmail.shinch.report.mapper;

import com.gmail.shinch.report.controller.api.keyword.MyHistoryResponseV1;
import com.gmail.shinch.report.dao.database.report.search_history.SearchHistoryEntity;
import com.gmail.shinch.report.service.history.HistoryDto;
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
public class HistoryMapperImpl implements HistoryMapper {

    @Override
    public HistoryDto toHistoryDto(SearchHistoryEntity inEntity) {
        if ( inEntity == null ) {
            return null;
        }

        HistoryDto historyDto = new HistoryDto();

        historyDto.setId( inEntity.getId() );
        historyDto.setKeyword( inEntity.getKeyword() );
        historyDto.setSearchAt( inEntity.getCreateAt() );

        return historyDto;
    }

    @Override
    public List<HistoryDto> toHistoryDtos(List<SearchHistoryEntity> inEntities) {
        if ( inEntities == null ) {
            return null;
        }

        List<HistoryDto> list = new ArrayList<HistoryDto>( inEntities.size() );
        for ( SearchHistoryEntity searchHistoryEntity : inEntities ) {
            list.add( toHistoryDto( searchHistoryEntity ) );
        }

        return list;
    }

    @Override
    public MyHistoryResponseV1 toMyHistoryResponseV1(HistoryDto inDto) {
        if ( inDto == null ) {
            return null;
        }

        MyHistoryResponseV1 myHistoryResponseV1 = new MyHistoryResponseV1();

        myHistoryResponseV1.setKeyword( inDto.getKeyword() );
        myHistoryResponseV1.setSearchAt( inDto.getSearchAt() );

        return myHistoryResponseV1;
    }

    @Override
    public List<MyHistoryResponseV1> toMyHistoryResponseV1s(List<HistoryDto> inDtos) {
        if ( inDtos == null ) {
            return null;
        }

        List<MyHistoryResponseV1> list = new ArrayList<MyHistoryResponseV1>( inDtos.size() );
        for ( HistoryDto historyDto : inDtos ) {
            list.add( toMyHistoryResponseV1( historyDto ) );
        }

        return list;
    }
}
