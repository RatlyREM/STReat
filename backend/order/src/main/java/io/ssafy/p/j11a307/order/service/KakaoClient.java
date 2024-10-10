package io.ssafy.p.j11a307.order.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoMapClient", url = "https://dapi.kakao.com/v2/local")

public interface KakaoClient {
    @GetMapping("/search/keyword.json")
    String searchLocation(@RequestParam("query") String query,
                          @RequestHeader("Authorization") String authorization);



}