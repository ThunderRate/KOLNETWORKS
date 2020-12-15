package com.kolnetworks.koln.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kolnetworks.koln.Constant;
import com.kolnetworks.koln.R;
import com.kolnetworks.koln.api.bean.ResDetail;
import com.kolnetworks.koln.base.BaseActivity;
import com.kolnetworks.koln.model.ProjectData;
import com.kolnetworks.koln.ui.projectinfo.ProjectInfoActivity;

import butterknife.BindView;
import q.rorbin.badgeview.QBadgeView;

import static com.kolnetworks.koln.adapter.CardViewListAdapter.PROJECT;
import static com.kolnetworks.koln.adapter.CardViewListAdapter.PROJECT_TYPE;

public class MainActivity extends BaseActivity {
    MainViewModel viewModel;

    @BindView(R.id.nav_view)
    BottomNavigationView navView;
    @BindView(R.id.container)
    ConstraintLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        initViewModel();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);


    }

    @Override
    protected void onResume() {
        super.onResume();


        if (getIntent().getBundleExtra("bundle") != null) {
            Bundle bundle = getIntent().getBundleExtra("bundle");
            assert bundle != null;
            String uuid = bundle.getString("uuid");
            kolNotify = bundle.getInt("kolNotify", 0);

            Log.d("JJJ", "uuid : " + uuid);
            Log.d("JJJ", "kolNotify : " + kolNotify);

            // TODO: 2020/8/24 接到 UUID
            assert uuid != null;
            if (!uuid.equals("")){
                Log.d("JJJ", "fetchDetails !!! ");
                viewModel.fetchDetails(uuid);
            }
            //viewModel.fetchDetails("cd66a4e0-d6f1-11ea-98f0-09cd938c7b54");
        }

    }
    int kolNotify = 0;
    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.loadUser();

        viewModel.judgeFindProjectBadge();

        viewModel.getMldShowBtmNavBadge1().observe(this, shouldShowBadge -> {
            if (!shouldShowBadge) {
                //showBadgeView(0,1);
            }
        });

        viewModel.getMldShowBtmNavBadge2().observe(this, shouldShowBadge -> {

        });

        viewModel.getMldResDetail().observe(this, resDetail -> {
            Intent it = new Intent();
            it.setClass(this, ProjectInfoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt(PROJECT_TYPE, kolNotify);
            bundle.putParcelable(PROJECT, getData(resDetail));
            it.putExtras(bundle);
            startActivity(it);
        });
    }

    private Parcelable getData(ResDetail resDetail) {
        ResDetail.ProjectBean bean = resDetail.getProject();
        ProjectData data = new ProjectData();
        data.setEnd_date(bean.getEnd_date());
        data.setIntroduction(bean.getIntroduction());
        data.setInvite_deadline(bean.getInvite_deadline());
        data.setKol_price(bean.getProject_kols().get(0).getKol_price());
        data.setKol_status(bean.getProject_kols().get(0).getKol_status());
        //data.setKol_expect_price(Integer.parseInt(bean.getProject_kols().get(0).getKol_expect_price()));
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
    protected void configViews() {

    }

    private void showBadgeView(int viewIndex, int showNumber) {
        // 具體child的查找和view的嵌套結構請在源碼中查看
        // 從bottomNavigationView中獲得BottomNavigationMenuView
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navView.getChildAt(0);
        // 從BottomNavigationMenuView中獲得childview, BottomNavigationItemView
        if (viewIndex < menuView.getChildCount()) {
            // 獲得viewIndex對應子tab
            View view = menuView.getChildAt(viewIndex);
            // 從子tab中獲得其中顯示圖片的ImageView
            View icon = view.findViewById(R.id.navigation_around);
            // 獲得圖標的寬度
            int iconWidth = icon.getWidth();
            Log.d("JJJ", "iconWidth : " + iconWidth);
            // 獲得tab的寬度/2
            int tabWidth = view.getWidth() / 2;
            Log.d("JJJ", "tabWidth : " + tabWidth);
            // 計算badge要距離右邊的距離
            int spaceWidth = tabWidth - iconWidth;
            Log.d("JJJ", "spaceWidth : " + spaceWidth);

            // 顯示badegeview
            new QBadgeView(this).bindTarget(view).setGravityOffset(spaceWidth + 500, 3, false).bindTarget(view);
        }
    }
}