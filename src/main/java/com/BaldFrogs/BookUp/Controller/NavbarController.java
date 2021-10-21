package com.BaldFrogs.BookUp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavbarController
{
    @GetMapping("/")
    private String renderNavbar(Model model)
    {
        return "navbar";
    }
}
