package com.gmail.shinch.report.dao.api.kakao;

import com.gmail.shinch.report.dao.api.base.ApiClient;
import com.gmail.shinch.report.dao.api.base.ApiExecutor;
import com.gmail.shinch.report.dao.model.PageParamVo;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@ApiClient
public class KeywordSerchApi {
    private final String API_HOST;
    private final RestTemplate restTemplate;
    private final String API_KEY;
    public static final int PLACE_PER_PAGE = 15;

    @Autowired
    public KeywordSerchApi(
            @Value("${apis.kakao.host}") String apiHost,
            @Value("${apis.kakao.access-key}") String apiKey,
            RestTemplate restTemplate ) {
        this.API_HOST = apiHost;
        this.API_KEY = apiKey;
        this.restTemplate = restTemplate;
    }

    public KakaoPageResponseVo<PlaceVo> getPlace(String keyword, PageParamVo page) {
        String url = "/v2/local/search/keyword.json";
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("query", keyword);
        paramMap.add("page", Integer.toString(page.getNowPage()));
        paramMap.add("size", Integer.toString(page.getLinePerPage()));
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(API_HOST + url);
        URI uri = builder.queryParams(paramMap).build().encode().toUri();

        final MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
        headers.add(HttpHeaders.AUTHORIZATION, "KakaoAK " + API_KEY);

        final HttpEntity<String> httpEntity =  new HttpEntity<>("", headers);

        ParameterizedTypeReference responseType = new ParameterizedTypeReference<KakaoPageResponseVo<PlaceVo>>() {};

        ResponseEntity<KakaoPageResponseVo<PlaceVo>> responseEntityData = ApiExecutor.execute(() -> restTemplate.exchange(uri, HttpMethod.GET, httpEntity, responseType));
        return ApiExecutor.getBody(responseEntityData, this.API_HOST);
    }

}
