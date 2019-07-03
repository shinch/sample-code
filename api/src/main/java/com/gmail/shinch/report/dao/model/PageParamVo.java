package com.gmail.shinch.report.dao.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageParamVo {
    private int nowPage;
    private int linePerPage;
}
