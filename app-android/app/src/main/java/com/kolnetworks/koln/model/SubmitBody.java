package com.kolnetworks.koln.model;

public class SubmitBody {
    /**
     * project_uuid : 84bc9980-8218-11ea-800f-3fc27977e011
     * report : http://www.google.com.tw
     */

    private String project_uuid;
    private String report;

    public String getProject_uuid() {
        return project_uuid;
    }

    public void setProject_uuid(String project_uuid) {
        this.project_uuid = project_uuid;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }
}
