package com.kolnetworks.koln;

import android.util.Log;

public class Constant {

    //華為推播參數
    public static final String HUAWEI_CLIENT_ID = "222956040809874432";

    public static final int TYPE_FOUND_UNIQ = 0x01; //專屬邀約
    public static final int TYPE_FOUND_APPLY_COOPERATION = 0x02; //申請合作
    public static final int TYPE_MY_APPLIED = 0x03; //已申請
    public static final int TYPE_MY_CONFIRM = 0x04; //合作確認
    public static final int TYPE_MY_FINISH = 0x05; //已結束
    public static final int TYPE_MANAGER_PROJECTS = 0x06; //經理專案

    public static String getPlatformName(int type) {
        String name = "";
        switch (type) {
            case 1:
                name = "Facebook";
                break;
            case 2:
                name = "Instgram";
                break;
            case 3:
                name = "YouTube";
                break;
            case 4:
                name = "Blog";
                break;
            default:
                name = "";
                break;
        }
        return name;
    }

    public static String getPlatformShortName(int type) {
        String name = "";
        switch (type) {
            case 1:
                name = "FB";
                break;
            case 2:
                name = "IG";
                break;
            case 3:
                name = "YT";
                break;
            case 4:
                name = "BL";
                break;
            default:
                name = "";
                break;
        }
        return name;
    }


    public static String TEL_REG = "[1][34578]\\d{9}";
    public static String NUM_REG = "[0-9]]";
    public static String TEL_REG_TW = "[0][9]\\d{8}";
    public static String EMAIL_REG = "^[0-9a-z_-]+([.][0-9a-z_-]+)*@[0-9a-z_-]+([.][0-9a-z_-]+)*$";

}
