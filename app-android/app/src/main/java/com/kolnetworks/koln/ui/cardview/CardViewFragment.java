package com.kolnetworks.koln.ui.cardview;

import android.util.Log;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.common.util.CollectionUtils;
import com.kolnetworks.koln.Constant;
import com.kolnetworks.koln.R;
import com.kolnetworks.koln.adapter.CardViewListAdapter;
import com.kolnetworks.koln.api.bean.ResponseProject;
import com.kolnetworks.koln.base.BaseFragment;
import com.kolnetworks.koln.data.SPApi;
import com.kolnetworks.koln.ui.main.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CardViewFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    MainViewModel viewModel;

    int type;


    public CardViewFragment(int type) {
        this.type = type;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_card_view;
    }

    @Override
    protected void initData() {
        initViewModel();


    }

    private void callApiGetProjectsData() {
        switch (type) {
            case Constant.TYPE_FOUND_UNIQ:
                viewModel.loadAroundUniq(type);
                break;
            case Constant.TYPE_FOUND_APPLY_COOPERATION:
                viewModel.loadAroundGoAround(type);
                break;
            case Constant.TYPE_MY_APPLIED:
                viewModel.loadAroundApply(type);
                break;
            case Constant.TYPE_MY_CONFIRM:
                viewModel.loadMissionDoing(type);
                break;
            case Constant.TYPE_MY_FINISH:
                viewModel.loadMissionFinish(type);
                break;
            case Constant.TYPE_MANAGER_PROJECTS:
                viewModel.fetchManagerProjects();
                break;
        }
    }

    @Override
    protected void configViews() {

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callApiGetProjectsData();
            }
        });

    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewModel.getMldAroundUniq().observe(this, list -> {
            setRecyclerView(Constant.TYPE_FOUND_UNIQ, list);
            compareWithSPData(String.valueOf(Constant.TYPE_FOUND_UNIQ), list);
            dismissSwipe();
        });

        viewModel.getMldAroundGoAround().observe(this, list -> {
            setRecyclerView(Constant.TYPE_FOUND_APPLY_COOPERATION, list);
            compareWithSPData(String.valueOf(Constant.TYPE_FOUND_APPLY_COOPERATION), list);
            dismissSwipe();
        });

        viewModel.getMldAroundApplies().observe(this, list -> {
            setRecyclerView(Constant.TYPE_MY_APPLIED, list);
            compareWithSPData(String.valueOf(Constant.TYPE_MY_APPLIED), list);
            dismissSwipe();
        });

        viewModel.getMldMissionDoing().observe(this, list -> {
            setRecyclerView(Constant.TYPE_MY_CONFIRM, list);
            compareWithSPData(String.valueOf(Constant.TYPE_MY_CONFIRM), list);
            dismissSwipe();
        });

        viewModel.getMldMissionFinish().observe(this, list -> {
            setRecyclerView(Constant.TYPE_MY_FINISH, list);
            compareWithSPData(String.valueOf(Constant.TYPE_MY_FINISH), list);
            dismissSwipe();
        });

        viewModel.getMldManagerProjects().observe(this, list -> {
            setRecyclerView(Constant.TYPE_MANAGER_PROJECTS, list);
            compareWithSPData(String.valueOf(Constant.TYPE_MANAGER_PROJECTS), list);
            dismissSwipe();
        });
    }

    private void compareWithSPData(String type, List<ResponseProject.ProjectsBean> list) {
//        ArrayList<Integer> idList = new ArrayList<>();
//        for (int i = 0; i < list.size(); i++) {
//            idList.add(list.get(i).getProject_id());
//        }
//
//        if (list.size() != SPApi.getProjectList(String.valueOf(type)).size()) {
//            // 點點出現
//        } else if (idList.retainAll(SPApi.getProjectList(type))) {
//            // 點點出現
//        } else {
//            // 點點消失
//        }
//
//        SPApi.putProjectList(String.valueOf(type), idList);
    }

    private void dismissSwipe() {
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
    }

    private void setRecyclerView(int type, List<ResponseProject.ProjectsBean> list) {
        CardViewListAdapter adapter = new CardViewListAdapter(context);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        adapter.setList(type, list);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        callApiGetProjectsData();
    }
}