package com.dobot.imjang.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.dobot.imjang.entity.Home;
import com.dobot.imjang.interfaces.HomeService;
import com.dobot.imjang.repository.HomeRepository;

@Service
public class HomeServiceImpl implements HomeService {
    private final HomeRepository homeRepository;

    public HomeServiceImpl(HomeRepository homeRepository) {
        this.homeRepository = homeRepository;
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
    public Home updateHome(UUID id, Home home) {
        Home existingHome = homeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Home not found"));

        existingHome.setName(home.getName());
        existingHome.setAddress(home.getAddress());
        existingHome.setMemo(home.getMemo());
        existingHome.setInformationItems(home.getInformationItems());

        return homeRepository.save(existingHome);
    }

    @Override
    public void deleteHome(UUID id) {
        homeRepository.deleteById(id);
    }
}
