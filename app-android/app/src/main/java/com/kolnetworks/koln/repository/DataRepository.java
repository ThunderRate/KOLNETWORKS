package com.kolnetworks.koln.repository;

import com.kolnetworks.koln.api.APIServiceClient;
import com.kolnetworks.koln.api.ApiServiceInterface;
import com.kolnetworks.koln.api.bean.ResDetail;
import com.kolnetworks.koln.api.bean.ResponseAnswer;
import com.kolnetworks.koln.api.bean.ResponseBoards;
import com.kolnetworks.koln.api.bean.ResponseCashFlow;
import com.kolnetworks.koln.api.bean.ResponseGetReportLink;
import com.kolnetworks.koln.api.bean.ResponseLayouts;
import com.kolnetworks.koln.api.bean.ResponseLinkIg;
import com.kolnetworks.koln.api.bean.ResponseManagerApplyCooperationList;
import com.kolnetworks.koln.api.bean.ResponseProject;
import com.kolnetworks.koln.api.bean.ResponsePutUser;
import com.kolnetworks.koln.api.bean.ResponseRegister;
import com.kolnetworks.koln.api.bean.ResponseTags;
import com.kolnetworks.koln.api.bean.ResponseUser;
import com.kolnetworks.koln.model.AnswerBody;
import com.kolnetworks.koln.model.ApplyBody;
import com.kolnetworks.koln.model.ChangeStatusBody;
import com.kolnetworks.koln.model.LinkBody;
import com.kolnetworks.koln.model.LinkIgBody;
import com.kolnetworks.koln.model.LoginBody;
import com.kolnetworks.koln.model.NameListBody;
import com.kolnetworks.koln.model.PutUserBody;
import com.kolnetworks.koln.model.RegisteBody;
import com.kolnetworks.koln.model.LinkCheckBody;
import com.kolnetworks.koln.model.ResetEmailBody;
import com.kolnetworks.koln.model.SubmitBody;
import com.kolnetworks.koln.model.TagBody;
import com.kolnetworks.koln.model.UserAccountBody;
import com.kolnetworks.koln.model.UserInfoBody;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class DataRepository {
    private static DataRepository instance;
    private final String TAG = this.getClass().getSimpleName();
    private ApiServiceInterface mApiServiceInterface = APIServiceClient.getInstance();

    private DataRepository() {
    }

    public static DataRepository getInstance() {
        if (instance == null) {
            synchronized (DataRepository.class) {
                if (instance == null)
                    instance = new DataRepository();
            }
        }
        return instance;
    }

    public Observable<String> fetchToken(LoginBody loginBody) {
        return mApiServiceInterface
                .postLogin(loginBody)
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(responseLogin -> {
                    if (responseLogin.isSuccess()) {
                        return responseLogin.getToken();
                    } else {
                        return null;
                    }
                });
    }

    public Observable<Boolean> fetchLinkIg(LinkIgBody body) {
        return mApiServiceInterface
                .postLinkIg(body)
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ResponseLinkIg::isSuccess);
    }

    public Observable<Boolean> fetchLinkFb(LinkBody body) {
        return mApiServiceInterface
                .postLinkFb(body)
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ResponseLinkIg::isSuccess);
    }

    public Observable<Boolean> fetchLinkYt(LinkBody body) {
        return mApiServiceInterface
                .postLinkYt(body)
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ResponseLinkIg::isSuccess);
    }

    public Observable<Boolean> fetchLinkBlog(LinkBody body) {
        return mApiServiceInterface
                .postLinkBlog(body)
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ResponseLinkIg::isSuccess);
    }

    public Observable<List<ResponseProject.ProjectsBean>> fetchAroundUniq() {
        return mApiServiceInterface
                .getProjects(1, 20, "2,3,4,5,6", "1")
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(responseProject -> {
                    if (responseProject.isSuccess()) {
                        return responseProject.getProjects();
                    } else
                        return null;
                });
    }

    public Observable<List<ResponseProject.ProjectsBean>> fetchAroundGoAround() {
        return mApiServiceInterface
                .getProjects(1, 20, "2,3,4,5,6", "2")
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(responseProject -> {
                    if (responseProject.isSuccess()) {
                        return responseProject.getProjects();
                    } else
                        return null;
                });
    }

    public Observable<List<ResponseProject.ProjectsBean>> fetchAroundApply(String projectStatus, String kolStatus) {
        return mApiServiceInterface
                .getProjects(1, 20, projectStatus, kolStatus)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(responseProject -> {
                    if (responseProject.isSuccess()) {
                        return responseProject.getProjects();
                    } else
                        return null;
                });
    }

    public Observable<List<ResponseProject.ProjectsBean>> fetchMissionDoing(String projectStatus, String kolStatus) {
        return mApiServiceInterface
                .getProjects(1, 20, projectStatus, kolStatus)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(responseProject -> {
                    if (responseProject.isSuccess()) {
                        return responseProject.getProjects();
                    } else
                        return null;
                });
    }

    public Observable<List<ResponseProject.ProjectsBean>> fetchMissionFinish(String projectStatus, String kolStatus) {
        return mApiServiceInterface
                .getProjects(1, 20, projectStatus, kolStatus)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(responseProject -> {
                    if (responseProject.isSuccess()) {
                        return responseProject.getProjects();
                    } else
                        return null;
                });
    }

    public Observable<List<ResponseProject.ProjectsBean>> fetchMissionNotStart(String projectStatus, String kolStatus) {
        return mApiServiceInterface
                .getProjects(1, 20, projectStatus, kolStatus)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(responseProject -> {
                    if (responseProject.isSuccess()) {
                        return responseProject.getProjects();
                    } else
                        return null;
                });
    }

    /**
     * manager 取得合作清單
     * @param uuid
     * @return
     */
    public Observable<List<ResponseManagerApplyCooperationList.KolsBean>> fetchApplyCooperationList(String uuid) {
        return mApiServiceInterface
                .getManagerProjectsList(uuid, "3,4,5,6,7", false)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(responseProject -> {
                    if (responseProject.isSuccess()) {
                        return responseProject.getKols();
                    } else
                        return null;
                });
    }

    public Observable<List<ResponseGetReportLink.ReportsBean>> fetchReportLink(String project_uuid, String kol_uuid) {
        return mApiServiceInterface
                .getReportLink(project_uuid, kol_uuid, "created_at")
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(responseProject -> {
                    if (responseProject.isSuccess()) {
                        return responseProject.getReports();
                    } else
                        return null;
                });
    }

    /**
     * manager 取得邀約清單
     * @param uuid
     * @return
     */
    public Observable<List<ResponseManagerApplyCooperationList.KolsBean>> fetchInviteList(String uuid) {
        return mApiServiceInterface
                .getManagerProjectsList(uuid, "1,3,4,5,7", true)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(responseProject -> {
                    if (responseProject.isSuccess()) {
                        return responseProject.getKols();
                    } else
                        return null;
                });
    }

    public Observable<String> putProjectStatus(ChangeStatusBody body) {
        return mApiServiceInterface
                .putChangeProjectsStatus(body)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(responseProject -> {
                    return responseProject;
                });
    }

    public Observable<ResponseUser.UserBean> fetchUser() {
        return mApiServiceInterface
                .getUser()
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(res -> {
                    if (res.isSuccess()) {
                        return res.getUser();
                    } else
                        return null;
                });
    }

    public Observable<ResponseLayouts> fetchLayouts() {
        return mApiServiceInterface
                .getLayouts()
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(res -> {
                    if (res.isSuccess()) {
                        return res;
                    } else
                        return null;
                });
    }

    public Observable<List<ResponseProject.ProjectsBean>> fetchManagerProjects() {
        return mApiServiceInterface
                .getManagerProjects(1,100,"created_at")
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(res -> {
                    if (res.isSuccess()) {
                        return res.getProjects();
                    } else
                    return null;
                        });
    }

    public Observable<ResDetail> fetchProjectsDetail(String uuid){
        return mApiServiceInterface
                .getProjectsDetail(uuid)
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(res -> {
                    if (res.isSuccess()){
                        return res;
                    }else return null;
                });
    }

    public Observable<ResponsePutUser.UserBean> updateUser(PutUserBody body) {
        return mApiServiceInterface
                .putUsers(body)
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(res -> {
                    if (res.isSuccess()) {
                        return res.getUser();
                    } else
                        return null;
                });
    }

    public Observable<ResponsePutUser.UserBean> updateUserBasicInfo(UserInfoBody body) {
        return mApiServiceInterface
                .putUsersBasicInfo(body)
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(res -> {
                    if (res.isSuccess()) {
                        return res.getUser();
                    } else
                        return null;
                });
    }

    public Observable<ResponsePutUser.UserBean> updateUserBankAccount(UserAccountBody body) {
        return mApiServiceInterface
                .putUserAccount(body)
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(res -> {
                    if (res.isSuccess()) {
                        return res.getUser();
                    } else
                        return null;
                });
    }

    public Observable<Boolean> updateUserTags(TagBody body) {
        return mApiServiceInterface
                .postTags(body)
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(res -> {
                    if (res.isSuccess()) {
                        return true;
                    } else
                        return null;
                });
    }

    public Observable<ResponseRegister.UserBean> fetchRegister(RegisteBody body) {
        return mApiServiceInterface
                .postRegister(body)
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(res -> {
                    if (res.isSuccess()) {
                        return res.getUser();
                    } else
                        return null;
                });
    }

    public Observable<Boolean> fetchAgree(AnswerBody body) {
        return mApiServiceInterface
                .postAgree(body)
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ResponseAnswer::isSuccess);
    }

    public Observable<Boolean> fetchReject(AnswerBody body) {
        return mApiServiceInterface
                .postReject(body)
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ResponseAnswer::isSuccess);
    }

    public Observable<Boolean> fetchApply(ApplyBody body) {
        return mApiServiceInterface
                .postApply(body)
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ResponseAnswer::isSuccess);
    }

    public Observable<List<ResponseBoards.BoardBean.CommentsBean>> fetchBoards(String boardId) {
        return mApiServiceInterface
                .getBoards(boardId)
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(res -> {
                    if (res.isSuccess()) {
                        return res.getBoard().getComments();
                    } else
                        return null;
                });
    }

    public Observable<Boolean> fetchSubmit(SubmitBody body) {
        return mApiServiceInterface
                .postSubmit(body)
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ResponseAnswer::isSuccess);
    }

    public Observable<Boolean> fetchAgreeKol(NameListBody body) {
        return mApiServiceInterface
                .postConfirmKol(body)
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ResponseAnswer::isSuccess);
    }

    public Observable<Boolean> fetchRejectKol(NameListBody body) {
        return mApiServiceInterface
                .postRejectKol(body)
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ResponseAnswer::isSuccess);
    }

    public Observable<Boolean> fetchRejectLink(LinkCheckBody body) {
        return mApiServiceInterface
                .postLinkReject(body)
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ResponseAnswer::isSuccess);
    }

    public Observable<Boolean> fetchAgreeLink(LinkCheckBody body) {
        return mApiServiceInterface
                .postLinkAgree(body)
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ResponseAnswer::isSuccess);
    }

    public Observable<ResponseCashFlow> fetchCashFlow() {
        return mApiServiceInterface
                .getCashFlow(1, 100, "cashflow_id")
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(res -> {
                    if (res.isSuccess()) {
                        return res;
                    } else {
                        return null;
                    }
                });
    }

    public Observable<List<ResponseTags.TagsBean>> fetchTag(String ia) {
        return mApiServiceInterface
                .getTags(1, 100, ia)
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(res -> {
                    if (res.isSuccess()) {
                        return res.getTags();
                    } else {
                        return null;
                    }
                });
    }

    public Observable<Boolean> resetEmail(ResetEmailBody body) {
        return mApiServiceInterface
                .resetEmail(body)
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ResponseAnswer::isSuccess);
    }
}
