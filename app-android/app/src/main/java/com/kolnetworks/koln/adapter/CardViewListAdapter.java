package com.kolnetworks.koln.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kolnetworks.koln.Constant;
import com.kolnetworks.koln.R;
import com.kolnetworks.koln.api.bean.ResponseProject;
import com.kolnetworks.koln.data.SPApi;
import com.kolnetworks.koln.model.ProjectData;
import com.kolnetworks.koln.ui.projectinfo.ProjectInfoActivity;
import com.kolnetworks.koln.util.DateUtil;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardViewListAdapter extends RecyclerView.Adapter<CardViewListAdapter.ProjectListViewHolder> {


    private Context context;
    private List<ResponseProject.ProjectsBean> list;
    private int type;
    public static String PROJECT = "project";
    public static String PROJECT_TYPE = "type_1";

    public CardViewListAdapter(Context context) {
        this.context = context;
    }

    public void setList(int type, List<ResponseProject.ProjectsBean> list) {
        this.list = list;
        this.type = type;
    }

    @NonNull
    @Override
    public ProjectListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_card_normal, parent, false);
        return new ProjectListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectListViewHolder holder, int position) {
        if (list.size() > 0) {
            holder.cardEmpty.setVisibility(View.GONE);
            setCardData(holder, position);
        } else {
            holder.cardEmpty.setVisibility(View.VISIBLE);
            setEmptyCard(holder);
        }
        if (type == Constant.TYPE_FOUND_APPLY_COOPERATION){
            holder.tvPrice.setVisibility(View.GONE);
        }
    }


    private void setEmptyCard(@NonNull ProjectListViewHolder holder) {
        switch (type) {
            case Constant.TYPE_FOUND_APPLY_COOPERATION:
                holder.tvTitle.setText(context.getString(R.string.project_apply_empty_title));
                holder.tvDescription.setText(context.getString(R.string.project_apply_empty_description));
                break;
            case Constant.TYPE_FOUND_UNIQ:
                holder.tvTitle.setText(context.getString(R.string.project_invite_empty_title));
                holder.tvDescription.setText(context.getString(R.string.project_invite_empty_description));
                break;
            case Constant.TYPE_MY_CONFIRM:
                holder.tvTitle.setText(context.getString(R.string.project_confirm_empty_title));
                holder.tvDescription.setText(context.getString(R.string.project_confirm_empty_description));
                break;
            case Constant.TYPE_MY_FINISH:
                holder.tvTitle.setText(context.getString(R.string.project_finnish_empty_title));
                holder.tvDescription.setVisibility(View.GONE);
                break;
            case Constant.TYPE_MY_APPLIED:
                holder.tvTitle.setText(context.getString(R.string.project_apply_empty_title));
                holder.tvDescription.setVisibility(View.GONE);
                break;
            case Constant.TYPE_MANAGER_PROJECTS:
                holder.tvTitle.setText("MANAGER EMPTY");
                holder.tvDescription.setVisibility(View.GONE);
                break;

        }
    }

    private void setCardData(@NonNull ProjectListViewHolder holder, int position) {
        holder.tvType.setText(Constant.getPlatformShortName(list.get(position).getPlatform()));
        holder.tvName.setText(list.get(position).getName());
        holder.tvDate.setText(String.format(context.getString(R.string.card_date), DateUtil.getDate(list.get(position).getInvite_deadline())));
        if (type != Constant.TYPE_MANAGER_PROJECTS){
            if (list.get(position).getProject_kols().get(0).getKol_expect_price() <= 0) {
                holder.tvPrice.setText(String.format(context.getString(R.string.card_price), String.valueOf(list.get(position).getProject_kols().get(0).getKol_price())));
            } else {
                holder.tvPrice.setText(String.format(context.getString(R.string.card_price), String.valueOf(list.get(position).getProject_kols().get(0).getKol_expect_price())));
            }
        }else {
            holder.tvPrice.setVisibility(View.GONE);
        }

        Glide.with(holder.imgPhoto).load(list.get(position).getPhoto()).into(holder.imgPhoto);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                it.setClass(context, ProjectInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(PROJECT_TYPE, type);
                bundle.putParcelable(PROJECT, getData(list.get(position)));
                it.putExtras(bundle);
                context.startActivity(it);
            }
        });
    }

    private ProjectData getData(ResponseProject.ProjectsBean bean) {
        ProjectData data = new ProjectData();
        data.setEnd_date(bean.getEnd_date());
        data.setIntroduction(bean.getIntroduction());
        data.setInvite_deadline(bean.getInvite_deadline());
        if (type != Constant.TYPE_MANAGER_PROJECTS){
            data.setKol_price(bean.getProject_kols().get(0).getKol_price());
            data.setKol_status(bean.getProject_kols().get(0).getKol_status());
            data.setKol_expect_price(bean.getProject_kols().get(0).getKol_expect_price());
        }
        data.setName(bean.getName());
        data.setProject_status(bean.getProject_status());
        data.setPhoto(bean.getPhoto());
        data.setPlace(bean.getPlace());
        data.setProject_no(bean.getProject_no());
        data.setStart_date(bean.getStart_date());
        data.setUuid(bean.getUuid());
        data.setMessenger(bean.getMessenger());
        if (bean.getBoard() != null) {
            data.setUuid_board(bean.getBoard().getUuid());
        }
        data.setPlatform(bean.getPlatform());

        return data;
    }

    @Override
    public int getItemCount() {
        if (list.size() > 0) {
            return list.size();
        } else {
            return 1;
        }
    }


    static class ProjectListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgPhoto)
        ImageView imgPhoto;
        @BindView(R.id.tvType)
        TextView tvType;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvPrice)
        TextView tvPrice;
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvDescription)
        TextView tvDescription;
        @BindView(R.id.card_empty)
        ConstraintLayout cardEmpty;
        @BindView(R.id.card)
        ConstraintLayout card;

        ProjectListViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
