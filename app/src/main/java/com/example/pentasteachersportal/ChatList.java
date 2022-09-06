package com.example.pentasteachersportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.pentasteachersportal.Adapter.ChatAdapter;
import com.example.pentasteachersportal.Model.UnitsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatList extends AppCompatActivity {

    Toolbar tb;
    RecyclerView rv;

    List<UnitsModel> userList;
    ChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        tb=findViewById(R.id.toolBar);
        rv=findViewById(R.id.selectedrecycler);

        setSupportActionBar(tb);
        getSupportActionBar().setTitle("UNITS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userList=new ArrayList<>();
        adapter=new ChatAdapter(this, userList);

        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        LinearLayoutManager lm=new LinearLayoutManager(this);
        lm.setReverseLayout(true);
        lm.setStackFromEnd(true);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);

        populateData();



    }

    private void populateData() {

        DatabaseReference ref;
        ref= FirebaseDatabase.getInstance().getReference("Subjects");
        Query f=ref.orderByChild("userid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());

        f.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                userList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){

                    UnitsModel m=ds.getValue(UnitsModel.class);
                    userList.add(m);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}