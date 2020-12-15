package com.kolnetworks.koln.ui.findproject;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.kolnetworks.koln.Constant;
import com.kolnetworks.koln.R;
import com.kolnetworks.koln.adapter.ViewPagerAdapter;
import com.kolnetworks.koln.base.BaseFragment;
import com.kolnetworks.koln.ui.cardview.CardViewFragment;
import com.kolnetworks.koln.ui.userinfo.UserInfoActivity;
import com.kolnetworks.koln.ui.main.MainViewModel;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class FindFragment extends BaseFragment {


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

    public FindFragment() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_around;
    }

    @Override
    protected void initData() {
        initViewModel();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

//        viewModel.loadAroundPair();
//        viewModel.loadUser();
        viewModel.getMldPhoto().observe(this, url->{
            if (!TextUtils.isEmpty(url)){
                Glide.with(this).load(url).into(imgPhoto);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void configViews() {

        title.setText(getString(R.string.nav_around));

        tabs.addTab(tabs.newTab().setText("申請合作")); // 原逛逛
        tabs.addTab(tabs.newTab().setText("專屬邀約")); // 原專屬

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new CardViewFragment(Constant.TYPE_FOUND_APPLY_COOPERATION));
        adapter.addFragment(new CardViewFragment(Constant.TYPE_FOUND_UNIQ));
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
    }

    @OnClick(R.id.imgPhoto)
    public void onViewClicked() {
        Intent intent = new Intent();
        intent.setClass(requireActivity(), UserInfoActivity.class);
        startActivity(intent);
    }
}