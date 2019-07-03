package com.gmail.shinch.report.mapper;

import com.gmail.shinch.report.controller.api.keyword.MyHistoryResponseV1;
import com.gmail.shinch.report.dao.database.report.search_history.SearchHistoryEntity;
import com.gmail.shinch.report.service.history.HistoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HistoryMapper {
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "keyword", source = "keyword"),
            @Mapping(target = "searchAt", source = "createAt"),
    })
    HistoryDto toHistoryDto(SearchHistoryEntity inEntity);
    List<HistoryDto> toHistoryDtos(List<SearchHistoryEntity> inEntities);

    @Mappings({
            @Mapping(target = "keyword", source = "keyword"),
            @Mapping(target = "searchAt", source = "searchAt"),
    })
    MyHistoryResponseV1 toMyHistoryResponseV1(HistoryDto inDto);
    List<MyHistoryResponseV1> toMyHistoryResponseV1s(List<HistoryDto> inDtos);
}
