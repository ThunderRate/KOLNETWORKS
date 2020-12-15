package com.kolnetworks.koln.api.bean;

import java.util.List;

public class ResponseManagerProjects {
    /**
     * success : true
     * projects : [{"project_no":"20200518083845","uuid":"fcb124c0-98e2-11ea-8fd0-5b574ac3b7f7","photo":"https://kolassets.s3-ap-northeast-1.amazonaws.com/uploads/c913e93536147a4bf8512b99e2361e5a1fb1149cfe5d4814c841aeb1a060b58a.jpg","name":"BFFECT 精華液 - 最實在的科學保養實驗","platform":2,"start_date":1592179200,"end_date":1593993600,"place":"NA","introduction":"<h2><strong style=\"color: rgb(0, 0, 0); background-color: transparent;\">【宣傳主軸】<\/strong><\/h2><p><span style=\"color: rgb(0, 0, 0); background-color: transparent;\">透過實測紀錄，讓 BFFECT 產品自然融入個人的生活情境，讓粉絲認識 BFFECT 品牌和產品。<\/span><\/p><p><br><\/p><p><br><\/p><p><br><\/p><h2><strong style=\"color: rgb(0, 0, 0); background-color: transparent;\">【合作內容】<\/strong><\/h2><p><span style=\"color: rgb(0, 0, 0); background-color: transparent;\">請發揮個人特色和生活創意，紀錄連續三週使用 BFFECT 精華液的過程、使用前後的變化與心得。<\/span><\/p><p><br><\/p><ul><li><span style=\"background-color: transparent;\">每週發佈一則 IG 限動，若能以 IGTV 形式分享將被優先考慮合作<\/span><\/li><li><span style=\"background-color: transparent;\">最後一週發佈一篇 IG 貼文，需包含 4~5 張指定照片<\/span><\/li><li><span style=\"background-color: transparent;\">廠商將提供粉絲福利(擇一)：產品抽獎或優惠碼 <\/span><\/li><li><span style=\"background-color: transparent;\">內文需包含廠商指定的關鍵字 3~5 個＆hashtag <\/span>3~5 個<\/li><\/ul><p><br><\/p><p><br><\/p><p><br><\/p><h2><strong style=\"color: rgb(32, 33, 36);\">【合作規範】<\/strong><\/h2><ul><li><span style=\"color: rgb(0, 102, 204);\">正式的合作內容，請依後續確認合作後的合作備忘錄為準。<\/span><\/li><li><span style=\"color: rgb(0, 102, 204);\">本次合作需審稿，如果發佈內容不符廠商需求，需修改並重新發佈。<\/span><\/li><li><span style=\"color: rgb(32, 33, 36);\">合作期間帳號必須為公開，且發文需保留到廠商活動結束，請勿刪除。<\/span><\/li><li><span style=\"color: rgb(32, 33, 36);\">活動結束後，需回傳後台數據才會收到合作費用。<\/span><\/li><\/ul><p><br><\/p><p><br><\/p><p><br><\/p><h3><br><\/h3><h3><strong>►► 關於此專案合作，若有任何疑問，歡迎直接點選右下方的「黑色留言按鈕」喔！<\/strong><\/h3>","invite_deadline":1591920000,"bonus_deadline":null,"post_link":null,"likes":null,"project_status":2,"comments":null,"engagement_rate":null,"shares":null,"budget":100000,"reach":null,"service_fee":null,"volume":null,"revenue":null,"volume_rank":null,"post_revenue":null,"commission":null,"dividend_income":null,"traveling_expenses":null,"modeling_fee":null,"reached_people":null,"gender_info":null,"geo_info":null,"age_info":null,"payment_status":1,"remark":"","conversation_record_link":null,"messenger":"https://lin.ee/wLWXSLUh","created_at":1589791125,"updated_at":1592212097,"removed_at":null}]
     */

    private boolean success;
    private List<ProjectsBean> projects;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ProjectsBean> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectsBean> projects) {
        this.projects = projects;
    }

    public static class ProjectsBean {
        /**
         * project_no : 20200518083845
         * uuid : fcb124c0-98e2-11ea-8fd0-5b574ac3b7f7
         * photo : https://kolassets.s3-ap-northeast-1.amazonaws.com/uploads/c913e93536147a4bf8512b99e2361e5a1fb1149cfe5d4814c841aeb1a060b58a.jpg
         * name : BFFECT 精華液 - 最實在的科學保養實驗
         * platform : 2
         * start_date : 1592179200
         * end_date : 1593993600
         * place : NA
         * introduction : <h2><strong style="color: rgb(0, 0, 0); background-color: transparent;">【宣傳主軸】</strong></h2><p><span style="color: rgb(0, 0, 0); background-color: transparent;">透過實測紀錄，讓 BFFECT 產品自然融入個人的生活情境，讓粉絲認識 BFFECT 品牌和產品。</span></p><p><br></p><p><br></p><p><br></p><h2><strong style="color: rgb(0, 0, 0); background-color: transparent;">【合作內容】</strong></h2><p><span style="color: rgb(0, 0, 0); background-color: transparent;">請發揮個人特色和生活創意，紀錄連續三週使用 BFFECT 精華液的過程、使用前後的變化與心得。</span></p><p><br></p><ul><li><span style="background-color: transparent;">每週發佈一則 IG 限動，若能以 IGTV 形式分享將被優先考慮合作</span></li><li><span style="background-color: transparent;">最後一週發佈一篇 IG 貼文，需包含 4~5 張指定照片</span></li><li><span style="background-color: transparent;">廠商將提供粉絲福利(擇一)：產品抽獎或優惠碼 </span></li><li><span style="background-color: transparent;">內文需包含廠商指定的關鍵字 3~5 個＆hashtag </span>3~5 個</li></ul><p><br></p><p><br></p><p><br></p><h2><strong style="color: rgb(32, 33, 36);">【合作規範】</strong></h2><ul><li><span style="color: rgb(0, 102, 204);">正式的合作內容，請依後續確認合作後的合作備忘錄為準。</span></li><li><span style="color: rgb(0, 102, 204);">本次合作需審稿，如果發佈內容不符廠商需求，需修改並重新發佈。</span></li><li><span style="color: rgb(32, 33, 36);">合作期間帳號必須為公開，且發文需保留到廠商活動結束，請勿刪除。</span></li><li><span style="color: rgb(32, 33, 36);">活動結束後，需回傳後台數據才會收到合作費用。</span></li></ul><p><br></p><p><br></p><p><br></p><h3><br></h3><h3><strong>►► 關於此專案合作，若有任何疑問，歡迎直接點選右下方的「黑色留言按鈕」喔！</strong></h3>
         * invite_deadline : 1591920000
         * bonus_deadline : null
         * post_link : null
         * likes : null
         * project_status : 2
         * comments : null
         * engagement_rate : null
         * shares : null
         * budget : 100000
         * reach : null
         * service_fee : null
         * volume : null
         * revenue : null
         * volume_rank : null
         * post_revenue : null
         * commission : null
         * dividend_income : null
         * traveling_expenses : null
         * modeling_fee : null
         * reached_people : null
         * gender_info : null
         * geo_info : null
         * age_info : null
         * payment_status : 1
         * remark :
         * conversation_record_link : null
         * messenger : https://lin.ee/wLWXSLUh
         * created_at : 1589791125
         * updated_at : 1592212097
         * removed_at : null
         */

        private String project_no;
        private String uuid;
        private String photo;
        private String name;
        private int platform;
        private int start_date;
        private int end_date;
        private String place;
        private String introduction;
        private int invite_deadline;
        private Object bonus_deadline;
        private Object post_link;
        private Object likes;
        private int project_status;
        private Object comments;
        private Object engagement_rate;
        private Object shares;
        private int budget;
        private Object reach;
        private Object service_fee;
        private Object volume;
        private Object revenue;
        private Object volume_rank;
        private Object post_revenue;
        private Object commission;
        private Object dividend_income;
        private Object traveling_expenses;
        private Object modeling_fee;
        private Object reached_people;
        private Object gender_info;
        private Object geo_info;
        private Object age_info;
        private int payment_status;
        private String remark;
        private Object conversation_record_link;
        private String messenger;
        private int created_at;
        private int updated_at;
        private Object removed_at;

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

        public int getPlatform() {
            return platform;
        }

        public void setPlatform(int platform) {
            this.platform = platform;
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

        public Object getBonus_deadline() {
            return bonus_deadline;
        }

        public void setBonus_deadline(Object bonus_deadline) {
            this.bonus_deadline = bonus_deadline;
        }

        public Object getPost_link() {
            return post_link;
        }

        public void setPost_link(Object post_link) {
            this.post_link = post_link;
        }

        public Object getLikes() {
            return likes;
        }

        public void setLikes(Object likes) {
            this.likes = likes;
        }

        public int getProject_status() {
            return project_status;
        }

        public void setProject_status(int project_status) {
            this.project_status = project_status;
        }

        public Object getComments() {
            return comments;
        }

        public void setComments(Object comments) {
            this.comments = comments;
        }

        public Object getEngagement_rate() {
            return engagement_rate;
        }

        public void setEngagement_rate(Object engagement_rate) {
            this.engagement_rate = engagement_rate;
        }

        public Object getShares() {
            return shares;
        }

        public void setShares(Object shares) {
            this.shares = shares;
        }

        public int getBudget() {
            return budget;
        }

        public void setBudget(int budget) {
            this.budget = budget;
        }

        public Object getReach() {
            return reach;
        }

        public void setReach(Object reach) {
            this.reach = reach;
        }

        public Object getService_fee() {
            return service_fee;
        }

        public void setService_fee(Object service_fee) {
            this.service_fee = service_fee;
        }

        public Object getVolume() {
            return volume;
        }

        public void setVolume(Object volume) {
            this.volume = volume;
        }

        public Object getRevenue() {
            return revenue;
        }

        public void setRevenue(Object revenue) {
            this.revenue = revenue;
        }

        public Object getVolume_rank() {
            return volume_rank;
        }

        public void setVolume_rank(Object volume_rank) {
            this.volume_rank = volume_rank;
        }

        public Object getPost_revenue() {
            return post_revenue;
        }

        public void setPost_revenue(Object post_revenue) {
            this.post_revenue = post_revenue;
        }

        public Object getCommission() {
            return commission;
        }

        public void setCommission(Object commission) {
            this.commission = commission;
        }

        public Object getDividend_income() {
            return dividend_income;
        }

        public void setDividend_income(Object dividend_income) {
            this.dividend_income = dividend_income;
        }

        public Object getTraveling_expenses() {
            return traveling_expenses;
        }

        public void setTraveling_expenses(Object traveling_expenses) {
            this.traveling_expenses = traveling_expenses;
        }

        public Object getModeling_fee() {
            return modeling_fee;
        }

        public void setModeling_fee(Object modeling_fee) {
            this.modeling_fee = modeling_fee;
        }

        public Object getReached_people() {
            return reached_people;
        }

        public void setReached_people(Object reached_people) {
            this.reached_people = reached_people;
        }

        public Object getGender_info() {
            return gender_info;
        }

        public void setGender_info(Object gender_info) {
            this.gender_info = gender_info;
        }

        public Object getGeo_info() {
            return geo_info;
        }

        public void setGeo_info(Object geo_info) {
            this.geo_info = geo_info;
        }

        public Object getAge_info() {
            return age_info;
        }

        public void setAge_info(Object age_info) {
            this.age_info = age_info;
        }

        public int getPayment_status() {
            return payment_status;
        }

        public void setPayment_status(int payment_status) {
            this.payment_status = payment_status;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public Object getConversation_record_link() {
            return conversation_record_link;
        }

        public void setConversation_record_link(Object conversation_record_link) {
            this.conversation_record_link = conversation_record_link;
        }

        public String getMessenger() {
            return messenger;
        }

        public void setMessenger(String messenger) {
            this.messenger = messenger;
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

        public Object getRemoved_at() {
            return removed_at;
        }

        public void setRemoved_at(Object removed_at) {
            this.removed_at = removed_at;
        }
    }
}
