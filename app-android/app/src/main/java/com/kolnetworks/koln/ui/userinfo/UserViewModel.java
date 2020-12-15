package com.kolnetworks.koln.ui.userinfo;

import androidx.lifecycle.MutableLiveData;

import com.kolnetworks.koln.api.ExceptionHandle;
import com.kolnetworks.koln.api.MyObserver;
import com.kolnetworks.koln.api.bean.ResponseProject;
import com.kolnetworks.koln.api.bean.ResponsePutUser;
import com.kolnetworks.koln.api.bean.ResponseUser;
import com.kolnetworks.koln.base.BaseViewModel;
import com.kolnetworks.koln.data.SPApi;
import com.kolnetworks.koln.model.LinkBody;
import com.kolnetworks.koln.model.LinkIgBody;
import com.kolnetworks.koln.model.PutUserBody;
import com.kolnetworks.koln.model.UserInfoBody;
import com.kolnetworks.koln.repository.DataRepository;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class UserViewModel extends BaseViewModel {
    private MutableLiveData<String> mText;
    private MutableLiveData<String> mldPhoto;
    private MutableLiveData<String> mldApiError;

    private MutableLiveData<ResponseUser.UserBean> mldUser;
    private MutableLiveData<ResponsePutUser.UserBean> mldUserBasic;
    private MutableLiveData<Boolean> mldCanFinish;


    public MutableLiveData<String> getMldPhoto() {
        if (mldPhoto == null) {
            mldPhoto = new MutableLiveData<>();
        }
        return mldPhoto;
    }

    public MutableLiveData<ResponseUser.UserBean> getMldUser() {
        if (mldUser == null) {
            mldUser = new MutableLiveData<>();
        }
        return mldUser;
    }

    public MutableLiveData<ResponsePutUser.UserBean> getMldUserBasic() {
        if (mldUserBasic == null) {
            mldUserBasic = new MutableLiveData<>();
        }
        return mldUserBasic;
    }

    public MutableLiveData<String> getMldApiError() {
        if (mldApiError == null) {
            mldApiError = new MutableLiveData<>();
        }
        return mldApiError;
    }

    public MutableLiveData<Boolean> getMldCanFinish() {
        if (mldCanFinish == null) {
            mldCanFinish = new MutableLiveData<>();
        }
        return mldCanFinish;
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
                        getMldPhoto().postValue(userBean.getPhoto());
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }

    public void updateUserData(String phone, String address, long birth, String line) {
        UserInfoBody body = new UserInfoBody();
        body.setPhone(phone);
        body.setAddress(address);
        body.setBirthday((int) birth);
        body.setLine_id(line);

        DataRepository.getInstance()
                .updateUserBasicInfo(body)
                .subscribe(new MyObserver<ResponsePutUser.UserBean>() {
                    @Override
                    public void onSuccess(ResponsePutUser.UserBean userBean) {
                        getMldUserBasic().postValue(userBean);
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }

    public void linkIg(String name) {
        LinkIgBody body = new LinkIgBody();
        body.setName(name);

        DataRepository.getInstance()
                .fetchLinkIg(body)
                .subscribe(new MyObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean success) {
                        if (success) {
                            getMldCanFinish().postValue(true);
                        }
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }

    public void linkYt(String name) {
        LinkBody body = new LinkBody();
        body.setName(name);

        DataRepository.getInstance()
                .fetchLinkYt(body)
                .subscribe(new MyObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean success) {
                        if (success) {
                            getMldCanFinish().postValue(true);
                        }
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }

    public void linkFb(String name) {
        LinkBody body = new LinkBody();
        body.setName(name);

        DataRepository.getInstance()
                .fetchLinkFb(body)
                .subscribe(new MyObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean success) {
                        if (success) {
                            getMldCanFinish().postValue(true);
                        }
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }

    public void linkBlog(String url) {
        LinkBody body = new LinkBody();
        body.setName(url);

        DataRepository.getInstance()
                .fetchLinkBlog(body)
                .subscribe(new MyObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean success) {
                        if (success) {
                            getMldCanFinish().postValue(true);
                        }
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }

}
