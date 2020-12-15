package com.kolnetworks.koln.ui.notifications;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.rxbinding3.view.RxView;
import com.kolnetworks.koln.R;
import com.kolnetworks.koln.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationsActivity extends BaseActivity {


    @BindView(R.id.btnArrow)
    ImageView btnArrow;
    @BindView(R.id.appBar)
    ConstraintLayout appBar;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.rvNotifications)
    RecyclerView rvNotifications;
    @BindView(R.id.bottom)
    CardView bottom;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notifications;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void configViews() {
        addDisposable(RxView.clicks(btnArrow)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(v -> {
                    finish();
                }));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}