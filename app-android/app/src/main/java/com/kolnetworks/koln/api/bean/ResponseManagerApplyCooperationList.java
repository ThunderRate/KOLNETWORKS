package com.kolnetworks.koln.api.bean;

import java.util.List;

public class ResponseManagerApplyCooperationList {
    /**
     * success : true
     * kols : [{"kol_status":6,"kol_price":1500,"kol_expect_price":1000,"is_picked":false,"applied_at":null,"created_at":null,"updated_at":null,"project":{"uuid":"fcb124c0-98e2-11ea-8fd0-5b574ac3b7f7"},"user":{"display_name":"Nick","uuid":"83253050-837b-11ea-a7dc-0d2999c5fd9c"}},{"kol_status":7,"kol_price":84,"kol_expect_price":1500,"is_picked":false,"applied_at":null,"created_at":null,"updated_at":null,"project":{"uuid":"fcb124c0-98e2-11ea-8fd0-5b574ac3b7f7"},"user":{"display_name":"Ruby 🏖️","uuid":"d5dea4a0-8dfa-11ea-b677-47701d184629"}},{"kol_status":2,"kol_price":0,"kol_expect_price":null,"is_picked":false,"applied_at":null,"created_at":null,"updated_at":null,"project":{"uuid":"fcb124c0-98e2-11ea-8fd0-5b574ac3b7f7"},"user":{"display_name":"Lulu🐋","uuid":"32635360-aa2a-11ea-84fc-61a875137ac2"}},{"kol_status":2,"kol_price":0,"kol_expect_price":null,"is_picked":false,"applied_at":null,"created_at":null,"updated_at":null,"project":{"uuid":"fcb124c0-98e2-11ea-8fd0-5b574ac3b7f7"},"user":{"display_name":" ","uuid":"5742ac70-ac06-11ea-90a1-536b33567b26"}},{"kol_status":2,"kol_price":0,"kol_expect_price":null,"is_picked":false,"applied_at":null,"created_at":null,"updated_at":null,"project":{"uuid":"fcb124c0-98e2-11ea-8fd0-5b574ac3b7f7"},"user":{"display_name":"","uuid":"20a65190-aa39-11ea-9571-271fa8398251"}},{"kol_status":2,"kol_price":0,"kol_expect_price":null,"is_picked":false,"applied_at":null,"created_at":null,"updated_at":null,"project":{"uuid":"fcb124c0-98e2-11ea-8fd0-5b574ac3b7f7"},"user":{"display_name":"邱佳萱 飛迅","uuid":"8f017bd0-a613-11ea-9b5f-73f333064f13"}},{"kol_status":2,"kol_price":0,"kol_expect_price":null,"is_picked":false,"applied_at":null,"created_at":null,"updated_at":null,"project":{"uuid":"fcb124c0-98e2-11ea-8fd0-5b574ac3b7f7"},"user":{"display_name":"H.J.Z 大鈞🐋🐬🐳","uuid":"fdaa73e0-af12-11ea-979b-1bb209041513"}},{"kol_status":6,"kol_price":0,"kol_expect_price":2000,"is_picked":false,"applied_at":1592473694,"created_at":null,"updated_at":1592473694,"project":{"uuid":"fcb124c0-98e2-11ea-8fd0-5b574ac3b7f7"},"user":{"display_name":"這個雪碧有點甜🎀雪碧兒❄","uuid":"a54e54f0-b147-11ea-bacc-dd5025d63825"}},{"kol_status":2,"kol_price":0,"kol_expect_price":null,"is_picked":false,"applied_at":null,"created_at":null,"updated_at":null,"project":{"uuid":"fcb124c0-98e2-11ea-8fd0-5b574ac3b7f7"},"user":{"display_name":"Y\u20deU\u20deA\u20deN\u20deY\u20deU\u20deA\u20deN\u20de媛媛兒「膠原蛋白/保健食品」","uuid":"03a2ce00-b215-11ea-b404-a90ef32be655"}},{"kol_status":2,"kol_price":0,"kol_expect_price":null,"is_picked":false,"applied_at":null,"created_at":null,"updated_at":null,"project":{"uuid":"fcb124c0-98e2-11ea-8fd0-5b574ac3b7f7"},"user":{"display_name":"🌈 Robby Su 🌈","uuid":"96048770-b503-11ea-bc7d-41dcee87a80b"}},{"kol_status":2,"kol_price":0,"kol_expect_price":null,"is_picked":false,"applied_at":null,"created_at":null,"updated_at":null,"project":{"uuid":"fcb124c0-98e2-11ea-8fd0-5b574ac3b7f7"},"user":{"display_name":"李鎮宇","uuid":"6e2143e0-a0b5-11ea-bb0b-874fe9a5c697"}},{"kol_status":2,"kol_price":0,"kol_expect_price":null,"is_picked":false,"applied_at":null,"created_at":null,"updated_at":null,"project":{"uuid":"fcb124c0-98e2-11ea-8fd0-5b574ac3b7f7"},"user":{"display_name":"跟著77去旅行","uuid":"5a867d20-b517-11ea-baff-f90be534dff0"}},{"kol_status":2,"kol_price":0,"kol_expect_price":null,"is_picked":false,"applied_at":null,"created_at":null,"updated_at":null,"project":{"uuid":"fcb124c0-98e2-11ea-8fd0-5b574ac3b7f7"},"user":{"display_name":"LEILA莉拉|JERÔSSE台灣婕樂纖代理｜","uuid":"ac094bc0-b87b-11ea-ba14-d913321fcdeb"}},{"kol_status":2,"kol_price":0,"kol_expect_price":null,"is_picked":false,"applied_at":null,"created_at":null,"updated_at":null,"project":{"uuid":"fcb124c0-98e2-11ea-8fd0-5b574ac3b7f7"},"user":{"display_name":"Lydia","uuid":"9299c4b0-ba12-11ea-9f82-018206bd7bbb"}}]
     */

    private boolean success;
    private List<KolsBean> kols;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<KolsBean> getKols() {
        return kols;
    }

    public void setKols(List<KolsBean> kols) {
        this.kols = kols;
    }

    public static class KolsBean {
        /**
         * kol_status : 6
         * kol_price : 1500
         * kol_expect_price : 1000
         * is_picked : false
         * applied_at : null
         * created_at : null
         * updated_at : null
         * project : {"uuid":"fcb124c0-98e2-11ea-8fd0-5b574ac3b7f7"}
         * user : {"display_name":"Nick","uuid":"83253050-837b-11ea-a7dc-0d2999c5fd9c"}
         */

        private int kol_status;
        private int kol_price;
        private int kol_expect_price;
        private boolean is_picked;
        private Object applied_at;
        private Object created_at;
        private Object updated_at;
        private ProjectBean project;
        private UserBean user;

        public int getKol_status() {
            return kol_status;
        }

        public void setKol_status(int kol_status) {
            this.kol_status = kol_status;
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

        public boolean isIs_picked() {
            return is_picked;
        }

        public void setIs_picked(boolean is_picked) {
            this.is_picked = is_picked;
        }

        public Object getApplied_at() {
            return applied_at;
        }

        public void setApplied_at(Object applied_at) {
            this.applied_at = applied_at;
        }

        public Object getCreated_at() {
            return created_at;
        }

        public void setCreated_at(Object created_at) {
            this.created_at = created_at;
        }

        public Object getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(Object updated_at) {
            this.updated_at = updated_at;
        }

        public ProjectBean getProject() {
            return project;
        }

        public void setProject(ProjectBean project) {
            this.project = project;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class ProjectBean {
            /**
             * uuid : fcb124c0-98e2-11ea-8fd0-5b574ac3b7f7
             */

            private String uuid;

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }
        }

        public static class UserBean {
            /**
             * display_name : Nick
             * uuid : 83253050-837b-11ea-a7dc-0d2999c5fd9c
             */

            private String display_name;
            private String uuid;

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            private String photo;

            public String getDisplay_name() {
                return display_name;
            }

            public void setDisplay_name(String display_name) {
                this.display_name = display_name;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }
        }
    }
}
