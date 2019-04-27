package com.example.androidnc.phap_android_giua_ky;

import org.json.JSONArray;

public interface IView {
    void onGetDataSuccess(JSONArray jsonArray);
    void onSuccess(String message);
    void onFail(String message);

}
