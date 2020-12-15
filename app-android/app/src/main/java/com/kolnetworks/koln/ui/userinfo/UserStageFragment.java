package com.kolnetworks.koln.ui.userinfo;

import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.jakewharton.rxbinding3.view.RxView;
import com.kolnetworks.koln.R;
import com.kolnetworks.koln.base.BaseFragment;
import com.kolnetworks.koln.ui.main.MainActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;


public class UserStageFragment extends BaseFragment {
    UserViewModel viewModel;
    @BindView(R.id.ig)
    TextView ig;
    @BindView(R.id.btnAddIg)
    TextView btnAddIg;
    @BindView(R.id.frameLayout2)
    FrameLayout frameLayout2;
    @BindView(R.id.etIg)
    EditText etIg;
    @BindView(R.id.frameLayout3)
    FrameLayout frameLayout3;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.tvIgPrice)
    TextView tvIgPrice;
    @BindView(R.id.fb)
    TextView fb;
    @BindView(R.id.btnAddFb)
    TextView btnAddFb;
    @BindView(R.id.frameLayout4)
    FrameLayout frameLayout4;
    @BindView(R.id.etFb)
    EditText etFb;
    @BindView(R.id.frameLayout5)
    FrameLayout frameLayout5;
    @BindView(R.id.tvFbPrice)
    TextView tvFbPrice;
    @BindView(R.id.yt)
    TextView yt;
    @BindView(R.id.btnAddYt)
    TextView btnAddYt;
    @BindView(R.id.frameLayout7)
    FrameLayout frameLayout7;
    @BindView(R.id.etYt)
    EditText etYt;
    @BindView(R.id.frameLayout8)
    FrameLayout frameLayout8;
    @BindView(R.id.tvYtPrice)
    TextView tvYtPrice;
    @BindView(R.id.bg)
    TextView bg;
    @BindView(R.id.btnAddBg)
    TextView btnAddBg;
    @BindView(R.id.frameLayout9)
    FrameLayout frameLayout9;
    @BindView(R.id.etBg)
    EditText etBg;
    @BindView(R.id.frameLayout10)
    FrameLayout frameLayout10;
    @BindView(R.id.tvBgPrice)
    TextView tvBgPrice;


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_user_stage;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void configViews() {
        viewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        viewModel.getMldUser().observe(this, bean -> {
            String igName = bean.getIg_url();
            if (!TextUtils.isEmpty(igName)) {
                String[] data = igName.split("/");

                etIg.setText(data[data.length - 1]);
                tvIgPrice.setText(bean.getIg_expect_price());
            }
            if (!TextUtils.isEmpty(bean.getFb_url())) {
                etFb.setText(bean.getFb_url());
                tvFbPrice.setText(bean.getFb_expect_price());
            }

            if (!TextUtils.isEmpty(bean.getYt_url())) {
                etYt.setText(bean.getYt_url());
                tvYtPrice.setText(bean.getYt_expect_price());
            }

            if (!TextUtils.isEmpty(bean.getBlog_url())) {
                etBg.setText(bean.getBlog_url());
                tvBgPrice.setText(bean.getBlog_expect_price());
            }
        });

        viewModel.getMldCanFinish().observe(this, canFinish -> {
            ((UserInfoActivity) requireActivity()).dismissProgressDialog();
        });

        //- 更新IG
        ((UserInfoActivity) requireActivity()).addDisposable(RxView.clicks(btnAddIg)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(v -> {
                    String igId = etIg.getText().toString();
                    if (!TextUtils.isEmpty(igId)) {
                        ((UserInfoActivity) requireActivity()).showProgressDialog("");
                        Log.d("JJJ", " update blog : " + igId);
                        viewModel.linkIg(igId);
                    }
                })
        );

        //- 更新FB
        ((UserInfoActivity) requireActivity()).addDisposable(RxView.clicks(btnAddFb)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(v -> {
                    String fbUrl = etFb.getText().toString();
                    Log.d("JJJ", "fb edit : " + fbUrl);
                    if (!TextUtils.isEmpty(fbUrl)) {
                        if (!fbUrl.toLowerCase().startsWith("https") &&
                                !fbUrl.toLowerCase().startsWith("http")) {
                            fbUrl = "https://" + fbUrl;
                        }
                        ((UserInfoActivity) requireActivity()).showProgressDialog("");
                        Log.d("JJJ", " update blog : " + fbUrl);
                        viewModel.linkFb(fbUrl);
                    }
                })
        );

        //- 更新YOUTUBE
        ((UserInfoActivity) requireActivity()).addDisposable(RxView.clicks(btnAddYt)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(v -> {
                    String ytUrl = etYt.getText().toString();
                    if (!TextUtils.isEmpty(ytUrl)) {
                        if (!ytUrl.toLowerCase().startsWith("https") &&
                                !ytUrl.toLowerCase().startsWith("http")) {
                            ytUrl = "https://" + ytUrl;
                        }
                        ((UserInfoActivity) requireActivity()).showProgressDialog("");
                        Log.d("JJJ", " update blog : " + ytUrl);
                        viewModel.linkYt(ytUrl);
                    }
                })
        );

        //- 更新BLOG
        ((UserInfoActivity) requireActivity()).addDisposable(RxView.clicks(btnAddBg)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(v -> {
                    String blogUrl = etBg.getText().toString();
                    if (!TextUtils.isEmpty(blogUrl)) {
                        if (!blogUrl.toLowerCase().startsWith("https") &&
                                !blogUrl.toLowerCase().startsWith("http")) {
                            blogUrl = "https://" + blogUrl;
                        }
                        ((UserInfoActivity) requireActivity()).showProgressDialog("");
                        Log.d("JJJ", " update blog : " + blogUrl);
                        viewModel.linkBlog(blogUrl);
                    }
                })
        );
    }
}