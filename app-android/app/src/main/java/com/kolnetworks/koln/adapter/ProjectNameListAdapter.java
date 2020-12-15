package com.kolnetworks.koln.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kolnetworks.koln.ImpAdapterClickListener;
import com.kolnetworks.koln.R;
import com.kolnetworks.koln.api.bean.ResponseManagerApplyCooperationList;
import com.kolnetworks.koln.util.DateUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProjectNameListAdapter extends RecyclerView.Adapter<ProjectNameListAdapter.NameListViewHolder> {

    ImpAdapterClickListener listener;

    private Context context;
    private List<ResponseManagerApplyCooperationList.KolsBean> list;

    public ProjectNameListAdapter(Context context, ImpAdapterClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setList(List<ResponseManagerApplyCooperationList.KolsBean> list) {
        this.list = list;
        Log.d("JJJ", "11 size : " + list.size());
    }

    @NonNull
    @Override
    public NameListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_apply_cooperation, parent, false);
        return new NameListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NameListViewHolder holder, int position) {

        if (list.get(position).getKol_status() == 6) {
            holder.btnAgree.setVisibility(View.VISIBLE);
            holder.btnDeny.setVisibility(View.VISIBLE);
            holder.tvStatus.setVisibility(View.GONE);
        } else {
            holder.btnAgree.setVisibility(View.GONE);
            holder.btnDeny.setVisibility(View.GONE);
            holder.tvStatus.setVisibility(View.VISIBLE);
        }

        if (list.get(position).getKol_status() == 4) {
            holder.tvStatus.setClickable(true);
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorGreenUpdate));
            holder.tvStatus.setOnClickListener(v -> listener.onBtnLinkCheckClick(list.get(position).getUser().getDisplay_name(), list.get(position).getProject().getUuid(), list.get(position).getUser().getUuid()));
        } else {
            holder.tvStatus.setClickable(false);
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorTextGrey));
        }

        if (list.get(position).getKol_status() == 1) {
            holder.tvStatus.setText("尚未回覆");
        } else if (list.get(position).getKol_status() == 3) {
            holder.tvStatus.setText("同意");
        } else if (list.get(position).getKol_status() == 4) {
            holder.tvStatus.setText("審核連結");
        } else if (list.get(position).getKol_status() == 5) {
            holder.tvStatus.setText("已審核");
        } else if (list.get(position).getKol_status() == 7) {
            holder.tvStatus.setText("拒絕");
        }
        holder.tvName.setText(list.get(position).getUser().getDisplay_name());
        holder.tvPrice.setText("$ " + list.get(position).getKol_price());
        if (!TextUtils.isEmpty(list.get(position).getUser().getPhoto())) {
            Glide.with(context).load(list.get(position).getUser().getPhoto()).into(holder.imgPhoto);
        }


        holder.btnAgree.setOnClickListener(v -> listener.onBtnAgreeClick(list.get(position).getProject().getUuid(), list.get(position).getUser().getUuid()));
        holder.btnDeny.setOnClickListener(v -> listener.onBtnDenyClick(list.get(position).getProject().getUuid(), list.get(position).getUser().getUuid()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class NameListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgPhoto)
        CircleImageView imgPhoto;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvPrice)
        TextView tvPrice;
        @BindView(R.id.tvStatus)
        TextView tvStatus;
        @BindView(R.id.btnAgree)
        Button btnAgree;
        @BindView(R.id.btnDeny)
        Button btnDeny;

        NameListViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
