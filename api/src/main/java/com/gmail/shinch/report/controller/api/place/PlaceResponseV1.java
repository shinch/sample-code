package com.gmail.shinch.report.controller.api.place;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="response.place.search.RES.V1", description = "위치 세부 정보")
public class PlaceResponseV1 {
    @ApiModelProperty(notes = "위치번호", required = true)
    private int placeId;
    @ApiModelProperty(notes = "위치명", required = true)
    private String placeName;
    @ApiModelProperty(notes = "지번주소", required = true)
    private String address;
    @ApiModelProperty(notes = "도로명주소", required = true)
    private String roadAddress;
    @ApiModelProperty(notes = "전화번호", required = true)
    private String phoneNumebr;
    @ApiModelProperty(notes = "경도", required = true)
    private String xPosition;
    @ApiModelProperty(notes = "위도", required = true)
    private String yPosition;
    @ApiModelProperty(notes = "지도 link", required = true)
    private String placeLink;
}
