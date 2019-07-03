package com.gmail.shinch.report.controller.api.keyword;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value="response.keyword.history.RES.V1", description = "조회 목록")
public class MyHistoryResponseV1 {
    @ApiModelProperty(notes = "키워드", required = true)
    private String keyword;
    @ApiModelProperty(notes = "조회일시", required = true)
    private LocalDateTime searchAt;
}
