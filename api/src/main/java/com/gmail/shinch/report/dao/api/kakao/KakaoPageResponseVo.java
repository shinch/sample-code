package com.gmail.shinch.report.dao.api.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
@Data
public class KakaoPageResponseVo<T> {
    private int totalCount;
    private int totalPage;
    private boolean end;
    @JsonProperty("documents")
    private List<T> documents;
    @JsonProperty("meta")
    public void unpackPageInfo(Map<String, Object> meta) {
        try {
            Integer tmpInt = (Integer)meta.get("pageable_count");
            this.totalPage = tmpInt == null ? 1 : tmpInt;
            tmpInt = (Integer)meta.get("total_count");
            this.totalCount = tmpInt == null ? 1 : tmpInt;
            Boolean tmpBoolean = (Boolean)meta.get("is_end");
            this.end = tmpBoolean == null ? true : tmpBoolean;
        } catch ( Exception ex ) {
            log.warn("예상하지 못한 결과가 반환 되었습니다. {}", meta, ex);
        }
    }

/*
  "meta": {
    "same_name": {
      "region": [],
      "keyword": "카카오프렌즈",
      "selected_region": ""
    },
    "pageable_count": 14,
    "total_count": 14,
    "is_end": true
  },
 */
}
