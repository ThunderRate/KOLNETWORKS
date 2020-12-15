package com.kolnetworks.koln.api.bean;

public class ResponseRegister {
    /**
     * success : true
     * user : {"uuid":"b2f239f0-8794-11ea-8c24-c9e90724eeaa","first_name":"","middle_name":null,"last_name":"","display_name":null,"email":"qq@gmail.com","phone":null,"photo":null,"address":null,"gender":1,"region":1,"age":null,"fb_id":null,"ig_id":null,"enabled":true,"is_verified":false,"is_reviewed":false,"is_reviewing":false,"verified_at":null,"created_at":1587888331,"updated_at":null,"invitation_code":"BoVzT36V","inviter_user_id":null,"invited":0,"invited_done_first_project":0}
     */

    private boolean success;
    private UserBean user;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * uuid : b2f239f0-8794-11ea-8c24-c9e90724eeaa
         * first_name :
         * middle_name : null
         * last_name :
         * display_name : null
         * email : qq@gmail.com
         * phone : null
         * photo : null
         * address : null
         * gender : 1
         * region : 1
         * age : null
         * fb_id : null
         * ig_id : null
         * enabled : true
         * is_verified : false
         * is_reviewed : false
         * is_reviewing : false
         * verified_at : null
         * created_at : 1587888331
         * updated_at : null
         * invitation_code : BoVzT36V
         * inviter_user_id : null
         * invited : 0
         * invited_done_first_project : 0
         */

        private String uuid;
        private String first_name;
        private Object middle_name;
        private String last_name;
        private Object display_name;
        private String email;
        private Object phone;
        private Object photo;
        private Object address;
        private int gender;
        private int region;
        private Object age;
        private Object fb_id;
        private Object ig_id;
        private boolean enabled;
        private boolean is_verified;
        private boolean is_reviewed;
        private boolean is_reviewing;
        private Object verified_at;
        private int created_at;
        private Object updated_at;
        private String invitation_code;
        private Object inviter_user_id;
        private int invited;
        private int invited_done_first_project;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public Object getMiddle_name() {
            return middle_name;
        }

        public void setMiddle_name(Object middle_name) {
            this.middle_name = middle_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public Object getDisplay_name() {
            return display_name;
        }

        public void setDisplay_name(Object display_name) {
            this.display_name = display_name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
            this.phone = phone;
        }

        public Object getPhoto() {
            return photo;
        }

        public void setPhoto(Object photo) {
            this.photo = photo;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getRegion() {
            return region;
        }

        public void setRegion(int region) {
            this.region = region;
        }

        public Object getAge() {
            return age;
        }

        public void setAge(Object age) {
            this.age = age;
        }

        public Object getFb_id() {
            return fb_id;
        }

        public void setFb_id(Object fb_id) {
            this.fb_id = fb_id;
        }

        public Object getIg_id() {
            return ig_id;
        }

        public void setIg_id(Object ig_id) {
            this.ig_id = ig_id;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public boolean isIs_verified() {
            return is_verified;
        }

        public void setIs_verified(boolean is_verified) {
            this.is_verified = is_verified;
        }

        public boolean isIs_reviewed() {
            return is_reviewed;
        }

        public void setIs_reviewed(boolean is_reviewed) {
            this.is_reviewed = is_reviewed;
        }

        public boolean isIs_reviewing() {
            return is_reviewing;
        }

        public void setIs_reviewing(boolean is_reviewing) {
            this.is_reviewing = is_reviewing;
        }

        public Object getVerified_at() {
            return verified_at;
        }

        public void setVerified_at(Object verified_at) {
            this.verified_at = verified_at;
        }

        public int getCreated_at() {
            return created_at;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }

        public Object getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(Object updated_at) {
            this.updated_at = updated_at;
        }

        public String getInvitation_code() {
            return invitation_code;
        }

        public void setInvitation_code(String invitation_code) {
            this.invitation_code = invitation_code;
        }

        public Object getInviter_user_id() {
            return inviter_user_id;
        }

        public void setInviter_user_id(Object inviter_user_id) {
            this.inviter_user_id = inviter_user_id;
        }

        public int getInvited() {
            return invited;
        }

        public void setInvited(int invited) {
            this.invited = invited;
        }

        public int getInvited_done_first_project() {
            return invited_done_first_project;
        }

        public void setInvited_done_first_project(int invited_done_first_project) {
            this.invited_done_first_project = invited_done_first_project;
        }
    }
}
