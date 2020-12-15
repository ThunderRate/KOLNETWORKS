package com.kolnetworks.koln.ui.userinfo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.jakewharton.rxbinding3.view.RxView;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.kolnetworks.koln.R;
import com.kolnetworks.koln.base.BaseFragment;
import com.kolnetworks.koln.data.SPApi;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;


public class PushCodeFragment extends BaseFragment {


    @BindView(R.id.qrCode)
    ImageView qrCode;
    @BindView(R.id.bottom)
    CardView bottom;
    @BindView(R.id.btnShare)
    ImageView btnShare;
    @BindView(R.id.tv_invite)
    TextView tvInvite;
    @BindView(R.id.tv_invite_done)
    TextView tvInviteDone;

    private String shareUrl = "";

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_push_code;
    }

    @Override
    protected void initData() {
        shareUrl = "www.kolnetworks.com/app/events/invitation?code=" + SPApi.getInvitationCode() + "&register=register";
        tvInvite.setText("註冊數：" + SPApi.getInviteCount());
        tvInviteDone.setText("接案成功：" + SPApi.getInviteCount());
    }

    @Override
    protected void configViews() {
        BarcodeEncoder encoder = new BarcodeEncoder();
        try {
            Bitmap bitmap = encoder.encodeBitmap(shareUrl, BarcodeFormat.QR_CODE, 600, 600);
            qrCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        ((UserInfoActivity) requireActivity()).addDisposable(RxView.clicks(btnShare)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(v -> {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareUrl);
                    startActivity(Intent.createChooser(shareIntent, "Share link using"));
                }));
    }
}