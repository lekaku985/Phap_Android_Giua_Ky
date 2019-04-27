package com.example.androidnc.phap_android_giua_ky;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubjectActivity extends AppCompatActivity {
   public static List<SubjectModel> models;
    RecyclerView recyclerView;
    int userid;
    Button btn_add,btn_edit,btn_remove;
    public  static  MyViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        models = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            userid = getIntent().getExtras().getInt("id");
        }
        recyclerView = findViewById(R.id.Subject_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        try {

            final Map<String,String> map = new HashMap<>();
            map.put("id", String.valueOf(userid));
            new SubjectAsyncTask(new IView() {
                @Override
                public void onGetDataSuccess(JSONArray jsonArray) {
                    for (int i = 0; i<jsonArray.length(); i++){
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            SubjectModel model = new SubjectModel();
                            model.setId(Integer.valueOf(jsonObject.getString("id")));
                            model.setName(jsonObject.getString("subject_name"));
                            model.setCode(jsonObject.getString("subject_code"));
                            model.setCredits(Integer.valueOf(jsonObject.getString("credits")));
                            model.setDescription(jsonObject.getString("description"));
                            models.add(model);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                     adapter = new MyViewAdapter(SubjectActivity.this,R.layout.subject_item,models);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onSuccess(String message) {

                }

                @Override
                public void onFail(String message) {

                }
            },map).execute("http://www.vidophp.tk/api/account/getdata");

        }
         catch (NumberFormatException e) {
            e.printStackTrace();
        }
        oninit();

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_database, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menuAdd){
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }
    public void oninit(){
        btn_edit = (Button)findViewById(R.id.edit_btn);
        btn_remove = (Button)findViewById(R.id.remove_btn);
    }
    private void DialogThem(){
        final Dialog dialog = new Dialog(this);
        //bo o title trong dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_layout);
        final EditText name = (EditText) dialog.findViewById(R.id.edt_name);
        final EditText code = (EditText) dialog.findViewById(R.id.edt_code);
        final EditText credits = (EditText) dialog.findViewById(R.id.edt_credits);
        final EditText descreption = (EditText) dialog.findViewById(R.id.edt_descreption);
        EditText id = (EditText) dialog.findViewById(R.id.edt_id);
        id.setText(String.valueOf(userid));

        Button btnadd = (Button) dialog.findViewById(R.id.btn_add);
        Button btnno = (Button) dialog.findViewById(R.id.btn_no);
        btnadd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String Name,Code,Descreption;
                String Id,Credits;
                Name = name.getText().toString();
                Code = code.getText().toString();
                Credits = credits.getText().toString();
                Descreption = descreption.getText().toString();
                if(Name.equals("")){
                    Toast.makeText(SubjectActivity.this,"Vui Long Nhap Du Lieu", Toast.LENGTH_SHORT).show();
                }else{

                    final Map<String,String> map = new HashMap<>();
                    map.put("user_id",String.valueOf(userid));
                    map.put("name", Name);
                    map.put("number", Credits);
                    map.put("code", Code);
                    map.put("description", Descreption);
                    new DataAsyncTask(new IView() {
                        @Override
                        public void onGetDataSuccess(JSONArray jsonArray) {
//                            for (int i = 0; i<jsonArray.length(); i++){
//                                try {
//                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                    SubjectModel model = new SubjectModel();
//                                    model.setId(Integer.valueOf(jsonObject.getString("id")));
//                                    model.setName(jsonObject.getString("subject_name"));
//                                    model.setCode(jsonObject.getString("subject_code"));
//                                    model.setCredits(Integer.valueOf(jsonObject.getString("credits")));
//                                    model.setDescription(jsonObject.getString("description"));
//                                    models.add(model);
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                            MyViewAdapter adapter = new MyViewAdapter(SubjectActivity.this,R.layout.subject_item,models);
//                            recyclerView.setAdapter(adapter);
                        }

                        @Override
                        public void onSuccess(String message) {
                            Toast.makeText(SubjectActivity.this, message, Toast.LENGTH_SHORT).show();
                            final Map<String,String> map = new HashMap<>();
                            map.put("id", String.valueOf(userid));
                            final List<SubjectModel> subModel = new ArrayList<>();
                            new SubjectAsyncTask(new IView() {
                                @Override
                                public void onGetDataSuccess(JSONArray jsonArray) {
                                    for (int i = 0; i<jsonArray.length(); i++){
                                        try {
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                                            SubjectModel model = new SubjectModel();
                                            model.setId(Integer.valueOf(jsonObject.getString("id")));
                                            model.setName(jsonObject.getString("subject_name"));
                                            model.setCode(jsonObject.getString("subject_code"));
                                            model.setCredits(Integer.valueOf(jsonObject.getString("credits")));
                                            model.setDescription(jsonObject.getString("description"));
                                            subModel.add(model);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    models.clear();
                                    models.addAll(subModel);
                                    adapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onSuccess(String message) {

                                }

                                @Override
                                public void onFail(String message) {

                                }
                            },map).execute("http://www.vidophp.tk/api/account/getdata");
                        }

                        @Override
                        public void onFail(String message) {
                            Toast.makeText(SubjectActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    },map).execute("http://www.vidophp.tk/api/account/dataaction?context=insert");
                }
            }
        });
        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    }

