package com.example.pentasteachersportal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pentasteachersportal.Model.NotesModel;
import com.example.pentasteachersportal.Model.UnitsModel;
import com.example.pentasteachersportal.Notes;
import com.example.pentasteachersportal.R;

import java.util.List;

public class ShowNotesAdapter extends RecyclerView.Adapter<ShowNotesAdapter.ViewHolder> {

    Context context;
    List<NotesModel> userList;

    public ShowNotesAdapter(Context context, List<NotesModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ShowNotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.show_subjects,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowNotesAdapter.ViewHolder holder, int position) {

        NotesModel u=userList.get(position);
        holder.Code.setText(u.getcode());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context, Notes.class);
                intent.putExtra("code",u.getcode());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView Code;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Code=itemView.findViewById(R.id.code);

        }
    }
}
