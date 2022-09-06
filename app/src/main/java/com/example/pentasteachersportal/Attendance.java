package com.example.pentasteachersportal;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.pentasteachersportal.Adapter.AttendanceAdapter;
import com.example.pentasteachersportal.Model.AttendanceModel;
import com.example.pentasteachersportal.Model.StudentModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

public class Attendance extends AppCompatActivity {

    Toolbar tb;
    RecyclerView v;
    List<StudentModel> userList;
    AttendanceAdapter adapter;
    Spinner spinner;
    String YEAR;
    String Username;
    ImageButton button;

    String Dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);


        tb=findViewById(R.id.toolBar);
        v=findViewById(R.id.selectedrecycler);
        spinner=findViewById(R.id.year);
        button=findViewById(R.id.add);

        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        userList=new ArrayList<>();
        adapter=new AttendanceAdapter(this, userList);

        LinearLayoutManager lm=new LinearLayoutManager(this);
        lm.setStackFromEnd(true);
        lm.setReverseLayout(true);
        v.setLayoutManager(lm);
        v.setHasFixedSize(true);
        v.setAdapter(adapter);


        populateData();

        SwipeToAdd();






    }

    private void SwipeToAdd() {

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                String SelectedItems=userList.get(viewHolder.getAdapterPosition()).getusername();

                DatabaseReference ref;
                ref=FirebaseDatabase.getInstance().getReference("Attendance");

                DatabaseReference reference;
                reference= FirebaseDatabase.getInstance().getReference("Classes").child(YEAR);

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot sn:snapshot.getChildren()){
                          Username =sn.child("username").getValue().toString();

                          if(Username.equals(SelectedItems)){
                              AttendanceModel am=new AttendanceModel(YEAR,Dates,Username);

                              ref.push().setValue(am);
                          }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });





            }
        }).attachToRecyclerView(v);


    }

    private void populateData() {

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {


                YEAR =spinner.getSelectedItem().toString();


                DateTimeFormatter ff= null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    ff = DateTimeFormatter.ofPattern("uuuu/MM/dd");
                    LocalDate localDate=LocalDate.now();

                    Dates=ff.format(localDate);
                }



                if(YEAR.equals("CLASS")){

                }else{

                    DatabaseReference reference;
                    reference= FirebaseDatabase.getInstance().getReference("Classes").child(YEAR);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            userList.clear();
                            for (DataSnapshot ds: snapshot.getChildren()){
                                StudentModel sm=ds.getValue(StudentModel.class);


                                userList.add(sm);
                            }
                            adapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }


            }
        });







    }
}