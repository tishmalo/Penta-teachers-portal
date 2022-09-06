package com.example.pentasteachersportal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pentasteachersportal.Message;
import com.example.pentasteachersportal.Model.UnitsModel;
import com.example.pentasteachersportal.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    Context context;
    List<UnitsModel> userList;

    public ChatAdapter(Context context, List<UnitsModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.show_subjects,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {

        UnitsModel um=userList.get(position);
        holder.CODE.setText(um.getcode());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, Message.class);
                intent.putExtra("CODE",um.getcode());
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
