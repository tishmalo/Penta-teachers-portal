package com.example.pentasteachersportal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pentasteachersportal.Model.PostsModel;
import com.example.pentasteachersportal.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    Context context;
    List<PostsModel> userList;

    public PostsAdapter(Context context, List<PostsModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.show_posts,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.ViewHolder holder, int position) {

        PostsModel pm=userList.get(position);
        holder.title.setText(pm.gettitle());
        holder.description.setText(pm.getdescription());

        Glide.with(context).load(pm.getphoto()).into(holder.photo);


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title,description;
        CircleImageView photo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.description);

            photo=itemView.findViewById(R.id.photo);

        }
    }

}
