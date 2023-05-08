package com.dobot.imjang.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.dobot.imjang.controller.HomeUpdateDTO;
import com.dobot.imjang.entity.Home;
import com.dobot.imjang.entity.HomeImage;
import com.dobot.imjang.entity.HomeInformationItem;
import com.dobot.imjang.entity.InformationItem;
import com.dobot.imjang.interfaces.HomeService;
import com.dobot.imjang.repository.HomeImageRepository;
import com.dobot.imjang.repository.HomeRepository;
import com.dobot.imjang.repository.InformationItemRepository;

@Service
public class HomeServiceImpl implements HomeService {
    private final HomeRepository homeRepository;
    private final InformationItemRepository informationItemRepository;
    private final HomeImageRepository homeImageRepository;

    public HomeServiceImpl(HomeRepository homeRepository, InformationItemRepository informationItemRepository,
            HomeImageRepository homeImageRepository) {
        this.homeRepository = homeRepository;
        this.informationItemRepository = informationItemRepository;
        this.homeImageRepository = homeImageRepository;
    }

    @Override
    public List<Home> getAllHomes() {
        return homeRepository.findAll();
    }

    @Override
    public Home getHomeById(UUID id) {
        return homeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Home not found"));
    }

    @Override
    public Home createHome(Home home) {
        return homeRepository.save(home);
    }

    @Override
    public Home updateHome(UUID id, HomeUpdateDTO dto) {
        Home home = homeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Home not found"));

        home.setName(dto.getName());
        home.setAddress(dto.getAddress());
        home.setMemo(dto.getMemo());

        List<HomeInformationItem> homeInformationItems = new ArrayList<HomeInformationItem>();
        if (dto.getInformationItemIds() != null) {
            for (UUID informationItemId : dto.getInformationItemIds()) {
                Optional<InformationItem> optional = informationItemRepository.findById(informationItemId);
                if (!optional.isPresent()) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "InformationItem not found");
                }
                InformationItem informationItem = optional.get();

                HomeInformationItem homeInformationItem = new HomeInformationItem();
                homeInformationItem.setHome(home);
                homeInformationItem.setInformationItem(informationItem);
                homeInformationItems.add(homeInformationItem);
            }

            home.setHomeInformationItems(homeInformationItems);
        }

        List<HomeImage> homeImages = new ArrayList<HomeImage>();
        if (dto.getImageIds() != null) {
            for (UUID homeImageId : dto.getImageIds()) {
                Optional<HomeImage> optional = homeImageRepository.findById(homeImageId);
                if (!optional.isPresent()) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "HomeImage not found");
                }
                homeImages.add(optional.get());
            }
        }
        home.setImages(homeImages);

        return homeRepository.save(home);
    }

    @Override
    public void deleteHome(UUID id) {
        homeRepository.deleteById(id);
    }
}
