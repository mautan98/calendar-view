package com.namviet.vtvtravel.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baseapp.menu.SlideMenu;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.listener.TravelSelectListener;
import com.namviet.vtvtravel.model.CategoryHome;
import com.daimajia.slider.library.ItemGroup;
import com.namviet.vtvtravel.model.News;
import com.namviet.vtvtravel.model.Video;
import com.namviet.vtvtravel.ultils.FirebaseAnalytic;
import com.namviet.vtvtravel.view.MainActivity;
import com.rbrooks.indefinitepagerindicator.IndefinitePagerIndicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.BaseCategory> {
    private Context mContext;

    private List<CategoryHome> categoryHomeList;
    private MainActivity mainActivity;

    public HomeCategoryAdapter(Context mContext) {
        this.mContext = mContext;
        mainActivity = (MainActivity) mContext;
        categoryHomeList = Collections.emptyList();
    }

    @Override
    public int getItemViewType(int position) {
        switch (categoryHomeList.get(position).getCode()) {
            case Constants.TypeItemHome.HIGHLIGHT:
                return R.layout.item_category_slide_three_home;
            case Constants.TypeItemHome.VIDEO:
                return R.layout.item_category_slide_two_home;
            case Constants.TypeItemHome.WHERE_GO:
                return R.layout.item_category_slide_two_home;
            case Constants.TypeItemHome.WHAT_PLAY:
                return R.layout.item_category_slide_three_home;
            case Constants.TypeItemHome.WHAT_EAT:
                return R.layout.item_category_slide_three_home;
            case Constants.TypeItemHome.WHERE_STAY:
                return R.layout.item_category_slide_three_home;
            case Constants.TypeItemHome.PHOTO_GALLERY:
                return R.layout.item_category_slide_two_home;
            case Constants.TypeItemHome.APP_NOTEBOOK_NEWS:
                return R.layout.item_category_voucher_home;
            case Constants.TypeItemHome.APP_VOUCHER:
                return R.layout.item_category_notebook_home;
            default:
                return R.layout.item_category_slide_three_home;
        }
    }

    @NonNull
    @Override
    public BaseCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(viewType, parent, false);
        if (viewType == R.layout.item_category_slide_two_home) {
            return new HomeSliderTwoHolder(v);
        } else if (viewType == R.layout.item_category_voucher_home) {
            return new HomeVoucherHolder(v);
        } else if (viewType == R.layout.item_category_notebook_home) {
            return new HomeNotebookHolder(v);
        } else {
            return new HomeSliderThreeHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseCategory holder, int position) {
        holder.binItem(position);

    }

    @Override
    public int getItemCount() {
        return categoryHomeList == null ? 0 : categoryHomeList.size();
    }


    public class BaseCategory extends RecyclerView.ViewHolder {
        private TextView tvTitle;

        public BaseCategory(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }

        public void binItem(int position) {
            tvTitle.setText(categoryHomeList.get(position).getName());
        }

        public void setImageUrl(String ulrCs, ImageView image) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(com.daimajia.slider.library.R.drawable.img_placeholder);
            requestOptions.error(com.daimajia.slider.library.R.drawable.img_placeholder);
            Glide.with(mContext).setDefaultRequestOptions(requestOptions).load(ulrCs).thumbnail(0.2f).into(image);

        }
    }

    public class HomeSliderThreeHolder extends BaseCategory implements TravelSelectListener {

        private RecyclerView vpSlide;
        private IndefinitePagerIndicator indicator;

        public HomeSliderThreeHolder(View itemView) {
            super(itemView);
            vpSlide = itemView.findViewById(R.id.vpSlide);
            indicator = itemView.findViewById(R.id.vpIndicator);
            vpSlide.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            SnapHelper snapHelper = new LinearSnapHelper();
            snapHelper.attachToRecyclerView(vpSlide);

        }

        @Override
        public void binItem(int position) {
            super.binItem(position);
            List<Travel> travels = categoryHomeList.get(position).getTravelList();
            List<ItemGroup> groups = pagingList(travels, 3);
            CategoryThreeSlideHomeAdapter adapter = new CategoryThreeSlideHomeAdapter(mContext, groups);
            adapter.setTravelSelectListener(this);
            vpSlide.setAdapter(adapter);
            indicator.attachToRecyclerView(vpSlide);

            RecyclerView.OnScrollListener onScrollListener;
            switch (categoryHomeList.get(position).getName()) {
                case "Chơi Gì":
                    onScrollListener = new RecyclerView.OnScrollListener() {
                        boolean check = true;
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            if (dx > 0 && check) {
                                mainActivity.pushEvent(FirebaseAnalytic.SWIPE_HP_RIGHT_EVENT);
                                check = false;
                            } else {
                                mainActivity.pushEvent(FirebaseAnalytic.SWIPE_HP_LEFT_EVENT);
                                check = false;
                            }
                        }
                    };
                    break;
                case "Ăn Gì":
                    onScrollListener = new RecyclerView.OnScrollListener() {
                        boolean check = true;
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            if (dx > 0 && check) {
                                mainActivity.pushEvent(FirebaseAnalytic.SWIPE_HP_RIGHT_RESTAURANT);
                                check = false;
                            } else {
                                mainActivity.pushEvent(FirebaseAnalytic.SWIPE_HP_LEFT_RESTAURANT);
                                check = false;
                            }
                        }
                    };
                    break;
                case "Ở Đâu":
                    onScrollListener = new RecyclerView.OnScrollListener() {
                        boolean check = true;
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            if (dx > 0 && check) {
                                mainActivity.pushEvent(FirebaseAnalytic.SWIPE_HP_RIGHT_HOTEL);
                                check = false;
                            } else {
                                mainActivity.pushEvent(FirebaseAnalytic.SWIPE_HP_LEFT_HOTEL);
                                check = false;
                            }
                        }
                    };
                    break;
                case "Nổi Bật":
                    onScrollListener = new RecyclerView.OnScrollListener() {
                        boolean check = true;
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            if (dx > 0 && check) {
                                mainActivity.pushEvent(FirebaseAnalytic.SWIPE_HP_RIGHT_FEATURE);
                                check = false;
                            } else {
                                mainActivity.pushEvent(FirebaseAnalytic.SWIPE_HP_LEFT_FEATURE);
                                check = false;
                            }
                        }
                    };
                    break;
                default:
                    onScrollListener = new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                        }
                    };
                    break;
            }
            vpSlide.addOnScrollListener(onScrollListener);
        }

        @Override
        public void onSelectTravel(Travel travel) {
            goToDetail(travel);
        }
    }

    public class HomeSliderTwoHolder extends BaseCategory implements TravelSelectListener {

        private RecyclerView vpSlide;
        private IndefinitePagerIndicator indicator;

        public HomeSliderTwoHolder(View itemView) {
            super(itemView);
            vpSlide = itemView.findViewById(R.id.vpSlide);
            indicator = itemView.findViewById(R.id.vpIndicator);
            vpSlide.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            SnapHelper snapHelper = new LinearSnapHelper();
            snapHelper.attachToRecyclerView(vpSlide);
        }

        @Override
        public void binItem(int position) {
            super.binItem(position);
//            vpSlide.clearSlider();
            List<Travel> travels = categoryHomeList.get(position).getTravelList();
            List<ItemGroup> groups = pagingList(travels, 2);
            CategoryTwoSlideHomeAdapter adapter = new CategoryTwoSlideHomeAdapter(mContext, groups);
            adapter.setTravelSelectListener(this);
            vpSlide.setAdapter(adapter);
            indicator.attachToRecyclerView(vpSlide);
            RecyclerView.OnScrollListener onScrollListener;

            switch (categoryHomeList.get(position).getName()) {
                case "VIDEO":
                    onScrollListener = new RecyclerView.OnScrollListener() {
                        boolean check = true;
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            if (dx > 0 && check) {
                                mainActivity.pushEvent(FirebaseAnalytic.SWIPE_HP_RIGHT_VIDEO);
                                check = false;
                            } else {
                                mainActivity.pushEvent(FirebaseAnalytic.SWIPE_HP_LEFT_VIDEO);
                                check = false;
                            }
                        }
                    };
                    break;
                case "Đi Đâu":
                    onScrollListener = new RecyclerView.OnScrollListener() {
                        boolean check = true;
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            if (dx > 0 && check) {
                                mainActivity.pushEvent(FirebaseAnalytic.SWIPE_HP_RIGHT_DESTINATION);
                                check = false;
                            } else {
                                mainActivity.pushEvent(FirebaseAnalytic.SWIPE_HP_LEFT_DESTINATION);
                                check = false;
                            }
                        }
                    };
                    break;
                case "Góc Ảnh":
                    onScrollListener = new RecyclerView.OnScrollListener() {
                        boolean check = true;
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            if (dx > 0 && check) {
                                mainActivity.pushEvent(FirebaseAnalytic.SWIPE_HP_RIGHT_ALBUM);
                                check = false;
                            } else {
                                mainActivity.pushEvent(FirebaseAnalytic.SWIPE_HP_LEFT_ALBUM);
                                check = false;
                            }
                        }
                    };
                    break;
                default:
                    onScrollListener = new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                        }
                    };
                    break;
            }
            vpSlide.addOnScrollListener(onScrollListener);
        }

        @Override
        public void onSelectTravel(Travel travel) {
            goToDetail(travel);
        }
    }

    public class HomeVoucherHolder extends BaseCategory {
        private ImageView ivItem1;
        private TextView tvItem1;

        private ImageView ivItem2;
        private TextView tvItem2;

        public HomeVoucherHolder(View itemView) {
            super(itemView);
            ivItem1 = itemView.findViewById(R.id.ivItem1);
            ivItem2 = itemView.findViewById(R.id.ivItem2);
            tvItem1 = itemView.findViewById(R.id.tvItem1);
            tvItem2 = itemView.findViewById(R.id.tvItem2);
        }

        @Override
        public void binItem(int position) {
            super.binItem(position);
            final List<Travel> items = categoryHomeList.get(position).getTravelList();
            if (null != items && items.size() > 0) {
                setImageUrl(items.get(0).getLogo_url(), ivItem1);
                tvItem1.setText(items.get(0).getName());
                ivItem1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goToDetail(items.get(0));
                    }
                });
            }
            if (null != items && items.size() > 1) {
                setImageUrl(items.get(1).getLogo_url(), ivItem2);
                tvItem2.setText(items.get(1).getName());
                ivItem2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goToDetail(items.get(1));
                    }
                });
            }
        }
    }

    public class HomeNotebookHolder extends BaseCategory {
        private ImageView ivItem1;
        private ImageView ivItem2;

        public HomeNotebookHolder(View itemView) {
            super(itemView);
            ivItem1 = itemView.findViewById(R.id.ivItem1);
            ivItem2 = itemView.findViewById(R.id.ivItem2);
        }

        @Override
        public void binItem(int position) {
            super.binItem(position);
            final List<Travel> items = categoryHomeList.get(position).getTravelList();
            if (null != items && items.size() > 0) {
                setImageUrl(items.get(0).getLogo_url(), ivItem1);
                ivItem1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goToDetail(items.get(0));
                    }
                });
            }
            if (null != items && items.size() > 1) {
                setImageUrl(items.get(1).getLogo_url(), ivItem2);
                ivItem2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goToDetail(items.get(1));
                    }
                });
            }
        }
    }

    public void updateCategoryHomeList(final List<CategoryHome> categoryHomeList) {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setCategoryHomeList(categoryHomeList);
            }
        });

    }

    public void setCategoryHomeList(List<CategoryHome> categoryHomeList) {
        this.categoryHomeList = categoryHomeList;
        notifyDataSetChanged();
    }

    private List<ItemGroup> pagingList(List<Travel> data, int number) {
        if (null != data) {
            int surplus = data.size() % number;
            int page;
            List<ItemGroup> itemGroups = new ArrayList<>();
            if (surplus > 0) {
                page = data.size() / number + 1;
                if (page > 0) {
                    for (int i = 1; i <= page; i++) {
                        List<Travel> listOfGroup = new ArrayList<>();
                        int start = (i - 1) * number;
                        if (i < page) {
                            for (int j = start; j < start + number; j++) {
                                listOfGroup.add(data.get(j));
                            }
                        } else {
                            for (int k = start; k < data.size(); k++) {
                                listOfGroup.add(data.get(k));
                            }
                        }
                        itemGroups.add(new ItemGroup(listOfGroup));
                    }
                }
            } else {
                page = data.size() / number;
                if (page > 0) {
                    for (int i = 1; i <= page; i++) {
                        List<Travel> listOfGroup = new ArrayList<>();
                        int start = (i - 1) * number;
                        for (int j = start; j < start + number; j++) {
                            listOfGroup.add(data.get(j));
                        }
                        itemGroups.add(new ItemGroup(listOfGroup));
                    }
                }
            }
            return itemGroups;
        }
        return null;

    }

    public List<CategoryHome> getCategoryHomeList() {
        return categoryHomeList;
    }

    private void goToDetail(Travel travel) {
        Bundle bundle = new Bundle();
        if (travel.getContent_type().equals(Constants.TypePlace.videos)) {
            Video video = new Video(travel.getId(), travel.getDetail_link());
            bundle.putParcelable(Constants.IntentKey.KEY_FRAGMENT, video);
            mainActivity.setBundle(bundle);
            mainActivity.switchFragment(SlideMenu.MenuType.PLAYLIST_VIDEO_SCREEN);
        } else if (travel.getContent_type().equals(Constants.TypePlace.places)) {
            bundle.putParcelable(Constants.IntentKey.KEY_TRAVEL, travel);
            mainActivity.setBundle(bundle);
            mainActivity.switchFragment(SlideMenu.MenuType.DETAIL_WHERE_SCREEN);
        } else if (travel.getContent_type().equals(Constants.TypePlace.restaurants)) {
            bundle.putParcelable(Constants.IntentKey.KEY_TRAVEL, travel);
            mainActivity.setBundle(bundle);
            mainActivity.switchFragment(SlideMenu.MenuType.DETAIL_EAT_SCREEN);
        } else if (travel.getContent_type().equals(Constants.TypePlace.hotels)) {
            bundle.putParcelable(Constants.IntentKey.KEY_TRAVEL, travel);
            mainActivity.setBundle(bundle);
            mainActivity.switchFragment(SlideMenu.MenuType.DETAIL_WHERE_STAY_SCREEN);
        } else if (travel.getContent_type().equals(Constants.TypePlace.centers)) {
            bundle.putParcelable(Constants.IntentKey.KEY_TRAVEL, travel);
            mainActivity.setBundle(bundle);
            mainActivity.switchFragment(SlideMenu.MenuType.DETAIL_WHERE_SCREEN);
        } else if (travel.getContent_type().equals(Constants.TypePlace.news)) {
            News news = new News(travel.getId(), travel.getDetail_link(), travel.getContent_type());
            bundle.putParcelable(Constants.IntentKey.KEY_FRAGMENT, news);
            mainActivity.setBundle(bundle);
            mainActivity.switchFragment(SlideMenu.MenuType.DETAIL_WHERE_GO_NEWS_SCREEN);
        } else if (travel.getContent_type().equals(Constants.TypePlace.galleries)) {
            News news = new News(travel.getId(), travel.getDetail_link(), travel.getContent_type());
            bundle.putParcelable(Constants.IntentKey.KEY_FRAGMENT, news);
            mainActivity.setBundle(bundle);
            mainActivity.switchFragment(SlideMenu.MenuType.DETAIL_WHERE_GO_NEWS_SCREEN);
        } else if (travel.getContent_type().equals(Constants.TypePlace.vouchers)) {
            if (travel.getType().equals(Constants.TypePlace.link)) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(travel.getDetail_link()));
                mContext.startActivity(intent);
            } else {
                News news = new News(travel.getId(), travel.getDetail_link(), travel.getContent_type());
                bundle.putParcelable(Constants.IntentKey.KEY_FRAGMENT, news);
                mainActivity.setBundle(bundle);
                mainActivity.switchFragment(SlideMenu.MenuType.DETAIL_WHERE_GO_NEWS_SCREEN);
            }

        }
    }
}
