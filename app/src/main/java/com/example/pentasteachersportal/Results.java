package com.example.pentasteachersportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pentasteachersportal.Model.ResultsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Results extends AppCompatActivity {

    String CODE,EMAIL;
    Toolbar tb;
    EditText opening;
    Button submit;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        SharedPreferences pref=this.getSharedPreferences("CODE",MODE_PRIVATE);
        CODE=pref.getString("CODE","");

        Intent intent=getIntent();
        EMAIL=intent.getStringExtra("EMAIL");


        tb=findViewById(R.id.toolBar);
        opening=findViewById(R.id.opening);

        submit=findViewById(R.id.submit);
        title=findViewById(R.id.courseCode);

        title.setText(CODE);

        setSupportActionBar(tb);
        getSupportActionBar().setTitle(CODE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        opening.setText("0");



        addResults();
        viewResults();


    }

    private void viewResults() {

        DatabaseReference ref;
        ref=FirebaseDatabase.getInstance().getReference("Results").child(EMAIL).child(CODE);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    for (DataSnapshot ds : snapshot.getChildren()) {

                        final String OPENING = ds.child("opening").getValue().toString();


                        opening.setText(OPENING);



                    }
                }else{
                    opening.setText("0");

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void addResults() {

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String OPENING=opening.getText().toString();




                DatabaseReference ref;
                ref= FirebaseDatabase.getInstance().getReference("Results");

                ResultsModel rm=new ResultsModel(OPENING,EMAIL,CODE);

                ref.child(EMAIL).child(CODE).setValue(rm);

                Intent intent=new Intent(Results.this, StudentList.class);
                startActivity(intent);


            }
        });

    }
}