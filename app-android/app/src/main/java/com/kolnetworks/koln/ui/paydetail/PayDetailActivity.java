package com.kolnetworks.koln.ui.paydetail;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.rxbinding3.view.RxView;
import com.kolnetworks.koln.R;
import com.kolnetworks.koln.adapter.PayAdapter;
import com.kolnetworks.koln.api.ExceptionHandle;
import com.kolnetworks.koln.api.MyObserver;
import com.kolnetworks.koln.api.bean.ResponseCashFlow;
import com.kolnetworks.koln.base.BaseActivity;
import com.kolnetworks.koln.repository.DataRepository;
import com.kolnetworks.koln.ui.main.MainViewModel;
import com.kolnetworks.koln.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayDetailActivity extends BaseActivity {


    @BindView(R.id.btnArrow)
    ImageView btnArrow;
    @BindView(R.id.appBar)
    ConstraintLayout appBar;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.rvBenefit)
    RecyclerView rvBenefit;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.des)
    TextView des;
    @BindView(R.id.card_empty)
    CardView cardEmpty;

    private MainViewModel viewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_detail;
    }

    @Override
    protected void initData() {
        showProgressDialog("");
        int type = getIntent().getIntExtra("benefit", 0);
        loadCashFlow(type);
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
        ButterKnife.bind(this);
    }

    private void setRecyclerView(List<ResponseCashFlow.CashflowBean> list) {
        PayAdapter adapter = new PayAdapter(context);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        adapter.setList(list);

        rvBenefit.setLayoutManager(manager);
        rvBenefit.setAdapter(adapter);
    }

    // 取得收益
    public void loadCashFlow(int type) {
        DataRepository.getInstance()
                .fetchCashFlow()
                .subscribe(new MyObserver<ResponseCashFlow>() {
                    @Override
                    public void onSuccess(ResponseCashFlow cashflowBeans) {
                        dismissProgressDialog();
                        List<ResponseCashFlow.CashflowBean> list = new ArrayList<>();
                        for (int i = 0; i < cashflowBeans.getCashflow().size(); i++) {
                            if (cashflowBeans.getCashflow().get(i).getOrder().getStatus() == type) {
                                list.add(cashflowBeans.getCashflow().get(i));
                            }
                        }
                        setRecyclerView(list);

                        if (list.size()>0){
                            title.setVisibility(View.INVISIBLE);
                            des.setVisibility(View.INVISIBLE);
                        }else {
                            title.setVisibility(View.VISIBLE);
                            des.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        dismissPermissionDialog();
                        ToastUtil.showFailedImgInToast(throwable.message);
                    }
                });
    }
}