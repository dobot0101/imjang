package com.dobot.imjang.domain.view;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dobot.imjang.domain.building.entities.Building;
import com.dobot.imjang.domain.building.services.BuildingService;
import com.dobot.imjang.domain.member.entities.Member;
import com.dobot.imjang.domain.member.services.MemberService;

@Controller
public class ViewController {
  private final BuildingService buildingService;
  private final MemberService memberService;

  public ViewController(BuildingService buildingService, MemberService memberService) {
    this.buildingService = buildingService;
    this.memberService = memberService;
  }

  @GetMapping("/")
  public String home(Model model) {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    Member member = memberService.getMemberByEmail(email);
    List<Building> buildings = buildingService.getBuildingsByMember(member);

    model.addAttribute("buildings", buildings);
    model.addAttribute("username", email);

    return "home";
  }

  @GetMapping("/building/register")
  public String showBuildingRegisterPage(Model model, @RequestParam("lat") double latitude,
      @RequestParam("lng") double longitude, @RequestParam("address") String address,
      @RequestParam("buildingName") String buildingName) {
    model.addAttribute("latitude", latitude);
    model.addAttribute("longitude", longitude);
    model.addAttribute("address", address);
    model.addAttribute("buildingName", buildingName);
    return "create-building";
  }
}
