package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotpassword extends AppCompatActivity {

    private EditText mforgotpassword;
    private Button mpasswordrecover;
    private TextView mgobacktologin;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgotpassword);

        //getSupportActionBar().hide();

        mforgotpassword = findViewById(R.id.forgotpassword);
        mpasswordrecover = findViewById(R.id.passwordrecover);
        mgobacktologin = findViewById(R.id.gobacktologin);

        firebaseAuth = FirebaseAuth.getInstance();

        mgobacktologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(forgotpassword.this , MainActivity.class);
                startActivity(intent);
            }
        });

        mpasswordrecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = mforgotpassword.getText().toString().trim();

                if(mail.isEmpty()){
                    Toast.makeText(getApplicationContext() , "Please enter your mail address" , Toast.LENGTH_SHORT).show();
                }
                else{
                    //we will send code
                    firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Password reset email is sent", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(forgotpassword.this , MainActivity.class));
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Please enter correct email address", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                }
            }
        });

    }
}