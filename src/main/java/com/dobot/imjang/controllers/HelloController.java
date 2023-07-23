package com.dobot.imjang.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
  @GetMapping("/")
  public String getMethodName() {
    return "Hello Imjang";
  }
}
