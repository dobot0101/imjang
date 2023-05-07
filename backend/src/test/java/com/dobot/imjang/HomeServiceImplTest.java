package com.dobot.imjang;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dobot.imjang.entity.Home;
import com.dobot.imjang.entity.HomeInformationCategory;
import com.dobot.imjang.entity.HomeInformationItem;
import com.dobot.imjang.interfaces.HomeService;

public class HomeServiceImplTest {
  @Autowired
  HomeService homeService;

  @BeforeAll
  void BeforeAll() {

    HomeInformationCategory category = new HomeInformationCategory();
    category.setName("테스트 카테고리");

    HomeInformationItem item = new HomeInformationItem();
    item.setName("테스트 아이템");
    item.setCategory(category);

    category.setItems(Arrays.asList(item));

  }

  @Test
  void 목록조회() {
    // given
    Home home = new Home();
    home.setName("테스트");
    home.setAddress("테스트 주소");
    home.setArea(32);
    home.setMemo("테스트 메모");
    home.setInformationItems(Arrays.asList(item));
    this.homeService.createHome(home);


    // when
    List<Home> allHomes = this.homeService.getAllHomes();

    // then
    assert
  }
}