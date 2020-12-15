package com.namviet.vtvtravel.response;

import androidx.lifecycle.MutableLiveData;

import com.namviet.vtvtravel.api.ApiClient;
import com.namviet.vtvtravel.api.ApiInterface;
import com.namviet.vtvtravel.model.channel.DetailLiveChannelDataM;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailLiveChannelResponse {
    private ApiInterface apiInterface;

    public DetailLiveChannelResponse() {
    }

    public MutableLiveData<DetailLiveChannelDataM.DetailLiveChannelData> getDetailLiveChannels(String channel) {

        final MutableLiveData<DetailLiveChannelDataM.DetailLiveChannelData> refferAndInvitePojoMutableLiveData = new MutableLiveData<>();
        apiInterface = ApiClient.getClientAuthentication().create(ApiInterface.class);
        Call<DetailLiveChannelDataM> call = apiInterface.getListDetailLiveChannel(channel);
        call.enqueue(new Callback<DetailLiveChannelDataM>() {
            @Override
            public void onResponse(Call<DetailLiveChannelDataM> call, Response<DetailLiveChannelDataM> response) {
                if (response.body() != null) {
                    refferAndInvitePojoMutableLiveData.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<DetailLiveChannelDataM> call, Throwable t) {

            }
        });

        return refferAndInvitePojoMutableLiveData;
    }
}
