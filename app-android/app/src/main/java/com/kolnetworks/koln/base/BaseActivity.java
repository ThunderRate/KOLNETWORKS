package com.kolnetworks.koln.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.TextAppearanceSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.kolnetworks.koln.R;

import java.lang.ref.WeakReference;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseActivity extends AppCompatActivity {

    public CompositeDisposable mCompositeDisposable;
    protected Context context;
    protected boolean isForeground;
    protected AlertDialog permissionAlertDialog;
    Unbinder unbinder;
    private AlertDialog baseDialog;
    private OnDialogClickListener dialogListener;
    private AlertDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        // Obtain the FirebaseAnalytics instance.
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        context = this;
        unbinder = ButterKnife.bind(this);
        mCompositeDisposable = new CompositeDisposable();
        initData();
        configViews();

    }


    @Override
    protected void onResume() {
        super.onResume();
        isForeground = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        isForeground = false;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        clearDisposable();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 添加订阅
     */
    public void addDisposable(Disposable mDisposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(mDisposable);
    }

    /**
     * 取消所有订阅
     */
    public void clearDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，
     * 来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected void hideKeyboard(EditText editText) {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    protected abstract int getLayoutId();

    /**
     * 初始化資料
     */
    protected abstract void initData();

    /**
     * 對各種元件進行配置、適配、填充數據
     */
    protected abstract void configViews();

    /**
     * 顯示客製化警示 Dialog
     *
     * @param title        標題
     * @param message      訊息
     * @param positiveText 右邊按鈕文字
     * @param negativeText 左邊按鈕文字
     */
    public void showAlertDialog(String title, String message, String positiveText, String negativeText) {
        if (dialogListener == null) {
            return;
        }
        SpannableString titleSpan = new SpannableString(title);
        titleSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, titleSpan.length(), 0);
        titleSpan.setSpan(new TextAppearanceSpan(context, R.style.headline_black_medium_left), 0, titleSpan.length(), 0);

        SpannableString messageSpan = new SpannableString(message);
        messageSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, messageSpan.length(), 0);
        messageSpan.setSpan(new TextAppearanceSpan(context, R.style.body_gray_regular_center), 0, messageSpan.length(), 0);

        SpannableString positiveSpan = new SpannableString(positiveText);
        positiveSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, positiveSpan.length(), 0);
        positiveSpan.setSpan(new TextAppearanceSpan(context, R.style.body_main_color_medium_center), 0, positiveSpan.length(), 0);

        SpannableString negativeSpan = new SpannableString(negativeText);
        negativeSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, negativeSpan.length(), 0);
        negativeSpan.setSpan(new TextAppearanceSpan(context, R.style.body_black_medium_center), 0, negativeSpan.length(), 0);

        baseDialog = new AlertDialog.Builder(context)
                .setTitle(titleSpan)
                .setMessage(messageSpan)
                .setPositiveButton(positiveSpan, (dialog, which) -> dialogListener.onPositiveClick())
                .setNegativeButton(negativeSpan, (dialog, which) -> dialogListener.onNegativeClick())
                .setCancelable(false)
                .create();
        baseDialog.show();
    }

    public void alertDialogDismiss() {
        if (baseDialog != null) {
            baseDialog.cancel();
        }
    }

    /**
     * 顯示進度對話框
     *
     * @param title 標題
     */
    public void showProgressDialog(String title) {
        if (progressDialog == null) {
            progressDialog = new AlertDialog.Builder(this, R.style.CustomProgressDialog)
                    .setView(R.layout.loading_progress_dialog)
                    .setCancelable(false)
                    .create();
        }
        progressDialog.setTitle(title);
        progressDialog.show();
    }

    /**
     * 關閉進度對話框
     */
    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.cancel();
            progressDialog = null;
        }
    }

    /**
     * 顯示權限對話框
     */
    public void showPermissionDialog(Context context, String permissionName) {
        if (permissionAlertDialog == null) {
            permissionAlertDialog = new AlertDialog.Builder(context)
                    .setMessage("未允许「" + getString(R.string.app_name) + "」" + permissionName + "，将使「" + getString(R.string.app_name) + "」无法正常运作，是否重新设定权限？")
                    .setPositiveButton("重新设定权限", null)
                    .setCancelable(false)
                    .create();
        }
        permissionAlertDialog.show();
    }

    /**
     * 關閉權限對話框
     */
    public void dismissPermissionDialog() {
        if (permissionAlertDialog != null && permissionAlertDialog.isShowing()) {
            permissionAlertDialog.cancel();
            permissionAlertDialog = null;
        }
    }

    public void setOnDialogClickListener(OnDialogClickListener listener) {
        dialogListener = listener;
    }




    public boolean isConnectInternet(){
        boolean result = false;
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=connManager.getActiveNetworkInfo();
        if (info == null || !info.isConnected())
        {
            result = false;
        }
        else
        {
            if (!info.isConnected())
            {
                result =false;
            }
            else
            {
                result = true;
            }
        }
        return result;
    }

    public interface OnDialogClickListener {
        void onPositiveClick();

        void onNegativeClick();
    }
}
