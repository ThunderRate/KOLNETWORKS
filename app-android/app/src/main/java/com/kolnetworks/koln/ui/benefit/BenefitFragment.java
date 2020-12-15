package com.kolnetworks.koln.ui.benefit;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.jakewharton.rxbinding3.view.RxView;
import com.kolnetworks.koln.R;
import com.kolnetworks.koln.adapter.ViewPagerAdapter;
import com.kolnetworks.koln.base.BaseFragment;
import com.kolnetworks.koln.ui.main.MainActivity;
import com.kolnetworks.koln.ui.main.MainViewModel;
import com.kolnetworks.koln.ui.userinfo.UserInfoActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class BenefitFragment extends BaseFragment {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.imgPhoto)
    CircleImageView imgPhoto;
    @BindView(R.id.appBar)
    ConstraintLayout appBar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.tabBar)
    ConstraintLayout tabBar;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private MainViewModel viewModel;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_benefit;
    }

    @Override
    protected void initData() {
        initViewModel();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        viewModel.loadCashFlow();

        viewModel.getMldPhoto().observe(this, url -> {
            if (!TextUtils.isEmpty(url)){
                Glide.with(this).load(url).into(imgPhoto);
            }
        });
    }

    @Override
    protected void configViews() {
        title.setText(R.string.nav_benefit);
        tabs.addTab(tabs.newTab().setText("總覽"));
        //tabs.addTab(tabs.newTab().setText("已撥款"));
        tabs.addTab(tabs.newTab().setText("帳務設定"));

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new OverAllFragment());
//        adapter.addFragment(new PayMoneyFragment());
        adapter.addFragment(new AccountSettingFragment());

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

        ((MainActivity) getActivity()).addDisposable(RxView.clicks(imgPhoto)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(v -> {
                    Intent intent = new Intent();
                    intent.setClass(requireActivity(), UserInfoActivity.class);
                    startActivity(intent);
                }));
    }
}