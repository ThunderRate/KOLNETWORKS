package com.kolnetworks.koln.ui.projectinfo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import com.jakewharton.rxbinding3.view.RxView;
import com.kolnetworks.koln.Constant;
import com.kolnetworks.koln.R;
import com.kolnetworks.koln.base.BaseActivity;
import com.kolnetworks.koln.base.BaseFragment;
import com.kolnetworks.koln.data.SPApi;
import com.kolnetworks.koln.model.ProjectData;
import com.kolnetworks.koln.util.DateUtil;
import com.kolnetworks.koln.util.ToastUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;


public class ProjectBasicFragment extends BaseFragment {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.btnSetPrice)
    Button btnSetPrice;
    @BindView(R.id.tvHint)
    TextView tvHint;
    @BindView(R.id.btnAgree)
    Button btnAgree;
    @BindView(R.id.btnDeny)
    Button btnDeny;
    @BindView(R.id.btnAttend)
    Button btnAttend;
    @BindView(R.id.btnSendLink)
    Button btnSendLink;
    @BindView(R.id.btnSendPicText)
    Button btnSendPicText;
    @BindView(R.id.top)
    CardView top;
    @BindView(R.id.tvPjIdTitle)
    TextView tvPjIdTitle;
    @BindView(R.id.tvPjId)
    TextView tvPjId;
    @BindView(R.id.tvPjStageTitle)
    TextView tvPjStageTitle;
    @BindView(R.id.tvPjStage)
    TextView tvPjStage;
    @BindView(R.id.tvPjDateTitle)
    TextView tvPjDateTitle;
    @BindView(R.id.tvPjDate)
    TextView tvPjDate;
    @BindView(R.id.tvPjDescriptionTitle)
    TextView tvPjDescriptionTitle;
    @BindView(R.id.tvPjDescription)
    TextView tvPjDescription;
    @BindView(R.id.bottom)
    CardView bottom;
    @BindView(R.id.btnStart)
    Button btnStart;
    private ProjectViewModel viewModel;
    private ProjectData data;
    private int type;
    private int setExpPrice;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_project_basic;
    }

    @Override
    protected void initData() {
        if (getActivity() != null) {
            data = ((ProjectInfoActivity) getActivity()).getProjectData();
            type = ((ProjectInfoActivity) getActivity()).getType();
        }
        initViewModel();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);
        if (data != null) {
            tvDate.setText(String.format(getString(R.string.card_date), DateUtil.getDate(data.getInvite_deadline())));
            title.setText(data.getName());
            tvPjId.setText(data.getProject_no());
            tvPjStage.setText(Constant.getPlatformName(data.getPlatform()));
            tvPjDate.setText(DateUtil.getDate(data.getStart_date()) + " - " + DateUtil.getDate(data.getEnd_date()));
            //tvPjPlace.setText(data.getPlace());
            tvPjDescription.setText(Html.fromHtml(data.getIntroduction()));
            tvPjDescription.setMovementMethod(LinkMovementMethod.getInstance());
            if (data.getKol_expect_price() <= 0) {
                tvPrice.setText(String.format(getString(R.string.card_price), String.valueOf(data.getKol_price())));
            } else {
                tvPrice.setText(String.format(getString(R.string.card_price), String.valueOf(data.getKol_expect_price())));
            }
        }

        // 提交連結完成
        viewModel.getMldCanFinishSendLink().observe(this, canFinish -> {
            if (canFinish) {
                btnSendPicText.setVisibility(View.GONE);
                btnSendLink.setVisibility(View.GONE);
                tvHint.setVisibility(View.VISIBLE);
                tvHint.setText(getString(R.string.project_hint_finish));
            }
            ((ProjectInfoActivity) requireActivity()).dismissProgressDialog();
        });

        // 拒絕完成
        viewModel.getMldCanFinishReject().observe(this, canFinish -> {
            if (canFinish) {
                btnAgree.setVisibility(View.GONE);
                btnDeny.setVisibility(View.GONE);
                tvHint.setVisibility(View.VISIBLE);
                tvHint.setText(getString(R.string.project_hint_reject));
            }
            ((ProjectInfoActivity) requireActivity()).dismissProgressDialog();
        });

        // 同意完成
        viewModel.getMldCanFinishAgree().observe(this, canFinish -> {
            if (canFinish) {
                btnAgree.setVisibility(View.GONE);
                btnDeny.setVisibility(View.GONE);
                tvHint.setVisibility(View.VISIBLE);
                tvHint.setText(getString(R.string.project_hint_apply));
                //requireActivity().finish();
            }
            ((ProjectInfoActivity) requireActivity()).dismissProgressDialog();
        });

        // 報名完成(報價)
        viewModel.getMldCanFinishAttend().observe(this, canFinish -> {
            if (canFinish) {
                btnAttend.setVisibility(View.GONE);
                tvHint.setVisibility(View.VISIBLE);
                tvHint.setText(getString(R.string.project_hint_apply));
            }
            ((ProjectInfoActivity) requireActivity()).dismissProgressDialog();
        });

        // 經理 開始
        viewModel.getMldChangeProjectStatus().observe(this,isSuccess->{
            ((ProjectInfoActivity) requireActivity()).dismissProgressDialog();
            if (isSuccess){
                ((ProjectInfoActivity) requireActivity()).finish();
            }
        });

        viewModel.getMldApiError().observe(this, errorMsg -> {
            ((ProjectInfoActivity)requireActivity()).dismissProgressDialog();
            ToastUtil.showFailedImgInToast(errorMsg);
        });

    }

    @Override
    protected void configViews() {

        // 同意
        ((ProjectInfoActivity) getActivity()).addDisposable(RxView.clicks(btnAgree)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(v -> {
                    ((ProjectInfoActivity) requireActivity()).showProgressDialog("");
                    viewModel.loadAgree(data.getUuid(), SPApi.getUserUuid());
                })
        );

        // 拒絕
        ((ProjectInfoActivity) getActivity()).addDisposable(RxView.clicks(btnDeny)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(v -> {
                    ((ProjectInfoActivity) requireActivity()).showProgressDialog("");
                    viewModel.loadReject(data.getUuid(), SPApi.getUserUuid());
                })
        );

        // 報名
        ((ProjectInfoActivity) getActivity()).addDisposable(RxView.clicks(btnAttend)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(v -> {
                    ((ProjectInfoActivity) requireActivity()).showProgressDialog("");
                    showPriceEditDialog();
                })
        );

        // 提交連結
        ((ProjectInfoActivity) getActivity()).addDisposable(RxView.clicks(btnSendLink)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(v -> {
                    ((ProjectInfoActivity) requireActivity()).showProgressDialog("");
                    showEditDialog();
                })
        );

        // 經理 開始
        ((ProjectInfoActivity) getActivity()).addDisposable(RxView.clicks(btnStart)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(v -> {
                    ((ProjectInfoActivity) requireActivity()).showProgressDialog("");
                    viewModel.putChangeProjectStatus(data.getUuid());
                })
        );

        // 圖文審核
        ((ProjectInfoActivity) getActivity()).addDisposable(RxView.clicks(btnSendPicText)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(v -> {
                    ((ProjectInfoActivity) getActivity()).setOnDialogClickListener(new BaseActivity.OnDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                            setPivTextCheckBtnStatus(false, true);
                        }

                        @Override
                        public void onNegativeClick() {

                        }
                    });
                    ((ProjectInfoActivity) getActivity()).showAlertDialog(
                            "圖文審稿",
                            "請記得先聯繫專案經理做圖文審稿唷！",
                            "我知道了",
                            ""
                    );
                })
        );

        switch (type) {
            case Constant.TYPE_FOUND_UNIQ:
                btnSetPrice.setVisibility(View.GONE);
                tvPrice.setVisibility(View.VISIBLE);

                btnAgree.setVisibility(View.VISIBLE);
                btnDeny.setVisibility(View.VISIBLE);
                btnAttend.setVisibility(View.GONE);
                btnSendLink.setVisibility(View.GONE);
                btnSendPicText.setVisibility(View.GONE);
                tvHint.setVisibility(View.GONE);
                btnStart.setVisibility(View.GONE);
                break;
            case Constant.TYPE_FOUND_APPLY_COOPERATION:
                btnSetPrice.setVisibility(View.GONE);
                tvPrice.setVisibility(View.GONE);

                btnAgree.setVisibility(View.GONE);
                btnDeny.setVisibility(View.GONE);
                btnSendLink.setVisibility(View.GONE);
                btnAttend.setVisibility(View.VISIBLE);
                btnSendPicText.setVisibility(View.GONE);
                tvHint.setVisibility(View.GONE);
                btnStart.setVisibility(View.GONE);
                break;
            case Constant.TYPE_MY_APPLIED:
                //case Constant.TYPE_MISSION_NOT_START:
                btnSetPrice.setVisibility(View.GONE);
                tvPrice.setVisibility(View.VISIBLE);

                btnAgree.setVisibility(View.GONE);
                btnDeny.setVisibility(View.GONE);
                btnSendLink.setVisibility(View.GONE);
                btnAttend.setVisibility(View.GONE);
                btnSendPicText.setVisibility(View.GONE);
                btnStart.setVisibility(View.GONE);
                tvHint.setVisibility(View.VISIBLE);
                tvHint.setText(getString(R.string.project_hint_apply));
                if (data.getKol_status() == 7) {
                    tvHint.setText(getString(R.string.project_hint_be_reject_not_match));
                }
                break;
            case Constant.TYPE_MY_CONFIRM:
                btnSetPrice.setVisibility(View.GONE);
                tvPrice.setVisibility(View.VISIBLE);

                btnAgree.setVisibility(View.GONE);
                btnDeny.setVisibility(View.GONE);
                btnAttend.setVisibility(View.GONE);
                btnStart.setVisibility(View.GONE);

                Log.d("JJJ", "data.getKol_status(): " + data.getKol_status());
                if (data.getKol_status() == 3 && (data.getProject_status() == 2 || data.getProject_status() == 3)) {
                    btnSendLink.setVisibility(View.GONE);
                    btnSendPicText.setVisibility(View.GONE);
                    tvHint.setVisibility(View.VISIBLE);
                    tvHint.setText(getString(R.string.project_hint_added));
                    return;
                } else if (data.getKol_status() == 3 || data.getKol_status() == 6) {
                    btnSendLink.setVisibility(View.VISIBLE);
                    btnSendPicText.setVisibility(View.VISIBLE);
                    tvHint.setVisibility(View.GONE);
                    setPivTextCheckBtnStatus(true, false);
                    return;
                } else if (data.getKol_status() == 4) {
                    btnSendLink.setVisibility(View.GONE);
                    btnSendPicText.setVisibility(View.GONE);
                    tvHint.setVisibility(View.VISIBLE);
                    tvHint.setText(getString(R.string.project_hint_finish));
                    return;
                } else if (data.getKol_status() == 5) {
                    btnSendLink.setVisibility(View.GONE);
                    btnSendPicText.setVisibility(View.GONE);
                    tvHint.setVisibility(View.VISIBLE);
                    tvHint.setText(getString(R.string.project_hint_done));
                    return;
                }
                break;
            case Constant.TYPE_MY_FINISH:
                btnSetPrice.setVisibility(View.GONE);
                tvPrice.setVisibility(View.VISIBLE);

                btnAgree.setVisibility(View.GONE);
                btnDeny.setVisibility(View.GONE);
                btnSendLink.setVisibility(View.GONE);
                btnAttend.setVisibility(View.GONE);
                btnSendPicText.setVisibility(View.GONE);
                btnStart.setVisibility(View.GONE);
                tvHint.setVisibility(View.VISIBLE);
                tvHint.setText(getString(R.string.project_hint_finish));
                if (data.getKol_status() == 5) {
                    tvHint.setText(getString(R.string.project_hint_wait_pay));
                }

                if (data.getProject_status() == 7 && data.getKol_status() == 5) {
                    tvHint.setText(getString(R.string.project_hint_all_done));
                }
                break;
            case Constant.TYPE_MANAGER_PROJECTS:
                btnSetPrice.setVisibility(View.GONE);
                tvPrice.setVisibility(View.VISIBLE);

                btnAgree.setVisibility(View.GONE);
                btnDeny.setVisibility(View.GONE);
                btnSendLink.setVisibility(View.GONE);
                btnAttend.setVisibility(View.GONE);
                btnSendPicText.setVisibility(View.GONE);

                tvHint.setText(R.string.project_project_going);
                if (data.getProject_status() == 2) {
                    btnStart.setVisibility(View.VISIBLE);
                    tvHint.setVisibility(View.GONE);
                } else if (data.getProject_status() > 2) {
                    btnStart.setVisibility(View.GONE);
                    tvHint.setVisibility(View.VISIBLE);
                } else {
                    btnStart.setVisibility(View.GONE);
                    tvHint.setVisibility(View.GONE);
                }

                break;
        }

    }


    private void setPivTextCheckBtnStatus(boolean activePicTextBtn, boolean activeSendBtn) {
        if (activePicTextBtn) {
            btnSendPicText.setClickable(true);
            btnSendPicText.setBackgroundColor(context.getResources().getColor(R.color.colorBackgroundBlack));
        } else {
            btnSendPicText.setClickable(false);
            btnSendPicText.setBackgroundColor(context.getResources().getColor(R.color.colorBackgroundGrey7));
        }

        if (activeSendBtn) {
            btnSendLink.setClickable(true);
            btnSendLink.setBackgroundColor(context.getResources().getColor(R.color.colorBackgroundBlack));
        } else {
            btnSendLink.setClickable(false);
            btnSendLink.setBackgroundColor(context.getResources().getColor(R.color.colorBackgroundGrey7));
        }
    }

    public void showEditDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("提交審稿");
        dialog.setMessage("提交審稿內容");

        final EditText et = new EditText(context);
        et.setHint("請貼上連結url");
        et.setPadding(10, 0, 10, 0);
        et.setBackground(null);

        dialog.setView(et);//给对话框添加一个EditText输入文本框

        //给对话框添加一个确定按钮，同样的方法可以添加一个取消按钮
        dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                String url = "";
                if (!et.getText().toString().startsWith("https://")) {
                    url = "https://" + et.getText().toString();
                }
                if (et.getText().toString().startsWith("http://")) {
                    url = et.getText().toString().replace("http://", "https://");
                }
                viewModel.loadSendLink(data.getUuid(), url);
            }
        });

        //下面是弹出键盘的关键处
        AlertDialog tempDialog = dialog.create();
        tempDialog.setView(et, 0, 0, 0, 0);

        tempDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            public void onShow(DialogInterface dialog) {
                InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        tempDialog.show();
    }

    public void showPriceEditDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("我要參加");
        if (TextUtils.isEmpty(data.getComments())) {
            dialog.setMessage("請確認合作內容與報價是否包含購買產品費用後，再進行報價喔！\n" +
                    "＊在你與廠商都同意合作後，需自行負擔匯款手續費");
        } else {
            dialog.setMessage(
                    data.getComments()
            );
        }

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                ((ProjectInfoActivity) requireActivity()).dismissProgressDialog();
            }
        });


        final EditText et = new EditText(context);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(convertPixelsToDp(100, requireContext()), convertPixelsToDp(5, requireContext()), 0, 0);
        et.setLayoutParams(lp);

        et.setHint("輸入預期金額");
        et.setInputType(InputType.TYPE_CLASS_TEXT);
        et.setLines(1);
        et.setPadding(10, 0, 10, 0);
        et.setBackground(null);

        dialog.setView(et);//给对话框添加一个EditText输入文本框

        //给对话框添加一个确定按钮，同样的方法可以添加一个取消按钮
        dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                if (!TextUtils.isEmpty(et.getText())) {
                    setExpPrice = Integer.parseInt(et.getText().toString());
                    ((ProjectInfoActivity) requireActivity()).showProgressDialog("");
                    if (setExpPrice > 0) {
                        viewModel.loadAttend(data.getUuid(), setExpPrice);
                    }
                }

            }
        });

        //下面是弹出键盘的关键处
        AlertDialog tempDialog = dialog.create();
        tempDialog.setView(et, 0, 0, 0, 0);

        tempDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            public void onShow(DialogInterface dialog) {
                InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        tempDialog.show();
    }

    public static int convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int dp = (int) (px / (metrics.densityDpi / 160f));
        return dp;
    }
}