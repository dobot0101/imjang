package com.dobot.imjang.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dobot.imjang.dto.HomeCreateRequest;
import com.dobot.imjang.dto.HomeUpdateRequest;
import com.dobot.imjang.entity.Home;
import com.dobot.imjang.entity.HomeImage;
import com.dobot.imjang.entity.HomeInformationItem;
import com.dobot.imjang.entity.InformationItem;
import com.dobot.imjang.exception.NotFoundException;
import com.dobot.imjang.interfaces.HomeService;
import com.dobot.imjang.repository.HomeImageRepository;
import com.dobot.imjang.repository.HomeRepository;
import com.dobot.imjang.repository.InformationItemRepository;

@Service
public class HomeServiceImpl implements HomeService {
    private final HomeRepository homeRepository;
    private final InformationItemRepository informationItemRepository;
    private final HomeImageRepository homeImageRepository;

    @Autowired
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
        Optional<Home> optional = homeRepository.findById(id);
        if (!optional.isPresent()) {
            throw new NotFoundException("Home not found");
        }
        return optional.get();
    }

    @Override
    public Home createHome(HomeCreateRequest createReq) {
        Home home = new Home();
        home.setAddress(createReq.getAddress());
        home.setArea(createReq.getArea());
        home.setMemo(createReq.getMemo());
        home.setName(createReq.getName());

        List<UUID> informationItemIds = createReq.getInformationItemIds();
        if (informationItemIds != null) {
            List<InformationItem> informationItems = informationItemRepository.findAllById(informationItemIds);
            List<HomeInformationItem> homeInformationItems = new ArrayList<>();
            if (!informationItems.isEmpty()) {
                HomeInformationItem homeInformationItem = new HomeInformationItem();
                for (InformationItem informationItem : informationItems) {
                    homeInformationItem.setHome(home);
                    homeInformationItem.setInformationItem(informationItem);
                    homeInformationItems.add(homeInformationItem);
                }
            }
            home.setHomeInformationItems(homeInformationItems);
        }

        List<UUID> imageIds = createReq.getImageIds();
        if (imageIds != null) {
            List<HomeImage> homeImages = homeImageRepository.findAllById(imageIds);
            home.setImages(homeImages);
        }

        return homeRepository.save(home);
    }

    @Override
    public Home updateHome(UUID id, HomeUpdateRequest updateReq) {
        Optional<Home> homeOptional = homeRepository.findById(id);
        if (!homeOptional.isPresent()) {
            throw new NotFoundException("Home not found");
        }

        Home home = homeOptional.get();
        home.setName(updateReq.getName());
        home.setAddress(updateReq.getAddress());
        home.setMemo(updateReq.getMemo());

        List<UUID> informationItemIds = updateReq.getInformationItemIds();
        if (informationItemIds != null) {
            List<InformationItem> informationItems = informationItemRepository.findAllById(informationItemIds);
            List<HomeInformationItem> homeInformationItems = new ArrayList<>();
            if (!informationItems.isEmpty()) {
                HomeInformationItem homeInformationItem = new HomeInformationItem();
                for (InformationItem informationItem : informationItems) {
                    homeInformationItem.setHome(home);
                    homeInformationItem.setInformationItem(informationItem);
                    homeInformationItems.add(homeInformationItem);
                }
            }
            home.setHomeInformationItems(homeInformationItems);
        }

        List<UUID> imageIds = updateReq.getImageIds();
        if (imageIds != null) {
            List<HomeImage> homeImages = homeImageRepository.findAllById(imageIds);
            home.setImages(homeImages);
        }

        return homeRepository.save(home);
    }

    @Override
    public void deleteHome(UUID id) {
        homeRepository.deleteById(id);
    }
}
