package com.example.weather_app.Models;

import java.util.List;

public class MausamData {
    private List<weather> weather;
    private main main;
    private String name;

    public MausamData(List<com.example.weather_app.Models.weather> weather, com.example.weather_app.Models.main main, String name) {
        this.weather = weather;
        this.main = main;
        this.name = name;
    }

    public List<com.example.weather_app.Models.weather> getWeather() {
        return weather;
    }

    public void setWeather(List<com.example.weather_app.Models.weather> weather) {
        this.weather = weather;
    }

    public com.example.weather_app.Models.main getMain() {
        return main;
    }

    public void setMain(com.example.weather_app.Models.main main) {
        this.main = main;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
