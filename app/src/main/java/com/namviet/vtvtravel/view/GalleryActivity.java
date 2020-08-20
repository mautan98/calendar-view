package com.namviet.vtvtravel.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.baseapp.activity.BaseActivity;
import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.adapter.ImageListRecyclerAdapter;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.model.CustomGallery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private TextView tvDone;
    private ImageView ivBack;

    String action;
    Handler handler;
    //    GalleryAdapter adapter;
    ImageListRecyclerAdapter imageListRecyclerAdapter;
    private HashMap<String, CustomGallery> imagesUri;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gallery;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        action = getIntent().getAction();
        if (action == null) {
            finish();
        }
        init();
    }


    private void init() {
        ivBack = findViewById(R.id.ivBack);
        recyclerView = findViewById(R.id.recyclerView);
        tvDone = findViewById(R.id.tvDone);
        ivBack.setOnClickListener(this);
        tvDone.setOnClickListener(this);
        handler = new Handler();
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        imageListRecyclerAdapter = new ImageListRecyclerAdapter(getApplicationContext());
        imageListRecyclerAdapter.setMultiplePick(true);
        recyclerView.setAdapter(imageListRecyclerAdapter);
        if (action.equalsIgnoreCase(Constants.Action.ACTION_MULTIPLE_PICK)) {
            imageListRecyclerAdapter.setMultiplePick(true);
        } else {
            imageListRecyclerAdapter.setMultiplePick(false);
        }

        imageListRecyclerAdapter.setEventListner(new ImageListRecyclerAdapter.EventListener() {
            @Override
            public void onItemClickListener(int position, ImageListRecyclerAdapter.VerticalItemHolder holder) {
                if (imageListRecyclerAdapter.isMultiSelected()) {
                    imageListRecyclerAdapter.changeSelection(holder, position);
                } else {
                    CustomGallery customGallery = imageListRecyclerAdapter.getItem(position);
                    Intent intent = new Intent();
                    intent.putExtra(Constants.IntentKey.ONE_PATH, customGallery);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

        new Thread() {

            @Override
            public void run() {
                Looper.prepare();
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        imageListRecyclerAdapter.addAll(getGalleryPhotos());
                        //checkImageStatus();
                    }
                });
                Looper.loop();
            }


        }.start();

    }

    private void checkImageStatus() {
    }


    private ArrayList<CustomGallery> getGalleryPhotos() {
        ArrayList<CustomGallery> galleryList = new ArrayList<CustomGallery>();

        try {
            final String[] columns = {MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media._ID};
            final String orderBy = MediaStore.Images.Media._ID;

            Cursor imagecursor = managedQuery(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
                    null, null, orderBy);

            if (imagecursor != null && imagecursor.getCount() > 0) {

                while (imagecursor.moveToNext()) {
                    CustomGallery item = new CustomGallery();

                    int dataColumnIndex = imagecursor
                            .getColumnIndex(MediaStore.Images.Media.DATA);

                    item.setSdcardPath(imagecursor.getString(dataColumnIndex));

                    galleryList.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // show newest photo at beginning of the list
        Collections.reverse(galleryList);
        return galleryList;
    }

    @Override
    public void onClick(View view) {
        if (view == ivBack) {
            finish();
        } else if (view == tvDone) {
            ArrayList<CustomGallery> selected = imageListRecyclerAdapter.getSelected();

            if (selected.size() > 10) {
                showMessage(getString(R.string.choose_over_photo));
            } else {
                Intent data = new Intent().putParcelableArrayListExtra(Constants.IntentKey.MUTI_PATH, selected);
                setResult(RESULT_OK, data);
                finish();
            }

        }
    }

    public void showMessage(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setMessage("" + message)
                .setPositiveButton(R.string.call, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
