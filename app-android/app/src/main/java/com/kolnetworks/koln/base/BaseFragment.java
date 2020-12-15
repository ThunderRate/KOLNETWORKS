package com.kolnetworks.koln.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    protected View parentView;
    protected FragmentActivity activity;
    protected LayoutInflater inflater;
    protected Context context;
    private Unbinder unbinder;

    /**
     * @return 回傳 layout id
     */
    @LayoutRes
    protected abstract int getLayoutResId();

    /**
     * 初始化資料
     */
    protected abstract void initData();

    /**
     * 對各種元件進行配置、適配、填充數據
     */
    protected abstract void configViews();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        parentView = inflater.inflate(getLayoutResId(), container, false);
        activity = getSupportActivity();
        this.context = activity;
        this.inflater = inflater;
        return parentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        initData();
        configViews();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.activity = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public FragmentActivity getSupportActivity() {
        return super.getActivity();
    }
}
