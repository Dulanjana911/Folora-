package com.dsmini.folorauserapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RememberPassActivity extends AppCompatActivity {

    private EditText rest;
    private TextView btn,log;
    private ProgressBar progressBar;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remember_pass);
        rest = findViewById(R.id.restemail);
        btn =findViewById(R.id.restbtn);
        progressBar = findViewById(R.id.restpb);
        auth = FirebaseAuth.getInstance();
        log = findViewById(R.id.logreset);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RememberPassActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetpassword();
            }
        });
    }

    private void resetpassword() {
        String e = rest.getText().toString().trim();

        if(e.isEmpty())
        {
            rest.setError("කරුණාකර ඔබේ විද්\u200Dයුත් ලිපිනය ඇතුලත් කරන්න!");
            rest.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(e).matches())
        {
            rest.setError("කරුණාකර වලංගු විද්\u200Dයුත් ලිපිනයක් ඇතුලත් කරන්න.");
            rest.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(e).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(RememberPassActivity.this, "Success!! Please Check Your Emails ", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                else
                {
                    Toast.makeText(RememberPassActivity.this, "Please Try Again ", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                }
            }
        });
    }
}