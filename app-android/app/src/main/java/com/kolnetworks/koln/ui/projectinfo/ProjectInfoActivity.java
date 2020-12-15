package com.kolnetworks.koln.ui.projectinfo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.jakewharton.rxbinding3.view.RxView;
import com.kolnetworks.koln.R;
import com.kolnetworks.koln.adapter.ViewPagerAdapter;
import com.kolnetworks.koln.base.BaseActivity;
import com.kolnetworks.koln.data.SPApi;
import com.kolnetworks.koln.model.ProjectData;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kolnetworks.koln.adapter.CardViewListAdapter.PROJECT;
import static com.kolnetworks.koln.adapter.CardViewListAdapter.PROJECT_TYPE;

public class ProjectInfoActivity extends BaseActivity {

    @BindView(R.id.imgPhoto)
    ImageView imgPhoto;
    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.tabBar)
    ConstraintLayout tabBar;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.mask)
    ImageView mask;
    @BindView(R.id.Chat)
    ImageView Chat;
    @BindView(R.id.btnChat)
    FrameLayout btnChat;

    private ProjectData data;
    private int type;
    private ProjectViewModel viewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_project_info;
    }

    @Override
    protected void initData() {
        initViewModel();
        Intent intent = getIntent();
        type = intent.getIntExtra(PROJECT_TYPE, 0);
        data = intent.getParcelableExtra(PROJECT);
        if (data != null && !TextUtils.isEmpty(data.getUuid_board())) {
            viewModel.loadBoards(data.getUuid_board());
        }

        Log.d("JJJ", "UUID : " +data.getUuid() );
        if (data != null && !TextUtils.isEmpty(data.getMessenger())) {
            btnChat.setVisibility(View.VISIBLE);
        } else {
            btnChat.setVisibility(View.GONE);
        }
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(ProjectViewModel.class);

    }

    @Override
    protected void configViews() {
        tabs.addTab(tabs.newTab().setText("基本"));
//        tabs.addTab(tabs.newTab().setText("諮詢"));

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ProjectBasicFragment());
//        adapter.addFragment(new ProjectAskFragment());

        if (SPApi.isManager()){
            tabs.addTab(tabs.newTab().setText("名單"));
            adapter.addFragment(new NameListFragment());
        }

        viewpager.setAdapter(adapter);

        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewpager));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        addDisposable(RxView.clicks(btnBack)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(v -> {
                    finish();
                }));

        addDisposable(RxView.clicks(btnChat)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(v -> {
                    Log.d("JJJ", "data.getMessenger() : " +data.getMessenger() );
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.getMessenger()));
                    startActivity(browserIntent);
                }));

        if (data.getPhoto() != null) {
            Glide.with(this).load(data.getPhoto()).into(imgPhoto);
        }
    }

    public ProjectData getProjectData() {
        return data;
    }

    public int getType() {
        return type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}