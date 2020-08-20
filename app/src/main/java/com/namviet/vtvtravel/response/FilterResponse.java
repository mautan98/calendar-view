package com.namviet.vtvtravel.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class FilterResponse extends BaseResponse implements Parcelable {
    private ArrayList<FilterData> data;


    protected FilterResponse(Parcel in) {
        data = in.createTypedArrayList(FilterData.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FilterResponse> CREATOR = new Creator<FilterResponse>() {
        @Override
        public FilterResponse createFromParcel(Parcel in) {
            return new FilterResponse(in);
        }

        @Override
        public FilterResponse[] newArray(int size) {
            return new FilterResponse[size];
        }
    };

    public ArrayList<FilterData> getData() {
        return data;
    }

    public void setData(ArrayList<FilterData> data) {
        this.data = data;
    }

}


