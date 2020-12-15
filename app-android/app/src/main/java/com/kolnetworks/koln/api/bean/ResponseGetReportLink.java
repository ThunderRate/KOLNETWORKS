package com.kolnetworks.koln.api.bean;

import java.util.List;

public class ResponseGetReportLink {
    /**
     * success : true
     * reports : [{"report":"https://www.google.com.tw","created_at":1594221951,"user":{"uuid":"feb53dd0-afba-11ea-8f88-d706349c7bc4"}}]
     */

    private boolean success;
    private List<ReportsBean> reports;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ReportsBean> getReports() {
        return reports;
    }

    public void setReports(List<ReportsBean> reports) {
        this.reports = reports;
    }

    public static class ReportsBean {
        /**
         * report : https://www.google.com.tw
         * created_at : 1594221951
         * user : {"uuid":"feb53dd0-afba-11ea-8f88-d706349c7bc4"}
         */

        private String report;
        private int created_at;
        private UserBean user;

        public String getReport() {
            return report;
        }

        public void setReport(String report) {
            this.report = report;
        }

        public int getCreated_at() {
            return created_at;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * uuid : feb53dd0-afba-11ea-8f88-d706349c7bc4
             */

            private String uuid;

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }
        }
    }
}
