package com.kolnetworks.koln.model;

public class UserInfoBody {
    /**
     * phone : 0986532666
     * address : null
     * birthday : 223311872
     * line_id : null
     */

    private String phone;
    private String address;
    private int birthday;
    private String line_id;

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

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }
}
