package com.example.pentasteachersportal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pentasteachersportal.Adapter.NotesAdapter;
import com.example.pentasteachersportal.Adapter.ShowNotesAdapter;
import com.example.pentasteachersportal.Model.NotesModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Notes extends AppCompatActivity {

   Button nameLabel;
    ImageButton add;
    TextView name;
    EditText pdf;
    de.hdodenhof.circleimageview.CircleImageView patientpicture;

    ProgressDialog dialog;

    RecyclerView recyclerView;

    Uri imageUri=null;

    List<NotesModel> userList;
    NotesAdapter adapter;

    String CODE,NAME;

    Toolbar toolbar;
    String PDF;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        Intent intent=getIntent();
        CODE=intent.getStringExtra("code");

        nameLabel=findViewById(R.id.nameLabel);
        add=findViewById(R.id.add);
        name=findViewById(R.id.pdf);
        pdf=findViewById(R.id.name);
        toolbar=findViewById(R.id.toolBar);
        recyclerView=findViewById(R.id.selectedrecycler);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(CODE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        userList=new ArrayList<>();
        adapter=new NotesAdapter(this, userList);

        dialog=new ProgressDialog(this);

        LinearLayoutManager lm=new LinearLayoutManager(this);
        lm.setStackFromEnd(true);
        lm.setReverseLayout(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);


        AddNotes();
        selectPdf();
        viewNotes();
        swipeToDelete();






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

                DatabaseReference reference=FirebaseDatabase.getInstance().getReference("notes");

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){

                            for (DataSnapshot ds:snapshot.getChildren()){

                                String CODE= ds.child("code").getValue().toString();
                                String TITLE=ds.child("name").getValue().toString();

                                if(CODE.equals(deletedItems)&&TITLE.equals(TITLE)){

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
        }).attachToRecyclerView(recyclerView);

    }

    private void viewNotes() {

        DatabaseReference ref;
        ref=FirebaseDatabase.getInstance().getReference("notes");


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                Query v= ref.orderByChild("code").equalTo(CODE);
                v.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        userList.clear();
                        for (DataSnapshot ds1: snapshot.getChildren()){
                            NotesModel nm= ds1.getValue(NotesModel.class);

                            userList.add(nm);

                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });






            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void selectPdf() {

        nameLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);

                intent.setType("application/pdf");

                startActivityForResult(intent,1);
            }
        });

    }

    private void AddNotes() {

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String TimeStamp= ""+System.currentTimeMillis();
                StorageReference storageReference= FirebaseStorage.getInstance().getReference();
                final String TimeId= TimeStamp;

                final StorageReference file= storageReference.child(TimeId+ "."+"pdf");
                dialog.show();

                file.putFile(imageUri).continueWithTask(new Continuation() {
                    @Override
                    public Object then(@NonNull Task task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return file.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            // After uploading is done it progress
                            // dialog box will be dismissed
                            dialog.dismiss();
                            Uri uri = task.getResult();
                            String myurl;
                            myurl = uri.toString();
                            String codes;
                            codes="";
                            NAME=pdf.getText().toString();


                            DatabaseReference ref;
                            ref= FirebaseDatabase.getInstance().getReference("notes");

                            NotesModel nm = new NotesModel(myurl,CODE,NAME);

                            ref.push().setValue(nm);

                            name.setText("Upload pdf");
                            pdf.setText("");



                            Toast.makeText(getApplicationContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "UploadedFailed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==1 && resultCode==RESULT_OK){




            imageUri=data.getData();

            PDF= imageUri.toString();



            name.setText(PDF);






        }
    }
}