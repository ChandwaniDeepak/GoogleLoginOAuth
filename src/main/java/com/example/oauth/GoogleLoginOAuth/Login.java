package com.example.oauth.GoogleLoginOAuth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Login {

    @RequestMapping("/login")
    private String login(){
        return "login.jsp";
    }
}
