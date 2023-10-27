package com.dobot.imjang.domain.building.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BuildingViewController {

  @GetMapping("/building/register")
  public String showBuildingRegisterForm(Model model) {
    return "create-building";
  }
}
