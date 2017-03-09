package com.theironyard.novauc;

/**
 * Created by Merlin on 3/7/17.
 */
public class Restaurant {
    int id;
    String nameOfRestaurant;
    String type;
    int maximumOccupancy;


//    public Restaurant(String nameOfRestaurant) {
//        this.nameOfRestaurant = nameOfRestaurant;
//    }


    public Restaurant(int id, String nameOfRestaurant, String type, int maximumOccupancy) {
        this.id = id;
        this.nameOfRestaurant = nameOfRestaurant;
        this.type = type;
        this.maximumOccupancy = maximumOccupancy;
    }
}
