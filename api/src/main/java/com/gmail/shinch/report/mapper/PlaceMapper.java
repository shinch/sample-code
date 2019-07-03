package com.gmail.shinch.report.mapper;

import com.gmail.shinch.report.controller.api.place.PlaceResponseV1;
import com.gmail.shinch.report.dao.api.kakao.PlaceVo;
import com.gmail.shinch.report.service.search.PlaceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlaceMapper {
    @Mappings({
            @Mapping(target = "placeName", source = "placeName"),
            @Mapping(target = "distance", source = "distance"),
            @Mapping(target = "placeUrl", source = "placeUrl"),
            @Mapping(target = "categoryName", source = "categoryName"),
            @Mapping(target = "address", source = "address"),
            @Mapping(target = "roadAddress", source = "roadAddress"),
            @Mapping(target = "placeId", source = "placeId"),
            @Mapping(target = "phone", source = "phone"),
            @Mapping(target = "categoryGroupCode", source = "categoryGroupCode"),
            @Mapping(target = "categoryGroupName", source = "categoryGroupName"),
            @Mapping(target = "XPosition", source = "XPosition"),
            @Mapping(target = "YPosition", source = "YPosition")
    })
    PlaceDto toPlaceDto(PlaceVo inVo);
    List<PlaceDto> toPlaceDtos(List<PlaceVo> inVos);

    @Mappings({
            @Mapping(target = "placeId", source = "placeId"),
            @Mapping(target = "placeName", source = "placeName"),
            @Mapping(target = "address", source = "address"),
            @Mapping(target = "roadAddress", source = "roadAddress"),
            @Mapping(target = "phoneNumebr", source = "phone"),
            @Mapping(target = "XPosition", source = "XPosition"),
            @Mapping(target = "YPosition", source = "YPosition"),
            @Mapping(target = "placeLink", source = "placeUrl")
    })
    PlaceResponseV1 toPlaceResponseV1(PlaceDto inDto);
    List<PlaceResponseV1> toPlaceResponseV1s(List<PlaceDto> inDtos);
}
