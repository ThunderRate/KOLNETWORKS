package com.kolnetworks.koln.ui.register;

import androidx.lifecycle.MutableLiveData;

import com.kolnetworks.koln.api.ExceptionHandle;
import com.kolnetworks.koln.api.MyObserver;
import com.kolnetworks.koln.api.bean.ResponseProject;
import com.kolnetworks.koln.api.bean.ResponseRegister;
import com.kolnetworks.koln.api.bean.ResponseUser;
import com.kolnetworks.koln.base.BaseViewModel;
import com.kolnetworks.koln.data.SPApi;
import com.kolnetworks.koln.model.LoginBody;
import com.kolnetworks.koln.model.RegisteBody;
import com.kolnetworks.koln.repository.DataRepository;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class JoinUsViewModel extends BaseViewModel {
    private MutableLiveData<ResponseRegister.UserBean> mldUser;
    private MutableLiveData<String> mldApiError;

    public MutableLiveData<ResponseRegister.UserBean> getMldUser() {
        if (mldUser == null) {
            mldUser = new MutableLiveData<>();
        }
        return mldUser;
    }

    public MutableLiveData<String> getMldApiError() {
        if (mldApiError == null) {
            mldApiError = new MutableLiveData<>();
        }
        return mldApiError;
    }

    public void register(String email, String pwd, String pwdAgain){

        RegisteBody body = new RegisteBody();
        body.setEmail(email);
        body.setPassword(pwd);
        body.setPassword_again(pwdAgain);
        body.setInvitation_code("1234567890");

        DataRepository.getInstance()
                .fetchRegister(body)
                .subscribe(new MyObserver<ResponseRegister.UserBean>() {
                    @Override
                    public void onSuccess(ResponseRegister.UserBean userBean) {
                        if (userBean != null){
                            getMldUser().postValue(userBean);
                        }
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }
}
