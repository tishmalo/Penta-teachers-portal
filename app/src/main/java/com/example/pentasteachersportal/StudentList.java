package com.example.pentasteachersportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.pentasteachersportal.Adapter.StudentListAdapter;
import com.example.pentasteachersportal.Model.UnitsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentList extends AppCompatActivity {
    
    String CODE;
    Toolbar tb;
    RecyclerView rv;
    
    List<UnitsModel> userList;
    StudentListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        SharedPreferences preferences=this.getSharedPreferences("CODE",MODE_PRIVATE);
        CODE=preferences.getString("CODE","");
        
        tb=findViewById(R.id.toolBar);
        rv=findViewById(R.id.selectedrecycler);
        
        userList=new ArrayList<>();
        adapter=new StudentListAdapter(this, userList);
        
        setSupportActionBar(tb);
        getSupportActionBar().setTitle(CODE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        LinearLayoutManager lm=new LinearLayoutManager(this);
        lm.setStackFromEnd(true);
        lm.setReverseLayout(true);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);
        
        populateData();
        
        
        
    }

    private void populateData() {

        DatabaseReference ref;
        ref= FirebaseDatabase.getInstance().getReference("Subjects");
        Query v=ref.orderByChild("code").equalTo(CODE);
        v.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                userList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    UnitsModel um=ds.getValue(UnitsModel.class);
                    userList.add(um);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}