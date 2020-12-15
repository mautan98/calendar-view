package com.namviet.vtvtravel.response;

import androidx.lifecycle.MutableLiveData;

import com.namviet.vtvtravel.api.ApiClient;
import com.namviet.vtvtravel.api.ApiInterface;
import com.namviet.vtvtravel.model.channel.LiveChannelData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveChannelResponse {
    private ApiInterface apiInterface;

    public LiveChannelResponse() {
    }

    public MutableLiveData<LiveChannelData> getLiveChannels() {

        final MutableLiveData<LiveChannelData> refferAndInvitePojoMutableLiveData = new MutableLiveData<>();
        apiInterface = ApiClient.getClientAuthentication().create(ApiInterface.class);
        Call<LiveChannelData> call = apiInterface.getListLiveChannel();
        call.enqueue(new Callback<LiveChannelData>() {
            @Override
            public void onResponse(Call<LiveChannelData> call, Response<LiveChannelData> response) {
                if (response.body() != null) {
                    refferAndInvitePojoMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<LiveChannelData> call, Throwable t) {

            }
        });

        return refferAndInvitePojoMutableLiveData;
    }
}
