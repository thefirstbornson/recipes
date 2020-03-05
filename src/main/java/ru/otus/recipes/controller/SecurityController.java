package ru.otus.recipes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.recipes.security.JWTUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@RestController
public class SecurityController {
    @GetMapping("/token")
    public String getToken() {
        return JWTUtil.getJWTToken();
    }

    @GetMapping(path = "/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "/";
    }
}