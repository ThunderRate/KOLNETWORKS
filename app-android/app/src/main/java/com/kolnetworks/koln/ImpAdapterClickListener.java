package com.kolnetworks.koln;

public interface ImpAdapterClickListener {
    void onBtnAgreeClick(String project_uuid, String kol_uuid);
    void onBtnDenyClick(String project_uuid, String kol_uuid);
    void onBtnLinkCheckClick(String name,String project_uuid, String kol_uuid);
}
