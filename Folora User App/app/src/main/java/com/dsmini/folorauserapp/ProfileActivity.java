package com.dsmini.folorauserapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dsmini.folorauserapp.Reviews.Review;
import com.dsmini.folorauserapp.Reviews.ReviewAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    private String reciever_id,sender_user_id,current_state,phone;
    private CircularImageView visit_profile;
    private TextView visit_name,visit_status;
    private Button request_button,decline_button,call;

    private FirebaseAuth mauth;
    ReviewAdapter mainAdapter;

    private EditText review1;

    private ImageButton add;

    private  RecyclerView recyclerView;


    DatabaseReference ref,chatrequestref,contactsRef,NotificationRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mauth=FirebaseAuth.getInstance();
        sender_user_id=mauth.getCurrentUser().getUid();
        reciever_id=getIntent().getExtras().get("visit_user_id").toString();
        add = findViewById(R.id.btn_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddReview();
            }
        });
        visit_profile=findViewById(R.id.visit_profile_image);
        visit_name=findViewById(R.id.visit_user_name);
        visit_status=findViewById(R.id.visit_status);
        request_button=findViewById(R.id.send_message_request_button);
        decline_button=findViewById(R.id.decline_message_request_button);
        review1 = findViewById(R.id.add_review);
        current_state="new";
        call  = findViewById(R.id.send_call);
        if(sender_user_id.equals(reciever_id))request_button.setVisibility(View.INVISIBLE);
        else request_button.setVisibility(View.VISIBLE);
        ref= FirebaseDatabase.getInstance().getReference();
        chatrequestref=FirebaseDatabase.getInstance().getReference().child("Chat Requests");
        contactsRef=FirebaseDatabase.getInstance().getReference().child("Contacts");
        NotificationRef=FirebaseDatabase.getInstance().getReference().child("Notifications");





        TextView textView = findViewById(R.id.review_txt);
        SpannableString content = new SpannableString("Passenger Reviews");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);

        ref.child("Users").child(reciever_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.hasChild("name") && dataSnapshot.hasChild("image"))
                {
                    String retrieveusername=dataSnapshot.child("name").getValue().toString();
                    String retrieveuserstatus=dataSnapshot.child("status").getValue().toString();
                    String retrieveuserimage=dataSnapshot.child("image").getValue().toString();
                    String retrieveusephone=dataSnapshot.child("number").getValue().toString();

                    visit_name.setText(retrieveusername);
                    visit_status.setText(retrieveuserstatus);
                    Picasso.get().load(retrieveuserimage).into(visit_profile);
                    phone = retrieveusephone;

                    ManageChatRequest();
                }
                else if(dataSnapshot.exists() && dataSnapshot.hasChild("name"))
                {
                    String retrieveusername=dataSnapshot.child("name").getValue().toString();
                    String retrieveuserstatus=dataSnapshot.child("status").getValue().toString();
                    String retrieveuserphone=dataSnapshot.child("number").getValue().toString();

                    visit_name.setText(retrieveusername);
                    visit_status.setText(retrieveuserstatus);
                    phone = retrieveuserphone;

                    ManageChatRequest();
                    String NAME  = retrieveusername;
                    recyclerView = findViewById(R.id.bikeshow);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(ProfileActivity.this);
                    recyclerView.setLayoutManager(layoutManager);

                    FirebaseRecyclerOptions<Review> options =
                            new FirebaseRecyclerOptions.Builder<Review>()
                                    .setQuery(FirebaseDatabase.getInstance().getReference().child("reviews").orderByChild("name").startAt(NAME).endAt(NAME),Review.class)
                                    .build();


                    mainAdapter = new ReviewAdapter(options);
                    recyclerView.setAdapter(mainAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ProfileActivity.this));
                    mainAdapter.startListening();

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        call.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("Contact Driver")
                        .setMessage("Are you sure you want to call this driver?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", null);

                AlertDialog dialog = builder.create();
                dialog.show();

            }


        });




    }


    private void AddReview()
    {
        String name = visit_name.getText().toString();
        String review = review1.getText().toString();
        String key = ref.child("reviews").push().getKey();
        Map<String, Object> reviewValues = new HashMap<>();
        reviewValues.put("name", name);
        reviewValues.put("review", review);
        ref.child("reviews").child(key).updateChildren(reviewValues)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(ProfileActivity.this, "Review added successfully!", Toast.LENGTH_SHORT).show();
                        review1.setText("");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }







    private void ManageChatRequest() {
        chatrequestref.child(sender_user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(reciever_id))
                {
                    String reuest_type=dataSnapshot.child(reciever_id).child("request_type").getValue().toString();
                    if(reuest_type.equals("sent"))
                    {
                        current_state="request_sent";
                        request_button.setText("  Cancel Ride Request  ");
                    }
                    else if(reuest_type.equals("received"))
                    {
                        current_state="request_received";
                        request_button.setText("  Accept Ride Request  ");

                        decline_button.setVisibility(View.VISIBLE);
                        decline_button.setEnabled(true);
                        decline_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CancelChatRequest();
                            }
                        });
                    }
                }
                else
                {
                    contactsRef.child(sender_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(reciever_id))
                            {
                                current_state="friends";
                                request_button.setText(" Remove this ride ");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        request_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request_button.setEnabled(false);

                if(current_state.equals("new"))
                {
                    SendChatRequest();
                }
                if(current_state.equals("request_sent"))
                {
                    CancelChatRequest();
                }
                if(current_state.equals("request_received"))
                {
                    AcceptChatRequest();
                }
                if(current_state.equals("friends"))
                {
                    RemoveSpecificChatRequest();
                }
            }
        });
    }

    private void RemoveSpecificChatRequest() {
        contactsRef.child(sender_user_id).child(reciever_id)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            contactsRef.child(reciever_id).child(sender_user_id)
                                    .removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                request_button.setEnabled(true);
                                                request_button.setText("  Send Request  ");
                                                current_state="new";

                                                decline_button.setVisibility(View.INVISIBLE);
                                                decline_button.setEnabled(false);
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    private void AcceptChatRequest() {
        contactsRef.child(sender_user_id).child(reciever_id)
                .child("Contacts").setValue("Saved")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            contactsRef.child(reciever_id).child(sender_user_id)
                                    .child("Contacts").setValue("Saved")
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                chatrequestref.child(sender_user_id).child(reciever_id)
                                                        .removeValue()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful())
                                                                {
                                                                    chatrequestref.child(reciever_id).child(sender_user_id)
                                                                            .removeValue()
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    request_button.setEnabled(true);
                                                                                    current_state="friends";
                                                                                    request_button.setText(" Remove this Ride ");

                                                                                    decline_button.setVisibility(View.INVISIBLE);
                                                                                    decline_button.setEnabled(false);
                                                                                }
                                                                            });
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    private void CancelChatRequest() {

        chatrequestref.child(sender_user_id).child(reciever_id)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            chatrequestref.child(reciever_id).child(sender_user_id)
                                    .removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                request_button.setEnabled(true);
                                                request_button.setText("  Send Request  ");
                                                current_state="new";

                                                decline_button.setVisibility(View.INVISIBLE);
                                                decline_button.setEnabled(false);
                                            }
                                        }
                                    });
                        }
                    }
                });


    }

    @Override
    protected void onStart() {




        super.onStart();

    }

    private void SendChatRequest() {

        chatrequestref.child(sender_user_id).child(reciever_id).child("request_type")
                .setValue("sent")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            chatrequestref.child(reciever_id).child(sender_user_id)
                                    .child("request_type").setValue("received")
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                HashMap<String,String> chatnotificationMap=new HashMap<>();
                                                chatnotificationMap.put("from",sender_user_id);
                                                chatnotificationMap.put("type","request");
                                                NotificationRef.child(reciever_id).push().setValue(chatnotificationMap)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful())
                                                                {
                                                                    request_button.setEnabled(true);
                                                                    current_state="request_sent";
                                                                    request_button.setText("  Cancel Ride Request  ");
                                                                }
                                                            }
                                                        });

                                            }
                                        }
                                    });


                        }
                    }
                });
    }
}
