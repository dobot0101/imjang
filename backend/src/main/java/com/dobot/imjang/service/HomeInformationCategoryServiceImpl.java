package com.dobot.imjang.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.dobot.imjang.entity.InformationCategory;
import com.dobot.imjang.interfaces.HomeInformationCategoryService;
import com.dobot.imjang.repository.InformationCategoryRepository;

public class HomeInformationCategoryServiceImpl implements HomeInformationCategoryService {
  private final InformationCategoryRepository homeInformationCategoryRepository;

  public HomeInformationCategoryServiceImpl(InformationCategoryRepository homeInformationCategoryRepository) {
    this.homeInformationCategoryRepository = homeInformationCategoryRepository;
  }

  @Override
  public List<InformationCategory> getAllHomeInformationCategories() {
    List<InformationCategory> categories = homeInformationCategoryRepository.findAll();
    return categories;
  }

  @Override
  public InformationCategory getHomeInformationCategoryById(UUID id) {
    Optional<InformationCategory> optional = homeInformationCategoryRepository.findById(id);
    if (optional.isPresent()) {
      return optional.get();
    }
    return null;
  }

  @Override
  public InformationCategory createHomeInformationCategory(InformationCategory category) {
    return homeInformationCategoryRepository.save(category);
  }

  @Override
  public InformationCategory updateHomeInformationCategory(UUID id, InformationCategory category) {
    InformationCategory existingCategory = homeInformationCategoryRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "HomeInformationCategory not found"));
    existingCategory.setItems(category.getItems());
    existingCategory.setName(category.getName());
    existingCategory.setParentCategory(category.getParentCategory());
    existingCategory.setSubCategories(category.getSubCategories());
    return homeInformationCategoryRepository.save(existingCategory);
  }

  @Override
  public void deleteHomeInformationCategory(UUID id) {
    homeInformationCategoryRepository.deleteById(id);
  }

}
