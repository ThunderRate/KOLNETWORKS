package com.kolnetworks.koln.data;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kolnetworks.koln.AppContext;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SPApi {

    private static final String TOKEN = "token";
    private static final String U_NAME = "name";
    private static final String U_PHOTO = "photo";
    private static final String U_UUID = "uuid";
    private static final String ACCOUNT = "account";
    private static final String PASSPORT = "passport";
    private static final String ISMANAGER = "ismanager";
    private static final String PASSWORD = "pwd";
    private static final String PROJECTLIST = "projectlist";
    private static final String INVITATION_CODE = "invitationcode";
    private static final String INVITE_COUNT = "invited_count";
    private static final String INVITE_DONE_COUNT = "invited_done_first_project";




    //返回SharedPreferences对象
    private static SharedPreferences getSharedPreferences() {
        return AppContext.sharedPreferences;
    }

    public static String getToken() {
        return getSharedPreferences().getString(TOKEN, "");
    }

    public static void putToken(String token) {
        getSharedPreferences().edit().putString(TOKEN, token).apply();
    }

    public static String getAccount() {
        return getSharedPreferences().getString(ACCOUNT, "");
    }

    public static void putAccount(String account) {
        getSharedPreferences().edit().putString(ACCOUNT, account).apply();
    }

    public static String getPassport() {
        return getSharedPreferences().getString(PASSPORT, "");
    }

    public static void putPassport(String passport) {
        getSharedPreferences().edit().putString(PASSPORT, passport).apply();
    }

    public static Boolean isManager() {
        return getSharedPreferences().getBoolean(ISMANAGER, false);
    }

    public static void putIsManager(Boolean manager) {
        getSharedPreferences().edit().putBoolean(ISMANAGER, manager).apply();
    }

    public static String getPassword() {
        return getSharedPreferences().getString(PASSWORD, "");
    }

    public static void putPassword(String pwd) {
        getSharedPreferences().edit().putString(PASSWORD, pwd).apply();
    }

    public static String getUserUuid() {
        return getSharedPreferences().getString(U_UUID, "");
    }

    public static void putUserUuid(String uuid) {
        getSharedPreferences().edit().putString(U_UUID, uuid).apply();
    }

    public static String getInvitationCode() {
        return getSharedPreferences().getString(INVITATION_CODE, "");
    }

    public static void putInvitationCode(String invitationCode) {
        getSharedPreferences().edit().putString(INVITATION_CODE, invitationCode).apply();
    }

    public static int getInviteCount() {
        return getSharedPreferences().getInt(INVITE_COUNT, 0);
    }

    public static void putInviteCount(int count) {
        getSharedPreferences().edit().putInt(INVITE_COUNT, count).apply();
    }

    public static int getInviteDoneCount() {
        return getSharedPreferences().getInt(INVITE_DONE_COUNT, 0);
    }

    public static void putInviteDoneCount(int count) {
        getSharedPreferences().edit().putInt(INVITE_DONE_COUNT, count).apply();
    }

    public static String getUName() {
        return getSharedPreferences().getString(U_NAME, "");
    }

    public static void putUName(String name) {
        getSharedPreferences().edit().putString(U_NAME, name).apply();
    }

    public static String getuPhoto() {
        return getSharedPreferences().getString(U_PHOTO, "");
    }

    public static void putuPhoto(String token) {
        getSharedPreferences().edit().putString(U_PHOTO, token).apply();
    }

    public static void putProjectList(String id, ArrayList<Integer> list){
        Log.d("JJJ", "list in size : " + list.size());
        Gson gson = new Gson();
        String json = gson.toJson(list);
        getSharedPreferences().edit().putString(id,json).apply();
    }

    public static ArrayList<Integer> getProjectList(String id){
        String json = getSharedPreferences().getString(id, "");
        ArrayList<Integer> list = new ArrayList<>();
        if (!json.isEmpty()){
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Integer>>(){}.getType();
            list = gson.fromJson(json,type);
        }
        Log.d("JJJ", "list: " + list.size());
        return list;
    }
}
