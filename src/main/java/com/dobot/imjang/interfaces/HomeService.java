package com.dobot.imjang.interfaces;

import java.util.List;
import java.util.UUID;

import com.dobot.imjang.dto.HomeCreateRequest;
import com.dobot.imjang.dto.HomeUpdateRequest;
import com.dobot.imjang.entity.Home;

public interface HomeService {
  List<Home> getAllHomes();

  Home getHomeById(UUID id);

  Home createHome(HomeCreateRequest homeCreateRequest);

  Home updateHome(UUID id, HomeUpdateRequest homeUpdateRequest);

  void deleteHome(UUID id);
}
