package com.kolnetworks.koln.api.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ResponseProject {

    /**
     * success : true
     * projects : [{"project_id":37,"project_no":"20200424022947","user_id":1,"uuid":"77407c90-85d3-11ea-9d1a-fd06d10b9040","photo":"https://kolassets.s3-ap-northeast-1.amazonaws.com/uploads/5d89119d4f468e589a8ebe212d900e6c318b3b1e1ae5edd3fcb66a79372a66cb.JPG","name":"真人版動物森林朋友","platform":2,"start_date":1587945600,"end_date":1588118400,"place":"大自然","introduction":"真人版動物森林朋友O","invite_deadline":1587772800,"bonus_deadline":null,"post_link":null,"likes":null,"project_status":2,"comments":null,"engagement_rate":null,"shares":null,"budget":4000,"reach":null,"service_fee":null,"volume":null,"revenue":null,"volume_rank":null,"post_revenue":null,"commission":null,"dividend_income":null,"traveling_expenses":null,"modeling_fee":null,"reached_people":null,"gender_info":null,"geo_info":null,"age_info":null,"payment_status":1,"remark":"","conversation_record_link":null,"created_at":1587695387,"updated_at":null,"removed_at":null,"project_kols":[{"kol_status":1,"is_picked":true,"kol_price":64}],"board":{"uuid":"77413fe0-85d3-11ea-9d1a-fd06d10b9040","content":"真人版動物森林朋友","created_at":1587695387,"updated_at":null}}]
     * count : 1
     */

    private boolean success;
    private int count;
    private List<ProjectsBean> projects;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ProjectsBean> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectsBean> projects) {
        this.projects = projects;
    }

    public static class ProjectsBean implements Parcelable {
        /**
         * project_id : 37
         * project_no : 20200424022947
         * user_id : 1
         * uuid : 77407c90-85d3-11ea-9d1a-fd06d10b9040
         * photo : https://kolassets.s3-ap-northeast-1.amazonaws.com/uploads/5d89119d4f468e589a8ebe212d900e6c318b3b1e1ae5edd3fcb66a79372a66cb.JPG
         * name : 真人版動物森林朋友
         * platform : 2
         * start_date : 1587945600
         * end_date : 1588118400
         * place : 大自然
         * introduction : 真人版動物森林朋友O
         * invite_deadline : 1587772800
         * bonus_deadline : null
         * post_link : null
         * likes : null
         * project_status : 2
         * comments : null
         * engagement_rate : null
         * shares : null
         * budget : 4000
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
         * created_at : 1587695387
         * updated_at : null
         * removed_at : null
         * project_kols : [{"kol_status":1,"is_picked":true,"kol_price":64}]
         * board : {"uuid":"77413fe0-85d3-11ea-9d1a-fd06d10b9040","content":"真人版動物森林朋友","created_at":1587695387,"updated_at":null}
         */

        private int project_id;
        private String project_no;
        private int user_id;
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
        private String comments;
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
        private String messenger;
        private Object conversation_record_link;
        private int created_at;
        private Object updated_at;
        private Object removed_at;
        private BoardBean board;
        private List<ProjectKolsBean> project_kols;

        public String getMessenger() {
            return messenger;
        }

        public void setMessenger(String messenger) {
            this.messenger = messenger;
        }

        public int getProject_id() {
            return project_id;
        }

        public void setProject_id(int project_id) {
            this.project_id = project_id;
        }

        public String getProject_no() {
            return project_no;
        }

        public void setProject_no(String project_no) {
            this.project_no = project_no;
        }

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

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
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

        public Object getRemoved_at() {
            return removed_at;
        }

        public void setRemoved_at(Object removed_at) {
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

        public static class BoardBean {
            /**
             * uuid : 77413fe0-85d3-11ea-9d1a-fd06d10b9040
             * content : 真人版動物森林朋友
             * created_at : 1587695387
             * updated_at : null
             */

            private String uuid;
            private String content;
            private int created_at;
            private Object updated_at;

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

            public Object getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(Object updated_at) {
                this.updated_at = updated_at;
            }
        }

        public static class ProjectKolsBean implements Parcelable {
            /**
             * kol_status : 1
             * is_picked : true
             * kol_price : 64
             */

            private int kol_status;
            private boolean is_picked;
            private int kol_price;
            private int kol_expect_price;

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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.kol_status);
                dest.writeByte(this.is_picked ? (byte) 1 : (byte) 0);
                dest.writeInt(this.kol_price);
                dest.writeInt(this.kol_expect_price);
            }

            public ProjectKolsBean() {
            }

            protected ProjectKolsBean(Parcel in) {
                this.kol_status = in.readInt();
                this.is_picked = in.readByte() != 0;
                this.kol_price = in.readInt();
                this.kol_expect_price = in.readInt();
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
            dest.writeInt(this.project_id);
            dest.writeString(this.project_no);
            dest.writeInt(this.user_id);
            dest.writeString(this.uuid);
            dest.writeString(this.photo);
            dest.writeString(this.name);
            dest.writeInt(this.platform);
            dest.writeInt(this.start_date);
            dest.writeInt(this.end_date);
            dest.writeString(this.place);
            dest.writeString(this.introduction);
            dest.writeInt(this.invite_deadline);
            dest.writeInt(this.project_status);
            dest.writeInt(this.budget);
            dest.writeInt(this.payment_status);
            dest.writeString(this.remark);
            dest.writeInt(this.created_at);
            dest.writeList(this.project_kols);
        }

        public ProjectsBean() {
        }

        protected ProjectsBean(Parcel in) {
            this.project_id = in.readInt();
            this.project_no = in.readString();
            this.user_id = in.readInt();
            this.uuid = in.readString();
            this.photo = in.readString();
            this.name = in.readString();
            this.platform = in.readInt();
            this.start_date = in.readInt();
            this.end_date = in.readInt();
            this.place = in.readString();
            this.introduction = in.readString();
            this.invite_deadline = in.readInt();
            this.bonus_deadline = in.readParcelable(Object.class.getClassLoader());
            this.post_link = in.readParcelable(Object.class.getClassLoader());
            this.likes = in.readParcelable(Object.class.getClassLoader());
            this.project_status = in.readInt();
            this.comments = in.readParcelable(Object.class.getClassLoader());
            this.engagement_rate = in.readParcelable(Object.class.getClassLoader());
            this.shares = in.readParcelable(Object.class.getClassLoader());
            this.budget = in.readInt();
            this.reach = in.readParcelable(Object.class.getClassLoader());
            this.service_fee = in.readParcelable(Object.class.getClassLoader());
            this.volume = in.readParcelable(Object.class.getClassLoader());
            this.revenue = in.readParcelable(Object.class.getClassLoader());
            this.volume_rank = in.readParcelable(Object.class.getClassLoader());
            this.post_revenue = in.readParcelable(Object.class.getClassLoader());
            this.commission = in.readParcelable(Object.class.getClassLoader());
            this.dividend_income = in.readParcelable(Object.class.getClassLoader());
            this.traveling_expenses = in.readParcelable(Object.class.getClassLoader());
            this.modeling_fee = in.readParcelable(Object.class.getClassLoader());
            this.reached_people = in.readParcelable(Object.class.getClassLoader());
            this.gender_info = in.readParcelable(Object.class.getClassLoader());
            this.geo_info = in.readParcelable(Object.class.getClassLoader());
            this.age_info = in.readParcelable(Object.class.getClassLoader());
            this.payment_status = in.readInt();
            this.remark = in.readString();
            this.conversation_record_link = in.readParcelable(Object.class.getClassLoader());
            this.created_at = in.readInt();
            this.updated_at = in.readParcelable(Object.class.getClassLoader());
            this.removed_at = in.readParcelable(Object.class.getClassLoader());
            this.board = in.readParcelable(BoardBean.class.getClassLoader());
            this.project_kols = new ArrayList<ProjectKolsBean>();
            in.readList(this.project_kols, ProjectKolsBean.class.getClassLoader());
        }

        public static final Parcelable.Creator<ProjectsBean> CREATOR = new Parcelable.Creator<ProjectsBean>() {
            @Override
            public ProjectsBean createFromParcel(Parcel source) {
                return new ProjectsBean(source);
            }

            @Override
            public ProjectsBean[] newArray(int size) {
                return new ProjectsBean[size];
            }
        };
    }
}
