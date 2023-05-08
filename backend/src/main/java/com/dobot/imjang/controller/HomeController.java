package com.dobot.imjang.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dobot.imjang.dto.HomeCreateRequest;
import com.dobot.imjang.dto.HomeUpdateRequest;
import com.dobot.imjang.entity.Home;
import com.dobot.imjang.interfaces.HomeService;

@RestController
@RequestMapping("/homes")
public class HomeController {
  private final HomeService homeService;

  @Autowired
  public HomeController(HomeService homeService) {
    this.homeService = homeService;
  }

  @GetMapping("")
  public List<Home> getAllHomes() {
    return homeService.getAllHomes();
  }

  @GetMapping("/{id}")
  public Home getHome(@PathVariable("id") UUID id) {
    return homeService.getHomeById(id);
  }

  @PostMapping("")
  public Home createHome(@RequestBody HomeCreateRequest homeCreateRequest) {
    return homeService.createHome(homeCreateRequest);
  }

  @PutMapping("/{id}")
  public Home updateHome(@PathVariable("id") UUID id, @RequestBody HomeUpdateRequest homeUpdateRequest) {
    return homeService.updateHome(id, homeUpdateRequest);
  }

  @DeleteMapping("/{id}")
  public void deleteHome(@PathVariable("id") UUID id) {
    homeService.deleteHome(id);
  }
}
