package com.dobot.imjang.domain.view;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dobot.imjang.domain.building.entity.Building;
import com.dobot.imjang.domain.building.service.BuildingService;
import com.dobot.imjang.domain.member.entity.Member;
import com.dobot.imjang.domain.member.service.MemberService;

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
}
