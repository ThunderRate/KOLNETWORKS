package com.kolnetworks.koln.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kolnetworks.koln.R;
import com.kolnetworks.koln.api.bean.ResponseBoards;
import com.kolnetworks.koln.api.bean.ResponseCashFlow;
import com.kolnetworks.koln.util.DateUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayAdapter extends RecyclerView.Adapter<PayAdapter.PayListViewHolder> {


    private Context context;
    private List<ResponseCashFlow.CashflowBean> list;

    public PayAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<ResponseCashFlow.CashflowBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public PayListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_pay, parent, false);
        return new PayListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PayListViewHolder holder, int position) {
        int totalMoney = list.get(position).getBasic_income() + list.get(position).getBasic_income_commission();
        holder.tvName.setText(list.get(position).getProject().getName());
        holder.tvMoney.setText(String.valueOf(totalMoney));
        holder.tvDate.setText(DateUtil.getDate(list.get(position).getCreated_at()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class PayListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.tvMoney)
        TextView tvMoney;

        PayListViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
