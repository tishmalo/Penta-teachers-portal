package com.example.pentasteachersportal.Adapter;

import android.content.Context;
import android.os.Messenger;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pentasteachersportal.Model.MessageModel;
import com.example.pentasteachersportal.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    Context context;
    List<MessageModel> userList;

    final static int CHAT_SENDER=0;
    final static int CHAT_RECEIVER=1;

    public MessageAdapter(Context context, List<MessageModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType==CHAT_SENDER){
            View v= LayoutInflater.from(context).inflate(R.layout.chat_item_right,parent,false);
            return new ViewHolder(v);
        }else{
            View t=LayoutInflater.from(context).inflate(R.layout.chat_item_left,parent,false);
            return new ViewHolder(t);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {

        MessageModel mm=userList.get(position);

        holder.Messanger.setText(mm.getmessage());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Messanger;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Messanger=itemView.findViewById(R.id.show_message);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(userList.get(position).getsender())){
            return CHAT_SENDER;
        }else{
            return CHAT_RECEIVER;
        }
    }
}
