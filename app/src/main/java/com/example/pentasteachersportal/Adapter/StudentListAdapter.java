package com.example.pentasteachersportal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pentasteachersportal.Model.UnitsModel;
import com.example.pentasteachersportal.R;
import com.example.pentasteachersportal.Results;
import com.example.pentasteachersportal.StudentList;

import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.ViewHolder> {
    Context context;
    List<UnitsModel> userList;

    public StudentListAdapter(Context context, List<UnitsModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public StudentListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.show_subjects,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentListAdapter.ViewHolder holder, int position) {

        UnitsModel u=userList.get(position);

        holder.EMAIL.setText(u.getusername());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent intent= new Intent(context, Results.class);
                intent.putExtra("EMAIL",u.getusername());
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView EMAIL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            EMAIL=itemView.findViewById(R.id.code);

        }
    }
}
