package com.kolnetworks.koln.util;

import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.kolnetworks.koln.AppContext;
import com.kolnetworks.koln.R;


/**
 * Created by Chu on 2016/3/14.
 */
public class ToastUtil {
    public static boolean isShow = true;
    private static Toast sToast = null;
    private static Toast customToast = null;

    public static void showToast(String msg) {
        if (!isShow) {
            return;
        }
        if (sToast == null) {
            sToast = Toast.makeText(AppContext.context(), msg, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(msg);
        }
        sToast.show();
    }

    public static void showToast(int resId) {
        if (!isShow) {
            return;
        }
        if (sToast == null) {
            sToast = Toast.makeText(AppContext.context(), resId, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(resId);
        }
        sToast.show();
    }

    public static void showToastLong(String msg) {
        if (!isShow) {
            return;
        }
        if (sToast == null) {
            sToast = Toast.makeText(AppContext.context(), msg, Toast.LENGTH_LONG);
        } else {
            sToast.setText(msg);
        }
        sToast.show();
    }

    public static void showToastLong(int resId) {
        if (!isShow) {
            return;
        }
        if (sToast == null) {
            sToast = Toast.makeText(AppContext.context(), resId, Toast.LENGTH_LONG);
        } else {
            sToast.setText(resId);
        }
        sToast.show();
    }

    /**
     * 客製化的 toast
     *
     * @param msg 欲呈現的訊息
     */
    public static void showCheckImgInToast(String msg) {
        if (customToast != null) {
            customToast.cancel();
        }
        SpannableString msgSpan = new SpannableString(msg);
        msgSpan.setSpan(new TextAppearanceSpan(AppContext.context(), R.style.content_white_regular_center), 0, msgSpan.length(), 0);
        customToast = Toast.makeText(AppContext.context(), msgSpan, Toast.LENGTH_SHORT);
        customToast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContentView = (LinearLayout) customToast.getView();
        toastContentView.setPadding(DensityUtil.dp2px(AppContext.context(), 10), DensityUtil.dp2px(AppContext.context(), 27), DensityUtil.dp2px(AppContext.context(), 10), 0);
        toastContentView.setBackground(ContextCompat.getDrawable(AppContext.context(), R.drawable.bg_customer_toast));
        ImageView imageView = new ImageView(AppContext.context());
        imageView.setImageResource(R.drawable.done);
        toastContentView.addView(imageView, 0);
        customToast.show();
    }

    public static void showFailedImgInToast(String msg) {
        if (customToast != null) {
            customToast.cancel();
        }
        SpannableString msgSpan = new SpannableString(msg);
        msgSpan.setSpan(new TextAppearanceSpan(AppContext.context(), R.style.content_white_regular_center), 0, msgSpan.length(), 0);
        customToast = Toast.makeText(AppContext.context(), msgSpan, Toast.LENGTH_SHORT);
        customToast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContentView = (LinearLayout) customToast.getView();
        toastContentView.setPadding(0, DensityUtil.dp2px(AppContext.context(), 27), 0, 0);
        toastContentView.setBackground(ContextCompat.getDrawable(AppContext.context(), R.drawable.bg_customer_toast));
        ImageView imageView = new ImageView(AppContext.context());
        imageView.setImageResource(R.drawable.fail);
        toastContentView.addView(imageView, 0);
        customToast.show();
    }

    public static void showNoInternetToast(String msg) {
        if (customToast != null) {
            customToast.cancel();
        }
        SpannableString msgSpan = new SpannableString(msg);
        msgSpan.setSpan(new TextAppearanceSpan(AppContext.context(), R.style.content_white_regular_center), 0, msgSpan.length(), 0);
        customToast = Toast.makeText(AppContext.context(), msgSpan, Toast.LENGTH_SHORT);
        customToast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContentView = (LinearLayout) customToast.getView();
        toastContentView.setPadding(0, DensityUtil.dp2px(AppContext.context(), 27), 0, 0);
        toastContentView.setBackground(ContextCompat.getDrawable(AppContext.context(), R.drawable.bg_customer_toast));
        ImageView imageView = new ImageView(AppContext.context());
        imageView.setImageResource(R.drawable.no_internet);
        toastContentView.addView(imageView, 0);
        customToast.show();
    }

    // 主要针对需要在某个时候，取消提示
    public static void cancelToast() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }

    public static void cancelCustomToast() {
        if (customToast != null) {
            customToast.cancel();
            customToast = null;
        }
    }

}
