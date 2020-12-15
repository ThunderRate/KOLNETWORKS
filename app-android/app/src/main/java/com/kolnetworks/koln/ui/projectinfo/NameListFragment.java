package com.kolnetworks.koln.ui.projectinfo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kolnetworks.koln.ImpAdapterClickListener;
import com.kolnetworks.koln.R;
import com.kolnetworks.koln.adapter.ProjectNameListAdapter;
import com.kolnetworks.koln.api.bean.ResponseManagerApplyCooperationList;
import com.kolnetworks.koln.base.BaseFragment;
import com.kolnetworks.koln.model.ProjectData;
import com.kolnetworks.koln.util.ToastUtil;

import java.util.List;

import butterknife.BindView;

public class NameListFragment extends BaseFragment implements ImpAdapterClickListener {


    @BindView(R.id.tvTitle1)
    TextView tvTitle1;
    @BindView(R.id.rv_apply_cooperation)
    RecyclerView rvApplyCooperation;
    @BindView(R.id.cv_apply_cooperation)
    CardView cvApplyCooperation;
    @BindView(R.id.tvTitle2)
    TextView tvTitle2;
    @BindView(R.id.rv_invite_name_list)
    RecyclerView rvInviteNameList;
    private ProjectViewModel viewModel;

    private ProjectData data;
    private int type;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_name_list;
    }

    @Override
    protected void initData() {
        if (getActivity() != null) {
            data = ((ProjectInfoActivity) getActivity()).getProjectData();
            type = ((ProjectInfoActivity) getActivity()).getType();
        }
        initViewModel();
    }

    @Override
    protected void configViews() {

    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);
        viewModel.getManagerApplyCooperationList(data.getUuid());
        viewModel.getManagerInviteList(data.getUuid());

        viewModel.getMldCooperationList().observe(this, this::setRecyclerViewCooperation);
        viewModel.getMldInviteList().observe(this, this::setRecyclerViewInvite);

        viewModel.getMldApiError().observe(this, errorMsg -> {
            ((ProjectInfoActivity)requireActivity()).dismissProgressDialog();
            ToastUtil.showFailedImgInToast(errorMsg);
        });

        viewModel.getMldReportLink().observe(this, bean -> {
            ((ProjectInfoActivity)requireActivity()).dismissProgressDialog();
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            //設定確定按鈕
            //設定取消按鈕
            builder.setTitle(clickedName) //設定標題文字
                    .setMessage(Html.fromHtml("<a href=\"" + bean.getReport() + "\">" + bean.getReport() + "</a>\"")) //設定內容文字
                    .setPositiveButton("同意", (dialog, which) -> {
                        ((ProjectInfoActivity)requireActivity()).showProgressDialog("");
                        viewModel.fetchAgreeLink(clickedProjectUuid, clickedKolUuid);
                    })
                    .setNegativeButton("拒絕", (dialog, which) -> {
                        ((ProjectInfoActivity)requireActivity()).showProgressDialog("");
                        viewModel.fetchRejectLink(clickedProjectUuid, clickedKolUuid);
                    });
            Dialog dialog = builder.create(); //建立對話方塊並存成 dialog
            dialog.show();
        });

        viewModel.getMldAgreeKol().observe(this, isSuccess -> {
            if (isSuccess){
                //viewModel.getManagerApplyCooperationList(data.getUuid());
                ((ProjectInfoActivity)requireActivity()).dismissProgressDialog();
                requireActivity().finish();
            }
        });

        viewModel.getMldRejectKol().observe(this, isSuccess -> {
            if (isSuccess){
                //viewModel.getManagerApplyCooperationList(data.getUuid());
                ((ProjectInfoActivity)requireActivity()).dismissProgressDialog();
                requireActivity().finish();
            }
        });

        viewModel.getMldAgreeLink().observe(this, isSuccess -> {
            if (isSuccess){
                //viewModel.getManagerInviteList(data.getUuid());
                ((ProjectInfoActivity)requireActivity()).dismissProgressDialog();
                requireActivity().finish();
            }
        });

        viewModel.getMldRejectLink().observe(this, isSuccess -> {
            if (isSuccess){
                //viewModel.getManagerInviteList(data.getUuid());
                ((ProjectInfoActivity)requireActivity()).dismissProgressDialog();
                requireActivity().finish();
            }
        });
    }

    private void setRecyclerViewCooperation(List<ResponseManagerApplyCooperationList.KolsBean> list) {
        ProjectNameListAdapter adapter = new ProjectNameListAdapter(context, this);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        adapter.setList(list);

        rvApplyCooperation.setLayoutManager(manager);
        rvApplyCooperation.setAdapter(adapter);
    }

    private void setRecyclerViewInvite(List<ResponseManagerApplyCooperationList.KolsBean> list) {
        ProjectNameListAdapter adapter = new ProjectNameListAdapter(context, this);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        adapter.setList(list);

        rvInviteNameList.setLayoutManager(manager);
        rvInviteNameList.setAdapter(adapter);
    }

    @Override
    public void onBtnAgreeClick(String project_uuid, String kol_uuid) {
        ((ProjectInfoActivity)requireActivity()).showProgressDialog("");
        viewModel.fetchAgreeKol(project_uuid, kol_uuid);
    }

    @Override
    public void onBtnDenyClick(String project_uuid, String kol_uuid) {
        ((ProjectInfoActivity)requireActivity()).showProgressDialog("");
        viewModel.fetchRejectKol(project_uuid, kol_uuid);
    }

    String clickedProjectUuid = "";
    String clickedKolUuid = "";
    String clickedName = "";

    @Override
    public void onBtnLinkCheckClick(String name, String project_uuid, String kol_uuid) {
        ((ProjectInfoActivity)requireActivity()).showProgressDialog("");
        clickedProjectUuid = project_uuid;
        clickedKolUuid = kol_uuid;
        clickedName = name;
        viewModel.getReportLink(project_uuid, kol_uuid);


    }
}