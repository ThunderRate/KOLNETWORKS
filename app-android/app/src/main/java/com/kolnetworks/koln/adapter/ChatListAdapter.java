package com.kolnetworks.koln.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kolnetworks.koln.R;
import com.kolnetworks.koln.api.bean.ResponseBoards;
import com.kolnetworks.koln.api.bean.ResponseProject;
import com.kolnetworks.koln.model.ProjectData;
import com.kolnetworks.koln.util.DateUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder> {

    private Context context;
    private List<ResponseBoards.BoardBean.CommentsBean> list;
    public ChatListAdapter(Context context) {
        this.context = context;
    }
    public void setList(List<ResponseBoards.BoardBean.CommentsBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_chat, parent, false);
        return new ChatListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListViewHolder holder, int position) {
        if (list.get(position).isIs_bg()){
            holder.tvContentUser.setVisibility(View.GONE);
            holder.tvDateUser.setVisibility(View.GONE);
            holder.tvContent.setVisibility(View.VISIBLE);
            holder.tvDate.setVisibility(View.VISIBLE);
            holder.tvContent.setText(list.get(position).getContent());
            holder.tvDate.setText(DateUtil.getDate(list.get(position).getCreated_at()) + "");
        } else {
            holder.tvContentUser.setVisibility(View.VISIBLE);
            holder.tvDateUser.setVisibility(View.VISIBLE);
            holder.tvContent.setVisibility(View.GONE);
            holder.tvDate.setVisibility(View.GONE);
            holder.tvContentUser.setText(list.get(position).getContent());
            holder.tvDateUser.setText(DateUtil.getDate(list.get(position).getCreated_at()) + "");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ChatListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvContentUser)
        TextView tvContentUser;
        @BindView(R.id.tvDateUser)
        TextView tvDateUser;
        @BindView(R.id.tvContent)
        TextView tvContent;
        @BindView(R.id.tvDate)
        TextView tvDate;

        ChatListViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
