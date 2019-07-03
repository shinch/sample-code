package com.gmail.shinch.report.mapper;

import com.gmail.shinch.report.controller.api.keyword.TopKeywordResponseV1;
import com.gmail.shinch.report.dao.database.report.top_keyword.TopKeywordEntity;
import com.gmail.shinch.report.service.top.TopKeywordDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TopMapper {
    @Mappings({
            @Mapping(target = "keyword", source = "keyword"),
            @Mapping(target = "cnt", source = "cnt")
    })
    TopKeywordDto toTopKeywordDto(TopKeywordEntity inEntity);
    List<TopKeywordDto> toTopKeywordDtos(List<TopKeywordEntity> inEntities);

    @Mappings({
            @Mapping(target = "keyword", source = "keyword"),
            @Mapping(target = "cnt", source = "cnt")
    })
    TopKeywordResponseV1 toTopKeywordResponseV1(TopKeywordDto inDto);
    List<TopKeywordResponseV1> toTopKeywordResponseV1s(List<TopKeywordDto> inDtos);
}
