package com.gmail.shinch.report.service.search;

import lombok.Data;

@Data
public class PlaceDto {
    private String placeName;
    private int distance;
    private String placeUrl;
    private String categoryName;
    private String address;
    private String roadAddress;
    private int placeId;
    private String phone;
    private String categoryGroupCode;
    private String categoryGroupName;
    private String xPosition;
    private String yPosition;
}
