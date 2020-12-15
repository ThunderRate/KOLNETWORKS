package com.kolnetworks.koln.ui.login;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.kolnetworks.koln.BuildConfig;
import com.kolnetworks.koln.api.ApiServiceInterface;
import com.kolnetworks.koln.api.ExceptionHandle;
import com.kolnetworks.koln.api.MyObserver;
import com.kolnetworks.koln.api.RetrofitManager;
import com.kolnetworks.koln.api.bean.ResponseLayouts;
import com.kolnetworks.koln.api.bean.ResponseTags;
import com.kolnetworks.koln.api.bean.ResponseUser;
import com.kolnetworks.koln.base.BaseViewModel;
import com.kolnetworks.koln.data.SPApi;
import com.kolnetworks.koln.model.LoginBody;
import com.kolnetworks.koln.repository.DataRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.observers.DisposableObserver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends BaseViewModel {
    private MutableLiveData<Boolean> mldCanFinish;
    private MutableLiveData<String> mldApiError;
    private MutableLiveData<Boolean> mldIsDataComplete;
    private String firebaseToken = "";
    private String igUrl = "";

    public MutableLiveData<Boolean> getMldCanFinish() {
        if (mldCanFinish == null) {
            mldCanFinish = new MutableLiveData<>();
        }
        return mldCanFinish;
    }

    public MutableLiveData<String> getMldApiError() {
        if (mldApiError == null) {
            mldApiError = new MutableLiveData<>();
        }
        return mldApiError;
    }

    public MutableLiveData<Boolean> getMldIsDataComplete() {
        if (mldIsDataComplete == null) {
            mldIsDataComplete = new MutableLiveData<>();
        }
        return mldIsDataComplete;
    }

    /**
     * 登入
     *
     * @param email    email
     * @param password pwd
     */
    public void login(String email, String password) {
        // call login api
        LoginBody body = new LoginBody();
        body.setEmail(email);
        body.setPassword(password);
        if (!BuildConfig.DEBUG) {
            body.setNotification_token(firebaseToken);
        }
        DataRepository.getInstance().fetchToken(body)
                .subscribe(new MyObserver<String>() {
                    @Override
                    public void onSuccess(String token) {

                        SPApi.putToken(token);
                        SPApi.putAccount(email);
                        SPApi.putPassword(password);

                        getUser();
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
//                        getMldApiError().postValue("帳號密碼錯誤！");
                        if (TextUtils.isEmpty(throwable.message)) {
                            getMldApiError().postValue(throwable.message);
                        }
                    }
                });
    }

    /**
     * get User data from api
     */
    public void getUser() {
        DataRepository.getInstance()
                .fetchUser()
                .subscribe(new MyObserver<ResponseUser.UserBean>() {
                    @Override
                    public void onSuccess(ResponseUser.UserBean userBean) {
                        if ((!TextUtils.isEmpty(userBean.getIg_url()) ||
                                !TextUtils.isEmpty(userBean.getFb_url()) ||
                                !TextUtils.isEmpty(userBean.getYt_url()) ||
                                !TextUtils.isEmpty(userBean.getBlog_url())) &&
                                userBean.getAudience_age().size() > 0 &&
                                userBean.getHabbits().size() > 0 &&
                                userBean.getCommunity().size() > 0 &&
                                userBean.getContents().size() > 0 &&
                                userBean.getOccupation().size() > 0 &&
                                userBean.getPersonality().size() > 0) {

                            getMldIsDataComplete().postValue(true);
                            if (!TextUtils.isEmpty(userBean.getDisplay_name())) {
                                SPApi.putUName(userBean.getDisplay_name());
                            }
                            if (!TextUtils.isEmpty(userBean.getInvitation_code())) {
                                SPApi.putInvitationCode(userBean.getInvitation_code());
                            }
                            SPApi.putInviteCount(userBean.getInvited());
                            SPApi.putInviteDoneCount(userBean.getInvited_done_first_project());

                        } else {
                            if (!TextUtils.isEmpty(userBean.getIg_url())) {
                                igUrl = userBean.getIg_url();
                            }
                            getMldIsDataComplete().postValue(false);
                        }

                        getLayouts(); //判斷是不是經理身份

                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        if (TextUtils.isEmpty(throwable.message)) {
                            getMldApiError().postValue(throwable.message);
                        }
                    }
                });
    }

    private void getLayouts() {
        DataRepository.getInstance()
                .fetchLayouts()
                .subscribe(new MyObserver<ResponseLayouts>() {
                    @Override
                    public void onSuccess(ResponseLayouts responseLayouts) {
                        Log.d("JJJ", " is manager : " + responseLayouts.isProjects());
                        SPApi.putIsManager(responseLayouts.isProjects());
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        Log.d("JJJ", " is manager onFail : " + throwable.message);
                    }
                });
    }

    /**
     * 取得 Firebase Token
     */
    public void getFirebaseToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        if (task.getResult() == null)
                            return;
                        firebaseToken = task.getResult().getToken();
                        Log.d("JJJ", " firebase token : " + firebaseToken);
                    }
                });
    }

    public String getIgUrl() {
        return igUrl;
    }
}
