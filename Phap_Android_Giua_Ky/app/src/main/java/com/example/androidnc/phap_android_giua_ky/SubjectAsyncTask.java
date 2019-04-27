package com.example.androidnc.phap_android_giua_ky;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

public class SubjectAsyncTask  extends AsyncTask<String,Void, JSONObject> {
    private IView mView;
    private Map<String, String> map;


    public SubjectAsyncTask(IView iView, Map<String, String> map) {
        this.mView = iView;
        this.map = map;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestMethod("POST");
            // ----- add request params//
            JSONObject jsonObject = new JSONObject();
            Iterator iter = map.keySet().iterator();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                String value = map.get(key);
                jsonObject.put(key, value);
            }
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(String.valueOf(jsonObject));
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            //            //------------//
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();
            String result;
            while ((result = bufferedReader.readLine()) != null){
                stringBuffer.append(result);
            }
            result  = stringBuffer.toString();
            JSONObject parentObject = new JSONObject(result);
            return parentObject;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        try {
            int mResult = jsonObject.getInt("result");
            if (mResult > 0){
                JSONArray jsonArray = jsonObject.getJSONArray("response_data");
                mView.onGetDataSuccess(jsonArray);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
