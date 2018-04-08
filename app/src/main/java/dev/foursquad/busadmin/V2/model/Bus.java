package dev.foursquad.busadmin.V2.model;

import java.io.Serializable;

public class Bus implements Serializable {

    private String key;
    private String name;
    private String description;
    private String status;
    private double lat;
    private double lng;
    private String stationA;
    private String price;
    private String timeTogo;
    private String timeToarrive;
    private String timeToback;
    private String timeToend;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getStationA() {
        return stationA;
    }

    public void setStationA(String stationA) {
        this.stationA = stationA;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTimeTogo() {
        return timeTogo;
    }

    public void setTimeTogo(String timeTogo) {
        this.timeTogo = timeTogo;
    }

    public String getTimeToarrive() {
        return timeToarrive;
    }

    public void setTimeToarrive(String timeToarrive) {
        this.timeToarrive = timeToarrive;
    }

    public String getTimeToback() {
        return timeToback;
    }

    public void setTimeToback(String timeToback) {
        this.timeToback = timeToback;
    }

    public String getTimeToend() {
        return timeToend;
    }

    public void setTimeToend(String timeToend) {
        this.timeToend = timeToend;
    }
}
