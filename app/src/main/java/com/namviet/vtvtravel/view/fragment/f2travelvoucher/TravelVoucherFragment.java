package com.namviet.vtvtravel.view.fragment.f2travelvoucher;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.travelvoucher.TravelVoucherAdapter;
import com.namviet.vtvtravel.databinding.F2FragmentTravelVoucherBinding;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.f2travelvoucher.CategoryVoucherResponse;
import com.namviet.vtvtravel.response.f2travelvoucher.ListVoucherResponse;
import com.namviet.vtvtravel.response.f2travelvoucher.RankVoucherResponse;
import com.namviet.vtvtravel.response.f2travelvoucher.RegionVoucherResponse;
import com.namviet.vtvtravel.response.f2travelvoucher.SortClass;
import com.namviet.vtvtravel.viewmodel.f2travelvoucher.TravelVoucherViewModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class TravelVoucherFragment extends BaseFragment<F2FragmentTravelVoucherBinding> implements Observer {
    private TravelVoucherAdapter travelVoucherAdapter;
    private TravelVoucherViewModel viewModel;
    private SortClass sortClass;
    private int page = 0;
    private List<ListVoucherResponse.Data.Voucher> vouchers = new ArrayList<>();
    private String regionId = "";
    private String categoryId = "";
    private String sortId = "0";
    private String memberRankId = "";

    private boolean isStore;

    @SuppressLint("ValidFragment")
    public TravelVoucherFragment(boolean isStore) {
        this.isStore = isStore;
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
        viewModel.addObserver(this);

        if (isStore) {
            getBinding().layoutRankFilter.setVisibility(View.VISIBLE);
        } else {
            getBinding().layoutRankFilter.setVisibility(View.GONE);
        }

        setDistance();
        getVoucher("VTVTRAVEL", sortId, regionId, memberRankId, categoryId, page);
        viewModel.getCategoryVoucher();
        viewModel.getRegionVoucher();
        viewModel.getRank();
    }

    private void getVoucher(String service, String sort, String regionId, String memberRankId, String categoryId, int page) {
        if (isStore) {
            viewModel.getOwnedVoucherStore(service, sort, regionId, memberRankId, categoryId, page);
        } else {
            viewModel.getOwnedVoucher(service, sort, regionId, memberRankId, categoryId, page);
        }
    }

    @Override
    public void initData() {
        travelVoucherAdapter = new TravelVoucherAdapter(vouchers, mActivity, new TravelVoucherAdapter.ClickItem() {
            @Override
            public void onClickItem(ListVoucherResponse.Data.Voucher voucher) {
                addFragment(new TravelVoucherDetailFragment(voucher, isStore));
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
                            getVoucher("VTVTRAVEL", sortId, regionId, memberRankId, categoryId, page);
                        }
                    }, "Khu vực");
                    regionDialog.show(mActivity.getSupportFragmentManager(), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        getBinding().btnCategoryFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    CategoryDialog categoryDialog = new CategoryDialog(categoryVoucherResponse, new CategoryDialog.DoneSort() {
                        @Override
                        public void onDoneSort(CategoryVoucherResponse.Category category) {
                            getBinding().tvCategoryFilterName.setText(category.getName());
                            categoryId = category.getId();
                            clearDataRCL();
                            getVoucher("VTVTRAVEL", sortId, regionId, memberRankId, categoryId, page);
                        }
                    }, "Danh mục");
                    categoryDialog.show(mActivity.getSupportFragmentManager(), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                            getVoucher("VTVTRAVEL", sortId, regionId, memberRankId, categoryId, page);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, "Sắp xếp");
                sortDialog.show(mActivity.getSupportFragmentManager(), null);
            }
        });


        getBinding().rclContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    getVoucher("VTVTRAVEL", sortId, regionId, memberRankId, categoryId, page);
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
                            getVoucher("VTVTRAVEL", sortId, regionId, memberRankId, categoryId, page);
                        }
                    }, "Hạng hội viên");
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
            } else if (o instanceof RankVoucherResponse) {
                rankVoucherResponse = (RankVoucherResponse) o;
                RankVoucherResponse.Rank rank = new RankVoucherResponse().new Rank();
                rank.setSelected(true);
                rank.setName("Tất cả");
                rank.setId("");
                rankVoucherResponse.getData().add(0, rank);
            } else if (o instanceof ListVoucherResponse) {

                ListVoucherResponse listVoucherResponse = (ListVoucherResponse) o;

                if (listVoucherResponse.getData().getVouchers() != null && listVoucherResponse.getData().getVouchers().size() > 0) {
                    page = page + 1;
                    vouchers.addAll(listVoucherResponse.getData().getVouchers());
                    travelVoucherAdapter.notifyDataSetChanged();
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
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
