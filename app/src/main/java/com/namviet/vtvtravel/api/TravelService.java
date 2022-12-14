package com.namviet.vtvtravel.api;


import com.namviet.vtvtravel.model.virtualcall.ListTicketHistoryResponse;
import com.namviet.vtvtravel.model.virtualcall.ListTicketResponse;
import com.namviet.vtvtravel.model.virtualcall.ProcessTicketResponse;
import com.namviet.vtvtravel.response.AccountResponse;
import com.namviet.vtvtravel.response.BannerResponse;
import com.namviet.vtvtravel.response.BaseResponse;
import com.namviet.vtvtravel.response.CategoryPhotoResponse;
import com.namviet.vtvtravel.response.CategoryResponse;
import com.namviet.vtvtravel.response.ChatResponse;
import com.namviet.vtvtravel.response.CityResponse;
import com.namviet.vtvtravel.response.CommentResponse;
import com.namviet.vtvtravel.response.CountUnreadRespone;
import com.namviet.vtvtravel.response.CreateRoomResponse;
import com.namviet.vtvtravel.response.DetailNewsResponse;
import com.namviet.vtvtravel.response.DetailPlaceResponse;
import com.namviet.vtvtravel.response.DetailResponse;
import com.namviet.vtvtravel.response.DetailScheduleCreateResponse;
import com.namviet.vtvtravel.response.DetailScheduleResponse;
import com.namviet.vtvtravel.response.FilterResponse;
import com.namviet.vtvtravel.response.FilterSearchResponse;
import com.namviet.vtvtravel.response.HomeResponse;
import com.namviet.vtvtravel.response.ItemCategoryResponse;
import com.namviet.vtvtravel.response.MenuResponse;
import com.namviet.vtvtravel.response.NewestResponse;
import com.namviet.vtvtravel.response.NewsResponse;
import com.namviet.vtvtravel.response.NotifyResponse;
import com.namviet.vtvtravel.response.PlaceResponse;
import com.namviet.vtvtravel.response.PlaylistVideoResponse;
import com.namviet.vtvtravel.response.PostCommentResponse;
import com.namviet.vtvtravel.response.SaveScheduleResponse;
import com.namviet.vtvtravel.response.ScheduleResponse;
import com.namviet.vtvtravel.response.SearchResponse;
import com.namviet.vtvtravel.response.SearchResultResponse;
import com.namviet.vtvtravel.response.ShareMomentResponse;
import com.namviet.vtvtravel.response.ShowAllResponse;
import com.namviet.vtvtravel.response.SlideShowResponse;
import com.namviet.vtvtravel.response.SuggestTravelResponse;
import com.namviet.vtvtravel.response.TypeMomentResponse;
import com.namviet.vtvtravel.response.VideoMomentResponse;
import com.namviet.vtvtravel.response.f2account.HtmlResponse;
import com.namviet.vtvtravel.response.f2biglocation.AllLocationResponse;
import com.namviet.vtvtravel.response.f2biglocation.BigLocationBaseResponse;
import com.namviet.vtvtravel.response.f2biglocation.BigLocationResponse;
import com.namviet.vtvtravel.response.f2biglocation.LocationResponse;
import com.namviet.vtvtravel.response.f2biglocation.PartBigLocationResponse;
import com.namviet.vtvtravel.response.f2biglocation.RegionResponse;
import com.namviet.vtvtravel.response.f2biglocation.TopLocationResponse;
import com.namviet.vtvtravel.response.f2chat.GetListThemeResponse;
import com.namviet.vtvtravel.response.f2chat.GetUserGuildResponse;
import com.namviet.vtvtravel.response.f2chat.GetUserThemeResponse;
import com.namviet.vtvtravel.response.f2chat.HelloMessageResponse;
import com.namviet.vtvtravel.response.f2chat.PostUserGuildResponse;
import com.namviet.vtvtravel.response.f2comment.CheckShowCaptcha;
import com.namviet.vtvtravel.response.f2filter.FilterByCodeResponse;
import com.namviet.vtvtravel.response.f2filter.FilterByPageResponse;
import com.namviet.vtvtravel.response.f2operator.OperatorInformationResponse;
import com.namviet.vtvtravel.response.f2review.CreateReviewResponse;
import com.namviet.vtvtravel.response.f2review.GetReviewResponse;
import com.namviet.vtvtravel.response.f2review.UploadImageResponse;
import com.namviet.vtvtravel.response.f2room.CallRoomResponse;
import com.namviet.vtvtravel.response.f2searchmain.MainResultSearchResponse;
import com.namviet.vtvtravel.response.f2searchmain.MainSearchResponse;
import com.namviet.vtvtravel.response.f2searchmain.SearchSuggestionResponse;
import com.namviet.vtvtravel.response.f2searchmain.SubBaseSearch;
import com.namviet.vtvtravel.response.f2searchmain.result.ResultSearch;
import com.namviet.vtvtravel.response.f2searchmain.result.ResultVideoSearch;
import com.namviet.vtvtravel.response.f2smalllocation.DetailSmallLocationResponse;
import com.namviet.vtvtravel.response.f2smalllocation.SortSmallLocationResponse;
import com.namviet.vtvtravel.response.f2systeminbox.ConfirmEnterTrip;
import com.namviet.vtvtravel.response.f2systeminbox.CountSystemInbox;
import com.namviet.vtvtravel.response.f2systeminbox.SystemInbox;
import com.namviet.vtvtravel.response.f2systeminbox.TicketInfo;
import com.namviet.vtvtravel.response.f2topexperience.SubTopExperienceResponse;
import com.namviet.vtvtravel.response.f2travelvoucher.CategoryVoucherResponse;
import com.namviet.vtvtravel.response.f2travelvoucher.CheckCanReceiver;
import com.namviet.vtvtravel.response.f2travelvoucher.ListVoucherResponse;
import com.namviet.vtvtravel.response.f2travelvoucher.RankVoucherResponse;
import com.namviet.vtvtravel.response.f2travelvoucher.RegionVoucherResponse;
import com.namviet.vtvtravel.response.f2video.VideoDetailResponse;
import com.namviet.vtvtravel.response.f2video.VideoResponse;
import com.namviet.vtvtravel.response.WeatherResponse;
import com.namviet.vtvtravel.response.f2callnow.CallListResponse;
import com.namviet.vtvtravel.response.f2callnow.CallNowHistoryResponse;
import com.namviet.vtvtravel.response.f2callnow.VerifyUserResponse;
import com.namviet.vtvtravel.response.f2callnow.ZipVersionResponse;
import com.namviet.vtvtravel.response.f2comment.CreateCommentResponse;
import com.namviet.vtvtravel.response.f2comment.DeleteCommentResponse;
import com.namviet.vtvtravel.response.f2comment.UpdateCommentResponse;
import com.namviet.vtvtravel.response.f2smalllocation.SmallLocationResponse;
import com.namviet.vtvtravel.response.f2video.DetailVideoResponse;
import com.namviet.vtvtravel.response.f2wheel.RuleLuckyWheel;
import com.namviet.vtvtravel.response.f2wheel.WheelActionResponse;
import com.namviet.vtvtravel.response.f2wheel.WheelAreasResponse;
import com.namviet.vtvtravel.response.f2wheel.WheelChartResponse;
import com.namviet.vtvtravel.response.f2wheel.WheelResultResponse;
import com.namviet.vtvtravel.response.f2wheel.WheelRotateResponse;
import com.namviet.vtvtravel.response.imagepart.ImagePartResponse;
import com.namviet.vtvtravel.response.imagepart.ItemImagePartResponse;
import com.namviet.vtvtravel.response.newhome.AppVoucherResponse;
import com.namviet.vtvtravel.response.newhome.BaseResponseNewHome;
import com.namviet.vtvtravel.response.newhome.BaseResponseSecondNewHome;
import com.namviet.vtvtravel.response.newhome.BaseResponseSpecialNewHome;
import com.namviet.vtvtravel.response.newhome.ConfigPopupResponse;
import com.namviet.vtvtravel.response.newhome.HomeServiceResponse;
import com.namviet.vtvtravel.response.newhome.ItemAppExperienceResponse;
import com.namviet.vtvtravel.response.newhome.MobileFromViettelResponse;
import com.namviet.vtvtravel.response.travelnews.DetailNewsCategoryResponse;
import com.namviet.vtvtravel.response.travelnews.DetailTravelNewsResponse;
import com.namviet.vtvtravel.response.travelnews.NewsCategoryResponse;
import com.namviet.vtvtravel.response.travelnews.NotebookResponse;
import com.namviet.vtvtravel.response.travelnews.PlaceNearByResponse;
import com.namviet.vtvtravel.view.f3.deal.model.BlockResponse;
import com.namviet.vtvtravel.view.f3.deal.model.deal.DealResponse;
import com.namviet.vtvtravel.view.f3.deal.model.dealcampaign.DealCampaignDetail;
import com.namviet.vtvtravel.view.f3.deal.model.dealfollow.DealFollowResponse;
import com.namviet.vtvtravel.view.f3.deal.model.mygift.MyGiftResponse;
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.MyTripsResponse;
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.cost.CostResponse;
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.createschedule.CreateScheduleResponse;
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.createschedule.DataCreateTrips;
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.detail.PlaceScheduleResponse;
import com.namviet.vtvtravel.view.fragment.f2mytrip.place.model.DetailPlacesResponse;
import com.namviet.vtvtravel.view.f3.notification.model.ui.NotificationResponse;
import com.namviet.vtvtravel.view.f3.notification.model.ui.NotificationTab;
import com.namviet.vtvtravel.view.f3.notification.model.ui.UpdateNotificationResponse;
import com.namviet.vtvtravel.view.fragment.f2service.GetInfoResponse;
import com.namviet.vtvtravel.view.fragment.f2service.ResentOtpServiceResponse;
import com.namviet.vtvtravel.view.fragment.f2service.ServiceOtpResponse;
import com.namviet.vtvtravel.view.fragment.f2service.ServiceResponse;

import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface TravelService {

    @GET(WSConfig.GET_HOME)
    Observable<HomeResponse> getDataHome(@QueryMap Map<String, Object> queryMap);

    @GET(WSConfig.GET_MENU)
    Observable<MenuResponse> getMenuList();

    @GET(WSConfig.Api.GET_SEARCH_SUGGEST)
    Observable<SearchResponse> getSearchSuggest(@QueryMap Map<String, Object> queryMap);

    @GET
    Observable<ShowAllResponse> getListAll(@Url String url, @QueryMap Map<String, Object> queryMap);

    @GET
    Observable<ItemCategoryResponse> getItemCategory(@Url String url);

    @GET
    Observable<DetailResponse> getDetailNews(@Url String url);

    @GET
    Observable<VideoResponse> getDetailVideo(@Url String url);

    @GET
    Observable<DetailVideoResponse> getDetailCategoryVideo(@Url String url);

    @GET
    Observable<BaseResponse> requesEncode(@Url String url);

    @GET(WSConfig.GET_NEAR)
    Observable<ShowAllResponse> getNearYou(@QueryMap Map<String, Object> queryMap);

    @POST(WSConfig.Api.LOGIN)
    Observable<AccountResponse> login(@Body RequestBody body);

    @POST(WSConfig.Api.REFRESH_TOKEN)
    Observable<AccountResponse> refreshToken(@Body RequestBody body, @Header(WSConfig.KeyParam.HEADER_TOKEN) String token);

    @POST(WSConfig.Api.REGISTER)
    Observable<AccountResponse> register(@Body RequestBody body);

    @POST(WSConfig.Api.RESENT_OTP)
    Observable<AccountResponse> resend(@Body RequestBody body);

    @POST(WSConfig.Api.RESENT_OTP_FOR_RESET_PASSWORD)
    Observable<AccountResponse> resendOtpForResetPassword(@Body RequestBody body);

    @POST(WSConfig.Api.GET_INFO)
    Observable<AccountResponse> getInfo(@Body RequestBody body);

    @POST(WSConfig.Api.REGISTER_OTP)
    Observable<AccountResponse> registerOtp(@Body RequestBody body);

    @POST(WSConfig.Api.RESET_PASS_OTP)
    Observable<AccountResponse> verifyResetPasswordOtp(@Body RequestBody body);

    @POST(WSConfig.Api.RESET_PASSWORD)
    Observable<AccountResponse> resetPassword(@Body RequestBody body);

    @POST(WSConfig.Api.REGISTER_ACCOUNT)
    Observable<AccountResponse> registerAccount(@Body RequestBody body);

    @POST(WSConfig.Api.UPDATE)
    Observable<AccountResponse> updateInfo(@Body RequestBody body);

    @POST(WSConfig.Api.RESET_PASS)
    Observable<AccountResponse> resetPass(@Header(WSConfig.KeyParam.HEADER_TOKEN) String auth, @Body RequestBody body);

    @Multipart
    @POST(WSConfig.Api.UPDATE_AVATAR)
    Observable<AccountResponse> uploadAvatar(@QueryMap Map<String, Object> queryMap, @Part MultipartBody.Part file);

    @Multipart
    @POST(WSConfig.Api.SHARE_MOMENT)
    Call<ShareMomentResponse> shareMoment(@PartMap Map<String, RequestBody> queryMap, @Part MultipartBody.Part fileCover, @Part MultipartBody.Part[] files);

    @POST(WSConfig.Api.CHANGE_PASSWORD)
    Observable<AccountResponse> changePassword(@Header("authorization") String auth, @Body RequestBody body);

    @GET(WSConfig.Api.GET_PLACE)
    Observable<PlaceResponse> loadPlace(@QueryMap Map<String, Object> queryMap);

    @GET(WSConfig.Api.SELECT_CITY)
    Observable<CityResponse> loadCity(@QueryMap Map<String, Object> queryMap);

    @GET(WSConfig.Api.SELECT_FILTER)
    Observable<FilterResponse> loadFilter(@QueryMap Map<String, Object> queryMap);

    @GET
    Observable<DetailPlaceResponse> loadDetailPlace(@Url String url);

    @GET
    Observable<PlaceResponse> loadNearPlace(@Url String url);

    @GET(WSConfig.Api.GET_RESTAURANTS)
    Observable<PlaceResponse> loadRestaurants(@QueryMap Map<String, Object> queryMap);

    @GET(WSConfig.Api.GET_HOTELS)
    Observable<PlaceResponse> loadWhereStay(@QueryMap Map<String, Object> queryMap);

    @GET(WSConfig.Api.GET_CENTER)
    Observable<PlaceResponse> loadWhatPlay(@QueryMap Map<String, Object> queryMap);

    @GET(WSConfig.Api.SELECT_CATEGORY_HIGHLIGHT)
    Observable<CategoryResponse> loadCategoryHighLight(@QueryMap Map<String, Object> queryMap);

    @GET(WSConfig.Api.GET_CATEGORY_NEWS)
    Observable<NewsResponse> getCategoryNews(@QueryMap Map<String, Object> queryMap);

    @GET
    Observable<DetailNewsResponse> getNewsById(@Url String url);

    @GET(WSConfig.Api.GET_CATEGORY_PHOTO_NICE)
    Observable<CategoryPhotoResponse> getCategoryPhotoNice(@QueryMap Map<String, Object> queryMap);

    @GET
    Observable<NewsResponse> loadListPhotoNice(@Url String url);

    @GET(WSConfig.Api.GET_SLIDE_SHOW)
    Observable<SlideShowResponse> loadSlideShow(@QueryMap Map<String, Object> queryMap);

    @GET(WSConfig.Api.GET_CATEGORY_VIDEO)
    Observable<CategoryPhotoResponse> loadCategoryVideo(@QueryMap Map<String, Object> queryMap);

    @GET
    Observable<VideoResponse> loadListVideo(@Url String url);

    @GET
    Observable<PlaylistVideoResponse> loadPlaylistVideo(@Url String url);

    @GET(WSConfig.Api.GET_CATEGORY_NEWS_SLIDE_SHOWS)
    Observable<SlideShowResponse> getSlideshowNews(@QueryMap Map<String, Object> queryMap);


    @GET(WSConfig.Api.GET_VOUCHER)
    Observable<PlaceResponse> loadVoucher(@QueryMap Map<String, Object> queryMap);

    @GET(WSConfig.Api.GET_MOMENT)
    Observable<PlaceResponse> loadMoment(@QueryMap Map<String, Object> queryMap);

    @GET(WSConfig.Api.GET_MOMENT_NEWSEST)
    Observable<NewestResponse> loadMomentNewest(@QueryMap Map<String, Object> queryMap);

    @GET(WSConfig.Api.GET_MOMENT_VIDEO)
    Observable<VideoMomentResponse> loadMomentVideo(@QueryMap Map<String, Object> queryMap);

    @POST(WSConfig.Api.GET_TOUR_SUGGEST)
    Observable<ScheduleResponse> loadTourSuggest(@Body RequestBody body);

    @POST(WSConfig.Api.GET_TOUR_SCHEDULE_DETAIL)
    Observable<DetailScheduleResponse> loadDetailSchedule(@Body RequestBody body);

    @POST(WSConfig.Api.GET_TOUR_SCHEDULE_RESTAURANTS)
    Observable<ScheduleResponse> loadScheduleRestaurant(@Body RequestBody body, @QueryMap Map<String, Object> queryMap);

    @POST(WSConfig.Api.GET_TOUR_SCHEDULE_PLACES)
    Observable<ScheduleResponse> loadSchedulePlace(@Body RequestBody body, @QueryMap Map<String, Object> queryMap);

    @POST(WSConfig.Api.GET_TOUR_SCHEDULE_CENTERS)
    Observable<ScheduleResponse> loadScheduleCenter(@Body RequestBody body, @QueryMap Map<String, Object> queryMap);

    @POST(WSConfig.Api.SAVE_SCHEDULE_CENTERS)
    Observable<SaveScheduleResponse> saveScheduleTravel(@Body RequestBody jsonBodyObject);


    @POST(WSConfig.Api.SAVE_TOUR)
    Observable<SaveScheduleResponse> saveTourTravel(@Body RequestBody jsonBodyObject);

    @GET(WSConfig.Api.GET_SEARCH_TREND)
    Observable<SearchResponse> loadSearchTrend(@QueryMap Map<String, Object> param);

    @GET(WSConfig.Api.SEARCH_SUGGESTION)
    Observable<SearchSuggestionResponse> getSearchSuggestion(@QueryMap Map<String, Object> param, @Query("keyword") String keyword, @Query("region_id") String regionId);

    @GET(WSConfig.Api.SEARCH_SUGGESTION)
    Observable<SearchSuggestionResponse> getSearchSuggestionForSpecificContent(@QueryMap Map<String, Object> param, @Query("keyword") String keyword, @Query("region_id") String regionId, @Query("content_type") String contentType);


    @GET(WSConfig.Api.SEARCH_ALL)
    Observable<ResultSearch> searchAll(@Path ("path") String path, @QueryMap Map<String, Object> queryMap,@Query("keyword") String keyword, @Query("region_id") String regionId, @Query("category_code") String categoryCode
    , @Query("district_id") String district_id, @Query("ward_id") String ward_id,  @Query("open") Boolean open, @Query("sort") String sort, @Query("content_type") String content_typ );

    @GET
    Observable<ResultSearch> searchAllWithFullLink( @Url String url, @QueryMap Map<String, Object> param);

    @GET(WSConfig.Api.SEARCH_ALL)
    Observable<ResultVideoSearch> searchAllVideo(@Path ("path") String path, @QueryMap Map<String, Object> queryMap, @Query("keyword") String keyword, @Query("region_id") String regionId, @Query("category_code") String categoryCode
            , @Query("district_id") String district_id, @Query("ward_id") String ward_id,  @Query("open") Boolean open, @Query("sort") String sort, @Query("content_type") String content_typ );

    @GET
    Observable<ResultVideoSearch> searchAllVideoWithFullLink(@Url String url, @QueryMap Map<String, Object> param);


    @GET(WSConfig.Api.SEARCH_ALL)
    Observable<ItemImagePartResponse> searchAllImage(@Path ("path") String path, @QueryMap Map<String, Object> queryMap, @Query("keyword") String keyword, @Query("region_id") String regionId, @Query("category_code") String categoryCode);

    @GET
    Observable<ItemImagePartResponse> searchAllImageWithFullLink(@Url String url, @QueryMap Map<String, Object> param);

    @GET(WSConfig.Api.GET_SEARCH_RESULT)
    Observable<SearchResultResponse> loadSearchResult(@QueryMap Map<String, Object> param);

    @POST(WSConfig.Api.SEARCH_SCHEDULE_CREATE)
    Observable<SaveScheduleResponse> loadScheduleCreate(@Body RequestBody jsonBodyObject);

    @POST(WSConfig.Api.SEARCH_TOUR_CREATE)
    Observable<SaveScheduleResponse> loadTourCreate(@Body RequestBody jsonBodyObject);

    @GET
    Observable<SearchResultResponse> loadListSearchVoucher(@Url String url);

    @GET(WSConfig.Api.NEARBY_FILTERS)
    Observable<FilterSearchResponse> loadFilterSearch(@QueryMap Map<String, Object> queryMap);

    @POST(WSConfig.Api.COMMENTS_CREATE)
    Observable<PostCommentResponse> commentsCreate(@Body RequestBody jsonBodyObject);

    @POST(WSConfig.Api.COMMENTS_SEARCH)
    Observable<CommentResponse> commentsSearch(@Body RequestBody jsonBodyObject);

    @POST(WSConfig.Api.GET_SCHEDULE_CREATE_DETAIL)
    Observable<DetailScheduleCreateResponse> loadDetailScheduleCreate(@Body RequestBody jsonBodyObject);

    @GET(WSConfig.Api.NOTIFICATION_REG)
    Observable<BaseResponse> notificationReg(@QueryMap Map<String, Object> queryMap);

    @GET(WSConfig.Api.NOTIFICATION_LIST)
    Observable<NotifyResponse> getListNotify(@Header(WSConfig.KeyParam.HEADER_TOKEN) String auth, @QueryMap Map<String, Object> queryMap);

    @GET
    Observable<NotifyResponse> getListNotifyMore(@Url String url);

    @GET
    Observable<BaseResponse> readNotify(@Url String url);

    @GET
    Observable<WeatherResponse> loadWeather(@Url String url, @QueryMap Map<String, Object> queryMap);

    @GET(WSConfig.Api.MOMENTS_TYPES)
    Observable<TypeMomentResponse> loadTypeMoment();

    @GET(WSConfig.Api.YOUR_MOMENT)
    Observable<PlaceResponse> loadYourMoment(@QueryMap Map<String, Object> queryMap);

    @GET
    Observable<BannerResponse> getBannerList(@Url String url);

    @GET(WSConfig.Api.NOTIFICATION_COUNTUNREAD)
    Call<CountUnreadRespone> getCountUnread(@QueryMap Map<String, Object> queryMap);

    @FormUrlEncoded
    @POST(WSConfig.Api.TRACK_LOCATION)
    Observable<BannerResponse> trackLocation(@Field(WSConfig.KeyParam.LAT) double lat, @Field(WSConfig.KeyParam.LOG) double lng,
                                             @Field(WSConfig.KeyParam.CHANEL) String chanel,
                                             @Field(WSConfig.KeyParam.PLATFORM) String platform,
                                             @Field(WSConfig.KeyParam.DEVICE_ID) String deviceId);

    @GET(WSConfig.Api.GET_HELLO_LOCATION)
    Observable<SuggestTravelResponse> getHelloLocation(@QueryMap Map<String, Object> queryMap);

    @GET(WSConfig.Api.GET_CHAT_BOT_QUESTION)
    Observable<ChatResponse> getChatBotData(@QueryMap Map<String, Object> queryMap);

    @POST(WSConfig.Api.CREATE_ROOM)
    Observable<CreateRoomResponse> createRoomChat(@Body RequestBody jsonBodyObject);

    @POST(WSConfig.Api.SEND_FORM_CHAT)
    Observable<BaseResponse> sendFormChat(@Body RequestBody jsonBodyObject);


    //====================================== F2
    @GET(WSConfig.Api.GET_CALL_HISTORY)
    Observable<CallNowHistoryResponse> getCallHistory();

    @POST(WSConfig.Api.DELETE_CALL_HISTORY)
    Observable<BaseResponse> deleteCallHistory(@Body RequestBody jsonBodyObject);

    @POST(WSConfig.Api.INVITE_CALL_NOW)
    Observable<BaseResponse> inviteCallNow(@Body RequestBody jsonBodyObject);

    @POST(WSConfig.Api.CALL_LIST)
    Observable<CallListResponse> callList(@Body RequestBody jsonBodyObject);

    @POST(WSConfig.Api.VERIFY_USER)
    Observable<VerifyUserResponse> verifyUser(@Body RequestBody jsonBodyObject);

    @GET(WSConfig.Api.ZIP_VERSION)
    Observable<ZipVersionResponse> zipVersion(@QueryMap Map<String, Object> queryMap);

    @GET(WSConfig.Api.HOME_SERVICE)
    Observable<HomeServiceResponse> getHomeService(@QueryMap Map<String, Object> queryMap);

    @GET
    Observable<BaseResponseNewHome> appFavoriteDestination(@Url String url);

    @GET
    Observable<BaseResponseSpecialNewHome> appFavoriteDestinationForAppDeal(@Url String url);

    @GET
    Observable<BaseResponseSecondNewHome> getDataFloorSecond(@Url String url);

    @POST(WSConfig.Api.GET_MOBILE_FROM_VIETTEL)
    Observable<MobileFromViettelResponse> getMobileFromViettel(@Body RequestBody jsonBodyObject);

    @GET(WSConfig.Api.GET_SETTING)
    Observable<com.namviet.vtvtravel.response.f2menu.MenuResponse> getSetting();

    @GET(WSConfig.Api.GET_NEW_CATEGORY)
    Observable<NewsCategoryResponse> getNewsCategory(@QueryMap Map<String, Object> queryMap);

    @GET(WSConfig.Api.GET_CATEGORY_VIDEO)
    Observable<VideoResponse> getCategoryVideo(@QueryMap Map<String, Object> queryMap);

    @GET
    Observable<DetailNewsCategoryResponse> getDetailNewsCategory(@Url String url);

    @GET
    Observable<DetailTravelNewsResponse> getDetailNewsTravel(@Url String url, @QueryMap Map<String, Object> queryMap);

    @GET
    Observable<PlaceNearByResponse> getPlaceNearBy(@Url String url, @QueryMap Map<String, Object> queryMap);

    @POST(WSConfig.Api.COMMENTS_SEARCH)
    Observable<com.namviet.vtvtravel.response.f2comment.CommentResponse> getComment(@Body RequestBody jsonBodyObject, @QueryMap Map<String, Object> queryMap);

    @POST(WSConfig.Api.COMMENTS_SEARCH)
    Observable<com.namviet.vtvtravel.response.f2comment.CommentResponse> getComment(@Body RequestBody jsonBodyObject);

    @POST(WSConfig.Api.COMMENTS_CREATE)
    Observable<CreateCommentResponse> createComment(@Body RequestBody jsonBodyObject);

    @POST(WSConfig.Api.COMMENTS_DELETE)
    Observable<DeleteCommentResponse> deleteComment(@Body RequestBody jsonBodyObject);

    @POST(WSConfig.Api.COMMENTS_UPDATE)
    Observable<UpdateCommentResponse> updateComment(@Body RequestBody jsonBodyObject);

    @POST(WSConfig.Api.CHECK_SHOW_CAPTCHA)
    Observable<CheckShowCaptcha> checkShowCaptcha(@Body RequestBody jsonBodyObject);

    @GET(WSConfig.Api.GET_GALLERY)
    Observable<ImagePartResponse> getGallery(@QueryMap Map<String, Object> queryMap);

    @GET
    Observable<ImagePartResponse> getGalleryMore(@Url String url, @QueryMap Map<String, Object> queryMap);

    @GET(WSConfig.Api.GET_GALLERY_ID)
    Observable<ItemImagePartResponse> getGalleryById(@QueryMap Map<String, Object> queryMap);

    @GET
    Observable<ItemImagePartResponse> getGalleryByUrl(@Url String url);

    @GET(WSConfig.Api.GET_NOTEBOOK)
    Observable<NotebookResponse> getNotebook(@QueryMap Map<String, Object> queryMap);

    @GET(WSConfig.Api.GET_NOTEBOOK)
    Observable<SmallLocationResponse> getSmallLocation(@QueryMap Map<String, Object> queryMap);

    @GET
    Observable<SmallLocationResponse> getSmallLocation(@Url String url, @QueryMap Map<String, Object> queryMap);

    //    @GET(WSConfig.Api.GET_NOTEBOOK)
//    Observable<SmallLocationResponse> getSmallLocation(@QueryMap Map<String, Object> queryMap);

    @GET
    Observable<DetailSmallLocationResponse> getDetailSmallLocation(@Url String url, @QueryMap Map<String, Object> queryMap);

    @GET(WSConfig.Api.FILTER_BY_CODE)
    Observable<FilterByCodeResponse> getFilterByCode(@QueryMap Map<String, Object> queryMap);

    @GET
    Observable<FilterByPageResponse> getFilterByPage(@Url String url, @QueryMap Map<String, Object> queryMap);

    @GET(WSConfig.Api.GET_VIDEO_BY_TAG)
    Observable<DetailVideoResponse> getVideoByTag(@QueryMap Map<String, Object> queryMap);

    @POST(WSConfig.Api.CREATE_REVIEW)
    Observable<CreateReviewResponse> createReview(@Body RequestBody jsonBodyObject);

    @POST(WSConfig.Api.GET_REVIEW)
    Observable<GetReviewResponse> getReview(@Body RequestBody jsonBodyObject);

    @Multipart
    @POST(WSConfig.Api.UPLOAD_IMAGE)
    Observable<UploadImageResponse> uploadImage(@Part MultipartBody.Part file);

    @GET(WSConfig.Api.SORT_SMALL_LOCATION)
    Observable<SortSmallLocationResponse> sortSmallLocation();

    @GET(WSConfig.Api.GET_BIG_LOCATION_BASE)
    Observable<BigLocationResponse> getBigLocation(@QueryMap Map<String, Object> queryMap);

    @GET(WSConfig.Api.GET_BIG_LOCATION_BASE)
    Observable<BigLocationBaseResponse> getBigLocationBase(@QueryMap Map<String, Object> queryMap);

    @GET(WSConfig.Api.LOCATION)
    Observable<LocationResponse> getLocation(@QueryMap Map<String, Object> queryMap);

    @GET
    Observable<RegionResponse> getRegion(@Url String url, @QueryMap Map<String, Object> queryMap);

    @GET(WSConfig.Api.TOP_LOCATION)
    Observable<TopLocationResponse> getTopLocation(@QueryMap Map<String, Object> queryMap);

    @GET(WSConfig.Api.ALL_LOCATION)
    Observable<AllLocationResponse> getAllLocation(@QueryMap Map<String, Object> queryMap);

    @POST(WSConfig.Api.CREATE_PLACE)
    Observable<BaseResponse> createPlace(@Body RequestBody jsonBodyObject);

    @POST(WSConfig.Api.POST_EVENT)
    Observable<BaseResponse> postEvent(@Body RequestBody jsonBodyObject);

    @GET
    Observable<SubTopExperienceResponse> getSubTopExperience(@Url String url);

    @GET
    Observable<PartBigLocationResponse> getPartBigLocation(@Url String url);


    @GET
    Observable<RuleLuckyWheel> getRuleOrPlayRuleLuckyWheel(@Url String url);



    @POST(WSConfig.Api.GET_OWNED_VOUCHER)
    Observable<ListVoucherResponse> getOwnedVoucher(@Body RequestBody jsonBodyObject, @QueryMap Map<String, Object> queryMap);

    @POST(WSConfig.Api.GET_OWNED_VOUCHER_STORE)
    Observable<ListVoucherResponse> getOwnedVoucherStore(@Body RequestBody jsonBodyObject, @QueryMap Map<String, Object> queryMap);

    @POST(WSConfig.Api.GET_CATEGORY_VOUCHER)
    Observable<CategoryVoucherResponse> getCategoryVoucher(@Body RequestBody jsonBodyObject);

    @POST(WSConfig.Api.GET_REGION_VOUCHER)
    Observable<RegionVoucherResponse> getRegionVoucher(@Body RequestBody jsonBodyObject);

    @POST(WSConfig.Api.GET_RANK)
    Observable<RankVoucherResponse> getRank(@Body RequestBody jsonBodyObject);


    @POST(WSConfig.Api.CHECK_CAN_RECEIVER)
    Observable<CheckCanReceiver> checkCanReceiver(@Body RequestBody jsonBodyObject);

    @POST(WSConfig.Api.USER_VOUCHER_RECEIVER)
    Observable<BaseResponse> checkUserVoucherReceiver(@Body RequestBody jsonBodyObject);


    @GET
    Observable<BaseResponseNewHome> getLiveTvData(@Url String url);


    @GET(WSConfig.Api.USAGE_RULES)
    Observable<HtmlResponse> getUsageRule();

    @GET(WSConfig.Api.CTKM_RULES)
    Observable<HtmlResponse> getCTKMRule();

    @GET(WSConfig.Api.DEAL_DETAIL)
    Observable<HtmlResponse> getDealDetail();


    @GET(WSConfig.Api.SYSTEM_INBOX)
    Observable<SystemInbox> getSystemInbox();

    @GET(WSConfig.Api.SYSTEM_INBOX_COUNT)
    Observable<CountSystemInbox> getCountSystemInbox();


    @POST(WSConfig.Api.SYSTEM_UPDATE_INBOX)
    Observable<BaseResponse> updateSystemInbox(@Body RequestBody jsonBodyObject);


    @GET
    Observable<SubBaseSearch<AppVoucherResponse>> getYourVoucher(@Url String url);


    @GET
    Observable<SubBaseSearch<ItemAppExperienceResponse>> getTrend(@Url String url, @QueryMap Map<String, Object> queryMap);

    @GET(WSConfig.Api.SEARCH_HOME)
    Observable<MainSearchResponse> getBlockSearch(@QueryMap Map<String, Object> queryMap);


    @GET(WSConfig.Api.SEARCH_PRE_RESULT)
    Observable<MainResultSearchResponse> getPreResultSearch(@QueryMap Map<String, Object> queryMap, @Query("keyword") String keyword, @Query("region_id") String regionId);

    @GET
    Observable<MainResultSearchResponse> getPreResultSearch(@Url String url);


    @POST(WSConfig.Api.CONFIRM_ENTER_SYSTEM_TRIP)
    Observable<ConfirmEnterTrip> confirmEnterTrip(@Body RequestBody jsonBodyObject);


    //////////////////////////////////////////////

    @GET(WSConfig.Api.GET_SERVICE)
    Observable<ServiceResponse> getService(@QueryMap Map<String, Object> queryMap);

    @POST(WSConfig.Api.REQUEST_SERVICE_OTP)
    Observable<ServiceOtpResponse> requestServiceOtp(@Body RequestBody body);

    @POST(WSConfig.Api.CONFIRM_SERVICE_OTP)
    Observable<ServiceOtpResponse> confirmServiceOtp(@Body RequestBody body);

    @POST(WSConfig.Api.RESENT_SERVICE_OTP)
    Observable<ResentOtpServiceResponse> resentServiceOtp(@Body RequestBody body);

    @POST(WSConfig.Api.GET_INFO)
    Observable<GetInfoResponse> getInfo(@Body RequestBody body, @Header(WSConfig.KeyParam.HEADER_TOKEN) String token);

    /////////////////////////////////////////////

    @POST(WSConfig.Api.USER_GUILD)
    Observable<GetUserGuildResponse> getUserGuild(@Body RequestBody body);

    @GET(WSConfig.Api.USER_GUILD)
    Observable<HelloMessageResponse> helloMessage();

    @POST(WSConfig.Api.USER_GUILD)
    Observable<PostUserGuildResponse> postUserGuild(@QueryMap Map<String, Object> queryMap);


    @POST(WSConfig.Api.USER_GUILD)
    Observable<PostUserGuildResponse> postUserGuild(@Body RequestBody body);

    @POST(WSConfig.Api.LIKE)
    Completable likeEvent(@Body RequestBody body);

    @POST(WSConfig.Api.WHEEL_ROTATE)
    Observable<WheelRotateResponse> wheelRotate(@Body RequestBody body);

    @POST(WSConfig.Api.WHEEL_RESULT)
    Observable<WheelResultResponse> wheelResult(@Body RequestBody body);

    @POST(WSConfig.Api.WHEEL_ACTION)
    Observable<WheelActionResponse> wheelAction(@Body RequestBody body);

    @POST(WSConfig.Api.WHEEL_AREAS)
    Observable<WheelAreasResponse> wheelAreas(@Body RequestBody body);

    @POST
    Observable<WheelAreasResponse> wheelAreasV2(@Body RequestBody body, @Url String url);

    @GET(WSConfig.Api.WHEEL_CHARTS)
    Observable<WheelChartResponse> getWheelChartResponse();

    @GET(WSConfig.Api.WHEEL_CHARTS_2)
    Observable<WheelChartResponse> getWheelChart();

    @GET
    Observable<VideoDetailResponse> viewVideo(@Url String url);

    @POST(WSConfig.Api.USER_REVIEW)
    Observable<BaseResponse> reviewChat(@Body RequestBody body);

    @POST(WSConfig.Api.USER_REVIEW_SYS)
    Completable yesNoReview(@Body RequestBody body);


    @POST(WSConfig.Api.USER_SUPPORTER)
    Observable<OperatorInformationResponse> getOperatorInformation(@Body RequestBody body);

    @POST(WSConfig.Api.USER_TICKET)
    Observable<ListTicketResponse> getListTicket(@Body RequestBody body, @QueryMap Map<String, Object> queryMap);

    @POST(WSConfig.Api.USER_TICKET_HISTORY)
    Observable<ListTicketHistoryResponse> getTicketHistory(@Body RequestBody body);

    @POST(WSConfig.Api.USER_TICKET_UPDATE)
    Observable<BaseResponse> updateTicket(@Body RequestBody body);

    @POST(WSConfig.Api.USER_TICKET)
    Observable<ProcessTicketResponse> submitProcessTicket(@Body RequestBody body);

    @POST(WSConfig.Api.USER_CREATE_ROOM)
    Observable<CallRoomResponse> createRoomAndGetRoomId(@Body RequestBody body);

    @POST(WSConfig.Api.SHARE)
    Observable<BaseResponse> share(@Body RequestBody body);

    @GET(WSConfig.Api.GET_HELLO_LOCATION)
    Observable<ConfigPopupResponse> getConfigPopup(@QueryMap Map<String, Object> queryMap);

    @POST(WSConfig.Api.GET_LIST_THEME)
    Observable<GetListThemeResponse> getListTheme(@Body RequestBody body);

    @POST(WSConfig.Api.UPDATE_THEME)
    Completable updateTheme(@Body RequestBody body);

    @POST(WSConfig.Api.GET_USER_THEME)
    Observable<GetUserThemeResponse> getUserTheme(@Body RequestBody body);

    @POST(WSConfig.Api.TICKET_INFO)
    Observable<TicketInfo> ticketInfo(@Body RequestBody body);

    @GET
    Observable<WheelChartResponse> getVQMMHistories(@Url String url);

    @GET(WSConfig.Api.CTKM_BLOCK)
    Observable<BlockResponse> getBlockDeal(@QueryMap Map<String, Object> queryMap);

    @GET
    Observable<DealResponse> getDeal(@Url String url);

    @GET
    Observable<DealResponse> getDealWithParam(@Url String url, @Query("isProcessing") String isProcessing, @Query("page") int page);

    @GET
    Observable<DealResponse> getHotDealWithParam(@Url String url, @Query("page") int page);

    @GET(WSConfig.Api.DEAL_CAMPAIGN_DETAIL)
    Observable<DealCampaignDetail> getDealCampaignDetail(@Query("id") String id);

    @GET(WSConfig.Api.DEAL_CHILD_DETAIL)
    Observable<DealCampaignDetail> getDealDetail(@Query("id") String id);

    @GET
    Observable<DealFollowResponse> getDealFollow(@Url String url, @Query("rewardStatus") String rewardStatus);


    @GET(WSConfig.Api.DEAL_BY_CAMPAIGN)
    Observable<DealResponse> getDealByCampaign(@Query("isProcessing") String isProcessing, @Query("campaignId") String campaignId, @Query("rewardStatus") String rewardStatus);


    @GET(WSConfig.Api.MY_GIFT)
    Observable<MyGiftResponse> getAllMyGift(@Query("page") int page);

    @GET(WSConfig.Api.API_TRIPS_SCHEDULE+WSConfig.Api.GET_SCHEDULE)
    Observable<MyTripsResponse> getAllTripsSchedule();

    @POST(WSConfig.Api.API_TRIPS_SCHEDULE+WSConfig.Api.CREATE_SCHEDULE)
    Observable<CreateScheduleResponse> createTrip(@Body RequestBody body);

    @GET(WSConfig.Api.API_TRIPS_SCHEDULE + WSConfig.Api.SCHEDULE_BY_ID)
    Observable<PlaceScheduleResponse> getDetailPlaceByScheduleid(@Query("scheduleCustomId") String scheduleCustomId);

    @POST(WSConfig.Api.API_TRIPS_SCHEDULE + WSConfig.Api.UPDATE_COST)
    Observable<BaseResponse> updateCost(@Body RequestBody bodyCost);

    @GET(WSConfig.Api.API_TRIPS_SCHEDULE + WSConfig.Api.GET_COST)
    Observable<CostResponse> getCostDetail(@Query("scheduleCustomId") String scheduleCustomId);

    @POST(WSConfig.Api.API_TRIPS_SCHEDULE + WSConfig.Api.UPDATE_SCHEDULE)
    Observable<BaseResponse> updateSchedule(@Body RequestBody bodyUpdate);

    @POST(WSConfig.Api.API_TRIPS_SCHEDULE + WSConfig.Api.DELETE_SCHEDULE)
    Observable<BaseResponse> deleteSchedule(@Body RequestBody bodyDelete);

    @POST(WSConfig.Api.API_TRIPS_SCHEDULE + WSConfig.Api.UPDATE_RANGE_SCHEDULE_CUSTOM)
    Observable<BaseResponse> updateRangeSchedule(@Body RequestBody bodyUpdateRange);

    @GET(WSConfig.Api.API_TRIPS_SCHEDULE + WSConfig.Api.PLACE)
    Observable<DetailPlacesResponse> getDetailPlaces(@Query("scheduleCustomId") String scheduleCustomId);

    @POST(WSConfig.Api.API_TRIPS_SCHEDULE + WSConfig.Api.EDIT_NOTE)
    Observable<BaseResponse> updateNoteSchedule(@Body RequestBody bodyUpdateNote);

    @POST(WSConfig.Api.API_TRIPS_SCHEDULE + WSConfig.Api.INVITE_SCHEDULE)
    Observable<BaseResponse> inviteSchedule(@Body RequestBody bodyInvite);

    @POST(WSConfig.Api.API_TRIPS_SCHEDULE + WSConfig.Api.UPDATE_ARRIVAL_TIME)
    Observable<BaseResponse> updateArrivalTime(@Body RequestBody bodyInvite);

    @POST(WSConfig.Api.API_TRIPS_SCHEDULE + WSConfig.Api.UPDATE_FREE_TIME)
    Observable<BaseResponse> updateFreeTime(@Body RequestBody bodyInvite);

    @POST(WSConfig.Api.API_TRIPS_SCHEDULE + WSConfig.Api.DELETE_PLACE)
    Observable<BaseResponse> deletePlace(@Body RequestBody schedulePlaceId);

    @POST(WSConfig.Api.API_TRIPS_SCHEDULE + WSConfig.Api.DELETE_PLACE_BY_DAY)
    Observable<BaseResponse> deletePlaceByDay(@Body RequestBody bodyDelete);

    @POST(WSConfig.Api.API_TRIPS_SCHEDULE + WSConfig.Api.UPDATE_DURATION_VISIT)
    Observable<BaseResponse> updateDurationVisit(@Body RequestBody bodyUpdateDuration);

    @POST(WSConfig.Api.API_TRIPS_SCHEDULE + WSConfig.Api.UPDATE_SCHEDULE_PLACE)
    Observable<BaseResponse> updateSchedulePlace(@Body RequestBody bodyUpdatePlace);

    @POST(WSConfig.Api.INBOX_TYPE)
    Observable<NotificationResponse> getNotificationByType(@Query("typeId") String type, @Body RequestBody body);

    @POST(WSConfig.Api.INBOX_MARK)
    Observable<NotificationResponse> getNotificationByMark(@Body RequestBody body);

    @POST(WSConfig.Api.SYSTEM_INBOX)
    Observable<NotificationResponse> getAllNotification(@Body RequestBody body);

    @POST(WSConfig.Api.INBOX_STATUS)
    Observable<NotificationResponse> getNotificationByStatus(@Query("status") String type, @Body RequestBody body);

    @POST(WSConfig.Api.UPDATE_MARK)
    Observable<UpdateNotificationResponse> updateMark(@Body RequestBody body);

    @POST(WSConfig.Api.UPDATE_INBOX)
    Observable<UpdateNotificationResponse> updateInbox(@Body RequestBody body);

    @POST(WSConfig.Api.USER_LOGOUT)
    Observable<Object> logout(@Body RequestBody body);

    @GET
    Observable<NotificationTab> getNotificationTab(@Url String url);


}
