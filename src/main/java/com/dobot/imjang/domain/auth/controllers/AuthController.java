package com.dobot.imjang.domain.auth.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dobot.imjang.domain.auth.dtos.LoginRequest;
import com.dobot.imjang.domain.auth.dtos.LoginResponse;
import com.dobot.imjang.domain.auth.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping(path = "/login")
  public ResponseEntity<LoginResponse> login(@RequestBody @Validated LoginRequest authLoginRequest) throws Exception {
    String token = this.authService.login(authLoginRequest);
    LoginResponse response = new LoginResponse(token);
    return ResponseEntity.ok().body(response);
  }
}
