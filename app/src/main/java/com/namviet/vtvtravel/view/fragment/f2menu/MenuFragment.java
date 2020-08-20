package com.namviet.vtvtravel.view.fragment.f2menu;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.f2menu.MenuAdapter;
import com.namviet.vtvtravel.adapter.f2menu.SubMenuAdapter;
import com.namviet.vtvtravel.adapter.newhome.NewHomeAdapter;
import com.namviet.vtvtravel.adapter.newhome.subnewhome.SubSmallHeaderAdapter;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2FragmentMenuBinding;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.listener.CitySelectListener;
import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.model.City;
import com.namviet.vtvtravel.model.ItemWeather;
import com.namviet.vtvtravel.model.f2event.OnLoginSuccessAndUpdateUserView;
import com.namviet.vtvtravel.model.f2event.OnUpdateAccount;
import com.namviet.vtvtravel.response.CityResponse;
import com.namviet.vtvtravel.response.WeatherResponse;
import com.namviet.vtvtravel.response.f2menu.MenuItem;
import com.namviet.vtvtravel.response.f2menu.MenuResponse;
import com.namviet.vtvtravel.response.newhome.HomeServiceResponse;
import com.namviet.vtvtravel.response.newhome.SettingResponse;
import com.namviet.vtvtravel.view.dialog.CityDialogFragment;
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew;
import com.namviet.vtvtravel.view.f2.UserInformationActivity;
import com.namviet.vtvtravel.view.fragment.MainFragment;
import com.namviet.vtvtravel.view.fragment.newhome.NewHomeFragment;
import com.namviet.vtvtravel.viewmodel.PlaceViewModel;
import com.namviet.vtvtravel.viewmodel.newhome.NewHomeViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

public class MenuFragment extends MainFragment implements Observer {
    private ArrayList<City> cityList;
    private ArrayList<ItemWeather> weatherList;
    private F2FragmentMenuBinding binding;
    private NewHomeViewModel viewModel;
    private String json = "{\n" +
            "\"status\": \"success\",\n" +
            "\"code\": \"SUCCESS\",\n" +
            "\"message\": \"\",\n" +
            "\"data\": {\n" +
            "\"menus\": {\n" +
            "\"left\": [\n" +
            "{\n" +
            "\"id\": \"5f1fccce4198d0a4738b458d\",\n" +
            "\"code\": \"APP_MAIN_HEADER_BOOKING\",\n" +
            "\"code_type\": \"APP_MAIN_HEADER_PACKAGE\",\n" +
            "\"name\": \"Đơn hàng\",\n" +
            "\"icon_url\": \"https://cdn1.travel.onex.vn/crop_70x70/data_files/menus_files/image/202007/28/buy_ibaco.png\",\n" +
            "\"icon_enable_url\": null,\n" +
            "\"link\": \"https://api1.travel.onex.vn\",\n" +
            "\"category_id\": null,\n" +
            "\"html_icon\": \"\",\n" +
            "\"weight\": 0,\n" +
            "\"banner_url\": null,\n" +
            "\"children\": []\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"5f1fcc634198d06d4d8b4592\",\n" +
            "\"code\": \"APP_MAIN_HEADER_BOOKING\",\n" +
            "\"code_type\": \"APP_MAIN_HEADER_MY_GIFT\",\n" +
            "\"name\": \"Quà của tôi\",\n" +
            "\"icon_url\": \"https://cdn1.travel.onex.vn/crop_70x70/data_files/menus_files/image/202007/28/gift_vmenm.png\",\n" +
            "\"icon_enable_url\": null,\n" +
            "\"link\": \"https://api1.travel.onex.vn\",\n" +
            "\"category_id\": null,\n" +
            "\"html_icon\": \"\",\n" +
            "\"weight\": 0,\n" +
            "\"banner_url\": null,\n" +
            "\"children\": [\n" +
            "{\n" +
            "\"id\": \"5f2e19774198d082048b456a\",\n" +
            "\"code\": \"APP_MAIN_VQMM_BOOKING\",\n" +
            "\"code_type\": \"APP_MAIN_VQMM_BOOKING\",\n" +
            "\"name\": \"Vòng quay may mắn\",\n" +
            "\"icon_url\": \"https://cdn1.travel.onex.vn/crop_70x70/data_files/menus_files/image/202008/08/wheel_gxzuy.png\",\n" +
            "\"icon_enable_url\": null,\n" +
            "\"link\": \"https://api1.travel.onex.vn\",\n" +
            "\"category_id\": null,\n" +
            "\"html_icon\": \"\",\n" +
            "\"weight\": 0,\n" +
            "\"banner_url\": null,\n" +
            "\"children\": []\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"5f2e18554198d0a87c8b4569\",\n" +
            "\"code\": \"APP_MAIN_VOUCHER_BOOKING\",\n" +
            "\"code_type\": \"APP_MAIN_VOUCHER_BOOKING\",\n" +
            "\"name\": \"Voucher du lịch\",\n" +
            "\"icon_url\": \"https://cdn1.travel.onex.vn/crop_70x70/data_files/menus_files/image/202008/08/voucher_xneoe.png\",\n" +
            "\"icon_enable_url\": null,\n" +
            "\"link\": \"https://api1.travel.onex.vn\",\n" +
            "\"category_id\": null,\n" +
            "\"html_icon\": \"\",\n" +
            "\"weight\": 0,\n" +
            "\"banner_url\": null,\n" +
            "\"children\": []\n" +
            "}\n" +
            "]\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"5f1fcc354198d06d4d8b4590\",\n" +
            "\"code\": \"APP_MAIN_HEADER_BOOKING\",\n" +
            "\"code_type\": \"APP_MAIN_HEADER_MY_TRIP\",\n" +
            "\"name\": \"Chuyến đi của tôi\",\n" +
            "\"icon_url\": \"https://cdn1.travel.onex.vn/crop_70x70/data_files/menus_files/image/202007/28/travel_fodlm.png\",\n" +
            "\"icon_enable_url\": null,\n" +
            "\"link\": \"https://api1.travel.onex.vn\",\n" +
            "\"category_id\": null,\n" +
            "\"html_icon\": \"\",\n" +
            "\"weight\": 0,\n" +
            "\"banner_url\": null,\n" +
            "\"children\": []\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"5f1fcc1e4198d06d4d8b458f\",\n" +
            "\"code\": \"APP_MAIN_BOOKING\",\n" +
            "\"code_type\": \"APP_MAIN_TRAVEL_NEWS\",\n" +
            "\"name\": \"TIN TỨC DU LỊCH\",\n" +
            "\"icon_url\": \"https://cdn1.travel.onex.vn/crop_70x70/data_files/menus_files/image/202007/28/news_tmded.png\",\n" +
            "\"icon_enable_url\": null,\n" +
            "\"link\": \"https://api1.travel.onex.vn\",\n" +
            "\"category_id\": null,\n" +
            "\"html_icon\": \"\",\n" +
            "\"weight\": 0,\n" +
            "\"banner_url\": null,\n" +
            "\"children\": []\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"5f1fcc054198d0a4738b458c\",\n" +
            "\"code\": \"APP_MAIN_BOOKING\",\n" +
            "\"code_type\": \"APP_MAIN_LIVETV\",\n" +
            "\"name\": \"LIVE TV\",\n" +
            "\"icon_url\": \"https://cdn1.travel.onex.vn/crop_70x70/data_files/menus_files/image/202007/28/tv_rmekz.png\",\n" +
            "\"icon_enable_url\": null,\n" +
            "\"link\": \"https://api1.travel.onex.vn/tv/broadcast\",\n" +
            "\"category_id\": null,\n" +
            "\"html_icon\": \"\",\n" +
            "\"weight\": 0,\n" +
            "\"banner_url\": null,\n" +
            "\"children\": []\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"5f1fcbed4198d0a4738b458b\",\n" +
            "\"code\": \"APP_MAIN_BOOKING\",\n" +
            "\"code_type\": \"APP_MAIN_VIDEO\",\n" +
            "\"name\": \"VIDEO\",\n" +
            "\"icon_url\": \"https://cdn1.travel.onex.vn/crop_70x70/data_files/menus_files/image/202007/28/video_zxzqz.png\",\n" +
            "\"icon_enable_url\": null,\n" +
            "\"link\": \"https://api1.travel.onex.vn/videos\",\n" +
            "\"category_id\": null,\n" +
            "\"html_icon\": \"\",\n" +
            "\"weight\": 0,\n" +
            "\"banner_url\": null,\n" +
            "\"children\": []\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"5f1fcbd54198d0a4738b458a\",\n" +
            "\"code\": \"APP_MAIN_BOOKING\",\n" +
            "\"code_type\": \"APP_MAIN_IMAGES\",\n" +
            "\"name\": \"GÓC ẢNH\",\n" +
            "\"icon_url\": \"https://cdn1.travel.onex.vn/crop_70x70/data_files/menus_files/image/202007/28/photo_iopkf.png\",\n" +
            "\"icon_enable_url\": null,\n" +
            "\"link\": \"https://api1.travel.onex.vn/galleries\",\n" +
            "\"category_id\": null,\n" +
            "\"html_icon\": \"\",\n" +
            "\"weight\": 0,\n" +
            "\"banner_url\": null,\n" +
            "\"children\": []\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"5f1fcbb34198d0a4738b4589\",\n" +
            "\"code\": \"APP_MAIN_BOOKING\",\n" +
            "\"code_type\": \"APP_MAIN_TOURIST_HANDBOOK\",\n" +
            "\"name\": \"SỔ TAY DU LỊCH\",\n" +
            "\"icon_url\": \"https://cdn1.travel.onex.vn/crop_70x70/data_files/menus_files/image/202007/28/notepad_vzraq.png\",\n" +
            "\"icon_enable_url\": null,\n" +
            "\"link\": \"https://api1.travel.onex.vn/news?category_code=NOTEBOOK_NEWS\",\n" +
            "\"category_id\": null,\n" +
            "\"html_icon\": \"\",\n" +
            "\"weight\": 0,\n" +
            "\"banner_url\": null,\n" +
            "\"children\": []\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"5f1fcb964198d0a4738b4588\",\n" +
            "\"code\": \"APP_MAIN_BOOKING\",\n" +
            "\"code_type\": \"APP_MAIN_MOMENT\",\n" +
            "\"name\": \"KHOẢNH KHẮC\",\n" +
            "\"icon_url\": \"https://cdn1.travel.onex.vn/crop_70x70/data_files/menus_files/image/202007/28/jump_kwubs.png\",\n" +
            "\"icon_enable_url\": null,\n" +
            "\"link\": \"https://api1.travel.onex.vn/moments\",\n" +
            "\"category_id\": null,\n" +
            "\"html_icon\": \"\",\n" +
            "\"weight\": 0,\n" +
            "\"banner_url\": null,\n" +
            "\"children\": []\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"5f1fcb814198d0a4738b4587\",\n" +
            "\"code\": \"APP_MAIN_BOOKING\",\n" +
            "\"code_type\": \"APP_MAIN_DEAL\",\n" +
            "\"name\": \"SĂN DEAL GIÁ TỐT\",\n" +
            "\"icon_url\": \"https://cdn1.travel.onex.vn/crop_70x70/data_files/menus_files/image/202007/28/hot-deal_sxift.png\",\n" +
            "\"icon_enable_url\": null,\n" +
            "\"link\": \"https://api1.travel.onex.vn\",\n" +
            "\"category_id\": null,\n" +
            "\"html_icon\": \"\",\n" +
            "\"weight\": 0,\n" +
            "\"banner_url\": null,\n" +
            "\"children\": []\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"5f1fcb684198d0a4738b4586\",\n" +
            "\"code\": \"APP_MAIN_BOOKING\",\n" +
            "\"code_type\": \"APP_MAIN_PROMOTION\",\n" +
            "\"name\": \"KHO KHUYẾN MẠI\",\n" +
            "\"icon_url\": \"https://cdn1.travel.onex.vn/crop_70x70/data_files/menus_files/image/202007/28/coupon_tbfuf.png\",\n" +
            "\"icon_enable_url\": null,\n" +
            "\"link\": \"https://api1.travel.onex.vn\",\n" +
            "\"category_id\": null,\n" +
            "\"html_icon\": \"\",\n" +
            "\"weight\": 0,\n" +
            "\"banner_url\": null,\n" +
            "\"children\": []\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"5f1fcb514198d0a4738b4585\",\n" +
            "\"code\": \"APP_MAIN_BOOKING\",\n" +
            "\"code_type\": \"APP_MAIN_SERVICE_PACK\",\n" +
            "\"name\": \"GÓI DỊCH VỤ\",\n" +
            "\"icon_url\": \"https://cdn1.travel.onex.vn/crop_70x70/data_files/menus_files/image/202007/28/package_tpivi.png\",\n" +
            "\"icon_enable_url\": null,\n" +
            "\"link\": \"https://api1.travel.onex.vn/service_pack\",\n" +
            "\"category_id\": null,\n" +
            "\"html_icon\": \"\",\n" +
            "\"weight\": 0,\n" +
            "\"banner_url\": null,\n" +
            "\"children\": []\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"5f1fcb3f4198d0a4738b4584\",\n" +
            "\"code\": \"APP_MAIN_FOOTER_BOOKING\",\n" +
            "\"code_type\": \"APP_MAIN_FOOTER_NOTIFICATION\",\n" +
            "\"name\": \"THÔNG BÁO\",\n" +
            "\"icon_url\": \"https://cdn1.travel.onex.vn/crop_70x70/data_files/menus_files/image/202007/28/notification_faozy.png\",\n" +
            "\"icon_enable_url\": null,\n" +
            "\"link\": \"https://api1.travel.onex.vn\",\n" +
            "\"category_id\": null,\n" +
            "\"html_icon\": \"\",\n" +
            "\"weight\": 0,\n" +
            "\"banner_url\": null,\n" +
            "\"children\": []\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"5f1fcb2c4198d0a4738b4583\",\n" +
            "\"code\": \"APP_MAIN_FOOTER_BOOKING\",\n" +
            "\"code_type\": \"APP_MAIN_FOOTER_SUPPORT\",\n" +
            "\"name\": \"TRUNG TÂM HỖ TRỢ\",\n" +
            "\"icon_url\": \"https://cdn1.travel.onex.vn/crop_70x70/data_files/menus_files/image/202007/28/help_cpklx.png\",\n" +
            "\"icon_enable_url\": null,\n" +
            "\"link\": \"https://api1.travel.onex.vn\",\n" +
            "\"category_id\": null,\n" +
            "\"html_icon\": \"\",\n" +
            "\"weight\": 0,\n" +
            "\"banner_url\": null,\n" +
            "\"children\": [\n" +
            "{\n" +
            "\"id\": \"5f2e1ca84198d082048b456d\",\n" +
            "\"code\": \"APP_MAIN_SUPPORT_BOOKING\",\n" +
            "\"code_type\": \"APP_MAIN_SUPPORT_BOOKING\",\n" +
            "\"name\": \"Tổng đài 1039\",\n" +
            "\"icon_url\": \"https://cdn1.travel.onex.vn/crop_70x70/data_files/menus_files/image/202008/08/support_yixvq.png\",\n" +
            "\"icon_enable_url\": null,\n" +
            "\"link\": \"https://api1.travel.onex.vn\",\n" +
            "\"category_id\": null,\n" +
            "\"html_icon\": \"\",\n" +
            "\"weight\": 0,\n" +
            "\"banner_url\": null,\n" +
            "\"children\": []\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"5f2e1bb04198d0a87c8b456a\",\n" +
            "\"code\": \"APP_MAIN_CHATBOT_BOOKING\",\n" +
            "\"code_type\": \"APP_MAIN_CHATBOT_BOOKING\",\n" +
            "\"name\": \"Chatbot\",\n" +
            "\"icon_url\": \"https://cdn1.travel.onex.vn/crop_70x70/data_files/menus_files/image/202008/08/chatbot_gbzlg.png\",\n" +
            "\"icon_enable_url\": null,\n" +
            "\"link\": \"https://api1.travel.onex.vn\",\n" +
            "\"category_id\": null,\n" +
            "\"html_icon\": \"\",\n" +
            "\"weight\": 0,\n" +
            "\"banner_url\": null,\n" +
            "\"children\": []\n" +
            "}\n" +
            "]\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"5f1fcb164198d0a4738b4582\",\n" +
            "\"code\": \"APP_MAIN_FOOTER_BOOKING\",\n" +
            "\"code_type\": \"APP_MAIN_FOOTER_SETTING\",\n" +
            "\"name\": \"CÀI ĐẶT\",\n" +
            "\"icon_url\": \"https://cdn1.travel.onex.vn/crop_70x70/data_files/menus_files/image/202007/28/settings_ovewb.png\",\n" +
            "\"icon_enable_url\": null,\n" +
            "\"link\": \"https://api1.travel.onex.vn/setting\",\n" +
            "\"category_id\": null,\n" +
            "\"html_icon\": \"\",\n" +
            "\"weight\": 0,\n" +
            "\"banner_url\": null,\n" +
            "\"children\": []\n" +
            "}\n" +
            "],\n" +
            "\"header\": [],\n" +
            "\"footer\": [\n" +
            "{\n" +
            "\"id\": \"5f1fcb014198d0a4738b4581\",\n" +
            "\"code\": \"APP_FOOTER_BOOKING\",\n" +
            "\"code_type\": \"APP_FOOTER_HOME\",\n" +
            "\"name\": \"TRANG CHỦ\",\n" +
            "\"icon_url\": \"https://cdn1.travel.onex.vn/crop_70x70/data_files/menus_files/image/202007/30/home-inactive_fysxk.png\",\n" +
            "\"icon_enable_url\": \"https://cdn1.travel.onex.vn/crop_70x70/data_files/menus_files/image/202007/30/home-active_vumcx.png\",\n" +
            "\"link\": \"https://api1.travel.onex.vn\",\n" +
            "\"category_id\": null,\n" +
            "\"html_icon\": \"\",\n" +
            "\"weight\": 0,\n" +
            "\"banner_url\": null,\n" +
            "\"children\": []\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"5f1fcad34198d0a4738b4580\",\n" +
            "\"code\": \"APP_FOOTER_BOOKING\",\n" +
            "\"code_type\": \"APP_FOOTER_MOMENT\",\n" +
            "\"name\": \"KHOẢNH KHẮC\",\n" +
            "\"icon_url\": \"https://cdn1.travel.onex.vn/crop_70x70/data_files/menus_files/image/202007/30/khoanhkhac-inactive_jhgwa.png\",\n" +
            "\"icon_enable_url\": \"https://cdn1.travel.onex.vn/crop_70x70/data_files/menus_files/image/202007/30/khoanhkhac-active_fohrn.png\",\n" +
            "\"link\": \"https://api1.travel.onex.vn/moments\",\n" +
            "\"category_id\": null,\n" +
            "\"html_icon\": \"\",\n" +
            "\"weight\": 0,\n" +
            "\"banner_url\": null,\n" +
            "\"children\": []\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"5f1fcab94198d0a4738b457f\",\n" +
            "\"code\": \"APP_FOOTER_BOOKING\",\n" +
            "\"code_type\": \"APP_FOOTER_BOOKING\",\n" +
            "\"name\": \"BOOKING\",\n" +
            "\"icon_url\": \"https://cdn1.travel.onex.vn/crop_70x70/data_files/menus_files/image/202007/30/booking-inactive_wsuzi.png\",\n" +
            "\"icon_enable_url\": \"https://cdn1.travel.onex.vn/crop_70x70/data_files/menus_files/image/202007/30/booking-active_lbdcx.png\",\n" +
            "\"link\": \"https://api1.travel.onex.vn\",\n" +
            "\"category_id\": null,\n" +
            "\"html_icon\": \"\",\n" +
            "\"weight\": 0,\n" +
            "\"banner_url\": null,\n" +
            "\"children\": []\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"5f1fca954198d0a4738b457e\",\n" +
            "\"code\": \"APP_FOOTER_BOOKING\",\n" +
            "\"code_type\": \"APP_FOOTER_VIDEO\",\n" +
            "\"name\": \"VIDEO\",\n" +
            "\"icon_url\": \"https://cdn1.travel.onex.vn/crop_70x70/data_files/menus_files/image/202007/30/video-inactive_tvxfz.png\",\n" +
            "\"icon_enable_url\": \"https://cdn1.travel.onex.vn/crop_70x70/data_files/menus_files/image/202007/30/video-active_uutdt.png\",\n" +
            "\"link\": \"https://api1.travel.onex.vn/videos\",\n" +
            "\"category_id\": null,\n" +
            "\"html_icon\": \"\",\n" +
            "\"weight\": 0,\n" +
            "\"banner_url\": null,\n" +
            "\"children\": []\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"5f1fca754198d0a4738b457d\",\n" +
            "\"code\": \"APP_FOOTER_BOOKING\",\n" +
            "\"code_type\": \"APP_FOOTER_MENU\",\n" +
            "\"name\": \"MENU\",\n" +
            "\"icon_url\": \"https://cdn1.travel.onex.vn/crop_70x70/data_files/menus_files/image/202007/30/menu-inactive_qtyno.png\",\n" +
            "\"icon_enable_url\": \"https://cdn1.travel.onex.vn/crop_70x70/data_files/menus_files/image/202007/30/menu-active_wfqwl.png\",\n" +
            "\"link\": \"https://api1.travel.onex.vn\",\n" +
            "\"category_id\": null,\n" +
            "\"html_icon\": \"\",\n" +
            "\"weight\": 0,\n" +
            "\"banner_url\": null,\n" +
            "\"children\": []\n" +
            "}\n" +
            "]\n" +
            "}\n" +
            "}\n" +
            "}";
    private SubMenuAdapter subMenuAdapter;
    private MenuAdapter menuAdapter;
    private List<MenuItem> itemMenusHeader = new ArrayList<>();
    private List<MenuItem> itemMenusNormal = new ArrayList<>();
    private List<MenuItem> itemMenusFooter = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f2_fragment_menu, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    @Override
    protected void initViews(View v) {
        updateViews();
    }

    @Override
    protected void updateViews() {
        super.updateViews();
        setUpLayout();
        setClickListener();

        viewModel = new NewHomeViewModel();
        binding.setNewHomeViewModel(viewModel);
        viewModel.addObserver(this);
        viewModel.getSetting();
        viewModel.loadWeather(null);
        viewModel.loadCity();

        MenuResponse menuResponse = new Gson().fromJson(json, MenuResponse.class);
        getDataMenu(menuResponse);
        subMenuAdapter = new SubMenuAdapter(itemMenusHeader, mActivity, null);
        binding.rclHeaderMenu.setAdapter(subMenuAdapter);

        menuAdapter = new MenuAdapter(itemMenusNormal, itemMenusFooter, mActivity, null);
        binding.rclContent.setAdapter(menuAdapter);

        binding.rclContent.setNestedScrollingEnabled(false);
        binding.rclHeaderMenu.setNestedScrollingEnabled(false);
    }

    private void setClickListener() {
        binding.layoutUserInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInformationActivity.openScreen(mActivity);
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginAndRegisterActivityNew.startScreen(mActivity, 1, false);
            }
        });

        binding.layoutWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != cityList) {
                    CityDialogFragment cityDialogFragment = CityDialogFragment.newInstance(cityList);
                    cityDialogFragment.show(mActivity.getSupportFragmentManager(), Constants.TAG_DIALOG);
                    cityDialogFragment.setCitySelectListener(new CitySelectListener() {
                        @Override
                        public void onSelect(ArrayList<City> list, City city) {
                            viewModel.loadWeather(city);
                        }
                    });
                }
            }
        });
    }

    private void getDataMenu(MenuResponse menuResponse) {
        for (int i = 0; i < menuResponse.getData().getMenus().getLeft().size(); i++) {
            if (menuResponse.getData().getMenus().getLeft().get(i).getCode().toUpperCase().equals("APP_MAIN_HEADER_BOOKING")) {
                itemMenusHeader.add(menuResponse.getData().getMenus().getLeft().get(i));
            } else if (menuResponse.getData().getMenus().getLeft().get(i).getCode().toUpperCase().equals("APP_MAIN_FOOTER_BOOKING")) {
                itemMenusFooter.add(menuResponse.getData().getMenus().getLeft().get(i));
            } else {
                itemMenusNormal.add(menuResponse.getData().getMenus().getLeft().get(i));
            }
        }
    }

    @Override
    public void update(Observable observable, Object o) {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    binding.shimmerViewContainer.stopShimmer();
//                    binding.shimmerViewContainer.setVisibility(View.GONE);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }, 1000);

        if (observable instanceof NewHomeViewModel && null != o) {
            if (o instanceof ErrorResponse) {
                ErrorResponse responseError = (ErrorResponse) o;
                try {
//                    Toast.makeText(mActivity, responseError.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                }
            } else if (o instanceof SettingResponse) {
                SettingResponse settingResponse = (SettingResponse) o;
            }

        }

        if (observable instanceof NewHomeViewModel && null != o) {
            if (o instanceof WeatherResponse) {
                WeatherResponse response = (WeatherResponse) o;
                weatherList = response.getData();
                setWeather(response.getData().get(0));
            } else if (o instanceof CityResponse) {
                CityResponse response = (CityResponse) o;
                cityList = response.getData();
            }
        }
    }

    private void setUpLayout() {
        Account account = MyApplication.getInstance().getAccount();
        if (null != account && account.isLogin()) {
            if (account.getImageProfile() != null && !account.getImageProfile().isEmpty()) {
                Glide.with(mActivity).load(account.getImageProfile()).apply(new RequestOptions().circleCrop()).into(binding.imgAvatar);
            } else {
                binding.imgAvatar.setImageResource(R.drawable.f2_defaut_user);
            }
            binding.tvName.setText(account.getFullname());
            binding.tvPhone.setText(String.valueOf(account.getMobile()));
            binding.layoutUser.setVisibility(View.VISIBLE);
            binding.layoutUserNotYetLogin.setVisibility(View.GONE);
            binding.rclHeaderMenu.setVisibility(View.VISIBLE);
        } else {
            binding.layoutUser.setVisibility(View.GONE);
            binding.layoutUserNotYetLogin.setVisibility(View.VISIBLE);
            binding.rclHeaderMenu.setVisibility(View.GONE);
        }
    }

    @Subscribe
    public void onReloadUserView(OnLoginSuccessAndUpdateUserView onLoginSuccessAndUpdateUserView) {
        setUpLayout();
    }


    private void setWeather(ItemWeather itemWeather) {
        if (null != itemWeather) {
            try {
                binding.tvCity.setText(itemWeather.getRegion_name());
                binding.tvStatus.setText(itemWeather.getWeather().getDescription() + ", " + Math.round(itemWeather.getTamp()) + "°C");
                Glide.with(mActivity).load(itemWeather.getWeather().getIcon_url()).placeholder(R.drawable.f2_ic_weather).error(R.drawable.f2_ic_weather).into(binding.imgWeather);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Subscribe
    public void onUpdateAccount(OnUpdateAccount onUpdateAccount) {
        setUpLayout();
    }

}
