package com.kolnetworks.koln.ui.userinfo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.kolnetworks.koln.ui.login.LoginActivity;
import com.kolnetworks.koln.util.ToastUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoActivity extends BaseActivity {

    UserViewModel viewModel;

    @BindView(R.id.btnArrow)
    ImageView btnArrow;
    @BindView(R.id.imgPhoto)
    CircleImageView imgPhoto;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.btnLogOut)
    ImageView btnLogOut;
    @BindView(R.id.appBar)
    ConstraintLayout appBar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.tabBar)
    ConstraintLayout tabBar;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initData() {
        initViewModel();
    }

    @Override
    protected void configViews() {

        tabs.addTab(tabs.newTab().setText("基本"));
        tabs.addTab(tabs.newTab().setText("平台"));
        tabs.addTab(tabs.newTab().setText("推廣碼"));

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new UserBasicFragment());
        adapter.addFragment(new UserStageFragment());
        adapter.addFragment(new PushCodeFragment());

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

        title.setText(SPApi.getUName());

        addDisposable(RxView.clicks(btnArrow)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(v -> {
                    finish();
                }));

        addDisposable(RxView.clicks(btnLogOut)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(v -> {
                    SPApi.putToken("");
                    Intent intent = new Intent();
                    intent.setClass(this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }));
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        showProgressDialog("");
        viewModel.loadUser();

        viewModel.getMldApiError().observe(this, errorMsg -> {
            dismissProgressDialog();
            ToastUtil.showFailedImgInToast(errorMsg);
        });

        viewModel.getMldUser().observe(this, bean -> {
            dismissProgressDialog();
            if (!TextUtils.isEmpty(bean.getPhoto())){
                Glide.with(this).load(bean.getPhoto()).into(imgPhoto);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}