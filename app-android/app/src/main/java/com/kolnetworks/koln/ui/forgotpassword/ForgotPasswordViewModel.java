package com.kolnetworks.koln.ui.forgotpassword;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kolnetworks.koln.api.ExceptionHandle;
import com.kolnetworks.koln.api.MyObserver;
import com.kolnetworks.koln.model.ResetEmailBody;
import com.kolnetworks.koln.repository.DataRepository;

public class ForgotPasswordViewModel extends ViewModel {
    private MutableLiveData<Boolean> mldCanFinish;
    private MutableLiveData<String> mldApiError;

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

    public void forgotPassword() {

    }

    public void sendVerifyEmail(String email) {
        ResetEmailBody body = new ResetEmailBody();
        body.setEmail(email);

        DataRepository.getInstance()
                .resetEmail(body)
                .subscribe(new MyObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        getMldCanFinish().postValue(true);
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }
}
