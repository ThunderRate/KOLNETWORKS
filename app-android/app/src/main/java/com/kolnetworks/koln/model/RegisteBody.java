package com.kolnetworks.koln.model;

public class RegisteBody {
    /**
     * email : lemontea03280@gmail.com
     * password : 1234567890
     * password_again : 1234567890
     * invitation_code : 1234567890
     */

    private String email;
    private String password;
    private String password_again;
    private String invitation_code;

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

    public String getPassword_again() {
        return password_again;
    }

    public void setPassword_again(String password_again) {
        this.password_again = password_again;
    }

    public String getInvitation_code() {
        return invitation_code;
    }

    public void setInvitation_code(String invitation_code) {
        this.invitation_code = invitation_code;
    }
}
