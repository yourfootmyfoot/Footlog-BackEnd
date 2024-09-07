package com.yfmf.footlog.login;

import com.yfmf.footlog.login.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    @GetMapping
    public String login() {
        return "login";
    }

    @PostMapping
    public String reissue(HttpServletRequest request, HttpServletResponse response) {

        loginService.tokenReissue(request, response);
        return "/";
    }

}
