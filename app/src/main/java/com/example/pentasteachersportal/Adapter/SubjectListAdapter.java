package com.example.pentasteachersportal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pentasteachersportal.Model.UnitsModel;
import com.example.pentasteachersportal.R;
import com.example.pentasteachersportal.StudentList;

import java.util.List;

public class SubjectListAdapter extends RecyclerView.Adapter<SubjectListAdapter.ViewHolder> {

    Context context;
    List<UnitsModel> userList;

    public SubjectListAdapter(Context context, List<UnitsModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public SubjectListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.show_subjects,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectListAdapter.ViewHolder holder, int position) {

        UnitsModel m=userList.get(position);

        holder.CODE.setText(m.getcode());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sh=context.getSharedPreferences("CODE",Context.MODE_PRIVATE);
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("CODE",m.getcode());
                ed.apply();

                Intent intent=new Intent(context, StudentList.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView CODE;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            CODE=itemView.findViewById(R.id.code);

        }
    }
}
