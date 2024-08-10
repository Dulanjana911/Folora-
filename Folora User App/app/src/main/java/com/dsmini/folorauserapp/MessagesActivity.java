package com.dsmini.folorauserapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MessagesActivity extends AppCompatActivity {

    private EditText busno,msg;
    private TextView email,send;

    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        busno = findViewById(R.id.busno1);
        msg = findViewById(R.id.usermsg);
        send = findViewById(R.id.btnSend);
        email = findViewById(R.id.sendemail);
        dbref = FirebaseDatabase.getInstance().getReference().child("Messages");

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String subject = busno.getText().toString();
                String message = msg.getText().toString();

                if (subject.isEmpty()) {
                    busno.setError("Please Enter Message Title.");
                    busno.requestFocus();
                    return;
                }
                if (message.isEmpty()) {
                    msg.setError("Please Enter Your Message.");
                    msg.requestFocus();
                    return;
                }
                else {
                    SendMail mail = new SendMail(subject,message);
                    dbref.push().setValue(mail);
                    Toast.makeText(MessagesActivity.this, "Message Sent Success!", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}