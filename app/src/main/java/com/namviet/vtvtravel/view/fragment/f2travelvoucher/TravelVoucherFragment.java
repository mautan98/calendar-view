package com.namviet.vtvtravel.view.fragment.f2travelvoucher;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.travelvoucher.TravelVoucherAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentTravelVoucherBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.response.f2travelvoucher.CategoryVoucherResponse;
import com.namviet.vtvtravel.response.f2travelvoucher.CheckCanReceiver;
import com.namviet.vtvtravel.response.f2travelvoucher.ListVoucherResponse;
import com.namviet.vtvtravel.response.f2travelvoucher.RankVoucherResponse;
import com.namviet.vtvtravel.response.f2travelvoucher.RegionVoucherResponse;
import com.namviet.vtvtravel.response.f2travelvoucher.SortClass;
import com.namviet.vtvtravel.tracking.TrackingAnalytic;
import com.namviet.vtvtravel.view.f2.VQMMWebviewActivity;
import com.namviet.vtvtravel.viewmodel.f2travelvoucher.TravelVoucherViewModel;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


// MÀn này là màn chung của màn kho khuyến mãi và màn voucher của tôi
// MÀn kho khuyến mãi có hai trạng thái
// Một trạng thái là vào trưc tiếp   isStore = true và isFromVip = false
// Một trạng thái là vào từ luồng nâng cấp vip, isStore  = true và isFromVip = true
public class TravelVoucherFragment extends BaseFragment<F2FragmentTravelVoucherBinding> implements Observer {
    private TravelVoucherAdapter travelVoucherAdapter;
    private TravelVoucherViewModel viewModel;
    private SortClass sortClass;
    private int page = 0;
    private final List<ListVoucherResponse.Data.Voucher> vouchers = new ArrayList<>();
    private String regionId = "";
    private String categoryId = "";
    private String sortId = "0";
    private String memberRankId = "";

    private boolean isStore;
    private boolean isFromRegVip;
    private CategoryDialog categoryDialog;
    private String positionIdCategory;

    public void setPositionIdCategory(String positionCategory) {
        this.positionIdCategory = positionCategory;
    }

    @SuppressLint("ValidFragment")
    public TravelVoucherFragment(boolean isStore, boolean isFromRegVip) {
        this.isStore = isStore;
        this.isFromRegVip = isFromRegVip;
    }

    public TravelVoucherFragment() {
    }

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_travel_voucher;
    }

    @Override
    public void initView() {
        viewModel = new TravelVoucherViewModel();
        getBinding().setTravelVoucherViewModel(viewModel);
        viewModel.addObserver(this);

        if (isStore) {
            getBinding().btnFilterBottom.setVisibility(View.VISIBLE);
            getBinding().btnSort.setVisibility(View.GONE);
            getBinding().layoutRankFilter.setVisibility(View.VISIBLE);

//            getBinding().imgOutDateVoucherHaveButton.setVisibility(View.VISIBLE);
//            getBinding().imgOutDateVoucher.setVisibility(View.GONE);
            getBinding().layoutNoData.setVisibility(View.VISIBLE);
        } else {
            getBinding().btnFilterBottom.setVisibility(View.GONE);
            getBinding().layoutRankFilter.setVisibility(View.GONE);
            getBinding().btnSort.setVisibility(View.VISIBLE);

//            getBinding().imgOutDateVoucherHaveButton.setVisibility(View.GONE);
//            getBinding().imgOutDateVoucher.setVisibility(View.VISIBLE);
            getBinding().layoutNoData.setVisibility(View.VISIBLE);
        }

        setTitle();

        setDistance();
        if (isFromRegVip) {
            //Nếu user đi từ nâng cấp Vip thành công thì gọi service check có thể nhận quà hay là nhận 3 lượt quay
            showLoading();
            viewModel.checkCanReceiver();
        }
        Log.e("Travelllllllllllllllll", "0");
        if ("0".equals(positionIdCategory))
            getVoucher("VTVTRAVEL", sortId, regionId, memberRankId, categoryId, page, isFromRegVip);
        viewModel.getCategoryVoucher();
        viewModel.getRegionVoucher();
        viewModel.getRank();
    }

    private void initCategoriesDialog() {
        categoryDialog = new CategoryDialog(categoryVoucherResponse, new CategoryDialog.DoneSort() {
            @Override
            public void onDoneSort(CategoryVoucherResponse.Category category) {
                getBinding().tvCategoryFilterName.setText(category.getName());
                categoryId = category.getId();
                clearDataRCL();
                getVoucher("VTVTRAVEL", sortId, regionId, memberRankId, categoryId, page, isFromRegVip);
            }
        }, "Danh mục", CategoryDialog.Type.VOUCHER_TYPE);
        categoryId = positionIdCategory;
        CategoryVoucherResponse.Category category;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            category = categoryVoucherResponse.getData().stream()
                    .filter(category1 -> positionIdCategory.equals(category1.getId()))
                    .findAny()
                    .orElse(null);
        } else {
            category = getCategoryFromList(positionIdCategory, categoryVoucherResponse.getData());
        }
        if (!positionIdCategory.isEmpty()) {
            categoryVoucherResponse.getData().get(0).setChecked(false);
            categoryVoucherResponse.getData().get(Integer.parseInt(positionIdCategory)).setChecked(true);
            categoryDialog.getCategoryFilterAdapter().notifyDataSetChanged();
        }
        categoryDialog.getCategoryFilterAdapter().getClickItem().onClickItem(category);
    }

    private CategoryVoucherResponse.Category getCategoryFromList(String id, List<CategoryVoucherResponse.Category> categoryList) {
        for (CategoryVoucherResponse.Category category : categoryList) {
            if (category.getId().equals(id)) {
                return category;
            }
        }
        return null;
    }

    private void setTitle() {
        if (isStore) {
            getBinding().tvTitle.setText("Khuyến mãi");
        } else {
            getBinding().tvTitle.setText("Voucher du lịch");
        }
    }

    private void getVoucher(String service, String sort, String regionId, String memberRankId, String categoryId, int page, boolean isFromRegVip) {
        if (isStore) {
            if(isFromRegVip) {
                viewModel.getOwnedVoucherStore(service, sort, regionId, memberRankId, categoryId, page);
            }else {
                viewModel.getOwnedVoucherStoreNonToken(service, sort, regionId, memberRankId, categoryId, page);
            }
        } else {
            viewModel.getOwnedVoucher(service, sort, regionId, memberRankId, categoryId, page);
        }

        try {
            TrackingAnalytic.postEvent(TrackingAnalytic.FILTER_PROMOTION, TrackingAnalytic.getDefault("Travel Voucher", "Danh sách voucher").setScreen_class(this.getClass().getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData() {
        travelVoucherAdapter = new TravelVoucherAdapter(vouchers, mActivity, new TravelVoucherAdapter.ClickItem() {
            @Override
            public void onClickItem(ListVoucherResponse.Data.Voucher voucher) {
                addFragment(new TravelVoucherDetailFragment(voucher, isStore, isFromRegVip));
            }
        });
        getBinding().rclContent.setAdapter(travelVoucherAdapter);
    }

    @Override
    public void inject() {

    }

    @Override
    public void setClickListener() {
        getBinding().imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onBackPressed();
            }
        });

        getBinding().btnRegionFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    RegionDialog regionDialog = new RegionDialog(regionVoucherResponse, new RegionDialog.DoneSort() {
                        @Override
                        public void onDoneSort(RegionVoucherResponse.Category category) {
                            getBinding().tvRegionFilterName.setText(category.getName());
                            regionId = category.getId();
                            clearDataRCL();
                            Log.e("Travelllllllllllllllll", "1");
                            getVoucher("VTVTRAVEL", sortId, regionId, memberRankId, categoryId, page, isFromRegVip);
                        }
                    }, "Khu vực", RegionDialog.Type.VOUCHER_TYPE);
                    regionDialog.show(mActivity.getSupportFragmentManager(), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        getBinding().btnCategoryFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                try {
//                    CategoryDialog categoryDialog = new CategoryDialog(categoryVoucherResponse, new CategoryDialog.DoneSort() {
//                        @Override
//                        public void onDoneSort(CategoryVoucherResponse.Category category) {
//                            getBinding().tvCategoryFilterName.setText(category.getName());
//                            categoryId = category.getId();
//                            clearDataRCL();
//                            Log.e("Travelllllllllllllllll", "2");
//                            getVoucher("VTVTRAVEL", sortId, regionId, memberRankId, categoryId, page, isFromRegVip);
//                        }
//                    }, "Danh mục", CategoryDialog.Type.VOUCHER_TYPE);
//                    categoryDialog.show(mActivity.getSupportFragmentManager(), null);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                if (categoryDialog != null)
                    categoryDialog.show(mActivity.getSupportFragmentManager(), null);
            }
        });

        getBinding().btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SortDialog sortDialog = new SortDialog(sortClass, new SortDialog.DoneSort() {
                    @Override
                    public void onDoneSort(SortClass.Sort sort) {
                        try {
                            sortId = sort.getValue();
                            clearDataRCL();
                            Log.e("Travelllllllllllllllll", "3");
                            getVoucher("VTVTRAVEL", sortId, regionId, memberRankId, categoryId, page, isFromRegVip);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, "Sắp xếp", SortDialog.Type.VOUCHER_TYPE);
                sortDialog.show(mActivity.getSupportFragmentManager(), null);
            }
        });

        getBinding().btnFilterBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SortDialog sortDialog = new SortDialog(sortClass, new SortDialog.DoneSort() {
                    @Override
                    public void onDoneSort(SortClass.Sort sort) {
                        try {
                            sortId = sort.getValue();
                            clearDataRCL();
                            Log.e("Travelllllllllllllllll", "3");
                            getVoucher("VTVTRAVEL", sortId, regionId, memberRankId, categoryId, page, isFromRegVip);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, "Sắp xếp", SortDialog.Type.VOUCHER_TYPE);
                sortDialog.show(mActivity.getSupportFragmentManager(), null);
            }
        });


        getBinding().rclContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                Log.e("newState", newState+"");
                if (newState == RecyclerView.SCROLL_STATE_IDLE && !recyclerView.canScrollVertically(1)) {
                    Log.e("Travelllllllllllllllll", "4");
                    getVoucher("VTVTRAVEL", sortId, regionId, memberRankId, categoryId, page, isFromRegVip);
                }
            }
        });

        getBinding().btnRankFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    RankDialog rankDialog = new RankDialog(rankVoucherResponse, new RankDialog.DoneSort() {
                        @Override
                        public void onDoneSort(RankVoucherResponse.Rank rank) {
                            getBinding().tvRankFilterName.setText(rank.getName());
                            memberRankId = rank.getId();
                            clearDataRCL();
                            Log.e("Travelllllllllllllllll", "5");
                            getVoucher("VTVTRAVEL", sortId, regionId, memberRankId, categoryId, page, isFromRegVip);
                        }
                    }, "Hạng Hội viên");
                    rankDialog.show(mActivity.getSupportFragmentManager(), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void clearDataRCL() {
        page = 0;
        vouchers.clear();
        travelVoucherAdapter.notifyDataSetChanged();
    }

    @Override
    public void setObserver() {

    }

    private CategoryVoucherResponse categoryVoucherResponse;
    private RegionVoucherResponse regionVoucherResponse;
    private RankVoucherResponse rankVoucherResponse;

    @Override
    public void update(Observable observable, Object o) {
        hideLoading();
        if (observable instanceof TravelVoucherViewModel && null != o) {
            if (o instanceof CategoryVoucherResponse) {
                categoryVoucherResponse = (CategoryVoucherResponse) o;
                CategoryVoucherResponse.Category category = new CategoryVoucherResponse().new Category();
                category.setChecked(true);
                category.setName("Tất cả");
                category.setId("");
                categoryVoucherResponse.getData().add(0, category);
                initCategoriesDialog();
            } else if (o instanceof RankVoucherResponse) {
                rankVoucherResponse = (RankVoucherResponse) o;
                RankVoucherResponse.Rank rank = new RankVoucherResponse().new Rank();
                rank.setSelected(true);
                rank.setName("Tất cả");
                rank.setId("");
                rankVoucherResponse.getData().add(0, rank);
            } else if (o instanceof CheckCanReceiver) {
                hideLoading();
                CheckCanReceiver checkCanReceiver = (CheckCanReceiver) o;
                CanReceiverGiftDialog canReceiverGiftDialog = CanReceiverGiftDialog.newInstance(checkCanReceiver.isData(), new CanReceiverGiftDialog.ClickButton() {
                    @Override
                    public void onClickButton(boolean isCheck) {
                        if(isCheck){

                        }else {
                            VQMMWebviewActivity.startScreen(mActivity, "");
                        }
                    }
                });
                canReceiverGiftDialog.show(mActivity.getSupportFragmentManager(), null);
                canReceiverGiftDialog.setCancelable(false);
            } else if (o instanceof ListVoucherResponse) {

                ListVoucherResponse listVoucherResponse = (ListVoucherResponse) o;
                vouchers.clear();
                if (listVoucherResponse.getData().getVouchers() != null && listVoucherResponse.getData().getVouchers().size() > 0) {
                    page = page + 1;
                    vouchers.addAll(listVoucherResponse.getData().getVouchers());
                }
                travelVoucherAdapter.notifyDataSetChanged();

                try {
                    if(vouchers.size() > 0){
//                        getBinding().imgOutDateVoucherHaveButton.setVisibility(View.GONE);
//                        getBinding().imgOutDateVoucher.setVisibility(View.GONE);
                        getBinding().layoutNoData.setVisibility(View.GONE);
                    }else {
//                        if(isStore){
//                            getBinding().imgOutDateVoucherHaveButton.setVisibility(View.VISIBLE);
//                            getBinding().imgOutDateVoucher.setVisibility(View.GONE);
//                        }else {
//                            getBinding().imgOutDateVoucherHaveButton.setVisibility(View.GONE);
//                            getBinding().imgOutDateVoucher.setVisibility(View.VISIBLE);
//                        }
                        getBinding().layoutNoData.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
//                    getBinding().imgOutDateVoucherHaveButton.setVisibility(View.GONE);
//                    getBinding().imgOutDateVoucher.setVisibility(View.GONE);
                    getBinding().layoutNoData.setVisibility(View.GONE);
                }

            } else if (o instanceof RegionVoucherResponse) {
                regionVoucherResponse = (RegionVoucherResponse) o;
                RegionVoucherResponse.Category category = new RegionVoucherResponse().new Category();
                category.setSelected(true);
                category.setName("Tất cả");
                category.setId("");
                regionVoucherResponse.getData().add(0, category);
            } else if (o instanceof ErrorResponse) {
                ErrorResponse responseError = (ErrorResponse) o;

                if(responseError.getCodeToSplitCase().equals("checkCanReceiver")){
                    try {
                        AlreadyReceiverDialog alreadyReceiverDialog = AlreadyReceiverDialog.newInstance("ALREADY_RECEIVE_VOUCHER");
                        alreadyReceiverDialog.show(mActivity.getSupportFragmentManager(), "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                try {
//                    ((BaseActivityNew) mActivity).showWarning(responseError.getMessage());
                } catch (Exception e) {

                }
            }

        }
    }

    public void setDistance() {
        sortClass = new Gson().fromJson(loadJSONFromAsset(), SortClass.class);
    }


    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = mActivity.getAssets().open("sortvoucher.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public void setScreenTitle() {
        super.setScreenTitle();
        setDataScreen(TrackingAnalytic.ScreenCode.VOUCHER_STORE, TrackingAnalytic.ScreenTitle.VOUCHER_STORE);
    }
}
