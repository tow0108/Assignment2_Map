package com.egco428.a23283.assignment2_map;

/**
 * Created by Natamon Tangmo on 20-Oct-16.
 */
public class Comment {
    private long id;
    private String username;
    private String password;
    private String latitude;
    private String longtitude;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }


    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLatitude() {
        return latitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }
    public String getLongtitude() {
        return longtitude;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return username;
    }
}