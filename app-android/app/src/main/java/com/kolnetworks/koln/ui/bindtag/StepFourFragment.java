package com.kolnetworks.koln.ui.bindtag;

import android.util.Log;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.chip.ChipGroup;
import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxCompoundButton;
import com.kolnetworks.koln.R;
import com.kolnetworks.koln.api.bean.ResponseTags;
import com.kolnetworks.koln.base.BaseFragment;
import com.kolnetworks.koln.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;


public class StepFourFragment extends BaseFragment {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_age)
    TextView titleAge;
    @BindView(R.id.cgAge)
    ChipGroup cgAgeIa2;
    @BindView(R.id.title_area)
    TextView titleArea;
    @BindView(R.id.cgArea)
    ChipGroup cgAreaIa4;
    @BindView(R.id.title_social)
    TextView titleSocial;
    @BindView(R.id.cgSocial)
    ChipGroup cgSocialIa5;
    @BindView(R.id.title_content)
    TextView titleContent;
    @BindView(R.id.cgContent)
    ChipGroup cgContentIa6;
    @BindView(R.id.title_job)
    TextView titleJob;
    @BindView(R.id.cgJob)
    ChipGroup cgJobIa7;
    @BindView(R.id.title_personal)
    TextView titlePersonal;
    @BindView(R.id.cgPersonal)
    ChipGroup cgPersonalIa8;
    @BindView(R.id.btnNext)
    TextView btnNext;
    @BindView(R.id.btnPre)
    TextView btnPre;

    private NavController navController;
    private BindTagViewModel viewModel;
    private HashMap<Integer, Integer> mapChipTagId = new HashMap<>();


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_step_four;
    }

    @Override
    protected void initData() {
        navController = NavHostFragment.findNavController(this);
        initViewModel();


    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(BindTagViewModel.class);

        viewModel.clearTagsList();

        viewModel.getTag("2");
        viewModel.getTag("4");
        viewModel.getTag("5");
        viewModel.getTag("6");
        viewModel.getTag("7");
        viewModel.getTag("8");

        viewModel.getMldIa2Order1().observe(this, tags -> {
            //tagMapIa2.clear();
            cgAgeIa2.removeAllViews();
            for (int i = 0; i < tags.size(); i++) {
                setChip(cgAgeIa2, tags.get(i) , 200 + i);
            }
        });
        viewModel.getMldIa4Order2().observe(this, tags -> {
            //tagMapIa3.clear();
            cgAreaIa4.removeAllViews();
            for (int i = 0; i < tags.size(); i++) {
                setChip(cgAreaIa4, tags.get(i), 400 + i);
            }
        });
        viewModel.getMldIa5Order3().observe(this, tags -> {
            //tagMapIa5.clear();
            cgSocialIa5.removeAllViews();
            for (int i = 0; i < tags.size(); i++) {
                setChip(cgSocialIa5, tags.get(i), 500 + i);
            }
        });
        viewModel.getMldIa6Order4().observe(this, tags -> {
            //tagMapIa6.clear();
            cgContentIa6.removeAllViews();
            for (int i = 0; i < tags.size(); i++) {
                setChip(cgContentIa6, tags.get(i), 600 + i);
            }
        });
        viewModel.getMldIa7Order5().observe(this, tags -> {
            //tagMapIa7.clear();
            cgJobIa7.removeAllViews();
            for (int i = 0; i < tags.size(); i++) {
                setChip(cgJobIa7, tags.get(i), 700 + i);
            }
        });
        viewModel.getMldIa8Order6().observe(this, tags -> {
            //tagMapIa8.clear();
            cgPersonalIa8.removeAllViews();
            for (int i = 0; i < tags.size(); i++) {
                setChip(cgPersonalIa8, tags.get(i), 800 + i);
            }
        });

        viewModel.getMldCanFinishStep4().observe(this, canFinish ->{
            if (canFinish){
                ((BindTagActivity)requireActivity()).dismissProgressDialog();
                navController.navigate(R.id.action_stepFourFragment_to_stepFiveFragment);
            }
        });

        viewModel.getMldApiError().observe(this, msg->{
            ((BindTagActivity)requireActivity()).dismissProgressDialog();
            ToastUtil.showFailedImgInToast(msg);
        });

        viewModel.getMldShowAlertToast().observe(this, show ->{
            if (show){
                ((BindTagActivity)requireActivity()).dismissProgressDialog();
                ToastUtil.showFailedImgInToast("所有欄位都要選取喔！");
            }
        });
    }

    @Override
    protected void configViews() {

        ((BindTagActivity) getActivity()).addDisposable(
                RxView.clicks(btnPre)
                        .throttleFirst(1, TimeUnit.SECONDS)
                        .subscribe(v -> {
                            navController.popBackStack();
                        })
        );

        ((BindTagActivity) getActivity()).addDisposable(
                RxView.clicks(btnNext)
                        .throttleFirst(1, TimeUnit.SECONDS)
                        .subscribe(v -> {
                            ((BindTagActivity)requireActivity()).showProgressDialog("");
                            viewModel.updateUserAndTagData();
                            //viewModel.updateUserTags();
                        })
        );
    }

    private void setChip(ChipGroup chipGroup, ResponseTags.TagsBean tag, int chipId) {
        LayoutInflater inflater = LayoutInflater.from(context);
        CheckBox chip = (CheckBox) inflater.inflate(R.layout.item_chip, chipGroup, false);
        chip.setId(chipId);
        chip.setText(tag.getName());
        mapChipTagId.put(chipId, tag.getTag_id());

        ((BindTagActivity) getActivity()).addDisposable(RxCompoundButton.checkedChanges(chip)
                .subscribe(v -> {
                    addTagIdToList(chip.isChecked() , chipId);
                }));
        chipGroup.addView(chip);
    }

    private void addTagIdToList(boolean isAdd, int chipId) {
        viewModel.setUserTagData(isAdd,chipId, mapChipTagId.get(chipId));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}