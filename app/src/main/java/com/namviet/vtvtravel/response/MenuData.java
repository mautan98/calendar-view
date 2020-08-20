package com.namviet.vtvtravel.response;

import com.daimajia.slider.library.NearDistance;
import com.namviet.vtvtravel.model.ItemMenu;
import com.namviet.vtvtravel.model.MenuLeft;
import com.namviet.vtvtravel.model.NearType;
import com.namviet.vtvtravel.model.Video;

import java.util.List;

public class MenuData {
    private MenuLeft menus;

    private List<Video> videos;
    private List<NearType> nearby_types;
    private List<NearDistance> nearby_distances;

    public MenuLeft getMenu() {
        return menus;
    }

    public void setMenu(MenuLeft menu) {
        this.menus = menu;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public List<NearType> getNearby_types() {
        return nearby_types;
    }

    public void setNearby_types(List<NearType> nearby_types) {
        this.nearby_types = nearby_types;
    }

    public List<NearDistance> getNearby_distances() {
        return nearby_distances;
    }

    public void setNearby_distances(List<NearDistance> nearby_distances) {
        this.nearby_distances = nearby_distances;
    }
}
