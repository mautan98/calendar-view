package com.namviet.vtvtravel.view.f3.deal.viewmodel;

import com.google.gson.Gson;
import com.namviet.vtvtravel.api.Param;
import com.namviet.vtvtravel.api.TravelService;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.view.f3.deal.model.BlockResponse;
import com.namviet.vtvtravel.view.f3.deal.model.deal.DealResponse;
import com.namviet.vtvtravel.view.f3.deal.model.dealcampaign.DealCampaignDetail;
import com.namviet.vtvtravel.viewmodel.BaseViewModel;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit2.HttpException;

public class DealViewModel extends BaseViewModel {
    String data = "// 20211027091205\n" +
            "// https://api-testing.vtvtravel.vn/ctkm\n" +
            "\n" +
            "{\n" +
            "  \"status\": \"success\",\n" +
            "  \"code\": \"SUCCESS\",\n" +
            "  \"message\": \"\",\n" +
            "  \"data\": {\n" +
            "    \"menus\": {\n" +
            "      \"menu_ctkm\": [\n" +
            "        {\n" +
            "          \"id\": \"61727bd0b44b1559ee63bf54\",\n" +
            "          \"code\": \"CTKM_ACCUMULATION\",\n" +
            "          \"code_type\": \"CTKM_ACCUMULATION\",\n" +
            "          \"name\": \"Tích lũy nhận quà\",\n" +
            "          \"icon_url\": null,\n" +
            "          \"icon_enable_url\": null,\n" +
            "          \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "          \"category_id\": \"\",\n" +
            "          \"html_icon\": \"\",\n" +
            "          \"weight\": 0,\n" +
            "          \"banner_url\": null,\n" +
            "          \"short_description\": \"\",\n" +
            "          \"children\": [\n" +
            "            {\n" +
            "              \"id\": \"617281e306038662da5fa994\",\n" +
            "              \"code\": \"CTKM_ACCUMULATION_HOT\",\n" +
            "              \"code_type\": \"CTKM_ACCUMULATION_HOT\",\n" +
            "              \"name\": \"Khối hot\",\n" +
            "              \"icon_url\": null,\n" +
            "              \"icon_enable_url\": null,\n" +
            "              \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "              \"category_id\": \"\",\n" +
            "              \"html_icon\": \"\",\n" +
            "              \"weight\": 0,\n" +
            "              \"banner_url\": null,\n" +
            "              \"short_description\": \"\",\n" +
            "              \"children\": [\n" +
            "                {\n" +
            "                  \"id\": \"61728e7d18e8c62d5236d2a4\",\n" +
            "                  \"code\": \"CTKM_ACCUMULATION_HOT_CENTER\",\n" +
            "                  \"code_type\": \"CTKM_ACCUMULATION_HOT_CENTER\",\n" +
            "                  \"name\": \"Chơi gì\",\n" +
            "                  \"icon_url\": null,\n" +
            "                  \"icon_enable_url\": null,\n" +
            "                  \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                  \"category_id\": \"\",\n" +
            "                  \"html_icon\": \"\",\n" +
            "                  \"weight\": 0,\n" +
            "                  \"banner_url\": null,\n" +
            "                  \"short_description\": \"\",\n" +
            "                  \"children\": [\n" +
            "                    {\n" +
            "                      \"id\": \"6172d1161c29c438783c25f4\",\n" +
            "                      \"code\": \"CTKM_ACCUMULATION_HOT_CENTER_UPCOMING\",\n" +
            "                      \"code_type\": \"CTKM_UPCOMING\",\n" +
            "                      \"name\": \"SapDienRa\",\n" +
            "                      \"icon_url\": null,\n" +
            "                      \"icon_enable_url\": null,\n" +
            "                      \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                      \"category_id\": \"\",\n" +
            "                      \"html_icon\": \"\",\n" +
            "                      \"weight\": 0,\n" +
            "                      \"banner_url\": null,\n" +
            "                      \"short_description\": \"\",\n" +
            "                      \"children\": [\n" +
            "                        \n" +
            "                      ]\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"id\": \"6172d06b44c4ee31bb386314\",\n" +
            "                      \"code\": \"CTKM_ACCUMULATION_HOT_CENTER_RUNNING\",\n" +
            "                      \"code_type\": \"CTKM_RUNNING\",\n" +
            "                      \"name\": \"DangDienRa\",\n" +
            "                      \"icon_url\": null,\n" +
            "                      \"icon_enable_url\": null,\n" +
            "                      \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                      \"category_id\": \"\",\n" +
            "                      \"html_icon\": \"\",\n" +
            "                      \"weight\": 0,\n" +
            "                      \"banner_url\": null,\n" +
            "                      \"short_description\": \"\",\n" +
            "                      \"children\": [\n" +
            "                        \n" +
            "                      ]\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"id\": \"6172cf1c8bf61578335330f5\",\n" +
            "                      \"code\": \"CTKM_ACCUMULATION_HOT_CENTER_SLIDE\",\n" +
            "                      \"code_type\": \"CTKM_SLIDE\",\n" +
            "                      \"name\": \"Slide\",\n" +
            "                      \"icon_url\": null,\n" +
            "                      \"icon_enable_url\": null,\n" +
            "                      \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                      \"category_id\": \"\",\n" +
            "                      \"html_icon\": \"\",\n" +
            "                      \"weight\": 0,\n" +
            "                      \"banner_url\": null,\n" +
            "                      \"short_description\": \"\",\n" +
            "                      \"children\": [\n" +
            "                        \n" +
            "                      ]\n" +
            "                    }\n" +
            "                  ]\n" +
            "                },\n" +
            "                {\n" +
            "                  \"id\": \"617287cdda8a5530953a4e44\",\n" +
            "                  \"code\": \"CTKM_ACCUMULATION_HOT_RESTAURANT\",\n" +
            "                  \"code_type\": \"CTKM_ACCUMULATION_HOT_RESTAURANT\",\n" +
            "                  \"name\": \"Ăn gì\",\n" +
            "                  \"icon_url\": null,\n" +
            "                  \"icon_enable_url\": null,\n" +
            "                  \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                  \"category_id\": \"\",\n" +
            "                  \"html_icon\": \"\",\n" +
            "                  \"weight\": 0,\n" +
            "                  \"banner_url\": null,\n" +
            "                  \"short_description\": \"\",\n" +
            "                  \"children\": [\n" +
            "                    {\n" +
            "                      \"id\": \"6172d12c93671d344809e924\",\n" +
            "                      \"code\": \"CTKM_ACCUMULATION_HOT_RESTAURANT_UPCOMING\",\n" +
            "                      \"code_type\": \"CTKM_UPCOMING\",\n" +
            "                      \"name\": \"SapDienRa\",\n" +
            "                      \"icon_url\": null,\n" +
            "                      \"icon_enable_url\": null,\n" +
            "                      \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                      \"category_id\": \"\",\n" +
            "                      \"html_icon\": \"\",\n" +
            "                      \"weight\": 0,\n" +
            "                      \"banner_url\": null,\n" +
            "                      \"short_description\": \"\",\n" +
            "                      \"children\": [\n" +
            "                        \n" +
            "                      ]\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"id\": \"6172d01706699d29eb0ba904\",\n" +
            "                      \"code\": \"CTKM_ACCUMULATION_HOT_RESTAURANT_RUNNING\",\n" +
            "                      \"code_type\": \"CTKM_RUNNING\",\n" +
            "                      \"name\": \"DangDienRa\",\n" +
            "                      \"icon_url\": null,\n" +
            "                      \"icon_enable_url\": null,\n" +
            "                      \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                      \"category_id\": \"\",\n" +
            "                      \"html_icon\": \"\",\n" +
            "                      \"weight\": 0,\n" +
            "                      \"banner_url\": null,\n" +
            "                      \"short_description\": \"\",\n" +
            "                      \"children\": [\n" +
            "                        \n" +
            "                      ]\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"id\": \"6172cfa43955196cfc6cbdd5\",\n" +
            "                      \"code\": \"CTKM_ACCUMULATION_HOT_RESTAURANT_SLIDE\",\n" +
            "                      \"code_type\": \"CTKM_SLIDE\",\n" +
            "                      \"name\": \"Slide\",\n" +
            "                      \"icon_url\": null,\n" +
            "                      \"icon_enable_url\": null,\n" +
            "                      \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                      \"category_id\": \"\",\n" +
            "                      \"html_icon\": \"\",\n" +
            "                      \"weight\": 0,\n" +
            "                      \"banner_url\": null,\n" +
            "                      \"short_description\": \"\",\n" +
            "                      \"children\": [\n" +
            "                        \n" +
            "                      ]\n" +
            "                    }\n" +
            "                  ]\n" +
            "                },\n" +
            "                {\n" +
            "                  \"id\": \"6172849ea5bd0351a55e7924\",\n" +
            "                  \"code\": \"CTKM_ACCUMULATION_HOT_HOTEL\",\n" +
            "                  \"code_type\": \"CTKM_ACCUMULATION_HOT_HOTEL\",\n" +
            "                  \"name\": \"Ở đâu\",\n" +
            "                  \"icon_url\": null,\n" +
            "                  \"icon_enable_url\": null,\n" +
            "                  \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                  \"category_id\": \"\",\n" +
            "                  \"html_icon\": \"\",\n" +
            "                  \"weight\": 0,\n" +
            "                  \"banner_url\": null,\n" +
            "                  \"short_description\": \"\",\n" +
            "                  \"children\": [\n" +
            "                    {\n" +
            "                      \"id\": \"6172d1006caa2408f65a7044\",\n" +
            "                      \"code\": \"CTKM_ACCUMULATION_HOT_HOTEL_UPCOMING\",\n" +
            "                      \"code_type\": \"CTKM_UPCOMING\",\n" +
            "                      \"name\": \"SapDienRa\",\n" +
            "                      \"icon_url\": null,\n" +
            "                      \"icon_enable_url\": null,\n" +
            "                      \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                      \"category_id\": \"\",\n" +
            "                      \"html_icon\": \"\",\n" +
            "                      \"weight\": 0,\n" +
            "                      \"banner_url\": null,\n" +
            "                      \"short_description\": \"\",\n" +
            "                      \"children\": [\n" +
            "                        \n" +
            "                      ]\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"id\": \"6172d0519181240cad2b1ac4\",\n" +
            "                      \"code\": \"CTKM_ACCUMULATION_HOT_HOTEL_RUNNING\",\n" +
            "                      \"code_type\": \"CTKM_RUNNING\",\n" +
            "                      \"name\": \"DangDienRa\",\n" +
            "                      \"icon_url\": null,\n" +
            "                      \"icon_enable_url\": null,\n" +
            "                      \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                      \"category_id\": \"\",\n" +
            "                      \"html_icon\": \"\",\n" +
            "                      \"weight\": 0,\n" +
            "                      \"banner_url\": null,\n" +
            "                      \"short_description\": \"\",\n" +
            "                      \"children\": [\n" +
            "                        \n" +
            "                      ]\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"id\": \"6172cf8ac355b10042128595\",\n" +
            "                      \"code\": \"CTKM_ACCUMULATION_HOT_HOTEL_SLIDE\",\n" +
            "                      \"code_type\": \"CTKM_SLIDE\",\n" +
            "                      \"name\": \"Slide\",\n" +
            "                      \"icon_url\": null,\n" +
            "                      \"icon_enable_url\": null,\n" +
            "                      \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                      \"category_id\": \"\",\n" +
            "                      \"html_icon\": \"\",\n" +
            "                      \"weight\": 0,\n" +
            "                      \"banner_url\": null,\n" +
            "                      \"short_description\": \"\",\n" +
            "                      \"children\": [\n" +
            "                        \n" +
            "                      ]\n" +
            "                    }\n" +
            "                  ]\n" +
            "                },\n" +
            "                {\n" +
            "                  \"id\": \"617282a397b99256c22b9a54\",\n" +
            "                  \"code\": \"CTKM_ACCUMULATION_HOT_PLACE\",\n" +
            "                  \"code_type\": \"CTKM_ACCUMULATION_HOT_PLACE\",\n" +
            "                  \"name\": \"Đi đâu\",\n" +
            "                  \"icon_url\": null,\n" +
            "                  \"icon_enable_url\": null,\n" +
            "                  \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                  \"category_id\": \"\",\n" +
            "                  \"html_icon\": \"\",\n" +
            "                  \"weight\": 0,\n" +
            "                  \"banner_url\": null,\n" +
            "                  \"short_description\": \"\",\n" +
            "                  \"children\": [\n" +
            "                    {\n" +
            "                      \"id\": \"6172d0e8a89ee359f10cc4b4\",\n" +
            "                      \"code\": \"CTKM_ACCUMULATION_HOT_PLACE_UPCOMING\",\n" +
            "                      \"code_type\": \"CTKM_UPCOMING\",\n" +
            "                      \"name\": \"SapDienRa\",\n" +
            "                      \"icon_url\": null,\n" +
            "                      \"icon_enable_url\": null,\n" +
            "                      \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                      \"category_id\": \"\",\n" +
            "                      \"html_icon\": \"\",\n" +
            "                      \"weight\": 0,\n" +
            "                      \"banner_url\": null,\n" +
            "                      \"short_description\": \"\",\n" +
            "                      \"children\": [\n" +
            "                        \n" +
            "                      ]\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"id\": \"6172d08338ea2546bc5186e4\",\n" +
            "                      \"code\": \"CTKM_ACCUMULATION_HOT_PLACE_RUNNING\",\n" +
            "                      \"code_type\": \"CTKM_RUNNING\",\n" +
            "                      \"name\": \"DangDienRa\",\n" +
            "                      \"icon_url\": null,\n" +
            "                      \"icon_enable_url\": null,\n" +
            "                      \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                      \"category_id\": \"\",\n" +
            "                      \"html_icon\": \"\",\n" +
            "                      \"weight\": 0,\n" +
            "                      \"banner_url\": null,\n" +
            "                      \"short_description\": \"\",\n" +
            "                      \"children\": [\n" +
            "                        \n" +
            "                      ]\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"id\": \"6172cf3d411b26725f0d82b4\",\n" +
            "                      \"code\": \"CTKM_ACCUMULATION_HOT_PLACE_SLIDE\",\n" +
            "                      \"code_type\": \"CTKM_SLIDE\",\n" +
            "                      \"name\": \"Slide\",\n" +
            "                      \"icon_url\": null,\n" +
            "                      \"icon_enable_url\": null,\n" +
            "                      \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                      \"category_id\": \"\",\n" +
            "                      \"html_icon\": \"\",\n" +
            "                      \"weight\": 0,\n" +
            "                      \"banner_url\": null,\n" +
            "                      \"short_description\": \"\",\n" +
            "                      \"children\": [\n" +
            "                        \n" +
            "                      ]\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              ]\n" +
            "            },\n" +
            "            {\n" +
            "              \"id\": \"6172d1db7c2bbe6852489424\",\n" +
            "              \"code\": \"CTKM_ACCUMULATION_HINTBLOCK\",\n" +
            "              \"code_type\": \"CTKM_ACCUMULATION_HINTBLOCK\",\n" +
            "              \"name\": \"Khối gợi ý\",\n" +
            "              \"icon_url\": null,\n" +
            "              \"icon_enable_url\": null,\n" +
            "              \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "              \"category_id\": \"\",\n" +
            "              \"html_icon\": \"\",\n" +
            "              \"weight\": 1,\n" +
            "              \"banner_url\": null,\n" +
            "              \"short_description\": \"\",\n" +
            "              \"children\": [\n" +
            "                {\n" +
            "                  \"id\": \"6172d2c11152e275711ab9a4\",\n" +
            "                  \"code\": \"CTKM_ACCUMULATION_HINTBLOCK_HOTEL\",\n" +
            "                  \"code_type\": \"CTKM_ACCUMULATION_HINTBLOCK_HOTEL\",\n" +
            "                  \"name\": \"Ở đâu\",\n" +
            "                  \"icon_url\": null,\n" +
            "                  \"icon_enable_url\": null,\n" +
            "                  \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                  \"category_id\": \"\",\n" +
            "                  \"html_icon\": \"\",\n" +
            "                  \"weight\": 0,\n" +
            "                  \"banner_url\": null,\n" +
            "                  \"short_description\": \"\",\n" +
            "                  \"children\": [\n" +
            "                    \n" +
            "                  ]\n" +
            "                },\n" +
            "                {\n" +
            "                  \"id\": \"6172d2a6255617269f2b1524\",\n" +
            "                  \"code\": \"CTKM_ACCUMULATION_HINTBLOCK_PLACE\",\n" +
            "                  \"code_type\": \"CTKM_ACCUMULATION_HINTBLOCK_PLACE\",\n" +
            "                  \"name\": \"ĐI đâu\",\n" +
            "                  \"icon_url\": null,\n" +
            "                  \"icon_enable_url\": null,\n" +
            "                  \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                  \"category_id\": \"\",\n" +
            "                  \"html_icon\": \"\",\n" +
            "                  \"weight\": 0,\n" +
            "                  \"banner_url\": null,\n" +
            "                  \"short_description\": \"\",\n" +
            "                  \"children\": [\n" +
            "                    \n" +
            "                  ]\n" +
            "                },\n" +
            "                {\n" +
            "                  \"id\": \"6172d28e15b6fb4d593beb45\",\n" +
            "                  \"code\": \"CTKM_ACCUMULATION_HINTBLOCK_CENTER\",\n" +
            "                  \"code_type\": \"CTKM_ACCUMULATION_HINTBLOCK_CENTER\",\n" +
            "                  \"name\": \"Chơi gì\",\n" +
            "                  \"icon_url\": null,\n" +
            "                  \"icon_enable_url\": null,\n" +
            "                  \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                  \"category_id\": \"\",\n" +
            "                  \"html_icon\": \"\",\n" +
            "                  \"weight\": 0,\n" +
            "                  \"banner_url\": null,\n" +
            "                  \"short_description\": \"\",\n" +
            "                  \"children\": [\n" +
            "                    \n" +
            "                  ]\n" +
            "                },\n" +
            "                {\n" +
            "                  \"id\": \"6172d273bb16a4069d48a404\",\n" +
            "                  \"code\": \"CTKM_ACCUMULATION_HINTBLOCK_RESTAURANT\",\n" +
            "                  \"code_type\": \"CTKM_ACCUMULATION_HINTBLOCK_RESTAURANT\",\n" +
            "                  \"name\": \"Ăn gì\",\n" +
            "                  \"icon_url\": null,\n" +
            "                  \"icon_enable_url\": null,\n" +
            "                  \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                  \"category_id\": \"\",\n" +
            "                  \"html_icon\": \"\",\n" +
            "                  \"weight\": 0,\n" +
            "                  \"banner_url\": null,\n" +
            "                  \"short_description\": \"\",\n" +
            "                  \"children\": [\n" +
            "                    \n" +
            "                  ]\n" +
            "                }\n" +
            "              ]\n" +
            "            }\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"id\": \"61727c8b4db8cb377338ad55\",\n" +
            "          \"code\": \"HUNTING_CTKM\",\n" +
            "          \"code_type\": \"HUNTING_CTKM\",\n" +
            "          \"name\": \"Săn quà\",\n" +
            "          \"icon_url\": null,\n" +
            "          \"icon_enable_url\": null,\n" +
            "          \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "          \"category_id\": \"\",\n" +
            "          \"html_icon\": \"\",\n" +
            "          \"weight\": 1,\n" +
            "          \"banner_url\": null,\n" +
            "          \"short_description\": \"\",\n" +
            "          \"children\": [\n" +
            "            {\n" +
            "              \"id\": \"6172d3e269702819685801d4\",\n" +
            "              \"code\": \"CTKM_HUNTING_HOT\",\n" +
            "              \"code_type\": \"CTKM_HUNTING_HOT\",\n" +
            "              \"name\": \"Khối hot\",\n" +
            "              \"icon_url\": null,\n" +
            "              \"icon_enable_url\": null,\n" +
            "              \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "              \"category_id\": \"\",\n" +
            "              \"html_icon\": \"\",\n" +
            "              \"weight\": 0,\n" +
            "              \"banner_url\": null,\n" +
            "              \"short_description\": \"\",\n" +
            "              \"children\": [\n" +
            "                {\n" +
            "                  \"id\": \"6172d5a418b5a931d30cbb75\",\n" +
            "                  \"code\": \"CTKM_HUNTING_HOT_CENTER\",\n" +
            "                  \"code_type\": \"CTKM_HUNTING_HOT_CENTER\",\n" +
            "                  \"name\": \"Chơi gì\",\n" +
            "                  \"icon_url\": null,\n" +
            "                  \"icon_enable_url\": null,\n" +
            "                  \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                  \"category_id\": \"\",\n" +
            "                  \"html_icon\": \"\",\n" +
            "                  \"weight\": 0,\n" +
            "                  \"banner_url\": null,\n" +
            "                  \"short_description\": \"\",\n" +
            "                  \"children\": [\n" +
            "                    {\n" +
            "                      \"id\": \"6172d69bd21e22395a715c74\",\n" +
            "                      \"code\": \"CTKM_HUNTING_HOT_CENTER_SLIDE\",\n" +
            "                      \"code_type\": \"CTKM_SLIDE\",\n" +
            "                      \"name\": \"Slide\",\n" +
            "                      \"icon_url\": null,\n" +
            "                      \"icon_enable_url\": null,\n" +
            "                      \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                      \"category_id\": \"\",\n" +
            "                      \"html_icon\": \"\",\n" +
            "                      \"weight\": 0,\n" +
            "                      \"banner_url\": null,\n" +
            "                      \"short_description\": \"\",\n" +
            "                      \"children\": [\n" +
            "                        \n" +
            "                      ]\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"id\": \"6172d678118c1c22bb0728e4\",\n" +
            "                      \"code\": \"CTKM_HUNTING_HOT_CENTER_RUNNING\",\n" +
            "                      \"code_type\": \"CTKM_RUNNING\",\n" +
            "                      \"name\": \"DangDienRa\",\n" +
            "                      \"icon_url\": null,\n" +
            "                      \"icon_enable_url\": null,\n" +
            "                      \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                      \"category_id\": \"\",\n" +
            "                      \"html_icon\": \"\",\n" +
            "                      \"weight\": 0,\n" +
            "                      \"banner_url\": null,\n" +
            "                      \"short_description\": \"\",\n" +
            "                      \"children\": [\n" +
            "                        \n" +
            "                      ]\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"id\": \"6172d6533371202dc34a1834\",\n" +
            "                      \"code\": \"CTKM_HUNTING_HOT_CENTER_UPCOMING\",\n" +
            "                      \"code_type\": \"CTKM_UPCOMING\",\n" +
            "                      \"name\": \"SapDienRa\",\n" +
            "                      \"icon_url\": null,\n" +
            "                      \"icon_enable_url\": null,\n" +
            "                      \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                      \"category_id\": \"\",\n" +
            "                      \"html_icon\": \"\",\n" +
            "                      \"weight\": 0,\n" +
            "                      \"banner_url\": null,\n" +
            "                      \"short_description\": \"\",\n" +
            "                      \"children\": [\n" +
            "                        \n" +
            "                      ]\n" +
            "                    }\n" +
            "                  ]\n" +
            "                },\n" +
            "                {\n" +
            "                  \"id\": \"6172d586107b89720c356794\",\n" +
            "                  \"code\": \"CTKM_HUNTING_HOT_PLACE\",\n" +
            "                  \"code_type\": \"CTKM_HUNTING_HOT_PLACE\",\n" +
            "                  \"name\": \"Đi đâu\",\n" +
            "                  \"icon_url\": null,\n" +
            "                  \"icon_enable_url\": null,\n" +
            "                  \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                  \"category_id\": \"\",\n" +
            "                  \"html_icon\": \"\",\n" +
            "                  \"weight\": 0,\n" +
            "                  \"banner_url\": null,\n" +
            "                  \"short_description\": \"\",\n" +
            "                  \"children\": [\n" +
            "                    {\n" +
            "                      \"id\": \"6172d6e9f6e0ea34523d3404\",\n" +
            "                      \"code\": \"CTKM_HUNTING_HOT_PLACE_UPCOMING\",\n" +
            "                      \"code_type\": \"CTKM_UPCOMING\",\n" +
            "                      \"name\": \"SapDienRa\",\n" +
            "                      \"icon_url\": null,\n" +
            "                      \"icon_enable_url\": null,\n" +
            "                      \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                      \"category_id\": \"\",\n" +
            "                      \"html_icon\": \"\",\n" +
            "                      \"weight\": 0,\n" +
            "                      \"banner_url\": null,\n" +
            "                      \"short_description\": \"\",\n" +
            "                      \"children\": [\n" +
            "                        \n" +
            "                      ]\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"id\": \"6172d6d44775bf3b2e762ad4\",\n" +
            "                      \"code\": \"CTKM_HUNTING_HOT_PLACE_RUNNING\",\n" +
            "                      \"code_type\": \"CTKM_RUNNING\",\n" +
            "                      \"name\": \"DangDienRa\",\n" +
            "                      \"icon_url\": null,\n" +
            "                      \"icon_enable_url\": null,\n" +
            "                      \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                      \"category_id\": \"\",\n" +
            "                      \"html_icon\": \"\",\n" +
            "                      \"weight\": 0,\n" +
            "                      \"banner_url\": null,\n" +
            "                      \"short_description\": \"\",\n" +
            "                      \"children\": [\n" +
            "                        \n" +
            "                      ]\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"id\": \"6172d6bc94e2cf741e1a9134\",\n" +
            "                      \"code\": \"CTKM_HUNTING_HOT_PLACE_SLIDE\",\n" +
            "                      \"code_type\": \"CTKM_SLIDE\",\n" +
            "                      \"name\": \"Slide\",\n" +
            "                      \"icon_url\": null,\n" +
            "                      \"icon_enable_url\": null,\n" +
            "                      \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                      \"category_id\": \"\",\n" +
            "                      \"html_icon\": \"\",\n" +
            "                      \"weight\": 0,\n" +
            "                      \"banner_url\": null,\n" +
            "                      \"short_description\": \"\",\n" +
            "                      \"children\": [\n" +
            "                        \n" +
            "                      ]\n" +
            "                    }\n" +
            "                  ]\n" +
            "                },\n" +
            "                {\n" +
            "                  \"id\": \"6172d567afcb545413443e84\",\n" +
            "                  \"code\": \"CTKM_HUNTING_HOT_HOTEL\",\n" +
            "                  \"code_type\": \"CTKM_HUNTING_HOT_HOTEL\",\n" +
            "                  \"name\": \"Ở đâu\",\n" +
            "                  \"icon_url\": null,\n" +
            "                  \"icon_enable_url\": null,\n" +
            "                  \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                  \"category_id\": \"\",\n" +
            "                  \"html_icon\": \"\",\n" +
            "                  \"weight\": 0,\n" +
            "                  \"banner_url\": null,\n" +
            "                  \"short_description\": \"\",\n" +
            "                  \"children\": [\n" +
            "                    {\n" +
            "                      \"id\": \"6172d733b6b9ed5db1347d54\",\n" +
            "                      \"code\": \"CTKM_HUNTING_HOT_HOTEL_SLIDE\",\n" +
            "                      \"code_type\": \"CTKM_SLIDE\",\n" +
            "                      \"name\": \"Slide\",\n" +
            "                      \"icon_url\": null,\n" +
            "                      \"icon_enable_url\": null,\n" +
            "                      \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                      \"category_id\": \"\",\n" +
            "                      \"html_icon\": \"\",\n" +
            "                      \"weight\": 0,\n" +
            "                      \"banner_url\": null,\n" +
            "                      \"short_description\": \"\",\n" +
            "                      \"children\": [\n" +
            "                        \n" +
            "                      ]\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"id\": \"6172d71eb4254c463c152194\",\n" +
            "                      \"code\": \"CTKM_HUNTING_HOT_HOTEL_RUNNING\",\n" +
            "                      \"code_type\": \"CTKM_RUNNING\",\n" +
            "                      \"name\": \"DangDienRa\",\n" +
            "                      \"icon_url\": null,\n" +
            "                      \"icon_enable_url\": null,\n" +
            "                      \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                      \"category_id\": \"\",\n" +
            "                      \"html_icon\": \"\",\n" +
            "                      \"weight\": 0,\n" +
            "                      \"banner_url\": null,\n" +
            "                      \"short_description\": \"\",\n" +
            "                      \"children\": [\n" +
            "                        \n" +
            "                      ]\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"id\": \"6172d702f1450d06426fdd24\",\n" +
            "                      \"code\": \"CTKM_HUNTING_HOT_HOTEL_UPCOMING\",\n" +
            "                      \"code_type\": \"CTKM_UPCOMING\",\n" +
            "                      \"name\": \"SapDienRa\",\n" +
            "                      \"icon_url\": null,\n" +
            "                      \"icon_enable_url\": null,\n" +
            "                      \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                      \"category_id\": \"\",\n" +
            "                      \"html_icon\": \"\",\n" +
            "                      \"weight\": 0,\n" +
            "                      \"banner_url\": null,\n" +
            "                      \"short_description\": \"\",\n" +
            "                      \"children\": [\n" +
            "                        \n" +
            "                      ]\n" +
            "                    }\n" +
            "                  ]\n" +
            "                },\n" +
            "                {\n" +
            "                  \"id\": \"6172d54c0f4f41248a20b7c4\",\n" +
            "                  \"code\": \"CTKM_HUNTING_HOT_RESTAURANT\",\n" +
            "                  \"code_type\": \"CTKM_HUNTING_HOT_RESTAURANT\",\n" +
            "                  \"name\": \"Ăn gì\",\n" +
            "                  \"icon_url\": null,\n" +
            "                  \"icon_enable_url\": null,\n" +
            "                  \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                  \"category_id\": \"\",\n" +
            "                  \"html_icon\": \"\",\n" +
            "                  \"weight\": 0,\n" +
            "                  \"banner_url\": null,\n" +
            "                  \"short_description\": \"\",\n" +
            "                  \"children\": [\n" +
            "                    {\n" +
            "                      \"id\": \"6172d782e28c5e2fd32f11b4\",\n" +
            "                      \"code\": \"CTKM_HUNTING_HOT_RESTAURANT_UPCOMING\",\n" +
            "                      \"code_type\": \"CTKM_UPCOMING\",\n" +
            "                      \"name\": \"SapDienRa\",\n" +
            "                      \"icon_url\": null,\n" +
            "                      \"icon_enable_url\": null,\n" +
            "                      \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                      \"category_id\": \"\",\n" +
            "                      \"html_icon\": \"\",\n" +
            "                      \"weight\": 0,\n" +
            "                      \"banner_url\": null,\n" +
            "                      \"short_description\": \"\",\n" +
            "                      \"children\": [\n" +
            "                        \n" +
            "                      ]\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"id\": \"6172d76872a17541776a5c94\",\n" +
            "                      \"code\": \"CTKM_HUNTING_HOT_RESTAURANT_RUNNING\",\n" +
            "                      \"code_type\": \"CTKM_RUNNING\",\n" +
            "                      \"name\": \"DangDienRa\",\n" +
            "                      \"icon_url\": null,\n" +
            "                      \"icon_enable_url\": null,\n" +
            "                      \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                      \"category_id\": \"\",\n" +
            "                      \"html_icon\": \"\",\n" +
            "                      \"weight\": 0,\n" +
            "                      \"banner_url\": null,\n" +
            "                      \"short_description\": \"\",\n" +
            "                      \"children\": [\n" +
            "                        \n" +
            "                      ]\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"id\": \"6172d7504a3f21227354e834\",\n" +
            "                      \"code\": \"CTKM_HUNTING_HOT_RESTAURANT_SLIDE\",\n" +
            "                      \"code_type\": \"CTKM_SLIDE\",\n" +
            "                      \"name\": \"Slide\",\n" +
            "                      \"icon_url\": null,\n" +
            "                      \"icon_enable_url\": null,\n" +
            "                      \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                      \"category_id\": \"\",\n" +
            "                      \"html_icon\": \"\",\n" +
            "                      \"weight\": 0,\n" +
            "                      \"banner_url\": null,\n" +
            "                      \"short_description\": \"\",\n" +
            "                      \"children\": [\n" +
            "                        \n" +
            "                      ]\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              ]\n" +
            "            },\n" +
            "            {\n" +
            "              \"id\": \"6172d384798bb5716a3028f4\",\n" +
            "              \"code\": \"CTKM_HUNTING_HINTBLOCK\",\n" +
            "              \"code_type\": \"CTKM_HUNTING_HINTBLOCK\",\n" +
            "              \"name\": \"Khối gợi ý\",\n" +
            "              \"icon_url\": null,\n" +
            "              \"icon_enable_url\": null,\n" +
            "              \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "              \"category_id\": \"\",\n" +
            "              \"html_icon\": \"\",\n" +
            "              \"weight\": 1,\n" +
            "              \"banner_url\": null,\n" +
            "              \"short_description\": \"\",\n" +
            "              \"children\": [\n" +
            "                {\n" +
            "                  \"id\": \"6172d52727def238aa6078e4\",\n" +
            "                  \"code\": \"CTKM_HUNTING_HINTBLOCK_RESTAURANT\",\n" +
            "                  \"code_type\": \"CTKM_HUNTING_HINTBLOCK_RESTAURANT\",\n" +
            "                  \"name\": \"Ăn gì\",\n" +
            "                  \"icon_url\": null,\n" +
            "                  \"icon_enable_url\": null,\n" +
            "                  \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                  \"category_id\": \"\",\n" +
            "                  \"html_icon\": \"\",\n" +
            "                  \"weight\": 0,\n" +
            "                  \"banner_url\": null,\n" +
            "                  \"short_description\": \"\",\n" +
            "                  \"children\": [\n" +
            "                    \n" +
            "                  ]\n" +
            "                },\n" +
            "                {\n" +
            "                  \"id\": \"6172d50af75d8b5ee55d6dd4\",\n" +
            "                  \"code\": \"CTKM_HUNTING_HINTBLOCK_CENTER\",\n" +
            "                  \"code_type\": \"CTKM_HUNTING_HINTBLOCK_CENTER\",\n" +
            "                  \"name\": \"Chơi gì\",\n" +
            "                  \"icon_url\": null,\n" +
            "                  \"icon_enable_url\": null,\n" +
            "                  \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                  \"category_id\": \"\",\n" +
            "                  \"html_icon\": \"\",\n" +
            "                  \"weight\": 0,\n" +
            "                  \"banner_url\": null,\n" +
            "                  \"short_description\": \"\",\n" +
            "                  \"children\": [\n" +
            "                    \n" +
            "                  ]\n" +
            "                },\n" +
            "                {\n" +
            "                  \"id\": \"6172d4f003d115051609c225\",\n" +
            "                  \"code\": \"CTKM_HUNTING_HINTBLOCK_PLACE\",\n" +
            "                  \"code_type\": \"CTKM_HUNTING_HINTBLOCK_PLACE\",\n" +
            "                  \"name\": \"ĐI đâu\",\n" +
            "                  \"icon_url\": null,\n" +
            "                  \"icon_enable_url\": null,\n" +
            "                  \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                  \"category_id\": \"\",\n" +
            "                  \"html_icon\": \"\",\n" +
            "                  \"weight\": 0,\n" +
            "                  \"banner_url\": null,\n" +
            "                  \"short_description\": \"\",\n" +
            "                  \"children\": [\n" +
            "                    \n" +
            "                  ]\n" +
            "                },\n" +
            "                {\n" +
            "                  \"id\": \"6172d4d56088d821153cec15\",\n" +
            "                  \"code\": \"CTKM_HUNTING_HINTBLOCK_HOTEL\",\n" +
            "                  \"code_type\": \"CTKM_HUNTING_HINTBLOCK_HOTEL\",\n" +
            "                  \"name\": \"Ở đâu\",\n" +
            "                  \"icon_url\": null,\n" +
            "                  \"icon_enable_url\": null,\n" +
            "                  \"link\": \"https://api-testing.vtvtravel.vn\",\n" +
            "                  \"category_id\": \"\",\n" +
            "                  \"html_icon\": \"\",\n" +
            "                  \"weight\": 0,\n" +
            "                  \"banner_url\": null,\n" +
            "                  \"short_description\": \"\",\n" +
            "                  \"children\": [\n" +
            "                    \n" +
            "                  ]\n" +
            "                }\n" +
            "              ]\n" +
            "            }\n" +
            "          ]\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  }\n" +
            "}";
    public BlockResponse getResponseFake(){
        Gson gson = new Gson();
        BlockResponse blockResponse = gson.fromJson(data, BlockResponse.class);
        return blockResponse;
    }

    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    public void getDealBlock() {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelService();
        Map<String, Object> queryMap = Param.getDefault();
        Disposable disposable = newsService.getBlockDeal(queryMap)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BlockResponse>() {
                    @Override
                    public void accept(BlockResponse blockResponse) throws Exception {
                        if (blockResponse != null) {
                            requestSuccess(blockResponse);
                        } else {
                            requestSuccess(null);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        requestFailed(throwable);
                    }
                });

        compositeDisposable.add(disposable);
    }
    public void getDeal(String url) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        Disposable disposable = newsService.getDeal(url)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DealResponse>() {
                    @Override
                    public void accept(DealResponse blockResponse) throws Exception {
                        if (blockResponse != null) {
                            requestSuccess(blockResponse);
                        } else {
                            requestSuccess(null);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        requestFailed(throwable);
                    }
                });

        compositeDisposable.add(disposable);
    }

    public void getDealCampaignDetail(String url) {
        MyApplication myApplication = MyApplication.getInstance();
        TravelService newsService = myApplication.getTravelServiceAcc();
        Disposable disposable = newsService.getDealCampaignDetail(url)
                .subscribeOn(myApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DealCampaignDetail>() {
                    @Override
                    public void accept(DealCampaignDetail blockResponse) throws Exception {
                        if (blockResponse != null) {
                            requestSuccess(blockResponse);
                        } else {
                            requestSuccess(null);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        requestFailed(throwable);
                    }
                });

        compositeDisposable.add(disposable);
    }

    private void requestSuccess(Object object) {
        setChanged();
        notifyObservers(object);
    }

    private void requestFailed(Throwable throwable) {
        try {
            onLoadFail();
        } catch (Exception e) {

        }

        try {
            HttpException error = (HttpException) throwable;
            String errorBody = error.response().errorBody().string();
            ErrorResponse errorResponse = new Gson().fromJson(errorBody, ErrorResponse.class);
            setChanged();
            notifyObservers(errorResponse);
        } catch (Exception e) {
            setChanged();
            notifyObservers();
        }
    }
}
