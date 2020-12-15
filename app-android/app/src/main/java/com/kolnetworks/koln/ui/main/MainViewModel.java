package com.kolnetworks.koln.ui.main;

import android.util.Log;

import androidx.annotation.MainThread;
import androidx.lifecycle.MutableLiveData;

import com.kolnetworks.koln.api.ExceptionHandle;
import com.kolnetworks.koln.api.MyObserver;
import com.kolnetworks.koln.api.bean.ResDetail;
import com.kolnetworks.koln.api.bean.ResponseCashFlow;
import com.kolnetworks.koln.api.bean.ResponseManagerProjects;
import com.kolnetworks.koln.api.bean.ResponseProject;
import com.kolnetworks.koln.api.bean.ResponsePutUser;
import com.kolnetworks.koln.api.bean.ResponseUser;
import com.kolnetworks.koln.base.BaseViewModel;
import com.kolnetworks.koln.data.SPApi;
import com.kolnetworks.koln.model.UserAccountBody;
import com.kolnetworks.koln.repository.DataRepository;
import com.kolnetworks.koln.util.DateUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Function4;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import rx.Scheduler;

public class MainViewModel extends BaseViewModel {
    private MutableLiveData<String> mText;
    private MutableLiveData<String> mldPhoto;
    private MutableLiveData<List<ResponseProject.ProjectsBean>> mldAroundGoAround;
    private MutableLiveData<List<ResponseProject.ProjectsBean>> mldAroundUniq;
    private MutableLiveData<List<ResponseProject.ProjectsBean>> mldAroundApplies;
    private MutableLiveData<List<ResponseProject.ProjectsBean>> mldMissionDoing;
    private MutableLiveData<List<ResponseProject.ProjectsBean>> mldMissionFinish;
    private MutableLiveData<List<ResponseProject.ProjectsBean>> mldMissionNotStart;
    private MutableLiveData<List<ResponseManagerProjects.ProjectsBean>> mldManagerProjects;
    private MutableLiveData<ResponseCashFlow> mldCashFlow;
    private MutableLiveData<ResponsePutUser.UserBean> mldUserBank;
    private MutableLiveData<Boolean> mldBankUpdateFinish;
    private MutableLiveData<Boolean> mldShowBtmNavBadge1;
    private MutableLiveData<Boolean> mldShowBtmNavBadge2;
    private MutableLiveData<ResponseUser.UserBean> mldUser;
    private MutableLiveData<String> mldApiError;
    private MutableLiveData<ResDetail> mldResDetail;

    public MutableLiveData<ResDetail> getMldResDetail() {
        if (mldResDetail == null) {
            mldResDetail = new MutableLiveData<>();
        }
        return mldResDetail;
    }


    public MutableLiveData<String> getMldApiError() {
        if (mldApiError == null) {
            mldApiError = new MutableLiveData<>();
        }
        return mldApiError;
    }

    public MutableLiveData<String> getMldPhoto() {
        if (mldPhoto == null) {
            mldPhoto = new MutableLiveData<>();
        }
        return mldPhoto;
    }

    public MutableLiveData<Boolean> getMldShowBtmNavBadge1() {
        if (mldBankUpdateFinish == null) {
            mldBankUpdateFinish = new MutableLiveData<>();
        }
        return mldBankUpdateFinish;
    }

    public MutableLiveData<Boolean> getMldShowBtmNavBadge2() {
        if (mldBankUpdateFinish == null) {
            mldBankUpdateFinish = new MutableLiveData<>();
        }
        return mldBankUpdateFinish;
    }

    public MutableLiveData<Boolean> getMldBankUpdateFinish() {
        if (mldBankUpdateFinish == null) {
            mldBankUpdateFinish = new MutableLiveData<>();
        }
        return mldBankUpdateFinish;
    }

    public MutableLiveData<List<ResponseProject.ProjectsBean>> getMldAroundGoAround() {
        if (mldAroundGoAround == null) {
            mldAroundGoAround = new MutableLiveData<>();
        }
        return mldAroundGoAround;
    }

    public MutableLiveData<List<ResponseProject.ProjectsBean>> getMldAroundUniq() {
        if (mldAroundUniq == null) {
            mldAroundUniq = new MutableLiveData<>();
        }
        return mldAroundUniq;
    }

    public MutableLiveData<List<ResponseProject.ProjectsBean>> getMldAroundApplies() {
        if (mldAroundApplies == null) {
            mldAroundApplies = new MutableLiveData<>();
        }
        return mldAroundApplies;
    }

    public MutableLiveData<List<ResponseProject.ProjectsBean>> getMldMissionDoing() {
        if (mldMissionDoing == null) {
            mldMissionDoing = new MutableLiveData<>();
        }
        return mldMissionDoing;
    }

    public MutableLiveData<List<ResponseProject.ProjectsBean>> getMldMissionFinish() {
        if (mldMissionFinish == null) {
            mldMissionFinish = new MutableLiveData<>();
        }
        return mldMissionFinish;
    }

    public MutableLiveData<List<ResponseProject.ProjectsBean>> getMldMissionNotStart() {
        if (mldMissionNotStart == null) {
            mldMissionNotStart = new MutableLiveData<>();
        }
        return mldMissionNotStart;
    }

    public MutableLiveData<List<ResponseProject.ProjectsBean>> getMldManagerProjects() {
        if (mldMissionNotStart == null) {
            mldMissionNotStart = new MutableLiveData<>();
        }
        return mldMissionNotStart;
    }

    public MutableLiveData<ResponseCashFlow> getMldCashFlow() {
        if (mldCashFlow == null) {
            mldCashFlow = new MutableLiveData<>();
        }
        return mldCashFlow;
    }

    public MutableLiveData<ResponseUser.UserBean> getMldUser() {
        if (mldUser == null) {
            mldUser = new MutableLiveData<>();
        }
        return mldUser;
    }

    public MutableLiveData<ResponsePutUser.UserBean> getMldUserBank() {
        if (mldUserBank == null) {
            mldUserBank = new MutableLiveData<>();
        }
        return mldUserBank;
    }

    // 找專案-申請合作
    public void loadAroundGoAround(int type) {
        DataRepository.getInstance()
                .fetchAroundGoAround()
                .subscribe(new MyObserver<List<ResponseProject.ProjectsBean>>() {
                    @Override
                    public void onSuccess(List<ResponseProject.ProjectsBean> projectsBeans) {
                        Collections.sort(projectsBeans, (o1, o2) -> o2.getCreated_at() - o1.getCreated_at());
                        getMldAroundGoAround().postValue(projectsBeans);
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }

    public void judgeFindProjectBadge() {
        Observable.zip(
                DataRepository.getInstance().fetchAroundGoAround(),
                DataRepository.getInstance().fetchAroundUniq(),
                new BiFunction<List<ResponseProject.ProjectsBean>, List<ResponseProject.ProjectsBean>, List<ResponseProject.ProjectsBean>>() {
                    @Override
                    public List<ResponseProject.ProjectsBean> apply(List<ResponseProject.ProjectsBean> projectsBeans, List<ResponseProject.ProjectsBean> projectsBeans2) throws Exception {
                        List<ResponseProject.ProjectsBean> list = new ArrayList<>();
                        list.addAll(projectsBeans);
                        list.addAll(projectsBeans2);

                        Collections.sort(list, (o1, o2) -> o2.getCreated_at() - o1.getCreated_at());
                        return list;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<List<ResponseProject.ProjectsBean>>() {
                    @Override
                    public void onSuccess(List<ResponseProject.ProjectsBean> beans) {
                        ArrayList<Integer> list = new ArrayList<>();
                        for (int i = 0; i < beans.size(); i++) {
                            list.add(beans.get(i).getProject_id());
                        }
                        int beforeListSize = SPApi.getProjectList("FIND").size();
                        int nowListSize =list.size();
                        if (beforeListSize != nowListSize){
                            getMldShowBtmNavBadge1().postValue(true);
                        }else {
                            getMldShowBtmNavBadge1().postValue(false);
                        }
                        SPApi.putProjectList("FIND", list);
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {

                    }
                });
    }

    // 找專案-專屬邀約
    public void loadAroundUniq(int type) {
        DataRepository.getInstance()
                .fetchAroundUniq()
                .subscribe(new MyObserver<List<ResponseProject.ProjectsBean>>() {
                    @Override
                    public void onSuccess(List<ResponseProject.ProjectsBean> projectsBeans) {
                        Collections.sort(projectsBeans, (o1, o2) -> o2.getCreated_at() - o1.getCreated_at());
                        getMldAroundUniq().postValue(projectsBeans);
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }

    // 我的專案-已申請
    public void loadAroundApply(int type) {
        Observable.zip(
                DataRepository.getInstance().fetchAroundApply("2,3,4,5,6", "6"),
                DataRepository.getInstance().fetchAroundApply("2,3,4,5,6", "7"),
                new BiFunction<List<ResponseProject.ProjectsBean>, List<ResponseProject.ProjectsBean>, List<ResponseProject.ProjectsBean>>() {
                    @Override
                    public List<ResponseProject.ProjectsBean> apply(List<ResponseProject.ProjectsBean> projectsBeans, List<ResponseProject.ProjectsBean> projectsBeans2) throws Exception {
                        List<ResponseProject.ProjectsBean> list = new ArrayList<>();
                        list.addAll(projectsBeans);
                        list.addAll(projectsBeans2);

                        Collections.sort(list, (o1, o2) -> o2.getCreated_at() - o1.getCreated_at());
                        return list;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<List<ResponseProject.ProjectsBean>>() {
                    @Override
                    public void onSuccess(List<ResponseProject.ProjectsBean> projectsBeans) {
                        Collections.sort(projectsBeans, (o1, o2) -> o2.getCreated_at() - o1.getCreated_at());
                        getMldAroundApplies().postValue(projectsBeans);
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }

    // 我的專案-進行 (合作確認)
    public void loadMissionDoing(int type) {
        Observable.zip(
                DataRepository.getInstance().fetchMissionNotStart("2,3", "3"),
                DataRepository.getInstance().fetchMissionDoing("4", "3,4,5"),
                DataRepository.getInstance().fetchMissionDoing("5", "3,4,5"),
                DataRepository.getInstance().fetchMissionNotStart("6", "3,4,5"),
                new Function4<List<ResponseProject.ProjectsBean>, List<ResponseProject.ProjectsBean>, List<ResponseProject.ProjectsBean>, List<ResponseProject.ProjectsBean>, List<ResponseProject.ProjectsBean>>() {
                    @Override
                    public List<ResponseProject.ProjectsBean> apply(List<ResponseProject.ProjectsBean> projectsBeans, List<ResponseProject.ProjectsBean> projectsBeans2, List<ResponseProject.ProjectsBean> projectsBeans3, List<ResponseProject.ProjectsBean> projectsBeans4) throws Exception {
                        List<ResponseProject.ProjectsBean> list = new ArrayList<>();
                        list.addAll(projectsBeans);
                        list.addAll(projectsBeans2);
                        list.addAll(projectsBeans3);
                        list.addAll(projectsBeans4);

                        Collections.sort(list, (o1, o2) -> o2.getCreated_at() - o1.getCreated_at());
                        return list;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<List<ResponseProject.ProjectsBean>>() {
                    @Override
                    public void onSuccess(List<ResponseProject.ProjectsBean> projectsBeans) {
                        getMldMissionDoing().postValue(projectsBeans);

                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        getMldApiError().postValue(throwable.message);
                    }
                });


    }

    // 我的專案-結束
    public void loadMissionFinish(int type) {
        DataRepository.getInstance().fetchMissionFinish("7", "5")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<List<ResponseProject.ProjectsBean>>() {
                    @Override
                    public void onSuccess(List<ResponseProject.ProjectsBean> projectsBeans) {
                        getMldMissionFinish().postValue(projectsBeans);
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }

    // 任務-尚未開始
    public void loadMissionNotStart(int type) {
        Observable.zip(
                DataRepository.getInstance().fetchMissionNotStart("2,3", "3"),
                DataRepository.getInstance().fetchMissionNotStart("3", "6"),
                new BiFunction<List<ResponseProject.ProjectsBean>, List<ResponseProject.ProjectsBean>, List<ResponseProject.ProjectsBean>>() {
                    @Override
                    public List<ResponseProject.ProjectsBean> apply(List<ResponseProject.ProjectsBean> projectsBeans, List<ResponseProject.ProjectsBean> projectsBeans2) throws Exception {
                        List<ResponseProject.ProjectsBean> list = new ArrayList<>();
                        list.addAll(projectsBeans);
                        list.addAll(projectsBeans2);

                        Collections.sort(list, (o1, o2) -> o2.getCreated_at() - o1.getCreated_at());
                        return list;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<List<ResponseProject.ProjectsBean>>() {
                    @Override
                    public void onSuccess(List<ResponseProject.ProjectsBean> projectsBeans) {
                        getMldMissionNotStart().postValue(projectsBeans);
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }

    /**
     * 經理專案(我的專案)
     */
    public void fetchManagerProjects() {
        DataRepository.getInstance()
                .fetchManagerProjects()
                .subscribe(new MyObserver<List<ResponseProject.ProjectsBean>>() {
                    @Override
                    public void onSuccess(List<ResponseProject.ProjectsBean> beans) {
                        getMldManagerProjects().postValue(beans);
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }

    // 使用者資料
    public void loadUser() {
        DataRepository.getInstance()
                .fetchUser()
                .subscribe(new MyObserver<ResponseUser.UserBean>() {
                    @Override
                    public void onSuccess(ResponseUser.UserBean userBean) {
                        getMldUser().postValue(userBean);
                        SPApi.putuPhoto(userBean.getPhoto());
                        SPApi.putUserUuid(userBean.getUuid());
                        getMldPhoto().postValue(userBean.getPhoto());
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }

    // 取得收益
    public void loadCashFlow() {
        DataRepository.getInstance()
                .fetchCashFlow()
                .subscribe(new MyObserver<ResponseCashFlow>() {
                    @Override
                    public void onSuccess(ResponseCashFlow cashflowBeans) {
                        getMldCashFlow().postValue(cashflowBeans);
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }

    public void updateBankAccount(String code, String branch, String name, String account) {
        UserAccountBody body = new UserAccountBody();
        body.setBank_code(code);
        body.setBranch_code(branch);
        body.setAccount_name(name);
        body.setAccount_no(account);

        DataRepository.getInstance().updateUserBankAccount(body)
                .subscribe(new MyObserver<ResponsePutUser.UserBean>() {
                    @Override
                    public void onSuccess(ResponsePutUser.UserBean userBean) {
                        getMldBankUpdateFinish().postValue(true);
                        getMldUserBank().postValue(userBean);
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }

    //FCM enter
    public void fetchDetails(String uuid) {
        DataRepository.getInstance()
                .fetchProjectsDetail(uuid)
                .subscribe(new MyObserver<ResDetail>() {
                    @Override
                    public void onSuccess(ResDetail res) {
                        if (res.isSuccess()){
                            mldResDetail.postValue(res);
                        }

                        Log.d("JJJ", " res: " + res.toString());
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {

                    }
                });
    }


}
