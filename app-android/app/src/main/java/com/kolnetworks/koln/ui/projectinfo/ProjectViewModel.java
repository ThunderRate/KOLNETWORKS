package com.kolnetworks.koln.ui.projectinfo;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.kolnetworks.koln.api.ExceptionHandle;
import com.kolnetworks.koln.api.MyObserver;
import com.kolnetworks.koln.api.bean.ResponseBoards;
import com.kolnetworks.koln.api.bean.ResponseGetReportLink;
import com.kolnetworks.koln.api.bean.ResponseManagerApplyCooperationList;
import com.kolnetworks.koln.base.BaseViewModel;
import com.kolnetworks.koln.model.AnswerBody;
import com.kolnetworks.koln.model.ApplyBody;
import com.kolnetworks.koln.model.ChangeStatusBody;
import com.kolnetworks.koln.model.NameListBody;
import com.kolnetworks.koln.model.ProjectData;
import com.kolnetworks.koln.model.LinkCheckBody;
import com.kolnetworks.koln.model.SubmitBody;
import com.kolnetworks.koln.repository.DataRepository;
import com.kolnetworks.koln.util.ToastUtil;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class ProjectViewModel extends BaseViewModel {

    private MutableLiveData<ProjectData> mldProjectData;
    //    private MutableLiveData<ProjectData> mldProjectData;
    private MutableLiveData<Boolean> mldCanFinish;
    private MutableLiveData<Boolean> mldAgreeKol;
    private MutableLiveData<Boolean> mldRejectKol;
    private MutableLiveData<Boolean> mldAgreeLink;
    private MutableLiveData<Boolean> mldRejectLink;
    private MutableLiveData<Boolean> mldCanFinishReject;
    private MutableLiveData<Boolean> mldCanFinishSendAgree;
    private MutableLiveData<Boolean> mldCanFinishSendLink;
    private MutableLiveData<Boolean> mldChangeProjectStatus;
    private MutableLiveData<List<ResponseManagerApplyCooperationList.KolsBean>> mldCooperationList;
    private MutableLiveData<List<ResponseManagerApplyCooperationList.KolsBean>> mldInviteList;
    private MutableLiveData<String> mldApiError;
    private MutableLiveData<ResponseGetReportLink.ReportsBean> mldReportLink;
    private MutableLiveData<List<ResponseBoards.BoardBean.CommentsBean>> mldChatList;

    public MutableLiveData<ProjectData> getMldProjectData() {
        if (mldProjectData == null) {
            mldProjectData = new MutableLiveData<>();
        }
        return mldProjectData;
    }

    public MutableLiveData<Boolean> getMldAgreeKol() {
        if (mldAgreeKol == null) {
            mldAgreeKol = new MutableLiveData<>();
        }
        return mldAgreeKol;
    }

    public MutableLiveData<Boolean> getMldRejectKol() {
        if (mldRejectKol == null) {
            mldRejectKol = new MutableLiveData<>();
        }
        return mldRejectKol;
    }

    public MutableLiveData<Boolean> getMldAgreeLink() {
        if (mldAgreeLink == null) {
            mldAgreeLink = new MutableLiveData<>();
        }
        return mldAgreeLink;
    }

    public MutableLiveData<Boolean> getMldRejectLink() {
        if (mldRejectLink == null) {
            mldRejectLink = new MutableLiveData<>();
        }
        return mldRejectLink;
    }

    public MutableLiveData<Boolean> getMldCanFinishReject() {
        if (mldCanFinishReject == null) {
            mldCanFinishReject = new MutableLiveData<>();
        }
        return mldCanFinishReject;
    }

    public MutableLiveData<Boolean> getMldChangeProjectStatus() {
        if (mldChangeProjectStatus == null) {
            mldChangeProjectStatus = new MutableLiveData<>();
        }
        return mldChangeProjectStatus;
    }

    public MutableLiveData<List<ResponseManagerApplyCooperationList.KolsBean>> getMldCooperationList() {
        if (mldCooperationList == null) {
            mldCooperationList = new MutableLiveData<>();
        }
        return mldCooperationList;
    }

    public MutableLiveData<List<ResponseManagerApplyCooperationList.KolsBean>> getMldInviteList() {
        if (mldInviteList == null) {
            mldInviteList = new MutableLiveData<>();
        }
        return mldInviteList;
    }


    public MutableLiveData<Boolean> getMldCanFinishAgree() {
        if (mldCanFinishSendAgree == null) {
            mldCanFinishSendAgree = new MutableLiveData<>();
        }
        return mldCanFinishSendAgree;
    }

    public MutableLiveData<Boolean> getMldCanFinishSendLink() {
        if (mldCanFinishSendLink == null) {
            mldCanFinishSendLink = new MutableLiveData<>();
        }
        return mldCanFinishSendLink;
    }

    public MutableLiveData<Boolean> getMldCanFinishAttend() {
        if (mldCanFinish == null) {
            mldCanFinish = new MutableLiveData<>();
        }
        return mldCanFinish;
    }

    public MutableLiveData<List<ResponseBoards.BoardBean.CommentsBean>> getMldChatList() {
        if (mldChatList == null) {
            mldChatList = new MutableLiveData<>();
        }
        return mldChatList;
    }

    public MutableLiveData<String> getMldApiError() {
        if (mldApiError == null) {
            mldApiError = new MutableLiveData<>();
        }
        return mldApiError;
    }

    public MutableLiveData<ResponseGetReportLink.ReportsBean> getMldReportLink() {
        if (mldReportLink == null) {
            mldReportLink = new MutableLiveData<>();
        }
        return mldReportLink;
    }

//    public void setData(ProjectData data){
//        getMldProjectData().postValue(data);
//    }


    public void loadAgree(String project_uuid, String kol_uuid) {
        AnswerBody body = new AnswerBody();
        body.setKol_uuid(kol_uuid);
        body.setProject_uuid(project_uuid);

        DataRepository.getInstance()
                .fetchAgree(body)
                .subscribe(new DisposableObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean isSuccess) {
                        if (isSuccess) {
                            getMldCanFinishAgree().postValue(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getManagerApplyCooperationList(String project_uuid) {
        DataRepository.getInstance()
                .fetchApplyCooperationList(project_uuid)
                .subscribe(new MyObserver<List<ResponseManagerApplyCooperationList.KolsBean>>() {
                    @Override
                    public void onSuccess(List<ResponseManagerApplyCooperationList.KolsBean> kolsBeans) {
                        getMldCooperationList().postValue(kolsBeans);
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }

    public void getManagerInviteList(String project_uuid) {
        DataRepository.getInstance()
                .fetchInviteList(project_uuid)
                .subscribe(new MyObserver<List<ResponseManagerApplyCooperationList.KolsBean>>() {
                    @Override
                    public void onSuccess(List<ResponseManagerApplyCooperationList.KolsBean> kolsBeans) {
                        getMldInviteList().postValue(kolsBeans);
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }

    // TODO: 2020/7/9 type
    public void putChangeProjectStatus(String project_uuid) {
        ChangeStatusBody body = new ChangeStatusBody();
        body.setProject_uuid(project_uuid);
        body.setProject_status(3);
        DataRepository.getInstance()
                .putProjectStatus(body)
                .subscribe(new MyObserver<String>() {

                    @Override
                    public void onSuccess(String s) {
                        getMldChangeProjectStatus().postValue(true);
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }



    public void loadReject(String project_uuid, String kol_uuid) {
        AnswerBody body = new AnswerBody();
        body.setKol_uuid(kol_uuid);
        body.setProject_uuid(project_uuid);

        DataRepository.getInstance()
                .fetchReject(body)
                .subscribe(new MyObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean isSuccess) {
                        if (isSuccess) {
                            getMldCanFinishReject().postValue(true);
                        }
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }

    public void loadAttend(String project_uuid, int price) {
        ApplyBody body = new ApplyBody();
        body.setProject_uuid(project_uuid);
        body.setKol_expect_price(price);

        DataRepository.getInstance()
                .fetchApply(body)
                .subscribe(new MyObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean isSuccess) {
                        if (isSuccess) {
                            getMldCanFinishAttend().postValue(true);
                        }
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }

    public void loadSendLink(String project_uuid, String url) {
        SubmitBody body = new SubmitBody();
        body.setProject_uuid(project_uuid);
        body.setReport(url);

        DataRepository.getInstance()
                .fetchSubmit(body)
                .subscribe(new MyObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean isSuccess) {
                        if (isSuccess) {
                            getMldCanFinishSendLink().postValue(true);
                        }
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }

    public void loadBoards(String board_uuid) {
        DataRepository.getInstance()
                .fetchBoards(board_uuid)
                .subscribe(new MyObserver<List<ResponseBoards.BoardBean.CommentsBean>>() {
                    @Override
                    public void onSuccess(List<ResponseBoards.BoardBean.CommentsBean> list) {
                        getMldChatList().postValue(list);
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }


    public void fetchAgreeKol(String project_uuid, String kol_uuid) {
        NameListBody body = new NameListBody();
        body.setKol_uuid(kol_uuid);
        body.setProject_uuid(project_uuid);

        DataRepository.getInstance()
                .fetchAgreeKol(body)
                .subscribe(new MyObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        getMldAgreeKol().postValue(true);
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        ToastUtil.showToast(throwable.message);
                    }
                });
    }

    public void fetchRejectKol(String project_uuid, String kol_uuid) {
        NameListBody body = new NameListBody();
        body.setKol_uuid(kol_uuid);
        body.setProject_uuid(project_uuid);

        DataRepository.getInstance()
                .fetchRejectKol(body)
                .subscribe(new MyObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        getMldRejectKol().postValue(true);
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        ToastUtil.showToast(throwable.message);
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }

    public void fetchRejectLink(String project_uuid, String kol_uuid) {
        LinkCheckBody body = new LinkCheckBody();
        body.setUser_uuid(kol_uuid);
        body.setProject_uuid(project_uuid);
        body.setComments(0);
        body.setLikes(0);

        DataRepository.getInstance()
                .fetchRejectLink(body)
                .subscribe(new MyObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        getMldRejectLink().postValue(true);
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        ToastUtil.showToast(throwable.message);
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }

    public void fetchAgreeLink(String project_uuid, String kol_uuid) {
        LinkCheckBody body = new LinkCheckBody();
        body.setUser_uuid(kol_uuid);
        body.setProject_uuid(project_uuid);
        body.setComments(0);
        body.setLikes(0);

        DataRepository.getInstance()
                .fetchAgreeLink(body)
                .subscribe(new MyObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        getMldAgreeLink().postValue(true);
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        ToastUtil.showToast(throwable.message);
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }

    public void getReportLink(String project_uuid, String kol_uuid) {
        DataRepository.getInstance()
                .fetchReportLink(project_uuid,kol_uuid)
                .subscribe(new MyObserver<List<ResponseGetReportLink.ReportsBean>>() {
                    @Override
                    public void onSuccess(List<ResponseGetReportLink.ReportsBean> reportsBeans) {
                        if (reportsBeans.get(0)!=null){
                            getMldReportLink().postValue(reportsBeans.get(0));
                        }
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        ToastUtil.showToast(throwable.message);
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }
}
