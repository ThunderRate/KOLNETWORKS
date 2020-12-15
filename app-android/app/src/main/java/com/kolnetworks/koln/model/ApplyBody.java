package com.kolnetworks.koln.model;

public class ApplyBody {

    /**
     * project_uuid : 5e5c2680-84a1-11ea-bb5c-f316aca9e695
     * kol_expect_price : 4
     */

    private String project_uuid;
    private int  kol_expect_price;

    public String getProject_uuid() {
        return project_uuid;
    }

    public void setProject_uuid(String project_uuid) {
        this.project_uuid = project_uuid;
    }

    public int getKol_expect_price() {
        return kol_expect_price;
    }

    public void setKol_expect_price(int kol_expect_price) {
        this.kol_expect_price = kol_expect_price;
    }
}
