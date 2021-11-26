package com.BaldFrogs.BookUp.Controller;

import com.BaldFrogs.BookUp.Database.Database;
import com.BaldFrogs.BookUp.Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController
{
    /*
        Try to login
    */
    @PostMapping("/login")
    private String renderLogin(Model model, @RequestParam(name = "username", required = false) String username, @RequestParam(name = "password", required = false) String password)
    {
        User u = new User(username, password);
        boolean b = Database.CheckUser(u);

        if(b == true)
            return "redirect:/";

        model.addAttribute("invalidCredentials", true);
        return "login";
    }

    /*
        Open login page
    */
    @GetMapping("/login")
    private String renderLogin(Model model)
    {
        return "login";
    }

    /*
        Logout
    */
    @GetMapping("/logout")
    private String logout(Model model)
    {
        return "redirect:/";
    }

    /*
        Try register
    */
    @PostMapping("/register")
    private String renderRegister(Model model, @RequestParam(name = "username", required = false) String username, @RequestParam(name = "password", required = false) String password)
    {
        User u = new User(username, password);
        boolean b = Database.InsertUser(u);

        if(b == true)
            return "login";

        model.addAttribute("invalidUsername", true);
        return "register";
    }

    /*
        Open register page
    */
    @GetMapping("/register")
    private String renderRegister(Model model)
    {
        return "register";
    }
}
