package com.dobot.imjang.controller;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dobot.imjang.entity.Member;
import com.dobot.imjang.repository.MemberRepository;

@Service
public class KakaoLoginService {
  private final RestTemplate restTemplate;
  private final MemberRepository memberRepository;

  @Autowired
  public KakaoLoginService(RestTemplate restTemplate, MemberRepository memberRepository) {
    this.restTemplate = restTemplate;
    this.memberRepository = memberRepository;
  }

  @Value("${kakao.clientId}")
  private String kakaoClientId;

  @Value("${kakao.redirectUri}")
  private String kakaoRedirectUri;

  public String processCallback(String code) {
    // Exchange the authorization code for an access token
    String accessToken = getAccessToken(code);

    // Get user information using the access token
    String userInfo = getUserInfo(accessToken);

    List<Member> members = this.memberRepository.findAll();
    List<Member> membersWithKakaoLogin = members.stream()
        .filter(member -> member.getKakaoLogin() != null && member.getKakaoLogin().getKakaoUserId() == userInfo.code)
        .collect(Collectors.toList());

    // Process the user information
    // ...

    return "Kakao login successful!";
  }

  private String getAccessToken(String code) {
    String accessTokenUrl = "https://kauth.kakao.com/oauth/token";

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    AccessTokenRequest request = getAccessTokenRequest(code);
    HttpEntity<AccessTokenRequest> entity = new HttpEntity<>(request, headers);

    ResponseEntity<AccessTokenResponse> response = restTemplate.postForEntity(accessTokenUrl, entity,
        AccessTokenResponse.class);

    return response.getBody().getAccessToken();
  }

  private AccessTokenRequest getAccessTokenRequest(String code) {
    AccessTokenRequest request = new AccessTokenRequest();
    request.setGrantType("authorization_code");
    request.setClientId(kakaoClientId);
    request.setRedirectUri(kakaoRedirectUri);
    request.setCode(code);
    return request;
  }

  private String getUserInfo(String accessToken) {
    String userInfoUrl = "https://kapi.kakao.com/v2/user/me";

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + accessToken);
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> entity = new HttpEntity<>(headers);

    ResponseEntity<String> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity,
        String.class);

    return response.getBody();
  }

}
