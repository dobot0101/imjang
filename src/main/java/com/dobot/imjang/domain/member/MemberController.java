package com.dobot.imjang.domain.member;

import com.dobot.imjang.domain.auth.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@Valid SignUpRequestDto signUpRequestDto,
      BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
    }

    this.memberService.signUp(signUpRequestDto);
//      return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
    return ResponseEntity.ok().body("회원가입이 완료되었습니다.");
  }

  @GetMapping("/signup")
  public String showSignupForm(Model model) {
    model.addAttribute("signUpRequestDto", SignUpRequestDto.builder().build());
    model.addAttribute("headerPageName", "회원가입");
    return "signup";
  }

  @GetMapping("/mypage")
  public String showMyPage(Model model) {
    CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
        .getAuthentication()
        .getPrincipal();
    model.addAttribute("username", userDetails.getUsername());
    model.addAttribute("headerPageName", "마이페이지");
    return "mypage";
  }
}
