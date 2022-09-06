package com.example.pentasteachersportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.pentasteachersportal.Adapter.MessageAdapter;
import com.example.pentasteachersportal.Model.MessageModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Message extends AppCompatActivity {


    RecyclerView rv;
    Toolbar toolbar;
    EditText message;
    ImageButton send;
    String CODE;

    List<MessageModel> userList;
    MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        SharedPreferences sh=Message.this.getSharedPreferences("code",MODE_PRIVATE);
        CODE=sh.getString("code","");


        toolbar=findViewById(R.id.toolBar);
        rv=findViewById(R.id.recyclerview);
        message=findViewById(R.id.text_send);
        send=findViewById(R.id.btn_send);



        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(CODE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        userList=new ArrayList<>();
        adapter=new MessageAdapter(this, userList);


        LinearLayoutManager lm=new LinearLayoutManager(this);
        lm.setReverseLayout(true);
        lm.setStackFromEnd(true);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);

        sendMessage();
        populateData();




    }

    private void sendMessage() {

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                final String SENDER=FirebaseAuth.getInstance().getCurrentUser().getUid();
                final String RECEIVER=CODE;
                final String MESSAGE=message.getText().toString();
                final String EMAIL=FirebaseAuth.getInstance().getCurrentUser().getEmail();


                if(TextUtils.isEmpty(MESSAGE)){
                    message.setText("Type Message");
                }else{
                    DatabaseReference ref;
                    ref= FirebaseDatabase.getInstance().getReference("Chats");
                    MessageModel um=new MessageModel(SENDER,RECEIVER,MESSAGE,EMAIL);
                    ref.push().setValue(um);
                    Log.d("dead","Tish");
                    message.setText("");
                }
            }
        });




    }

    private void populateData() {

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    Query v=reference.orderByChild("receiver").equalTo(CODE);
                    v.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            userList.clear();
                            for (DataSnapshot ds1: snapshot.getChildren()){

                                MessageModel cm= ds1.getValue(MessageModel.class);

                                userList.add(cm);

                            }

                            adapter.notifyDataSetChanged();





                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}