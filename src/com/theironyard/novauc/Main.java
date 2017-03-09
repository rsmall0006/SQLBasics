package com.theironyard.novauc;

import org.h2.tools.Server;
import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.security.MessageDigest;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static ArrayList<Restaurant> restaurants = new ArrayList();
    static User user;
    public static ArrayList<Restaurant> selectRestaurant(Connection conn, int id) throws SQLException{
        ArrayList<Restaurant> restaurantids = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM restaurants WHERE restaurants.id = ?");
        stmt.setInt(1, id);
//        stmt.setString(1, nameOfRestaurant);
        ResultSet results = stmt.executeQuery();
        while (results.next()){
            int idd = results.getInt("restaurants.id");
            String nameOfRestaurant = results.getString("restaurants.nameOfRestaurant");
            String type = results.getString("restaurants.type");
            int maximumOccupancy = results.getInt("restaurants.maximumOccupancy");
            Restaurant restauranttest = new Restaurant(idd, nameOfRestaurant, type, maximumOccupancy);
            restaurantids.add(restauranttest);
        }
        return restaurantids;
    }

    public static void insertRestaurant(Connection conn, String nameOfRestaurant, String type, int maximumOccupancy) throws SQLException{
        PreparedStatement stmt = conn.prepareStatement ("INSERT INTO restaurants VALUES(NULL, ?, ?, ?)");
        stmt.setString(1, nameOfRestaurant);
        stmt.setString(2, type);
        stmt.setInt(3, maximumOccupancy);
        stmt.execute();
    }
    public static void removeRestaurant (Connection conn, String nameOfRestaurant) throws SQLException{
//        PreparedStatement stmt = conn.prepareStatement("DELETE FROM restaurants WHERE id = ?");
//        PreparedStatement stmt = conn.prepareStatement("DELETE FROM restaurants WHERE id = ?, nameOfRestaurant = ?, type = ?, maximumOccupancy = ?");
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM restaurants WHERE nameOfRestaurant = ?");

        stmt.setString(1, nameOfRestaurant);
//        stmt.setString(2, type);
//        stmt.setInt(3, maximumOccupancy);
        stmt.execute();
    }

    public static void editRestaurant (Connection conn, String newNameOfRestaurant,String oldNameOfRestaurant) throws SQLException{
        PreparedStatement stmt = conn.prepareStatement("UPDATE restaurants SET nameOfRestaurant = ? WHERE nameOfRestaurant = ?");
        stmt.setString(1, newNameOfRestaurant);
        stmt.setString(2, oldNameOfRestaurant);
        stmt.execute();
    }


    public static void main(String[] args) throws SQLException{
	// write your code here
        Server.createWebServer().start();
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS restaurants (id IDENTITY, nameOfRestaurant VARCHAR, type VARCHAR, maximumOccupancy INT)");

        Spark.init();

        Spark.get("/",
                ((request, response) -> {
                    HashMap log = new HashMap();
                    Session session = request.session();

//                    ArrayList<Restaurant> newone = new ArrayList<>();
//                    ArrayList<Restaurant> newone = selectRestaurant (conn, nameOfRestaurant);
//                    for (Restaurant user : restaurants) {
                        if (user == null) {
                            return new ModelAndView(log, "login.html");
                        } else {
                            log.put("name", user.name);
                            log.put("restaurant", restaurants);


//                            log.put("restaurants", newone);
//                            log.put("id", nameOfRestaurant);

                            return new ModelAndView(log, "home.html");
                        }
                    }
                ), new MustacheTemplateEngine()
        );
        Spark.post("/user",
                ((request, response) -> {
                    String name = request.queryParams("user");
                    user = new User(name);
                    response.redirect("/");
                    return "";
                }));
        Spark.post("/restaurant",
                ((request, response) -> {
                    String restaurant0 = request.queryParams("restaurant0");
                    String restaurant1 = request.queryParams("restaurant1");
//                    Restaurant r1 = new Restaurant();
//                    restaurants.add(r1);
                    String restaurant2 = request.queryParams("restaurant2");
                    String restaurant3 = request.queryParams("restaurant3");
                    int restaurantNameNum = -1;
                    if (restaurant0 != null){
                        restaurantNameNum = Integer.valueOf(restaurant0);
                    }
                    HashMap test = new HashMap();
                    ArrayList<Restaurant> newone = selectRestaurant (conn, restaurantNameNum);
                    test.put("restaurant", restaurantNameNum);


                    Restaurant r1 = new Restaurant(Integer.valueOf(restaurant0), restaurant1, restaurant2, Integer.valueOf(restaurant3));
                    restaurants.add(r1);

//                    Restaurant r1 = new Restaurant(restaurant1);
//                    restaurants.add(r1);
//                    Restaurant r2 = new Restaurant(restaurant2);
//                    restaurants.add(r2);
//                    Restaurant r3 = new Restaurant(restaurant3);
//                    restaurants.add(r3);
                    insertRestaurant(conn, restaurant1, restaurant2, Integer.valueOf(restaurant3));
                    response.redirect("/");
                    return "";
                })
        );

        Spark.post("/delete",
                ((request, response) -> {
                    String delete0 = request.queryParams("delete0");
                    String delete1 = request.queryParams("delete1");
                    String delete2 = request.queryParams("delete2");
                    String delete3 = request.queryParams("delete3");
                    Restaurant d1 = new Restaurant(Integer.valueOf(delete0), delete1, delete2, Integer.valueOf(delete3));
//                    int restaurantDeleter = Integer.valueOf(delete1, delete2, delete3);
//                    int restaurantDeleter = Integer.valueOf(delete1 + delete2 + delete3);
                    int restaurantDeleter1 = Integer.valueOf(delete0);
//                    int restaurantDeleter1 = delete0;
//                    int restaurantDeleter2 = Integer.valueOf(delete2);
//                    int restaurantDeleter3 = Integer.valueOf(delete3);
//                    restaurants.remove(restaurantDeleter1 -1);
//                    restaurants.remove(restaurantDeleter2 -1);
//                    restaurants.remove(restaurantDeleter3 -1);
//                    Restaurant d1 = new Restaurant();
//                    restaurants.remove(d1 - 1);
//                    restaurants.remove(d1);
//                    int postDeleter = Integer.valueOf(message);
                    restaurants.remove(restaurantDeleter1 -1);
                    removeRestaurant(conn, delete1);
                    response.redirect("/");
                    return "";

                })
        );

        Spark.post("/edit",
                ((request, response) -> {
                    String edit0 = request.queryParams("edit0");
                    String edit1 = request.queryParams("edit1");
                    String edit2 = request.queryParams("edit2");
                    String edit3 = request.queryParams("edit3");
                    int postEditer0 = Integer.valueOf(edit0);
//                    int postEditer1 = Integer.valueOf(edit1);
//                    int postEditer2 = Integer.valueOf(edit2);
//                    int postEditer3 = Integer.valueOf(edit3);
                    restaurants.remove(postEditer0 -1);
//                    restaurants.remove(postEditer1 -1);
//                    restaurants.remove(postEditer2 -1);
//                    restaurants.remove(postEditer3 -1);
                    Restaurant older = new Restaurant(Integer.valueOf(edit0), edit1, edit2, Integer.valueOf(edit3));
                    String change0 = request.queryParams("change0");
                    String change1 = request.queryParams("change1");
                    String change2 = request.queryParams("change2");
                    String change3 = request.queryParams("change3");
                    Restaurant change = new Restaurant(Integer.valueOf(change0), change1, change2, Integer.valueOf(change3));

//                    restaurants.add(postEditer0 -1, cookies);
//                    restaurants.remove(older);
                    restaurants.add(change);
                    editRestaurant(conn, change1, edit1);
                    response.redirect("/");
                    return "";
                })
                );

    }
}
