package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class editnoteactivity extends AppCompatActivity {

    Intent data;
    EditText medittitleofnote, meditcontentofnote;
    FloatingActionButton msaveeditnote;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editnoteactivity);

        medittitleofnote = findViewById(R.id.edittitleofnote);
        meditcontentofnote = findViewById(R.id.editcontentofnote);
        msaveeditnote = findViewById(R.id.saveeditnote);
        data= getIntent();

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        Toolbar toolbar = findViewById(R.id.toolbarofeditnote);
        getSupportActionBar().hide();
        // Toolbar üzerine geri butonu eklemek

        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24); // Geri simgesini ekle
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(editnoteactivity.this, notesActivity.class);
                startActivity(intent);
                // Geri butonuna tıklandığında geri dön
                finish();
            }
        });


        msaveeditnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_SHORT).show();

                String newtitle = medittitleofnote.getText().toString();
                String newcontent = meditcontentofnote.getText().toString();

                if(newtitle.isEmpty() || newcontent.isEmpty()){
                    Toast.makeText(getApplicationContext(), "something is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{

                    DocumentReference documentReference = firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").document(data.getStringExtra("noteId"));
                    Map<String, Object> note = new HashMap<>();

                    note.put("title", newtitle);
                    note.put("content", newcontent);
                    documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            //Toast.makeText(getApplicationContext(), "Note is updated", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(editnoteactivity.this, notesActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(), "Note is updated", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed to update note", Toast.LENGTH_SHORT).show();
                        }
                    });

                }


            }
        });


        String notetitle = data.getStringExtra("title");
        String notecontent = data.getStringExtra("content");
        meditcontentofnote.setText(notecontent);
        medittitleofnote.setText(notetitle);




    }
}