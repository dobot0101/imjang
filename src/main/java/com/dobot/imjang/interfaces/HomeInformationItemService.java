package com.dobot.imjang.interfaces;

import java.util.List;
import java.util.UUID;

import com.dobot.imjang.entity.InformationItem;

public interface HomeInformationItemService {
  List<InformationItem> getAllHomeInformationItems();

  InformationItem getHomeInformationItemById(UUID id);

  InformationItem createHomeInformationItem(InformationItem home);

  InformationItem updateHomeInformationItem(UUID id, InformationItem home);

  void deleteHomeInformationItem(UUID id);
}
