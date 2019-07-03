package com.gmail.shinch.report.service.model;

import com.gmail.shinch.report.dao.model.PageParamVo;
import lombok.Data;

import java.util.List;

@Data
public class PageResultDto<T> {
    public PageResultDto(List<T> content, PageParamVo pageParam, int allCount) {
        this.content = content;
        this.nowPage = pageParam.getNowPage();
        this.linePerPage = pageParam.getLinePerPage();
        this.totalElements = allCount;
    }
    private final List<T> content;
    private final int nowPage;
    private final int linePerPage;
    private final int totalElements;
}
