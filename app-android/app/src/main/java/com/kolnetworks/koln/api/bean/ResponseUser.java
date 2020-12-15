package com.kolnetworks.koln.api.bean;

import java.util.List;

public class ResponseUser {

    /**
     * success : true
     * user : {"user_id":640,"uuid":"d1babc10-8508-11ea-9c05-d72015886950","first_name":"","middle_name":null,"last_name":"","display_name":"bb","email":"gn01143784@gmail.com","phone":"0912345678","birthday":1691467776,"photo":"https://scontent-nrt1-1.cdninstagram.com/v/t51.2885-19/s150x150/95056049_618808985340449_56112424872312832_n.jpg?_nc_ht=scontent-nrt1-1.cdninstagram.com&_nc_ohc=IiEVu6zfCYAAX-BZSvl&oh=ca6b73594c3ade27d1853ec404a8c99d&oe=5ED4D60C","address":"Taiwan","gender":1,"region":1,"age":null,"audience_gender":0,"notification_token":"test1234567890","bank_code":"1233","branch_code":null,"account_no":"1212133434354545","account_name":null,"enabled":true,"is_verified":true,"is_reviewed":false,"is_reviewing":false,"verified_at":1587608371,"created_at":1587608351,"updated_at":1588298764,"disabled_at":null,"invitation_code":"6XX4EK5x","inviter_user_id":null,"invited":0,"invited_done_first_project":0,"inviter_fee_percentage":null,"executor_fee_percentage":null,"fb_id":null,"ig_id":"1446820179","yt_id":null,"blog_id":null,"fb_url":null,"ig_url":"https://www.instagram.com/try","yt_url":null,"blog_url":null,"ig_followers":"21994","fb_followers":null,"yt_followers":null,"blog_followers":null,"fb_expect_price":null,"ig_expect_price":"2620","yt_expect_price":null,"blog_expect_price":null,"line_id":null,"line_url":null,"fb_engagement_rate":null,"ig_engagement_rate":null,"yt_engagement_rate":null,"blog_engagement_rate":null,"fb_bu_engagement_rate":null,"ig_bu_engagement_rate":null,"yt_bu_engagement_rate":null,"blog_bu_engagement_rate":null,"basic_income":null,"like_bonus_income_withdrawable":null,"like_bonus_income_nonwithdrawable":null,"ig_story_expect_price":"2620","ig_tv_expect_price":"2620","average_big_heart":null,"big_heart_cost":null,"management_company":null,"rejection_category":null,"can_revenue":null,"remark":null,"roles":{"kol":{"name":"kol","items":[]}},"audience_age":[{"tag_id":13,"name":"16-20"},{"tag_id":14,"name":"21-30"},{"tag_id":15,"name":"31-40"}],"audience_region":[],"habbits":[{"tag_id":25,"name":"演奏"},{"tag_id":24,"name":"戲劇"},{"tag_id":23,"name":"藝術"},{"tag_id":26,"name":"歌唱"}],"community":[{"tag_id":48,"name":"音樂舞蹈"},{"tag_id":49,"name":"電影戲劇"},{"tag_id":50,"name":"投資創業"}],"contents":[{"tag_id":74,"name":"美妝保養"},{"tag_id":75,"name":"造型穿搭"},{"tag_id":73,"name":"親子相關"}],"occupation":[{"tag_id":88,"name":"廣告行銷"},{"tag_id":90,"name":"法律"},{"tag_id":89,"name":"程式開發"},{"tag_id":99,"name":"髮型造型"}],"personality":[{"tag_id":129,"name":"時尚"},{"tag_id":130,"name":"高顏值"}]}
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
         * user_id : 640
         * uuid : d1babc10-8508-11ea-9c05-d72015886950
         * first_name :
         * middle_name : null
         * last_name :
         * display_name : bb
         * email : gn01143784@gmail.com
         * phone : 0912345678
         * birthday : 1691467776
         * photo : https://scontent-nrt1-1.cdninstagram.com/v/t51.2885-19/s150x150/95056049_618808985340449_56112424872312832_n.jpg?_nc_ht=scontent-nrt1-1.cdninstagram.com&_nc_ohc=IiEVu6zfCYAAX-BZSvl&oh=ca6b73594c3ade27d1853ec404a8c99d&oe=5ED4D60C
         * address : Taiwan
         * gender : 1
         * region : 1
         * age : null
         * audience_gender : 0
         * notification_token : test1234567890
         * bank_code : 1233
         * branch_code : null
         * account_no : 1212133434354545
         * account_name : null
         * enabled : true
         * is_verified : true
         * is_reviewed : false
         * is_reviewing : false
         * verified_at : 1587608371
         * created_at : 1587608351
         * updated_at : 1588298764
         * disabled_at : null
         * invitation_code : 6XX4EK5x
         * inviter_user_id : null
         * invited : 0
         * invited_done_first_project : 0
         * inviter_fee_percentage : null
         * executor_fee_percentage : null
         * fb_id : null
         * ig_id : 1446820179
         * yt_id : null
         * blog_id : null
         * fb_url : null
         * ig_url : https://www.instagram.com/try
         * yt_url : null
         * blog_url : null
         * ig_followers : 21994
         * fb_followers : null
         * yt_followers : null
         * blog_followers : null
         * fb_expect_price : null
         * ig_expect_price : 2620
         * yt_expect_price : null
         * blog_expect_price : null
         * line_id : null
         * line_url : null
         * fb_engagement_rate : null
         * ig_engagement_rate : null
         * yt_engagement_rate : null
         * blog_engagement_rate : null
         * fb_bu_engagement_rate : null
         * ig_bu_engagement_rate : null
         * yt_bu_engagement_rate : null
         * blog_bu_engagement_rate : null
         * basic_income : null
         * like_bonus_income_withdrawable : null
         * like_bonus_income_nonwithdrawable : null
         * ig_story_expect_price : 2620
         * ig_tv_expect_price : 2620
         * average_big_heart : null
         * big_heart_cost : null
         * management_company : null
         * rejection_category : null
         * can_revenue : null
         * remark : null
         * roles : {"kol":{"name":"kol","items":[]}}
         * audience_age : [{"tag_id":13,"name":"16-20"},{"tag_id":14,"name":"21-30"},{"tag_id":15,"name":"31-40"}]
         * audience_region : []
         * habbits : [{"tag_id":25,"name":"演奏"},{"tag_id":24,"name":"戲劇"},{"tag_id":23,"name":"藝術"},{"tag_id":26,"name":"歌唱"}]
         * community : [{"tag_id":48,"name":"音樂舞蹈"},{"tag_id":49,"name":"電影戲劇"},{"tag_id":50,"name":"投資創業"}]
         * contents : [{"tag_id":74,"name":"美妝保養"},{"tag_id":75,"name":"造型穿搭"},{"tag_id":73,"name":"親子相關"}]
         * occupation : [{"tag_id":88,"name":"廣告行銷"},{"tag_id":90,"name":"法律"},{"tag_id":89,"name":"程式開發"},{"tag_id":99,"name":"髮型造型"}]
         * personality : [{"tag_id":129,"name":"時尚"},{"tag_id":130,"name":"高顏值"}]
         */

        private int user_id;
        private String uuid;
        private String first_name;
        private Object middle_name;
        private String last_name;
        private String display_name;
        private String email;
        private String phone;
        private int birthday;
        private String photo;
        private String address;
        private int gender;
        private int region;
        private Object age;
        private int audience_gender;
        private String notification_token;
        private String bank_code;
        private String  branch_code;
        private String account_no;
        private String account_name;
        private boolean enabled;
        private boolean is_verified;
        private boolean is_reviewed;
        private boolean is_reviewing;
        private int verified_at;
        private int created_at;
        private int updated_at;
        private Object disabled_at;
        private String invitation_code;
        private Object inviter_user_id;
        private int invited;
        private int invited_done_first_project;
        private Object inviter_fee_percentage;
        private Object executor_fee_percentage;
        private String fb_id;
        private String ig_id;
        private String yt_id;
        private String blog_id;
        private String fb_url;
        private String ig_url;
        private String yt_url;
        private String blog_url;
        private String ig_followers;
        private Object fb_followers;
        private Object yt_followers;
        private Object blog_followers;
        private String fb_expect_price;
        private String ig_expect_price;
        private String yt_expect_price;
        private String blog_expect_price;
        private String line_id;
        private String line_url;
        private Object fb_engagement_rate;
        private Object ig_engagement_rate;
        private Object yt_engagement_rate;
        private Object blog_engagement_rate;
        private Object fb_bu_engagement_rate;
        private Object ig_bu_engagement_rate;
        private Object yt_bu_engagement_rate;
        private Object blog_bu_engagement_rate;
        private Object basic_income;
        private Object like_bonus_income_withdrawable;
        private Object like_bonus_income_nonwithdrawable;
        private String ig_story_expect_price;
        private String ig_tv_expect_price;
        private Object average_big_heart;
        private Object big_heart_cost;
        private Object management_company;
        private Object rejection_category;
        private Object can_revenue;
        private Object remark;
        private RolesBean roles;
        private List<AudienceAgeBean> audience_age;
        private List<?> audience_region;
        private List<HabbitsBean> habbits;
        private List<CommunityBean> community;
        private List<ContentsBean> contents;
        private List<OccupationBean> occupation;
        private List<PersonalityBean> personality;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

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

        public String getDisplay_name() {
            return display_name;
        }

        public void setDisplay_name(String display_name) {
            this.display_name = display_name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getBirthday() {
            return birthday;
        }

        public void setBirthday(int birthday) {
            this.birthday = birthday;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
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

        public int getAudience_gender() {
            return audience_gender;
        }

        public void setAudience_gender(int audience_gender) {
            this.audience_gender = audience_gender;
        }

        public String getNotification_token() {
            return notification_token;
        }

        public void setNotification_token(String notification_token) {
            this.notification_token = notification_token;
        }

        public String getBank_code() {
            return bank_code;
        }

        public void setBank_code(String bank_code) {
            this.bank_code = bank_code;
        }

        public String getBranch_code() {
            return branch_code;
        }

        public void setBranch_code(String branch_code) {
            this.branch_code = branch_code;
        }

        public String getAccount_no() {
            return account_no;
        }

        public void setAccount_no(String account_no) {
            this.account_no = account_no;
        }

        public String getAccount_name() {
            return account_name;
        }

        public void setAccount_name(String account_name) {
            this.account_name = account_name;
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

        public int getVerified_at() {
            return verified_at;
        }

        public void setVerified_at(int verified_at) {
            this.verified_at = verified_at;
        }

        public int getCreated_at() {
            return created_at;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }

        public int getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(int updated_at) {
            this.updated_at = updated_at;
        }

        public Object getDisabled_at() {
            return disabled_at;
        }

        public void setDisabled_at(Object disabled_at) {
            this.disabled_at = disabled_at;
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

        public Object getInviter_fee_percentage() {
            return inviter_fee_percentage;
        }

        public void setInviter_fee_percentage(Object inviter_fee_percentage) {
            this.inviter_fee_percentage = inviter_fee_percentage;
        }

        public Object getExecutor_fee_percentage() {
            return executor_fee_percentage;
        }

        public void setExecutor_fee_percentage(Object executor_fee_percentage) {
            this.executor_fee_percentage = executor_fee_percentage;
        }

        public String getFb_id() {
            return fb_id;
        }

        public void setFb_id(String fb_id) {
            this.fb_id = fb_id;
        }

        public String getIg_id() {
            return ig_id;
        }

        public void setIg_id(String ig_id) {
            this.ig_id = ig_id;
        }

        public String getYt_id() {
            return yt_id;
        }

        public void setYt_id(String yt_id) {
            this.yt_id = yt_id;
        }

        public String getBlog_id() {
            return blog_id;
        }

        public void setBlog_id(String blog_id) {
            this.blog_id = blog_id;
        }

        public String getFb_url() {
            return fb_url;
        }

        public void setFb_url(String fb_url) {
            this.fb_url = fb_url;
        }

        public String getIg_url() {
            return ig_url;
        }

        public void setIg_url(String ig_url) {
            this.ig_url = ig_url;
        }

        public String getYt_url() {
            return yt_url;
        }

        public void setYt_url(String yt_url) {
            this.yt_url = yt_url;
        }

        public String getBlog_url() {
            return blog_url;
        }

        public void setBlog_url(String blog_url) {
            this.blog_url = blog_url;
        }

        public String getIg_followers() {
            return ig_followers;
        }

        public void setIg_followers(String ig_followers) {
            this.ig_followers = ig_followers;
        }

        public Object getFb_followers() {
            return fb_followers;
        }

        public void setFb_followers(Object fb_followers) {
            this.fb_followers = fb_followers;
        }

        public Object getYt_followers() {
            return yt_followers;
        }

        public void setYt_followers(Object yt_followers) {
            this.yt_followers = yt_followers;
        }

        public Object getBlog_followers() {
            return blog_followers;
        }

        public void setBlog_followers(Object blog_followers) {
            this.blog_followers = blog_followers;
        }

        public String getFb_expect_price() {
            return fb_expect_price;
        }

        public void setFb_expect_price(String fb_expect_price) {
            this.fb_expect_price = fb_expect_price;
        }

        public String getIg_expect_price() {
            return ig_expect_price;
        }

        public void setIg_expect_price(String ig_expect_price) {
            this.ig_expect_price = ig_expect_price;
        }

        public String getYt_expect_price() {
            return yt_expect_price;
        }

        public void setYt_expect_price(String yt_expect_price) {
            this.yt_expect_price = yt_expect_price;
        }

        public String getBlog_expect_price() {
            return blog_expect_price;
        }

        public void setBlog_expect_price(String blog_expect_price) {
            this.blog_expect_price = blog_expect_price;
        }

        public String getLine_id() {
            return line_id;
        }

        public void setLine_id(String line_id) {
            this.line_id = line_id;
        }

        public String getLine_url() {
            return line_url;
        }

        public void setLine_url(String line_url) {
            this.line_url = line_url;
        }

        public Object getFb_engagement_rate() {
            return fb_engagement_rate;
        }

        public void setFb_engagement_rate(Object fb_engagement_rate) {
            this.fb_engagement_rate = fb_engagement_rate;
        }

        public Object getIg_engagement_rate() {
            return ig_engagement_rate;
        }

        public void setIg_engagement_rate(Object ig_engagement_rate) {
            this.ig_engagement_rate = ig_engagement_rate;
        }

        public Object getYt_engagement_rate() {
            return yt_engagement_rate;
        }

        public void setYt_engagement_rate(Object yt_engagement_rate) {
            this.yt_engagement_rate = yt_engagement_rate;
        }

        public Object getBlog_engagement_rate() {
            return blog_engagement_rate;
        }

        public void setBlog_engagement_rate(Object blog_engagement_rate) {
            this.blog_engagement_rate = blog_engagement_rate;
        }

        public Object getFb_bu_engagement_rate() {
            return fb_bu_engagement_rate;
        }

        public void setFb_bu_engagement_rate(Object fb_bu_engagement_rate) {
            this.fb_bu_engagement_rate = fb_bu_engagement_rate;
        }

        public Object getIg_bu_engagement_rate() {
            return ig_bu_engagement_rate;
        }

        public void setIg_bu_engagement_rate(Object ig_bu_engagement_rate) {
            this.ig_bu_engagement_rate = ig_bu_engagement_rate;
        }

        public Object getYt_bu_engagement_rate() {
            return yt_bu_engagement_rate;
        }

        public void setYt_bu_engagement_rate(Object yt_bu_engagement_rate) {
            this.yt_bu_engagement_rate = yt_bu_engagement_rate;
        }

        public Object getBlog_bu_engagement_rate() {
            return blog_bu_engagement_rate;
        }

        public void setBlog_bu_engagement_rate(Object blog_bu_engagement_rate) {
            this.blog_bu_engagement_rate = blog_bu_engagement_rate;
        }

        public Object getBasic_income() {
            return basic_income;
        }

        public void setBasic_income(Object basic_income) {
            this.basic_income = basic_income;
        }

        public Object getLike_bonus_income_withdrawable() {
            return like_bonus_income_withdrawable;
        }

        public void setLike_bonus_income_withdrawable(Object like_bonus_income_withdrawable) {
            this.like_bonus_income_withdrawable = like_bonus_income_withdrawable;
        }

        public Object getLike_bonus_income_nonwithdrawable() {
            return like_bonus_income_nonwithdrawable;
        }

        public void setLike_bonus_income_nonwithdrawable(Object like_bonus_income_nonwithdrawable) {
            this.like_bonus_income_nonwithdrawable = like_bonus_income_nonwithdrawable;
        }

        public String getIg_story_expect_price() {
            return ig_story_expect_price;
        }

        public void setIg_story_expect_price(String ig_story_expect_price) {
            this.ig_story_expect_price = ig_story_expect_price;
        }

        public String getIg_tv_expect_price() {
            return ig_tv_expect_price;
        }

        public void setIg_tv_expect_price(String ig_tv_expect_price) {
            this.ig_tv_expect_price = ig_tv_expect_price;
        }

        public Object getAverage_big_heart() {
            return average_big_heart;
        }

        public void setAverage_big_heart(Object average_big_heart) {
            this.average_big_heart = average_big_heart;
        }

        public Object getBig_heart_cost() {
            return big_heart_cost;
        }

        public void setBig_heart_cost(Object big_heart_cost) {
            this.big_heart_cost = big_heart_cost;
        }

        public Object getManagement_company() {
            return management_company;
        }

        public void setManagement_company(Object management_company) {
            this.management_company = management_company;
        }

        public Object getRejection_category() {
            return rejection_category;
        }

        public void setRejection_category(Object rejection_category) {
            this.rejection_category = rejection_category;
        }

        public Object getCan_revenue() {
            return can_revenue;
        }

        public void setCan_revenue(Object can_revenue) {
            this.can_revenue = can_revenue;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public RolesBean getRoles() {
            return roles;
        }

        public void setRoles(RolesBean roles) {
            this.roles = roles;
        }

        public List<AudienceAgeBean> getAudience_age() {
            return audience_age;
        }

        public void setAudience_age(List<AudienceAgeBean> audience_age) {
            this.audience_age = audience_age;
        }

        public List<?> getAudience_region() {
            return audience_region;
        }

        public void setAudience_region(List<?> audience_region) {
            this.audience_region = audience_region;
        }

        public List<HabbitsBean> getHabbits() {
            return habbits;
        }

        public void setHabbits(List<HabbitsBean> habbits) {
            this.habbits = habbits;
        }

        public List<CommunityBean> getCommunity() {
            return community;
        }

        public void setCommunity(List<CommunityBean> community) {
            this.community = community;
        }

        public List<ContentsBean> getContents() {
            return contents;
        }

        public void setContents(List<ContentsBean> contents) {
            this.contents = contents;
        }

        public List<OccupationBean> getOccupation() {
            return occupation;
        }

        public void setOccupation(List<OccupationBean> occupation) {
            this.occupation = occupation;
        }

        public List<PersonalityBean> getPersonality() {
            return personality;
        }

        public void setPersonality(List<PersonalityBean> personality) {
            this.personality = personality;
        }

        public static class RolesBean {
            /**
             * kol : {"name":"kol","items":[]}
             */

            private KolBean kol;

            public KolBean getKol() {
                return kol;
            }

            public void setKol(KolBean kol) {
                this.kol = kol;
            }

            public static class KolBean {
                /**
                 * name : kol
                 * items : []
                 */

                private String name;
                private List<?> items;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public List<?> getItems() {
                    return items;
                }

                public void setItems(List<?> items) {
                    this.items = items;
                }
            }
        }

        public static class AudienceAgeBean {
            /**
             * tag_id : 13
             * name : 16-20
             */

            private int tag_id;
            private String name;

            public int getTag_id() {
                return tag_id;
            }

            public void setTag_id(int tag_id) {
                this.tag_id = tag_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class HabbitsBean {
            /**
             * tag_id : 25
             * name : 演奏
             */

            private int tag_id;
            private String name;

            public int getTag_id() {
                return tag_id;
            }

            public void setTag_id(int tag_id) {
                this.tag_id = tag_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class CommunityBean {
            /**
             * tag_id : 48
             * name : 音樂舞蹈
             */

            private int tag_id;
            private String name;

            public int getTag_id() {
                return tag_id;
            }

            public void setTag_id(int tag_id) {
                this.tag_id = tag_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class ContentsBean {
            /**
             * tag_id : 74
             * name : 美妝保養
             */

            private int tag_id;
            private String name;

            public int getTag_id() {
                return tag_id;
            }

            public void setTag_id(int tag_id) {
                this.tag_id = tag_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class OccupationBean {
            /**
             * tag_id : 88
             * name : 廣告行銷
             */

            private int tag_id;
            private String name;

            public int getTag_id() {
                return tag_id;
            }

            public void setTag_id(int tag_id) {
                this.tag_id = tag_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class PersonalityBean {
            /**
             * tag_id : 129
             * name : 時尚
             */

            private int tag_id;
            private String name;

            public int getTag_id() {
                return tag_id;
            }

            public void setTag_id(int tag_id) {
                this.tag_id = tag_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
