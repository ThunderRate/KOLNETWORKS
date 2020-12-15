package com.kolnetworks.koln.ui.bindtag;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.jakewharton.rxbinding3.view.RxView;
import com.kolnetworks.koln.R;
import com.kolnetworks.koln.base.BaseFragment;
import com.kolnetworks.koln.util.ToastUtil;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class StepOneFragment extends BaseFragment {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.btnFb)
    TextView btnFb;
    @BindView(R.id.btnIg)
    TextView btnIg;
    @BindView(R.id.btnYouTube)
    TextView btnYouTube;
    @BindView(R.id.btnBlog)
    TextView btnBlog;
    @BindView(R.id.etIg)
    EditText etIg;
    @BindView(R.id.btnNext)
    TextView btnNext;
    @BindView(R.id.btnPre)
    TextView btnPre;

    private NavController navController;
    private BindTagViewModel viewModel;
    private final int IG = 0;
    private final int YOUTUBE = 1;
    private final int FB = 2;
    private final int BLOG = 3;
    private int type = 0;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_step_one;
    }

    @Override
    protected void initData() {
        navController = NavHostFragment.findNavController(this);
        initViewModel();
        if (((BindTagActivity)requireActivity()).hasIgUrl()){
            navController.navigate(R.id.action_stepOneFragment_to_stepTwoFragment2);
        }
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(BindTagViewModel.class);

        viewModel.getMldCanFinishStep1().observe(this, canFinish ->{
            if (canFinish){
                ((BindTagActivity) requireActivity()).dismissProgressDialog();
                navController.navigate(R.id.action_stepOneFragment_to_stepTwoFragment2);
            }
        });

        viewModel.getMldApiError().observe(this, errorMsg ->{
            ((BindTagActivity)requireActivity()).dismissProgressDialog();
            ToastUtil.showFailedImgInToast(errorMsg);
        });
    }

    @Override
    protected void configViews() {

        ((BindTagActivity) getActivity()).addDisposable(
                RxView.clicks(btnPre)
                        .throttleFirst(1, TimeUnit.SECONDS)
                        .subscribe( v -> {
                            getActivity().finish();
                        })
        );

        ((BindTagActivity) getActivity()).addDisposable(
                RxView.clicks(btnNext)
                        .throttleFirst(1, TimeUnit.SECONDS)
                        .subscribe( v -> {

                            String content = etIg.getText().toString();
                            switch (type){
                                case IG:
                                    if (!etIg.getText().toString().isEmpty()){
                                        ((BindTagActivity)getActivity()).showProgressDialog("");
                                        Log.d("JJJ", "IG Id: " + etIg.getText().toString() );
                                        viewModel.linkIg(etIg.getText().toString());
                                    }
                                    break;
                                case YOUTUBE:
                                    if (!content.isEmpty()){
                                        ((BindTagActivity)getActivity()).showProgressDialog("");
                                        if (!content.toLowerCase().startsWith("https")&&
                                                !content.toLowerCase().startsWith("http")){
                                            content = "https://"+content;
                                        }
                                        Log.d("JJJ", "YOUTUBE link: " + content );
                                        viewModel.linkYt(content);
                                    }
                                    break;
                                case FB:
                                    if (!content.isEmpty()){
                                        ((BindTagActivity)getActivity()).showProgressDialog("");
                                        if (!content.toLowerCase().startsWith("https") &&
                                                !content.toLowerCase().startsWith("http")){
                                            content = "https://"+content;
                                        }
                                        Log.d("JJJ", "FB link: " + content );
                                        viewModel.linkFb(content);
                                    }
                                    break;
                                case BLOG:
                                    if (!content.isEmpty()){
                                        ((BindTagActivity)getActivity()).showProgressDialog("");
                                        if (!content.toLowerCase().startsWith("https") &&
                                                !content.toLowerCase().startsWith("http")){
                                            content = "https://"+content;
                                        }
                                        Log.d("JJJ", "BLOG link: " + content );
                                        viewModel.linkBlog(content);
                                    }
                                    break;
                            }
                        })
        );

        ((BindTagActivity) requireActivity()).addDisposable(
                RxView.clicks(btnIg)
                        .throttleFirst(1, TimeUnit.SECONDS)
                        .subscribe( v -> {
                            type = IG;
                            etIg.setHint(R.string.hint_ig_id);
                            etIg.setVisibility(View.VISIBLE);
                            btnIg.setBackgroundResource(R.color.colorBackgroundGreyB);
                            btnFb.setBackgroundResource(R.color.colorBackgroundGrey5);
                            btnYouTube.setBackgroundResource(R.color.colorBackgroundGrey5);
                            btnBlog.setBackgroundResource(R.color.colorBackgroundGrey5);
                        })
        );

        ((BindTagActivity) requireActivity()).addDisposable(
                RxView.clicks(btnFb)
                        .throttleFirst(1, TimeUnit.SECONDS)
                        .subscribe( v -> {
                            type = FB;
                            etIg.setHint(R.string.hint_fb_id);
                            etIg.setVisibility(View.VISIBLE);
                            btnFb.setBackgroundResource(R.color.colorBackgroundGreyB);
                            btnIg.setBackgroundResource(R.color.colorBackgroundGrey5);
                            btnYouTube.setBackgroundResource(R.color.colorBackgroundGrey5);
                            btnBlog.setBackgroundResource(R.color.colorBackgroundGrey5);
                        })
        );

        ((BindTagActivity) requireActivity()).addDisposable(
                RxView.clicks(btnYouTube)
                        .throttleFirst(1, TimeUnit.SECONDS)
                        .subscribe( v -> {
                            type = YOUTUBE;
                            etIg.setHint(R.string.hint_yt_id);
                            etIg.setVisibility(View.VISIBLE);
                            btnYouTube.setBackgroundResource(R.color.colorBackgroundGreyB);
                            btnIg.setBackgroundResource(R.color.colorBackgroundGrey5);
                            btnFb.setBackgroundResource(R.color.colorBackgroundGrey5);
                            btnBlog.setBackgroundResource(R.color.colorBackgroundGrey5);
                        })
        );

        ((BindTagActivity) requireActivity()).addDisposable(
                RxView.clicks(btnBlog)
                        .throttleFirst(1, TimeUnit.SECONDS)
                        .subscribe( v -> {
                            type = BLOG;
                            etIg.setVisibility(View.VISIBLE);
                            etIg.setHint(R.string.hint_bg_id);
                            btnBlog.setBackgroundResource(R.color.colorBackgroundGreyB);
                            btnIg.setBackgroundResource(R.color.colorBackgroundGrey5);
                            btnFb.setBackgroundResource(R.color.colorBackgroundGrey5);
                            btnYouTube.setBackgroundResource(R.color.colorBackgroundGrey5);
                        })
        );
    }
}