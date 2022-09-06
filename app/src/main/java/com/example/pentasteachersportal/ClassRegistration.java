package com.example.pentasteachersportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pentasteachersportal.Adapter.SubjectAdapter;
import com.example.pentasteachersportal.Model.UnitsModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClassRegistration extends AppCompatActivity {
    
    Toolbar tb;
    RecyclerView rv;
    TextView name;
    Spinner year, subject;
    ImageButton add;
    FloatingActionButton proceed;

    List<UnitsModel> userList;
    SubjectAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_registration);
        
        tb=findViewById(R.id.toolBar);
        rv=findViewById(R.id.selectedrecycler);
        name=findViewById(R.id.LecName);
        year=findViewById(R.id.year);
        subject=findViewById(R.id.courses);
        add=findViewById(R.id.add);
        proceed=findViewById(R.id.proceed);

        userList=new ArrayList<>();
        adapter=new SubjectAdapter(this, userList);

        swipeToDelete();

        name.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        
        setSupportActionBar(tb);
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
        
        addCourses();
        viewCourses();

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ClassRegistration.this, MainActivity.class);
                startActivity(intent);
            }
        });
        
        
        
    }

    private void swipeToDelete() {

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {


                String deletedItems=userList.get(viewHolder.getAdapterPosition()).getcode();

                DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Subjects");

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){

                            for (DataSnapshot ds:snapshot.getChildren()){

                                String CODE= ds.child("code").getValue().toString();
                                String USER=ds.child("userid").getValue().toString();

                                if(CODE.equals(deletedItems)&&USER.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){

                                    userList.remove(viewHolder.getAdapterPosition());
                                    adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                                    ds.getRef().removeValue();

                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        }).attachToRecyclerView(rv);

    }

    private void viewCourses() {

        DatabaseReference ref;
        ref=FirebaseDatabase.getInstance().getReference("Subjects");
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

    private void addCourses() {
        
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String YEAR=year.getSelectedItem().toString();
                final String SUBJECT=subject.getSelectedItem().toString();
                
                if (YEAR.equals("CLASS YEAR")){
                    Toast.makeText(ClassRegistration.this, "Enter Class", Toast.LENGTH_SHORT).show();
                }if (SUBJECT.equals("SUBJECT")){
                    Toast.makeText(ClassRegistration.this, "Enter SUBJECT", Toast.LENGTH_SHORT).show();
                }else{

                    DatabaseReference ref;
                    ref= FirebaseDatabase.getInstance().getReference("Subjects");

                    final String CODE=YEAR + " " + SUBJECT;
                    final String USERID= FirebaseAuth.getInstance().getCurrentUser().getUid();
                    final String EMAIL=FirebaseAuth.getInstance().getCurrentUser().getEmail();

                    UnitsModel uu=new UnitsModel(CODE,USERID,EMAIL);
                    
                    ref.push().setValue(uu);
                    
                    
                }
                
            }
        });
        
    }
}