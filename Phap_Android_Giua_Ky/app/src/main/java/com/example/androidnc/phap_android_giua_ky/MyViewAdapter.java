package com.example.androidnc.phap_android_giua_ky;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyViewAdapter extends RecyclerView.Adapter<MyViewAdapter.ViewHolder> {
    List<SubjectModel> models;
    int mResource;
    Context mContext;

    public MyViewAdapter(Context mContext, int mResource,List<SubjectModel> models) {
        this.models = models;
        this.mResource = mResource;
        this.mContext = mContext;
    }


    @Override
    public MyViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(mResource,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewAdapter.ViewHolder holder, final int position) {
        SubjectModel model= models.get(position);
        holder.edtid.setText(String.valueOf(model.getId()));
        holder.edtcode.setText(model.getCode());
        holder.edtname.setText(model.getName());
        holder.edtcredits.setText(String.valueOf(model.getCredits()));
        holder.edtdescription.setText(model.getDescription());
        holder.buttondelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Id;

                Id = holder.edtid.getText().toString();
                final Map<String,String> map = new HashMap<>();

                map.put("id",String.valueOf(Id));
                map.put("user_id",String.valueOf(LoginActivity.id));
                new EditAsyncTask(new IView() {
                    @Override
                    public void onGetDataSuccess(JSONArray jsonArray) {
                    }

                    @Override
                    public void onSuccess(String message) {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                        SubjectActivity.models.remove(position);
                        SubjectActivity.adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFail(String message) {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    }
                },map).execute("http://www.vidophp.tk/api/account/dataaction?context=delete");
            }


        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private EditText edtid;
        private EditText edtcode;
        private EditText edtname;
        private EditText edtcredits;
        private EditText edtdescription;
        Button buttondelete;
        Button buttonedit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.buttondelete = itemView.findViewById(R.id.remove_btn);
            this.buttonedit = itemView.findViewById(R.id.edit_btn);
            this.edtid = itemView.findViewById(R.id.Subject_edt_id);
            this.edtcode = itemView.findViewById(R.id.Subject_edt_code);
            this.edtname = itemView.findViewById(R.id.Subject_edt_name);
            this.edtcredits = itemView.findViewById(R.id.Subject_edt_credits);
            this.edtdescription = itemView.findViewById(R.id.Subject_edt_description);
        }
    }
}
