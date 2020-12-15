package com.kolnetworks.koln.model;

public class LinkCheckBody {

    /**
     * project_uuid : project_uuid
     * user_uuid : user_uuid
     * likes : 0
     * comments : 0
     */

    private String project_uuid;
    private String user_uuid;
    private int likes;
    private int comments;

    public String getProject_uuid() {
        return project_uuid;
    }

    public void setProject_uuid(String project_uuid) {
        this.project_uuid = project_uuid;
    }

    public String getUser_uuid() {
        return user_uuid;
    }

    public void setUser_uuid(String user_uuid) {
        this.user_uuid = user_uuid;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }
}
