package com.BaldFrogs.BookUp.Model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class Listing {
    private String location;
    private String description;
    private ArrayList<Date> availableDays;
    private float price;
    private int maxGuests;
    private ArrayList<Integer> images;
    private String contactInformation;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Date> getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(ArrayList<Date> availableDays) {
        this.availableDays = availableDays;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }

    public ArrayList<Integer> getImages() {
        return images;
    }

    public void setImages(ArrayList<Integer> images) {
        this.images = images;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    private Listing(String location){
        this.location = location;
    }

}
