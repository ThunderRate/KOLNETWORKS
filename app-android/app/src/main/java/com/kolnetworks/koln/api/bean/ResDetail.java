package com.kolnetworks.koln.api.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ResDetail {
    /**
     * success : true
     * project : {"project_no":"20200805080100","uuid":"cd66a4e0-d6f1-11ea-98f0-09cd938c7b54","photo":"https://kolassets.s3-ap-northeast-1.amazonaws.com/uploads/4e686f374da3f205e26c2f2582a48697093b6328d4366e9729ce7f39e47e854c.png","name":"\b你是哪一族～原住民限時動態濾鏡（發布於臉書）","platform":1,"start_date":1596585600,"end_date":1598832000,"place":"taipei","introduction":"<p>活動內容：<\/p><p><br><\/p><p>玩原住民特色頭飾濾鏡～<\/p><p>讓我們穿戴特別的原住民頭飾吧！！<\/p><p><br><\/p><p>發布在Facebook！<\/p><p><br><\/p><p>1.限時動態一則：使用濾鏡<\/p><p>2.FB PO文一則：影片下載後發文在臉書。<\/p><p>3.文需放上濾鏡連結，讓大家也去玩濾鏡。<\/p><p>4.帶到指定內文關鍵字<\/p><p><br><\/p><p>濾鏡介紹：<\/p><p>1.隨機成為一族的原住民<\/p><p>2.搭配該族的頭飾及特徵<\/p><p>3.可選擇男生或女生，頭飾會不一樣唷！<\/p><p><br><\/p><p>濾鏡示意圖： <a href=\"https://drive.google.com/file/d/1UxDX1jR8jVng6YjoLbgCdLaQkxlTpB_L/view\" rel=\"noopener noreferrer\" target=\"_blank\">https://drive.google.com/file/d/1UxDX1jR8jVng6YjoLbgCdLaQkxlTpB_L/view<\/a><\/p>","invite_deadline":1598832000,"bonus_deadline":null,"post_link":null,"likes":null,"project_status":2,"comments":"活動是發布在臉書唷！！","engagement_rate":null,"shares":null,"budget":90000,"reach":null,"service_fee":null,"volume":null,"revenue":null,"volume_rank":null,"post_revenue":null,"commission":null,"dividend_income":null,"traveling_expenses":null,"modeling_fee":null,"reached_people":null,"gender_info":null,"geo_info":null,"age_info":null,"payment_status":1,"remark":"","conversation_record_link":null,"messenger":"https://lin.ee/wLWXSLUh","created_at":1596614460,"updated_at":1596627968,"removed_at":null,"project_kols":[{"kol_status":2,"is_picked":false,"kol_price":0,"kol_expect_price":null,"applied_at":null}],"board":{"uuid":"cd67dd60-d6f1-11ea-98f0-09cd938c7b54","content":"�你是哪一族～原住民限時動態濾鏡（發布於臉書）","created_at":1596614460,"updated_at":null}}
     */

    private boolean success;
    private ProjectBean project;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ProjectBean getProject() {
        return project;
    }

    public void setProject(ProjectBean project) {
        this.project = project;
    }

    public static class ProjectBean implements Parcelable {
        /**
         * project_no : 20200805080100
         * uuid : cd66a4e0-d6f1-11ea-98f0-09cd938c7b54
         * photo : https://kolassets.s3-ap-northeast-1.amazonaws.com/uploads/4e686f374da3f205e26c2f2582a48697093b6328d4366e9729ce7f39e47e854c.png
         * name : 你是哪一族～原住民限時動態濾鏡（發布於臉書）
         * platform : 1
         * start_date : 1596585600
         * end_date : 1598832000
         * place : taipei
         * introduction : <p>活動內容：</p><p><br></p><p>玩原住民特色頭飾濾鏡～</p><p>讓我們穿戴特別的原住民頭飾吧！！</p><p><br></p><p>發布在Facebook！</p><p><br></p><p>1.限時動態一則：使用濾鏡</p><p>2.FB PO文一則：影片下載後發文在臉書。</p><p>3.文需放上濾鏡連結，讓大家也去玩濾鏡。</p><p>4.帶到指定內文關鍵字</p><p><br></p><p>濾鏡介紹：</p><p>1.隨機成為一族的原住民</p><p>2.搭配該族的頭飾及特徵</p><p>3.可選擇男生或女生，頭飾會不一樣唷！</p><p><br></p><p>濾鏡示意圖： <a href="https://drive.google.com/file/d/1UxDX1jR8jVng6YjoLbgCdLaQkxlTpB_L/view" rel="noopener noreferrer" target="_blank">https://drive.google.com/file/d/1UxDX1jR8jVng6YjoLbgCdLaQkxlTpB_L/view</a></p>
         * invite_deadline : 1598832000
         * bonus_deadline : null
         * post_link : null
         * likes : null
         * project_status : 2
         * comments : 活動是發布在臉書唷！！
         * engagement_rate : null
         * shares : null
         * budget : 90000
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
         * created_at : 1596614460
         * updated_at : 1596627968
         * removed_at : null
         * project_kols : [{"kol_status":2,"is_picked":false,"kol_price":0,"kol_expect_price":null,"applied_at":null}]
         * board : {"uuid":"cd67dd60-d6f1-11ea-98f0-09cd938c7b54","content":"�你是哪一族～原住民限時動態濾鏡（發布於臉書）","created_at":1596614460,"updated_at":null}
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
        private String bonus_deadline;
        private String post_link;
        private String likes;
        private int project_status;
        private String comments;
        private String engagement_rate;
        private String shares;
        private int budget;
        private String reach;
        private String service_fee;
        private String volume;
        private String revenue;
        private String volume_rank;
        private String post_revenue;
        private String commission;
        private String dividend_income;
        private String traveling_expenses;
        private String modeling_fee;
        private String reached_people;
        private String gender_info;
        private String geo_info;
        private String age_info;
        private int payment_status;
        private String remark;
        private String conversation_record_link;
        private String messenger;
        private int created_at;
        private int updated_at;
        private String removed_at;
        private BoardBean board;
        private List<ProjectKolsBean> project_kols;

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

        public String getBonus_deadline() {
            return bonus_deadline;
        }

        public void setBonus_deadline(String bonus_deadline) {
            this.bonus_deadline = bonus_deadline;
        }

        public String getPost_link() {
            return post_link;
        }

        public void setPost_link(String post_link) {
            this.post_link = post_link;
        }

        public String getLikes() {
            return likes;
        }

        public void setLikes(String likes) {
            this.likes = likes;
        }

        public int getProject_status() {
            return project_status;
        }

        public void setProject_status(int project_status) {
            this.project_status = project_status;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getEngagement_rate() {
            return engagement_rate;
        }

        public void setEngagement_rate(String engagement_rate) {
            this.engagement_rate = engagement_rate;
        }

        public String getShares() {
            return shares;
        }

        public void setShares(String shares) {
            this.shares = shares;
        }

        public int getBudget() {
            return budget;
        }

        public void setBudget(int budget) {
            this.budget = budget;
        }

        public String getReach() {
            return reach;
        }

        public void setReach(String reach) {
            this.reach = reach;
        }

        public String getService_fee() {
            return service_fee;
        }

        public void setService_fee(String service_fee) {
            this.service_fee = service_fee;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getRevenue() {
            return revenue;
        }

        public void setRevenue(String revenue) {
            this.revenue = revenue;
        }

        public String getVolume_rank() {
            return volume_rank;
        }

        public void setVolume_rank(String volume_rank) {
            this.volume_rank = volume_rank;
        }

        public String getPost_revenue() {
            return post_revenue;
        }

        public void setPost_revenue(String post_revenue) {
            this.post_revenue = post_revenue;
        }

        public String getCommission() {
            return commission;
        }

        public void setCommission(String commission) {
            this.commission = commission;
        }

        public String getDividend_income() {
            return dividend_income;
        }

        public void setDividend_income(String dividend_income) {
            this.dividend_income = dividend_income;
        }

        public String getTraveling_expenses() {
            return traveling_expenses;
        }

        public void setTraveling_expenses(String traveling_expenses) {
            this.traveling_expenses = traveling_expenses;
        }

        public String getModeling_fee() {
            return modeling_fee;
        }

        public void setModeling_fee(String modeling_fee) {
            this.modeling_fee = modeling_fee;
        }

        public String getReached_people() {
            return reached_people;
        }

        public void setReached_people(String reached_people) {
            this.reached_people = reached_people;
        }

        public String getGender_info() {
            return gender_info;
        }

        public void setGender_info(String gender_info) {
            this.gender_info = gender_info;
        }

        public String getGeo_info() {
            return geo_info;
        }

        public void setGeo_info(String geo_info) {
            this.geo_info = geo_info;
        }

        public String getAge_info() {
            return age_info;
        }

        public void setAge_info(String age_info) {
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

        public String getConversation_record_link() {
            return conversation_record_link;
        }

        public void setConversation_record_link(String conversation_record_link) {
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

        public String getRemoved_at() {
            return removed_at;
        }

        public void setRemoved_at(String removed_at) {
            this.removed_at = removed_at;
        }

        public BoardBean getBoard() {
            return board;
        }

        public void setBoard(BoardBean board) {
            this.board = board;
        }

        public List<ProjectKolsBean> getProject_kols() {
            return project_kols;
        }

        public void setProject_kols(List<ProjectKolsBean> project_kols) {
            this.project_kols = project_kols;
        }

        public static class BoardBean implements Parcelable {
            /**
             * uuid : cd67dd60-d6f1-11ea-98f0-09cd938c7b54
             * content : �你是哪一族～原住民限時動態濾鏡（發布於臉書）
             * created_at : 1596614460
             * updated_at : null
             */

            private String uuid;
            private String content;
            private int created_at;
            private String updated_at;

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getCreated_at() {
                return created_at;
            }

            public void setCreated_at(int created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.uuid);
                dest.writeString(this.content);
                dest.writeInt(this.created_at);
                dest.writeString(this.updated_at);
            }

            public BoardBean() {
            }

            protected BoardBean(Parcel in) {
                this.uuid = in.readString();
                this.content = in.readString();
                this.created_at = in.readInt();
                this.updated_at = in.readString();
            }

            public static final Creator<BoardBean> CREATOR = new Creator<BoardBean>() {
                @Override
                public BoardBean createFromParcel(Parcel source) {
                    return new BoardBean(source);
                }

                @Override
                public BoardBean[] newArray(int size) {
                    return new BoardBean[size];
                }
            };
        }

        public static class ProjectKolsBean implements Parcelable {
            /**
             * kol_status : 2
             * is_picked : false
             * kol_price : 0
             * kol_expect_price : null
             * applied_at : null
             */

            private int kol_status;
            private boolean is_picked;
            private int kol_price;
            private String kol_expect_price;
            private String applied_at;

            public int getKol_status() {
                return kol_status;
            }

            public void setKol_status(int kol_status) {
                this.kol_status = kol_status;
            }

            public boolean isIs_picked() {
                return is_picked;
            }

            public void setIs_picked(boolean is_picked) {
                this.is_picked = is_picked;
            }

            public int getKol_price() {
                return kol_price;
            }

            public void setKol_price(int kol_price) {
                this.kol_price = kol_price;
            }

            public String getKol_expect_price() {
                return kol_expect_price;
            }

            public void setKol_expect_price(String kol_expect_price) {
                this.kol_expect_price = kol_expect_price;
            }

            public String getApplied_at() {
                return applied_at;
            }

            public void setApplied_at(String applied_at) {
                this.applied_at = applied_at;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.kol_status);
                dest.writeByte(this.is_picked ? (byte) 1 : (byte) 0);
                dest.writeInt(this.kol_price);
                dest.writeString(this.kol_expect_price);
                dest.writeString(this.applied_at);
            }

            public ProjectKolsBean() {
            }

            protected ProjectKolsBean(Parcel in) {
                this.kol_status = in.readInt();
                this.is_picked = in.readByte() != 0;
                this.kol_price = in.readInt();
                this.kol_expect_price = in.readString();
                this.applied_at = in.readString();
            }

            public static final Creator<ProjectKolsBean> CREATOR = new Creator<ProjectKolsBean>() {
                @Override
                public ProjectKolsBean createFromParcel(Parcel source) {
                    return new ProjectKolsBean(source);
                }

                @Override
                public ProjectKolsBean[] newArray(int size) {
                    return new ProjectKolsBean[size];
                }
            };
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
            dest.writeInt(this.platform);
            dest.writeInt(this.start_date);
            dest.writeInt(this.end_date);
            dest.writeString(this.place);
            dest.writeString(this.introduction);
            dest.writeInt(this.invite_deadline);
            dest.writeString(this.bonus_deadline);
            dest.writeString(this.post_link);
            dest.writeString(this.likes);
            dest.writeInt(this.project_status);
            dest.writeString(this.comments);
            dest.writeString(this.engagement_rate);
            dest.writeString(this.shares);
            dest.writeInt(this.budget);
            dest.writeString(this.reach);
            dest.writeString(this.service_fee);
            dest.writeString(this.volume);
            dest.writeString(this.revenue);
            dest.writeString(this.volume_rank);
            dest.writeString(this.post_revenue);
            dest.writeString(this.commission);
            dest.writeString(this.dividend_income);
            dest.writeString(this.traveling_expenses);
            dest.writeString(this.modeling_fee);
            dest.writeString(this.reached_people);
            dest.writeString(this.gender_info);
            dest.writeString(this.geo_info);
            dest.writeString(this.age_info);
            dest.writeInt(this.payment_status);
            dest.writeString(this.remark);
            dest.writeString(this.conversation_record_link);
            dest.writeString(this.messenger);
            dest.writeInt(this.created_at);
            dest.writeInt(this.updated_at);
            dest.writeString(this.removed_at);
            dest.writeParcelable(this.board, flags);
            dest.writeList(this.project_kols);
        }

        public ProjectBean() {
        }

        protected ProjectBean(Parcel in) {
            this.project_no = in.readString();
            this.uuid = in.readString();
            this.photo = in.readString();
            this.name = in.readString();
            this.platform = in.readInt();
            this.start_date = in.readInt();
            this.end_date = in.readInt();
            this.place = in.readString();
            this.introduction = in.readString();
            this.invite_deadline = in.readInt();
            this.bonus_deadline = in.readString();
            this.post_link = in.readString();
            this.likes = in.readString();
            this.project_status = in.readInt();
            this.comments = in.readString();
            this.engagement_rate = in.readString();
            this.shares = in.readString();
            this.budget = in.readInt();
            this.reach = in.readString();
            this.service_fee = in.readString();
            this.volume = in.readString();
            this.revenue = in.readString();
            this.volume_rank = in.readString();
            this.post_revenue = in.readString();
            this.commission = in.readString();
            this.dividend_income = in.readString();
            this.traveling_expenses = in.readString();
            this.modeling_fee = in.readString();
            this.reached_people = in.readString();
            this.gender_info = in.readString();
            this.geo_info = in.readString();
            this.age_info = in.readString();
            this.payment_status = in.readInt();
            this.remark = in.readString();
            this.conversation_record_link = in.readString();
            this.messenger = in.readString();
            this.created_at = in.readInt();
            this.updated_at = in.readInt();
            this.removed_at = in.readString();
            this.board = in.readParcelable(BoardBean.class.getClassLoader());
            this.project_kols = new ArrayList<ProjectKolsBean>();
            in.readList(this.project_kols, ProjectKolsBean.class.getClassLoader());
        }

        public static final Parcelable.Creator<ProjectBean> CREATOR = new Parcelable.Creator<ProjectBean>() {
            @Override
            public ProjectBean createFromParcel(Parcel source) {
                return new ProjectBean(source);
            }

            @Override
            public ProjectBean[] newArray(int size) {
                return new ProjectBean[size];
            }
        };
    }
}
