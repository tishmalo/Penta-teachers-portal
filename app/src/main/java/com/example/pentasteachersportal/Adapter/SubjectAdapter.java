package com.example.pentasteachersportal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pentasteachersportal.Model.UnitsModel;
import com.example.pentasteachersportal.R;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {

    Context context;
    List<UnitsModel> userList;

    public SubjectAdapter(Context context, List<UnitsModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public SubjectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.show_subjects,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectAdapter.ViewHolder holder, int position) {

        UnitsModel mm=userList.get(position);

        holder.Code.setText(mm.getcode());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Code;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Code=itemView.findViewById(R.id.code);
        }
    }
}
