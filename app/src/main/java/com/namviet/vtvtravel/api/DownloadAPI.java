package com.namviet.vtvtravel.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;

public interface DownloadAPI {
    @GET("file_offline/configuration_files/Call_Now.zip")
    Call<ResponseBody> downloadFileWithFixedUrl();

}
