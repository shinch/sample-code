package com.gmail.shinch.report.mapper;

import com.gmail.shinch.report.controller.api.place.PlaceResponseV1;
import com.gmail.shinch.report.dao.api.kakao.PlaceVo;
import com.gmail.shinch.report.service.search.PlaceDto;
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
public class PlaceMapperImpl implements PlaceMapper {

    @Override
    public PlaceDto toPlaceDto(PlaceVo inVo) {
        if ( inVo == null ) {
            return null;
        }

        PlaceDto placeDto = new PlaceDto();

        placeDto.setAddress( inVo.getAddress() );
        placeDto.setDistance( inVo.getDistance() );
        placeDto.setPlaceUrl( inVo.getPlaceUrl() );
        placeDto.setPlaceId( inVo.getPlaceId() );
        placeDto.setCategoryName( inVo.getCategoryName() );
        placeDto.setXPosition( inVo.getXPosition() );
        placeDto.setPhone( inVo.getPhone() );
        placeDto.setCategoryGroupName( inVo.getCategoryGroupName() );
        placeDto.setYPosition( inVo.getYPosition() );
        placeDto.setRoadAddress( inVo.getRoadAddress() );
        placeDto.setCategoryGroupCode( inVo.getCategoryGroupCode() );
        placeDto.setPlaceName( inVo.getPlaceName() );

        return placeDto;
    }

    @Override
    public List<PlaceDto> toPlaceDtos(List<PlaceVo> inVos) {
        if ( inVos == null ) {
            return null;
        }

        List<PlaceDto> list = new ArrayList<PlaceDto>( inVos.size() );
        for ( PlaceVo placeVo : inVos ) {
            list.add( toPlaceDto( placeVo ) );
        }

        return list;
    }

    @Override
    public PlaceResponseV1 toPlaceResponseV1(PlaceDto inDto) {
        if ( inDto == null ) {
            return null;
        }

        PlaceResponseV1 placeResponseV1 = new PlaceResponseV1();

        placeResponseV1.setXPosition( inDto.getXPosition() );
        placeResponseV1.setAddress( inDto.getAddress() );
        placeResponseV1.setYPosition( inDto.getYPosition() );
        placeResponseV1.setRoadAddress( inDto.getRoadAddress() );
        placeResponseV1.setPlaceId( inDto.getPlaceId() );
        placeResponseV1.setPhoneNumebr( inDto.getPhone() );
        placeResponseV1.setPlaceName( inDto.getPlaceName() );
        placeResponseV1.setPlaceLink( inDto.getPlaceUrl() );

        return placeResponseV1;
    }

    @Override
    public List<PlaceResponseV1> toPlaceResponseV1s(List<PlaceDto> inDtos) {
        if ( inDtos == null ) {
            return null;
        }

        List<PlaceResponseV1> list = new ArrayList<PlaceResponseV1>( inDtos.size() );
        for ( PlaceDto placeDto : inDtos ) {
            list.add( toPlaceResponseV1( placeDto ) );
        }

        return list;
    }
}
