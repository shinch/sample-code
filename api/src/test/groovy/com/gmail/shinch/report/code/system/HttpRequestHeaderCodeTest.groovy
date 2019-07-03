package com.gmail.shinch.report.code.system

import spock.lang.Specification

class HttpRequestHeaderCodeTest extends Specification {
    def "enum 기본 확인 Spec"() {
        when:
        HttpRequestHeaderCode checkEnum = sourceEnum
        then:
        checkEnum.headerName != null
        checkEnum.description != null
        HttpRequestHeaderCode.valueOf(checkEnum.name()) != null
        where:
        sourceEnum << HttpRequestHeaderCode.values()
    }
}
