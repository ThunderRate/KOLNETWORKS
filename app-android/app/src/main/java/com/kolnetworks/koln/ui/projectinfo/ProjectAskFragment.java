package com.kolnetworks.koln.ui.projectinfo;

import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kolnetworks.koln.R;
import com.kolnetworks.koln.adapter.ChatListAdapter;
import com.kolnetworks.koln.api.bean.ResponseBoards;
import com.kolnetworks.koln.base.BaseFragment;

import java.util.List;

import butterknife.BindView;


public class ProjectAskFragment extends BaseFragment {
    @BindView(R.id.rvAsk)
    RecyclerView rvAsk;
    @BindView(R.id.bottom)
    CardView bottom;
    @BindView(R.id.title)
    TextView title;

    private ProjectViewModel viewModel;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_project_ask;
    }

    @Override
    protected void initData() {
        initViewModel();

    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);
        viewModel.getMldChatList().observe(this, list -> {
            if (list.size() > 0) {
                rvAsk.setVisibility(View.VISIBLE);
                title.setVisibility(View.GONE);
                setRecyclerView(list);
            } else {
                rvAsk.setVisibility(View.GONE);
                title.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void configViews() {

    }

    private void setRecyclerView(List<ResponseBoards.BoardBean.CommentsBean> list) {
        ChatListAdapter adapter = new ChatListAdapter(context);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        adapter.setList(list);

        rvAsk.setLayoutManager(manager);
        rvAsk.setAdapter(adapter);
    }
}