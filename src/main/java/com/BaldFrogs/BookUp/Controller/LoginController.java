package com.BaldFrogs.BookUp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController
{
    @GetMapping("/login")
    private String renderLogin(Model model)
    {
        return "login";
    }
}
