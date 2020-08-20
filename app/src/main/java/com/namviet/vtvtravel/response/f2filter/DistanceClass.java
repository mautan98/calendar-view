package com.namviet.vtvtravel.response.f2filter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DistanceClass implements Serializable {
    private List<Distance> distance;

    public List<Distance> getDistances() {
        return distance;
    }

    public void setDistances(List<Distance> distances) {
        this.distance = distances;
    }

    public class Distance implements Serializable {

        @Expose
        @SerializedName("isSelected")
        private boolean isSelected;
        @Expose
        @SerializedName("value")
        private String value;
        @Expose
        @SerializedName("label")
        private String label;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }
}
