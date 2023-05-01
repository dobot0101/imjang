package com.dobot.imjang.interfaces;

import java.util.List;
import java.util.UUID;

import com.dobot.imjang.entity.Home;

public interface HomeService {
  List<Home> getAllHomes();

  Home getHomeById(UUID id);

  Home createHome(Home home);

  Home updateHome(UUID id, Home home);

  void deleteHome(UUID id);
}
