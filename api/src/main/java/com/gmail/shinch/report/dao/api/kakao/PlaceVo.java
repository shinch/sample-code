package com.gmail.shinch.report.dao.api.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PlaceVo {
    @JsonProperty("place_name")
    private String placeName;
    @JsonProperty("distance")
    private int distance;
    @JsonProperty("place_url")
    private String placeUrl;
    @JsonProperty("category_name")
    private String categoryName;
    @JsonProperty("address_name")
    private String address;
    @JsonProperty("road_address_name")
    private String roadAddress;
    @JsonProperty("id")
    private int placeId;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("category_group_code")
    private String categoryGroupCode;
    @JsonProperty("category_group_name")
    private String categoryGroupName;
    @JsonProperty("x")
    private String xPosition;
    @JsonProperty("y")
    private String yPosition;
}
