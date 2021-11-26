package com.BaldFrogs.BookUp.Controller;

import com.BaldFrogs.BookUp.Database.Database;
import com.BaldFrogs.BookUp.Model.Listing;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class IndexController
{
    //Show new created listings on home page
    @GetMapping("/")
    private String index(Model model)
    {
        ArrayList<Listing> listings = Database.NewListings();
        if(listings != null && listings.size() > 0)
            model.addAttribute("listings", Arrays.asList(listings.toArray()));

        return "index";
    }
}
