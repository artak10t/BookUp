package com.BaldFrogs.BookUp.Controller;

import com.BaldFrogs.BookUp.Model.Listing;
import com.BaldFrogs.BookUp.Model.ListingsDatabase;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@Controller
public class ListingController
{
    /*
        GetRequest for rendering listing
     */
    @GetMapping("/listing/{id}")
    private String viewListing(Model model, @PathVariable Integer id)
    {
        Listing l = ListingsDatabase.Query(id);
        model.addAttribute("id", id);
        model.addAttribute("description", l.getDescription());

        if (l.getAvailableDays().size() > 0)
            model.addAttribute("availableDays", Arrays.asList(l.getAvailableDays().toArray()));

        if(l.getImages() != null && l.getImages().size() > 0)
            model.addAttribute("images", Arrays.asList(l.getImages().toArray()));

        model.addAttribute("price", l.getPrice());
        model.addAttribute("maxGuests", l.getMaxGuests());

        model.addAttribute("contactInformation", l.getContactInformation());

        return "listing";
    }

    /*
        New listing form page
     */
    @GetMapping("/listing")
    private String viewListing(Model model)
    {
        Listing l = new Listing();
        model.addAttribute("listing", l);
        return "forms/newListing";
    }

    /*
        Add new listing into database and redirect to the listing page
    */
    @PostMapping("/listing/new")
    public String greetingSubmit(@ModelAttribute Listing listing, Model model)
    {
        ListingsDatabase.Insert(listing);
        int id = ListingsDatabase.Size() - 1;
        return "redirect:/listing/" + id;
    }
}
