package com.namviet.vtvtravel.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.namviet.vtvtravel.model.Filter;

import java.util.ArrayList;
import java.util.List;

public class FilterData implements Parcelable {
    private String id;
    private String name;
    private String label;
    private String code;
    private ArrayList<Filter> inputs;

    protected FilterData(Parcel in) {
        id = in.readString();
        name = in.readString();
        label = in.readString();
        code = in.readString();
        inputs = in.createTypedArrayList(Filter.CREATOR);
    }

    public static final Creator<FilterData> CREATOR = new Creator<FilterData>() {
        @Override
        public FilterData createFromParcel(Parcel in) {
            return new FilterData(in);
        }

        @Override
        public FilterData[] newArray(int size) {
            return new FilterData[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<Filter> getInputs() {
        return inputs;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setInputs(ArrayList<Filter> inputs) {
        this.inputs = inputs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(label);
        parcel.writeString(code);
        parcel.writeTypedList(inputs);
    }
}
