package com.namviet.vtvtravel.api;

import com.namviet.vtvtravel.model.LicensePhoto;
import com.namviet.vtvtravel.model.channel.DetailLiveChannelDataM;
import com.namviet.vtvtravel.model.channel.LiveChannelData;
import com.namviet.vtvtravel.model.chat.ChatBase;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ApiInterface {
    @GET(WSConfig.Api.GET_LIVE_CHANNEL)
    Call<LiveChannelData> getListLiveChannel();

    @GET(WSConfig.Api.GET_DETAIL_LIVE_CHANNEL_FAKE + "{channel}")
    Call<DetailLiveChannelDataM> getListDetailLiveChannel(@Path("channel") String channel);

    @GET(WSConfig.Api.GET_LICENSE_PHOTO + "{url}")
    Call<LicensePhoto> getLicensePhotoData(@Path("url") String url);

    @GET(WSConfig.Api.GET_CHAT_BOT_QUESTION)
    Call<ChatBase> getChatBotData(@QueryMap Map<String, Object> queryMap);
}
