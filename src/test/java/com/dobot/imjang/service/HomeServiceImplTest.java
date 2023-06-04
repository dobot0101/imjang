// package com.dobot.imjang.service;

// import static org.junit.jupiter.api.Assertions.assertTrue;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;

// import com.dobot.imjang.dto.HomeCreateRequest;
// import com.dobot.imjang.interfaces.HomeService;
// import com.dobot.imjang.repository.HomeImageRepository;
// import com.dobot.imjang.repository.InformationItemRepository;
// import com.dobot.imjang.repository.JpaHomeRepository;

// public class HomeServiceImplTest {

// HomeService homeService;

// @Autowired
// JpaHomeRepository homeRepository;

// @Autowired
// HomeImageRepository homeImageRepository;

// @Autowired
// InformationItemRepository informationItemRepository;

// @BeforeEach
// public void beforeEach() {
// this.homeService = new HomeServiceImpl(homeRepository,
// informationItemRepository, homeImageRepository);
// }

// @Test
// void 전체목록조회() {
// // given
// var req = new HomeCreateRequest();
// req.setName("테스트집");
// req.setAddress("테스트 주소");
// req.setArea(32);
// req.setMemo("테스트용 홈 데이터 생성");
// var home = homeService.createHome(req);

// var homes = homeService.getAllHomes();
// assertTrue(homes.contains(home));
// }
// }
