package com.kolnetworks.koln.api.bean;

import java.util.List;

public class ResponseCashFlow {


    /**
     * success : true
     * commission_rate : 0
     * cashflow : [{"uuid":"e7bde920-c1ac-11ea-aa9d-35540cff5f6a","basic_income":153,"basic_income_commission":0,"like_bonus_income":0,"like_bonus_income_commission":0,"created_at":1594275895,"project":{"uuid":"9f0cf8c0-967c-11ea-908a-e3f610123c73","name":"【專案報名截止】八方雲集創意料理競賽"},"user":{"uuid":"a89bf940-83a8-11ea-bf6d-97e167e59e08","display_name":"Pin"},"order":{"uuid":"0abb0380-c1ae-11ea-be20-fbcc8d3350d5","status":2}},{"uuid":"526c8fb0-d86e-11ea-9fb5-c9692e8db521","basic_income":153,"basic_income_commission":0,"like_bonus_income":0,"like_bonus_income_commission":0,"created_at":1596777892,"project":{"uuid":"d648dbd0-9684-11ea-b509-45a2d7e5c0be","name":"【專案報名截止】全家便利商店 - 友善食光"},"user":{"uuid":"a89bf940-83a8-11ea-bf6d-97e167e59e08","display_name":"Pin"},"order":{"uuid":"0abb0380-c1ae-11ea-be20-fbcc8d3350d5","status":2}},{"uuid":"74ba25a0-d86e-11ea-856a-db0c96b45831","basic_income":153,"basic_income_commission":0,"like_bonus_income":0,"like_bonus_income_commission":0,"created_at":1596777950,"project":{"uuid":"0cb9a050-9685-11ea-b509-45a2d7e5c0be","name":"【專案報名截止】全家便利商店 - 極鬆餅"},"user":{"uuid":"a89bf940-83a8-11ea-bf6d-97e167e59e08","display_name":"Pin"},"order":{"uuid":"0abb0380-c1ae-11ea-be20-fbcc8d3350d5","status":2}},{"uuid":"d1b7ec90-d870-11ea-bc65-d3cbcfc5a898","basic_income":153,"basic_income_commission":0,"like_bonus_income":0,"like_bonus_income_commission":0,"created_at":1596778965,"project":{"uuid":"f1b8e880-9687-11ea-b509-45a2d7e5c0be","name":"【專案報名截止】全家便利商店 - 自由配"},"user":{"uuid":"a89bf940-83a8-11ea-bf6d-97e167e59e08","display_name":"Pin"},"order":{"uuid":"0abb0380-c1ae-11ea-be20-fbcc8d3350d5","status":2}},{"uuid":"66c2f120-dd2d-11ea-a6e1-759a8acf7cf1","basic_income":153,"basic_income_commission":0,"like_bonus_income":0,"like_bonus_income_commission":0,"created_at":1597299765,"project":{"uuid":"301f2400-9687-11ea-b509-45a2d7e5c0be","name":"【專案報名截止】全家便利商店 - 私品茶"},"user":{"uuid":"a89bf940-83a8-11ea-bf6d-97e167e59e08","display_name":"Pin"},"order":{"uuid":"70b64a30-db9b-11ea-8dee-298f980cff09","status":2}},{"uuid":"a69c9d50-dd2d-11ea-bc92-d5651d03b9fd","basic_income":100,"basic_income_commission":0,"like_bonus_income":0,"like_bonus_income_commission":0,"created_at":1597299872,"project":{"uuid":"25ef1620-af7d-11ea-8f88-d706349c7bc4","name":"【報名截止】全家 - 友善食光，吃掉我～不要丟掉我～～"},"user":{"uuid":"a89bf940-83a8-11ea-bf6d-97e167e59e08","display_name":"Pin"},"order":{"uuid":"70b64a30-db9b-11ea-8dee-298f980cff09","status":2}}]
     */

    private boolean success;
    private float commission_rate;
    private List<CashflowBean> cashflow;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public float getCommission_rate() {
        return commission_rate;
    }

    public void setCommission_rate(float commission_rate) {
        this.commission_rate = commission_rate;
    }

    public List<CashflowBean> getCashflow() {
        return cashflow;
    }

    public void setCashflow(List<CashflowBean> cashflow) {
        this.cashflow = cashflow;
    }

    public static class CashflowBean {
        /**
         * uuid : e7bde920-c1ac-11ea-aa9d-35540cff5f6a
         * basic_income : 153
         * basic_income_commission : 0
         * like_bonus_income : 0
         * like_bonus_income_commission : 0
         * created_at : 1594275895
         * project : {"uuid":"9f0cf8c0-967c-11ea-908a-e3f610123c73","name":"【專案報名截止】八方雲集創意料理競賽"}
         * user : {"uuid":"a89bf940-83a8-11ea-bf6d-97e167e59e08","display_name":"Pin"}
         * order : {"uuid":"0abb0380-c1ae-11ea-be20-fbcc8d3350d5","status":2}
         */

        private String uuid;
        private int basic_income;
        private int basic_income_commission;
        private int like_bonus_income;
        private int like_bonus_income_commission;
        private int created_at;
        private ProjectBean project;
        private UserBean user;
        private OrderBean order;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public int getBasic_income() {
            return basic_income;
        }

        public void setBasic_income(int basic_income) {
            this.basic_income = basic_income;
        }

        public int getBasic_income_commission() {
            return basic_income_commission;
        }

        public void setBasic_income_commission(int basic_income_commission) {
            this.basic_income_commission = basic_income_commission;
        }

        public int getLike_bonus_income() {
            return like_bonus_income;
        }

        public void setLike_bonus_income(int like_bonus_income) {
            this.like_bonus_income = like_bonus_income;
        }

        public int getLike_bonus_income_commission() {
            return like_bonus_income_commission;
        }

        public void setLike_bonus_income_commission(int like_bonus_income_commission) {
            this.like_bonus_income_commission = like_bonus_income_commission;
        }

        public int getCreated_at() {
            return created_at;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
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

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public static class ProjectBean {
            /**
             * uuid : 9f0cf8c0-967c-11ea-908a-e3f610123c73
             * name : 【專案報名截止】八方雲集創意料理競賽
             */

            private String uuid;
            private String name;

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class UserBean {
            /**
             * uuid : a89bf940-83a8-11ea-bf6d-97e167e59e08
             * display_name : Pin
             */

            private String uuid;
            private String display_name;

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public String getDisplay_name() {
                return display_name;
            }

            public void setDisplay_name(String display_name) {
                this.display_name = display_name;
            }
        }

        public static class OrderBean {
            /**
             * uuid : 0abb0380-c1ae-11ea-be20-fbcc8d3350d5
             * status : 2
             */

            private String uuid;
            private int status;

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
