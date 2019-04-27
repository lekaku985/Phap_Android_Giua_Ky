package com.example.androidnc.phap_android_giua_ky;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText edt_username, edt_pass;
    Button btn_login, btn_cancle;
    String Username;
    String Pass;
    public static int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Oninit();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onvalue()){
                    final Map<String,String> map = new HashMap<>();
                    map.put("user_name",Username);
                    map.put("password",Pass);

                    new LoginAsyncTask(new interfaceLogin() {
                        @Override
                        public void onLoginsuccess(String m,int iId) {
                            id = iId;
                            Toast.makeText(LoginActivity.this,m,Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, SubjectActivity.class);
                            intent.putExtra("id",iId);
                            startActivity(intent);
                        }

                        @Override
                        public void onLoginfail(String m) {
                            Toast.makeText(LoginActivity.this,m,Toast.LENGTH_SHORT).show();
                        }


                    },map).execute("http://www.vidophp.tk/api/account/signin");
                }
            }
        });
    }
    public void Oninit(){
        edt_username = (EditText)findViewById(R.id.edt_username);
        edt_pass = (EditText)findViewById(R.id.edt_pass);
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_cancle = (Button)findViewById(R.id.btn_cancle);

    }
    private boolean onvalue(){

        Username = edt_username.getText().toString();
        if(Username.length()<1){
            edt_username.setError("Username cannot be null");
            return false;
        }
        Pass = edt_pass.getText().toString();
        if(Pass.length()<1){
            edt_pass.setError("Passworld cannot be null");
            return false;
        }
        return true;
    }
}
