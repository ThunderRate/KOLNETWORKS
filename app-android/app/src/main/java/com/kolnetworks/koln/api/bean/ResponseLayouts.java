package com.kolnetworks.koln.api.bean;

public class ResponseLayouts {
    /**
     * success : true
     * backend : true
     * posts : true
     * users : true
     * roles : true
     * tags : true
     * questionnaire : true
     * banners : true
     * projects : true
     * report : true
     */

    private boolean success;
    private boolean backend;
    private boolean posts;
    private boolean users;
    private boolean roles;
    private boolean tags;
    private boolean questionnaire;
    private boolean banners;
    private boolean projects;
    private boolean report;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isBackend() {
        return backend;
    }

    public void setBackend(boolean backend) {
        this.backend = backend;
    }

    public boolean isPosts() {
        return posts;
    }

    public void setPosts(boolean posts) {
        this.posts = posts;
    }

    public boolean isUsers() {
        return users;
    }

    public void setUsers(boolean users) {
        this.users = users;
    }

    public boolean isRoles() {
        return roles;
    }

    public void setRoles(boolean roles) {
        this.roles = roles;
    }

    public boolean isTags() {
        return tags;
    }

    public void setTags(boolean tags) {
        this.tags = tags;
    }

    public boolean isQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(boolean questionnaire) {
        this.questionnaire = questionnaire;
    }

    public boolean isBanners() {
        return banners;
    }

    public void setBanners(boolean banners) {
        this.banners = banners;
    }

    public boolean isProjects() {
        return projects;
    }

    public void setProjects(boolean projects) {
        this.projects = projects;
    }

    public boolean isReport() {
        return report;
    }

    public void setReport(boolean report) {
        this.report = report;
    }
}
