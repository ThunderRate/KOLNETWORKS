package com.kolnetworks.koln.api;


import com.kolnetworks.koln.api.bean.ResDetail;
import com.kolnetworks.koln.api.bean.ResponseAnswer;
import com.kolnetworks.koln.api.bean.ResponseBoards;
import com.kolnetworks.koln.api.bean.ResponseCashFlow;
import com.kolnetworks.koln.api.bean.ResponseGetReportLink;
import com.kolnetworks.koln.api.bean.ResponseLayouts;
import com.kolnetworks.koln.api.bean.ResponseManagerApplyCooperationList;
import com.kolnetworks.koln.api.bean.ResponseProject;
import com.kolnetworks.koln.api.bean.ResponseLinkIg;
import com.kolnetworks.koln.api.bean.ResponseLogin;
import com.kolnetworks.koln.api.bean.ResponsePutUser;
import com.kolnetworks.koln.api.bean.ResponseRegister;
import com.kolnetworks.koln.api.bean.ResponseTags;
import com.kolnetworks.koln.api.bean.ResponseUser;
import com.kolnetworks.koln.model.AnswerBody;
import com.kolnetworks.koln.model.ApplyBody;
import com.kolnetworks.koln.model.ChangeStatusBody;
import com.kolnetworks.koln.model.CommentBody;
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


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServiceInterface {

    @POST("/login")
    Observable<ResponseLogin> postLogin(@Body LoginBody loginBody);

    @POST("/register")
    Observable<ResponseRegister> postRegister(@Body RegisteBody registeBody);

    @POST("/projects/confirm")
    Observable<ResponseAnswer> postAgree(@Body AnswerBody body);

    @POST("/projects/reject")
    Observable<ResponseAnswer> postReject(@Body AnswerBody body);

    @POST("/projects/report/submit")
    Observable<ResponseAnswer> postSubmit(@Body SubmitBody body);

    @POST("/comments")
    Observable<ResponseAnswer> postComment(@Body CommentBody body);

    @POST("/projects/reject")
    Observable<ResponseAnswer> postRejectKol(@Body NameListBody body);

    @POST("/projects/confirm")
    Observable<ResponseAnswer> postConfirmKol(@Body NameListBody body);

    @POST("/projects/report/reject")
    Observable<ResponseAnswer> postLinkReject(@Body LinkCheckBody body);

    @POST("/projects/report/confirm")
    Observable<ResponseAnswer> postLinkAgree(@Body LinkCheckBody body);

    @POST("/projects/apply")
    Observable<ResponseAnswer> postApply(@Body ApplyBody body);

    @POST("/users/linkig")
    Observable<ResponseLinkIg> postLinkIg(@Body LinkIgBody linkIgBody);

    @POST("/users/linkfb")
    Observable<ResponseLinkIg> postLinkFb(@Body LinkBody linkBody);

    @POST("/users/linkyt")
    Observable<ResponseLinkIg> postLinkYt(@Body LinkBody linkBody);

    @POST("/users/linkblog")
    Observable<ResponseLinkIg> postLinkBlog(@Body LinkBody linkBody);

    @POST("/tags/user/assign")
    Observable<ResponseAnswer> postTags(@Body TagBody tagBody);

    @POST("/reset/token")
    Observable<ResponseAnswer> resetEmail(@Body ResetEmailBody tagBody);

    @PUT("/users")
    Observable<ResponsePutUser> putUsers(@Body PutUserBody body);

    @PUT("/users")
    Observable<ResponsePutUser> putUsersBasicInfo(@Body UserInfoBody body);

    @PUT("/users")
    Observable<ResponsePutUser> putUserAccount(@Body UserAccountBody body);

    @GET("/my/projects")
    Observable<ResponseProject> getProjects(
            @Query("page") Integer page,
            @Query("pageSize") Integer pageSize,
            @Query("projectStatus") String projectStatus,
            @Query("kolStatus") String kolStatus);

    @GET("/user")
    Observable<ResponseUser> getUser();

    @GET("/layouts")
    Observable<ResponseLayouts> getLayouts();

    @GET("/projects/management")
    Observable<ResponseProject> getManagerProjects(@Query("page") int page,@Query("pageSize") int pageSize, @Query("orderby") String orderby);

    @GET("/projects/{uuid}")
    Observable<ResDetail> getProjectsDetail(@Path("uuid") String uuid);

    @GET("/projectkols/{uuid}")
    Observable<ResponseManagerApplyCooperationList> getManagerProjectsList(@Path("uuid") String uuid, @Query("kolStatus") String kolStatus, @Query("isPicked") boolean isPicked);

    @GET("/projects/report/{uuid}")
    Observable<ResponseGetReportLink> getReportLink(@Path("uuid") String uuid, @Query("kol_uuid") String kol_uuid, @Query("orderby") String orderby);

    @PUT("/projects/status")
    Observable<String> putChangeProjectsStatus(@Body ChangeStatusBody body);


    @GET("/user")
    Call<ResponseUser> getUserTest();

    @GET("/boards/{id}")
    Observable<ResponseBoards> getBoards(@Path("id") String id);

    @GET("/tags")
    Observable<ResponseTags> getTags(
            @Query("page") Integer page,
            @Query("pageSize") Integer pageSize,
            @Query("ia") String ia);

    @GET("/my/cashflow")
    Observable<ResponseCashFlow> getCashFlow(
            @Query("page") Integer page,
            @Query("pageSize") Integer pageSize,
            @Query("orderby") String cashflow_id);

}
