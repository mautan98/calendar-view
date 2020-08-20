package com.namviet.vtvtravel.model;

public class HelloLocation {
    private String id;
    private String name;
    private String name_ascii;
    private float distance;
    private String greeting;
    private String banner_greeting;
    private String banner_url;
    private String description;

    // Getter Methods

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getName_ascii() {
        return name_ascii;
    }

    public float getDistance() {
        return distance;
    }

    public String getGreeting() {
        return greeting;
    }

    public String getBanner_greeting() {
        return banner_greeting;
    }

    public String getBanner_url() {
        return banner_url;
    }

    public String getDescription() {
        return description;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setName_ascii(String name_ascii) {
        this.name_ascii = name_ascii;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public void setBanner_greeting(String banner_greeting) {
        this.banner_greeting = banner_greeting;
    }

    public void setBanner_url(String banner_url) {
        this.banner_url = banner_url;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
