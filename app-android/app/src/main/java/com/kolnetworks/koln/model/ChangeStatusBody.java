package com.kolnetworks.koln.model;

public class ChangeStatusBody {
    /**
     * project_uuid : project_uuid
     * project_status : 3
     */

    private String project_uuid;
    private int project_status;

    public String getProject_uuid() {
        return project_uuid;
    }

    public void setProject_uuid(String project_uuid) {
        this.project_uuid = project_uuid;
    }

    public int getProject_status() {
        return project_status;
    }

    public void setProject_status(int project_status) {
        this.project_status = project_status;
    }
}
