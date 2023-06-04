package com.dobot.imjang.interfaces;

import java.util.List;
import java.util.UUID;

import com.dobot.imjang.entity.InformationCategory;

public interface HomeInformationCategoryService {
  List<InformationCategory> getAllHomeInformationCategories();

  InformationCategory getHomeInformationCategoryById(UUID id);

  InformationCategory createHomeInformationCategory(InformationCategory category);

  InformationCategory updateHomeInformationCategory(UUID id, InformationCategory category);

  void deleteHomeInformationCategory(UUID id);
}
