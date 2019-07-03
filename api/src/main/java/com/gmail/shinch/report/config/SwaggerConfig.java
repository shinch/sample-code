package com.gmail.shinch.report.config;

import com.gmail.shinch.report.authentication.UserInfoVo;
import com.gmail.shinch.report.code.system.HttpRequestHeaderCode;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    // HTTP 공통 Header 설명 정의
    private Map<String, Header> getResponseHeaders() {
        Map<String, Header> commonHeaders = Maps.newHashMap();
        return commonHeaders;
    }
    private List<Parameter> getDefaultHeaders() {
        return Lists.newArrayList(
                new ParameterBuilder().name("Accept").description("api version 정보 : application/vnd.shinch.api.report-V?+xxx").defaultValue("application/vnd.shinch.api.report-V1+json").required(true).modelRef(new ModelRef("string")).parameterType("header").build(),
                new ParameterBuilder().name(HttpRequestHeaderCode.TOKEN_HEADER.getHeaderName()).description(HttpRequestHeaderCode.TOKEN_HEADER.getDescription()).defaultValue("00000000000000000000000000000000000000000000").required(true).modelRef(new ModelRef("string")).parameterType("header").build(),
                new ParameterBuilder().name(HttpRequestHeaderCode.USER_HEADER.getHeaderName()).description(HttpRequestHeaderCode.USER_HEADER.getDescription()).defaultValue("user-01").required(true).modelRef(new ModelRef("string")).parameterType("header").build()
        );
    }

    // HTTP 공통 Response 설명 정의
    private List<ResponseMessage> getDefaultMessage() {
        return Lists.newArrayList(
                new ResponseMessageBuilder().code(HttpStatus.BAD_REQUEST.value()).message("요청 항목 형식이 유효 하지 않을때 발생시 반환 됩니다. body에 배열로 상세 설명 반환").headersWithDescription(getResponseHeaders()).build(),
                new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("로직 수행 중 발생 한 처리되지 못한 에러 발생시 반환 됩니다.").headersWithDescription(getResponseHeaders()).build() );
    }
    private List<ResponseMessage> getGetDefaultMessage() {
        List<ResponseMessage> responseMessages = Lists.newArrayList(
                new ResponseMessageBuilder().code(HttpStatus.NO_CONTENT.value()).message("요청 데이타가 없을때 반환 됩니다.").headersWithDescription(getResponseHeaders()).build(),
                new ResponseMessageBuilder().code(HttpStatus.BAD_GATEWAY.value()).message("외부 API 호출 실패시 반환 됩니다.").headersWithDescription(getResponseHeaders()).build() );
        responseMessages.addAll(getDefaultMessage());
        return responseMessages;
    }
    private List<ResponseMessage> getPostDefaultMessage() {
        List<ResponseMessage> responseMessages = Lists.newArrayList();
        responseMessages.addAll(getDefaultMessage());
        return responseMessages;
    }
    private List<ResponseMessage> getPutDefaultMessage() {
        List<ResponseMessage> responseMessages = Lists.newArrayList();
        responseMessages.addAll(getDefaultMessage());
        return responseMessages;
    }
    private List<ResponseMessage> getPatchDefaultMessage() {
        List<ResponseMessage> responseMessages = Lists.newArrayList(
                new ResponseMessageBuilder().code(HttpStatus.NO_CONTENT.value()).message("수정 요청 데이타가 없을때 반환 됩니다.").headersWithDescription(getResponseHeaders()).build() );
        responseMessages.addAll(getDefaultMessage());
        return responseMessages;
    }
    private List<ResponseMessage> getDeleteDefaultMessage() {
        List<ResponseMessage> responseMessages = Lists.newArrayList(
                new ResponseMessageBuilder().code(HttpStatus.NO_CONTENT.value()).message("삭제 요청 데이타가 없을때 반환 됩니다.").headersWithDescription(getResponseHeaders()).build() );
        responseMessages.addAll(getDefaultMessage());
        return responseMessages;
    }

    @Bean
    public Docket allApis() {
        return getApis("00.전체", "com.gmail.shinch.report.controller", PathSelectors.any());
    }

    @Bean
    public Docket utilApis() {
        return getApis("90.Utility", "com.gmail.shinch.report.controller.util", PathSelectors.any());
    }

    @Bean
    public Docket manageApis() {
        return getApis("99.시스템 관리", "com.gmail.shinch.report.controller.manage", PathSelectors.any());
    }

    private Docket getApis(String grouppName, String packageName, Predicate<String> paths) {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, getGetDefaultMessage())
                .globalResponseMessage(RequestMethod.POST, getPostDefaultMessage())
                .globalResponseMessage(RequestMethod.PUT, getPutDefaultMessage())
                .globalResponseMessage(RequestMethod.PATCH, getPatchDefaultMessage())
                .globalResponseMessage(RequestMethod.DELETE, getDeleteDefaultMessage())
                .globalOperationParameters(getDefaultHeaders())
                .ignoredParameterTypes(UserInfoVo.class)
                .groupName(grouppName)
                .select()
                .apis(RequestHandlerSelectors.basePackage(packageName))
                .paths(paths)
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "API Documents",
                "문서내 포함되어 있는 Response content type을 Accept Header로 요청 하여야 합니다.",
                null,
                "http://google.com",
                new Contact("신창훈", "", "changhunshin@yanolja.com"),
                "API 사용 방법 및 설명",
                "http://google.com",
                new ArrayList<>());
        return apiInfo;
    }

    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                .defaultModelsExpandDepth(1)
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(false)
                .docExpansion(DocExpansion.NONE)
                .filter(false)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                .validatorUrl(null)
                .build();
    }

}
