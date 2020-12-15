package com.kolnetworks.koln.ui.bindtag;

import com.kolnetworks.koln.R;
import com.kolnetworks.koln.base.BaseActivity;

import static com.kolnetworks.koln.ui.login.LoginActivity.HAS_IG_URL;

public class BindTagActivity extends BaseActivity {

    private boolean hasIgUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_tag;
    }

    @Override
    protected void initData() {
        initViewModel();
        hasIgUrl = getIntent().getBooleanExtra(HAS_IG_URL, false);

    }

    private void initViewModel() {

    }

    @Override
    protected void configViews() {

    }

    public Boolean hasIgUrl(){
        return hasIgUrl;
    }
}