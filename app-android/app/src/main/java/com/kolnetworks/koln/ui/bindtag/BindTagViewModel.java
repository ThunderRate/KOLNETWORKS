package com.kolnetworks.koln.ui.bindtag;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.kolnetworks.koln.api.ExceptionHandle;
import com.kolnetworks.koln.api.MyObserver;
import com.kolnetworks.koln.api.bean.ResponseBoards;
import com.kolnetworks.koln.api.bean.ResponsePutUser;
import com.kolnetworks.koln.api.bean.ResponseRegister;
import com.kolnetworks.koln.api.bean.ResponseTags;
import com.kolnetworks.koln.api.bean.ResponseUser;
import com.kolnetworks.koln.base.BaseViewModel;
import com.kolnetworks.koln.model.AnswerBody;
import com.kolnetworks.koln.model.ApplyBody;
import com.kolnetworks.koln.model.CommentBody;
import com.kolnetworks.koln.model.LinkBody;
import com.kolnetworks.koln.model.LinkIgBody;
import com.kolnetworks.koln.model.ProjectData;
import com.kolnetworks.koln.model.PutUserBody;
import com.kolnetworks.koln.model.RegisteBody;
import com.kolnetworks.koln.model.SubmitBody;
import com.kolnetworks.koln.model.TagBody;
import com.kolnetworks.koln.repository.DataRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class BindTagViewModel extends BaseViewModel {

    private PutUserBody body = new PutUserBody();
    private List<Integer> tags = new ArrayList<>();
    private MutableLiveData<String> mldApiError;
    private MutableLiveData<Boolean> mldCanFinishStep1;
    //    private MutableLiveData<Boolean> mldCanFinishStep3;
    private MutableLiveData<Boolean> mldCanFinishStep4;
    private MutableLiveData<Boolean> mldShowAlertToast;
    private MutableLiveData<List<ResponseTags.TagsBean>> mldIa2Order1;
    private MutableLiveData<List<ResponseTags.TagsBean>> mldIa4Order2;
    private MutableLiveData<List<ResponseTags.TagsBean>> mldIa5Order3;
    private MutableLiveData<List<ResponseTags.TagsBean>> mldIa6Order4;
    private MutableLiveData<List<ResponseTags.TagsBean>> mldIa7Order5;
    private MutableLiveData<List<ResponseTags.TagsBean>> mldIa8Order6;

    private List<Integer> ia2List = new ArrayList<>();
    private List<Integer> ia4List = new ArrayList<>();
    private List<Integer> ia5List = new ArrayList<>();
    private List<Integer> ia6List = new ArrayList<>();
    private List<Integer> ia7List = new ArrayList<>();
    private List<Integer> ia8List = new ArrayList<>();

    public MutableLiveData<List<ResponseTags.TagsBean>> getMldIa2Order1() {
        if (mldIa2Order1 == null) {
            mldIa2Order1 = new MutableLiveData<>();
        }
        return mldIa2Order1;
    }

    public MutableLiveData<List<ResponseTags.TagsBean>> getMldIa4Order2() {
        if (mldIa4Order2 == null) {
            mldIa4Order2 = new MutableLiveData<>();
        }
        return mldIa4Order2;
    }

    public MutableLiveData<List<ResponseTags.TagsBean>> getMldIa5Order3() {
        if (mldIa5Order3 == null) {
            mldIa5Order3 = new MutableLiveData<>();
        }
        return mldIa5Order3;
    }

    public MutableLiveData<List<ResponseTags.TagsBean>> getMldIa6Order4() {
        if (mldIa6Order4 == null) {
            mldIa6Order4 = new MutableLiveData<>();
        }
        return mldIa6Order4;
    }

    public MutableLiveData<List<ResponseTags.TagsBean>> getMldIa7Order5() {
        if (mldIa7Order5 == null) {
            mldIa7Order5 = new MutableLiveData<>();
        }
        return mldIa7Order5;
    }

    public MutableLiveData<List<ResponseTags.TagsBean>> getMldIa8Order6() {
        if (mldIa8Order6 == null) {
            mldIa8Order6 = new MutableLiveData<>();
        }
        return mldIa8Order6;
    }

    public MutableLiveData<String> getMldApiError() {
        if (mldApiError == null) {
            mldApiError = new MutableLiveData<>();
        }
        return mldApiError;
    }

    public MutableLiveData<Boolean> getMldCanFinishStep1() {
        if (mldCanFinishStep1 == null) {
            mldCanFinishStep1 = new MutableLiveData<>();
        }
        return mldCanFinishStep1;
    }

//    public MutableLiveData<Boolean> getMldCanFinishStep3() {
//        if (mldCanFinishStep3 == null) {
//            mldCanFinishStep3 = new MutableLiveData<>();
//        }
//        return mldCanFinishStep3;
//    }

    public MutableLiveData<Boolean> getMldCanFinishStep4() {
        if (mldCanFinishStep4 == null) {
            mldCanFinishStep4 = new MutableLiveData<>();
        }
        return mldCanFinishStep4;
    }

    public MutableLiveData<Boolean> getMldShowAlertToast() {
        if (mldShowAlertToast == null) {
            mldShowAlertToast = new MutableLiveData<>();
        }
        return mldShowAlertToast;
    }

    public void getTag(String ia) {
        DataRepository.getInstance()
                .fetchTag(ia)
                .subscribe(new MyObserver<List<ResponseTags.TagsBean>>() {
                    @Override
                    public void onSuccess(List<ResponseTags.TagsBean> tags) {
                        switch (ia) {
                            case "2":
                                getMldIa2Order1().postValue(tags);
                                break;
                            case "4":
                                getMldIa4Order2().postValue(tags);
                                break;
                            case "5":
                                getMldIa5Order3().postValue(tags);
                                break;
                            case "6":
                                getMldIa6Order4().postValue(tags);
                                break;
                            case "7":
                                getMldIa7Order5().postValue(tags);
                                break;
                            case "8":
                                getMldIa8Order6().postValue(tags);
                                break;
                        }
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }

    private void putUserData(PutUserBody body) {
        DataRepository.getInstance()
                .updateUser(body)
                .subscribe(new MyObserver<ResponsePutUser.UserBean>() {
                    @Override
                    public void onSuccess(ResponsePutUser.UserBean userBean) {
                        updateUserTags();
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
                            getMldCanFinishStep1().postValue(true);
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
                            getMldCanFinishStep1().postValue(true);
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
                            getMldCanFinishStep1().postValue(true);
                        }
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }

    public void linkBlog(String name) {
        LinkBody body = new LinkBody();
        body.setName(name);

        DataRepository.getInstance()
                .fetchLinkBlog(body)
                .subscribe(new MyObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean success) {
                        if (success) {
                            getMldCanFinishStep1().postValue(true);
                        }
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }

    public void updateUserTags() {
        TagBody body = new TagBody();
        body.setTag_ids(tags);

        DataRepository.getInstance().updateUserTags(body)
                .subscribe(new MyObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        getMldCanFinishStep4().postValue(true);
                    }

                    @Override
                    public void onFail(ExceptionHandle.ResponseThrowable throwable) {
                        getMldApiError().postValue(throwable.message);
                    }
                });
    }

    public void setUserInfoDataStepTwo(String name, String phone, long birthStamp) {
        body.setDisplay_name(name);
        body.setPhone(phone);
        body.setBirthday((int) birthStamp);
    }

    public void setUserInfoDataStepThree(int region, int gender) {
        body.setRegion(region);
        body.setGender(gender);
        body.setAudience_gender(10);
    }

    public void setUserTagData(boolean isAdd, int chipId, int tagId) {
        if (200 <= chipId && chipId < 300) {
            addTagIdToEachList(ia2List, tagId, isAdd);
        } else if (400 <= chipId && chipId < 500) {
            addTagIdToEachList(ia4List, tagId, isAdd);
        } else if (500 <= chipId && chipId < 600) {
            addTagIdToEachList(ia5List, tagId, isAdd);
        } else if (600 <= chipId && chipId < 700) {
            addTagIdToEachList(ia6List, tagId, isAdd);
        } else if (700 <= chipId && chipId < 800) {
            addTagIdToEachList(ia7List, tagId, isAdd);
        } else if (800 <= chipId && chipId < 900) {
            addTagIdToEachList(ia8List, tagId, isAdd);
        }
    }

    private void addTagIdToEachList(List<Integer> list, int tagId, boolean isAdd) {
        if (isAdd) {
            tags.add(tagId);
            list.add(tagId);
        } else {
            for (int i = 0; i < tags.size(); i++) {
                if (tags.get(i) == tagId) {
                    tags.remove(i);
                }
            }
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) == tagId) {
                    list.remove(i);
                }
            }
        }
    }

    public void updateUserAndTagData() {
        if (ia2List.size() > 0 &&
                ia4List.size() > 0 &&
                ia5List.size() > 0 &&
                ia6List.size() > 0 &&
                ia7List.size() > 0 &&
                ia8List.size() > 0) {
            putUserData(body);  // tag都選的話先更新user data,成功在update tags
        } else {
            getMldShowAlertToast().postValue(true);
        }

    }

    public void clearTagsList(){
        ia2List.clear();
        ia4List.clear();
        ia5List.clear();
        ia6List.clear();
        ia7List.clear();
        ia8List.clear();
        tags.clear();
    }
}
