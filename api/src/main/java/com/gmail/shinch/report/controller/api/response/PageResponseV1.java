package com.gmail.shinch.report.controller.api.response;

import com.gmail.shinch.report.service.model.PageResultDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.List;

@Getter
@ApiModel(value="response.page.RES.V1", description = "페이지 결과")
public class PageResponseV1<T> {
    public PageResponseV1(List<T> content, PageResultDto inPage) {
        this.content = content;
        this.number = inPage.getNowPage();
        this.numberOfElements = content.size();
        this.size = inPage.getLinePerPage();
        this.totalElements = inPage.getTotalElements();
        this.totalPages = inPage.getTotalElements() / this.size + (inPage.getTotalElements() % this.size > 0 ? 1 : 0);
        this.first = this.number == 1;
        this.last = this.totalPages == inPage.getNowPage();
    }
    @ApiModelProperty(notes = "결과목록", required = true)
    private final List<T> content;
    @ApiModelProperty(notes = "첫번째 페이지 여부", required = true)
    private final boolean first;
    @ApiModelProperty(notes = "마지막 페이지 여부", required = true)
    private final boolean last;
    @ApiModelProperty(notes = "현 페이지 수", required = true)
    private final int number;
    @ApiModelProperty(notes = "현 항목 수", required = true)
    private final int numberOfElements;
    @ApiModelProperty(notes = "페이지당 항목 수", required = true)
    private final int size;
    @ApiModelProperty(notes = "전체 항목 수", required = true)
    private final int totalElements;
    @ApiModelProperty(notes = "전체 페이지 수", required = true)
    private final int totalPages;
}
