package com.dobot.imjang.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dobot.imjang.dtos.AuthLoginRequest;
import com.dobot.imjang.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping(path = "/login")
  public ResponseEntity<String> login(@RequestBody @Validated AuthLoginRequest authLoginRequest) {
    return ResponseEntity.ok().body(this.authService.login(authLoginRequest));
  }
}
