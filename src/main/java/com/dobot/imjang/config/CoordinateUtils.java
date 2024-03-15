package com.dobot.imjang.config;

import java.util.Random;

public class CoordinateUtils {
  public static record Coordinates(double latitude, double longitude) {
  }

  public static Coordinates getRandomCoordinates() {
    // Create a Random object
    Random random = new Random();

    // Generate random latitude and longitude
    double minLat = -90.0;
    double maxLat = 90.0;
    double randomLat = minLat + (maxLat - minLat) * random.nextDouble();

    double minLon = -180.0;
    double maxLon = 180.0;
    double randomLon = minLon + (maxLon - minLon) * random.nextDouble();

    return new Coordinates(randomLat, randomLon);
  }
}