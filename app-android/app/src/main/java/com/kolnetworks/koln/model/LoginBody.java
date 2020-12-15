package com.kolnetworks.koln.model;

public class LoginBody {
    /**
     * notification_token : test1234567890
     * email : gn01143784@gmail.com
     * password : 1234567890
     */

    private String notification_token;
    private String email;
    private String password;

    public String getNotification_token() {
        return notification_token;
    }

    public void setNotification_token(String notification_token) {
        this.notification_token = notification_token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
