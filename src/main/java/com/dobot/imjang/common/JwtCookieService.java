package com.dobot.imjang.common;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public class JwtCookieService {

  public static void createJwtCookie(HttpServletResponse response, String jwt) {
    var newCookie = new Cookie("jwt", jwt);
    newCookie.setValue(jwt);
    newCookie.setHttpOnly(true);
    newCookie.setPath("/");
    newCookie.setMaxAge(60 * 60);
    response.addCookie(newCookie);
  }

  public static void deleteJwtCookie(HttpServletResponse response) {
    Cookie deleteCookie = new Cookie("jwt", null);
    deleteCookie.setMaxAge(0);
    deleteCookie.setHttpOnly(true);
    deleteCookie.setPath("/");
    response.addCookie(deleteCookie);
  }
}
