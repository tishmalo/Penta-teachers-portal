package com.example.pentasteachersportal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pentasteachersportal.Model.StudentModel;
import com.example.pentasteachersportal.R;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {
    Context context;
    List<StudentModel> userList;

    public AttendanceAdapter(Context context, List<StudentModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public AttendanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.show_subjects,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceAdapter.ViewHolder holder, int position) {

        StudentModel sm=userList.get(position);
        holder.UserName.setText(sm.getusername());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView UserName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            UserName=itemView.findViewById(R.id.code);

        }
    }
}
