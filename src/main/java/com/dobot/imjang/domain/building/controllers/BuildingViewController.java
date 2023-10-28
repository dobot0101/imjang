package com.dobot.imjang.domain.building.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/building")
public class BuildingViewController {

  @GetMapping("/register")
  public String showBuildingRegisterPage(Model model, @RequestParam("lat") double latitude,
      @RequestParam("lng") double longitude) {
    model.addAttribute("latitude", latitude);
    model.addAttribute("longitude", longitude);
    return "create-building";
  }

  @GetMapping("/list")
  public String showRegisteredBuildingListPage(Model model) {

    return "building-list";
  }
}
