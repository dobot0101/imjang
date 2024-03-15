package com.dobot.imjang;

import com.dobot.imjang.config.BuildingTestDataInitService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.ConfigurableApplicationContext;

public class InitTestData {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ImjangApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        ConfigurableApplicationContext ctx = app.run(args);

        BuildingTestDataInitService buildingTestDataInitService = ctx.getBean(BuildingTestDataInitService.class);
        buildingTestDataInitService.initBuildingData();

        ctx.close();
    }
}
