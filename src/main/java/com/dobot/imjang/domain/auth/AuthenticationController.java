package com.dobot.imjang.domain.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dobot.imjang.config.JwtUtil;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;

  // @Qualifier("myUserDetailsService")
  // private final UserDetailsService userDetailsService;

  public AuthenticationController(AuthenticationManager authenticationManager, JwtUtil jwtUtil
  // UserDetailsService userDetailsService
  ) {
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
    // this.userDetailsService = userDetailsService;
  }

  @PostMapping
  public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
    Authentication authentication = this.authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(authenticationRequest.email(), authenticationRequest.password()));
    final String jwt = jwtUtil.generateToken(authentication);
    return ResponseEntity.ok(new AuthenticationResponse(jwt));
  }

  // SpringSecurity의 formLogin을 사용하게 돼서 아래 코드들 주석 처리
  // private final AuthService authService;
  // @PostMapping("/login")
  // public ResponseEntity<LoginResponseDto> login(@Valid LoginRequestDto
  // loginRequestDto)
  // throws Exception {
  // LoginResponseDto loginResponseDto =
  // authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
  // return ResponseEntity.ok().body(loginResponseDto);
  // }

  @GetMapping("/login")
  public String showLoginForm(Model model) {
    model.addAttribute("loginRequestDto", new LoginRequestDto());
    return "login";
  }
}
