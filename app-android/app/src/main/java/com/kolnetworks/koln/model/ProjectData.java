package com.kolnetworks.koln.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ProjectData implements Parcelable {

    /**
     * project_no : 20200424022947
     * uuid : 77407c90-85d3-11ea-9d1a-fd06d10b9040
     * photo : https://kolassets.s3-ap-northeast-1.amazonaws.com/uploads/5d89119d4f468e589a8ebe212d900e6c318b3b1e1ae5edd3fcb66a79372a66cb.JPG
     * name : 真人版動物森林朋友
     * start_date : 1587945600
     * end_date : 1588118400
     * place : 大自然
     * introduction : 真人版動物森林朋友O
     * invite_deadline : 1587772800
     * kol_price : 64
     * uuid_board : 77413fe0-85d3-11ea-9d1a-fd06d10b9040
     */

    private String project_no;
    private String uuid;
    private String photo;
    private String name;
    private int start_date;
    private int end_date;
    private String place;
    private String introduction;
    private int invite_deadline;
    private int kol_price;
    private int kol_expect_price;
    private int kol_status;
    private int project_status;
    private int platform;
    private String uuid_board;
    private String messenger;
    private String comments;
    private int type;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getProject_no() {
        return project_no;
    }

    public void setProject_no(String project_no) {
        this.project_no = project_no;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStart_date() {
        return start_date;
    }

    public void setStart_date(int start_date) {
        this.start_date = start_date;
    }

    public int getEnd_date() {
        return end_date;
    }

    public void setEnd_date(int end_date) {
        this.end_date = end_date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getInvite_deadline() {
        return invite_deadline;
    }

    public void setInvite_deadline(int invite_deadline) {
        this.invite_deadline = invite_deadline;
    }

    public int getKol_price() {
        return kol_price;
    }

    public void setKol_price(int kol_price) {
        this.kol_price = kol_price;
    }

    public int getKol_expect_price() {
        return kol_expect_price;
    }

    public void setKol_expect_price(int kol_expect_price) {
        this.kol_expect_price = kol_expect_price;
    }

    public int getKol_status() {
        return kol_status;
    }

    public void setKol_status(int kol_status) {
        this.kol_status = kol_status;
    }

    public int getProject_status() {
        return project_status;
    }

    public void setProject_status(int project_status) {
        this.project_status = project_status;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public String getUuid_board() {
        return uuid_board;
    }

    public void setUuid_board(String uuid_board) {
        this.uuid_board = uuid_board;
    }

    public String getMessenger() {
        return messenger;
    }

    public void setMessenger(String messenger) {
        this.messenger = messenger;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.project_no);
        dest.writeString(this.uuid);
        dest.writeString(this.photo);
        dest.writeString(this.name);
        dest.writeInt(this.start_date);
        dest.writeInt(this.end_date);
        dest.writeString(this.place);
        dest.writeString(this.introduction);
        dest.writeInt(this.invite_deadline);
        dest.writeInt(this.kol_price);
        dest.writeInt(this.kol_expect_price);
        dest.writeInt(this.kol_status);
        dest.writeInt(this.project_status);
        dest.writeInt(this.platform);
        dest.writeString(this.uuid_board);
        dest.writeString(this.messenger);
        dest.writeString(this.comments);
        dest.writeInt(this.type);
    }

    public ProjectData() {
    }

    protected ProjectData(Parcel in) {
        this.project_no = in.readString();
        this.uuid = in.readString();
        this.photo = in.readString();
        this.name = in.readString();
        this.start_date = in.readInt();
        this.end_date = in.readInt();
        this.place = in.readString();
        this.introduction = in.readString();
        this.invite_deadline = in.readInt();
        this.kol_price = in.readInt();
        this.kol_expect_price = in.readInt();
        this.kol_status = in.readInt();
        this.project_status = in.readInt();
        this.platform = in.readInt();
        this.uuid_board = in.readString();
        this.messenger = in.readString();
        this.comments = in.readString();
        this.type = in.readInt();
    }

    public static final Parcelable.Creator<ProjectData> CREATOR = new Parcelable.Creator<ProjectData>() {
        @Override
        public ProjectData createFromParcel(Parcel source) {
            return new ProjectData(source);
        }

        @Override
        public ProjectData[] newArray(int size) {
            return new ProjectData[size];
        }
    };
}
