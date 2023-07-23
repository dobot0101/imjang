package com.dobot.imjang.service;

import java.util.Optional;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dobot.imjang.dtos.AccessTokenRequest;
import com.dobot.imjang.dtos.AccessTokenResponse;
import com.dobot.imjang.dtos.KakaoUserInfo;
import com.dobot.imjang.entities.Member;
import com.dobot.imjang.entities.MemberKakaoLogin;
import com.dobot.imjang.repository.MemberKakaoLoginRepository;
import com.dobot.imjang.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KakaoLoginService {
  private final RestTemplate restTemplate;
  private final MemberKakaoLoginRepository memberKakaoLoginRepository;
  private final AuthService authService;

  public KakaoLoginService(RestTemplate restTemplate,
      MemberKakaoLoginRepository memberKakaoLoginRepository, MemberRepository memberRepository,
      AuthService authService) {
    this.restTemplate = restTemplate;
    this.memberKakaoLoginRepository = memberKakaoLoginRepository;
    this.authService = authService;
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

    ObjectMapper objectMapper = new ObjectMapper();
    KakaoUserInfo kakaoUserInfo;
    try {
      kakaoUserInfo = objectMapper.readValue(userInfo, KakaoUserInfo.class);
      Optional<MemberKakaoLogin> optional = this.memberKakaoLoginRepository.findByKakaoUserId(kakaoUserInfo.getId());
      Member member = optional.map(MemberKakaoLogin::getMember).orElse(null);
      String jwt = null;
      if (member != null) {
        jwt = this.authService.login(member);
      } else {
        member = this.authService.signup(kakaoUserInfo);
      }

      if (jwt == null) {
        throw new AuthenticationException("authentication failed");
      }

      return jwt;
    } catch (Exception e) {
      e.printStackTrace();
    }

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

    AccessTokenResponse body = response.getBody();
    if (body != null) {
      return body.getAccessToken();
    }
    return null;
  }

  private AccessTokenRequest getAccessTokenRequest(String code) {
    AccessTokenRequest request = new AccessTokenRequest();
    request.setGrantType("authorization_code");
    request.setKakaoClientId(kakaoClientId);
    request.setKakaoRedirectUri(kakaoRedirectUri);
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
