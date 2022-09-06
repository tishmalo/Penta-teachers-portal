package com.example.pentasteachersportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    EditText email, password, cpassword;
    Button btn;
    TextView already;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        cpassword=findViewById(R.id.cpassword);
        btn=findViewById(R.id.therapist);
        already=findViewById(R.id.already);

        mAuth=FirebaseAuth.getInstance();

        RegisterUser();
        gotoLogin();


    }

    private void gotoLogin() {

        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });

    }

    private void RegisterUser() {

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String EMAIL=email.getText().toString();
                final String PASSWORD=password.getText().toString();
                final String CPASSWORD=cpassword.getText().toString();

                if (!Patterns.EMAIL_ADDRESS.matcher(EMAIL).matches()){
                    email.setError("Enter Valid Email");
                }if (TextUtils.isEmpty(PASSWORD)){
                    password.setError("Enter Password");
                }if (!PASSWORD.equals(CPASSWORD)){
                    cpassword.setError("Passwords do not match");
                }if (PASSWORD.length()<6){
                    password.setError("Password should be longer");
                }else{

                   mAuth.createUserWithEmailAndPassword(EMAIL,PASSWORD).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (!task.isSuccessful()){
                               Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                           }else{
                               DatabaseReference reference;
                               reference= FirebaseDatabase.getInstance().getReference("Teachers");
                               HashMap map=new HashMap();
                               map.put("Email", EMAIL);
                               map.put("UserId",FirebaseAuth.getInstance().getCurrentUser().getUid());

                               reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
                                   @Override
                                   public void onComplete(@NonNull Task task) {

                                       if (task.isSuccessful()){
                                           Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                       }

                                   }
                               });

                               Intent intent=new Intent(Register.this, MainActivity.class);
                               startActivity(intent);

                               finish();

                           }

                       }
                   }) ;

                }




            }
        });

    }
}