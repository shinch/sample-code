package com.gmail.shinch.report.controller.api.keyword;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="response.keyword.top.RES.V1", description = "인기 키워드")
public class TopKeywordResponseV1 {
    @ApiModelProperty(notes = "키워드", required = true)
    private String keyword;
    @ApiModelProperty(notes = "검색건수", required = true)
    private int cnt;
}
