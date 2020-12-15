package com.kolnetworks.koln.model;

public class PutUserBody {

    /**
     * display_name : jjj
     * phone : 0928685194
     * address : Taiwan
     * birthday : 158705156
     * region : 1
     * gender : 1
     * audience_gender : 10
     * line_id : lemontea03280
     */

    private String display_name;
    private String phone;
    private String address;
    private int birthday;
    private int region;
    private int gender;
    private int audience_gender;
    private String line_id;

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getBirthday() {
        return birthday;
    }

    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAudience_gender() {
        return audience_gender;
    }

    public void setAudience_gender(int audience_gender) {
        this.audience_gender = audience_gender;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }
}
