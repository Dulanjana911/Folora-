package com.dsmini.foloradriverapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager myviewPager;
    private TabLayout mytabLayout;
    private TabsAccessorAdapter mytabsAccessorAdapter;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();

        toolbar=findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ƒolora | Driver");

        myviewPager=findViewById(R.id.main_tabs_pager);
        mytabsAccessorAdapter=new TabsAccessorAdapter(getSupportFragmentManager());
        myviewPager.setAdapter(mytabsAccessorAdapter);

        mytabLayout=findViewById(R.id.main_tabs);
        mytabLayout.setupWithViewPager(myviewPager);
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        if(currentUser==null)
        {
            sendUserToLoginActivity();
        }
        else
        {
            updateUserStatus("online");
            VerifyUserexistance();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        if(currentUser!=null)
        {
            updateUserStatus("offline");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        if(currentUser!=null)
        {
            updateUserStatus("offline");
        }
    }

    private void VerifyUserexistance() {
        String currentUserID=firebaseAuth.getCurrentUser().getUid();
        databaseReference.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("name").exists())
                {
                    //Toast.makeText(MainActivity.this,"Welcome",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    sendUserToSettingsActivity();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void sendUserToLoginActivity() {
        Intent loginintent=new Intent(MainActivity.this,LoginActivity.class);
        loginintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginintent);
        finish();
    }

    private void sendUserToSettingsActivity() {
        Intent settingsintent=new Intent(MainActivity.this,SettingsActivity.class);
        startActivity(settingsintent);
    }

    private void sendUserToFindFriendsActivity() {
        Intent friendsintent=new Intent(MainActivity.this,FindFriendsActivity.class);
        startActivity(friendsintent);
    }

    private void updateUserStatus(String state)
    {
        String savecurrentTime,savecurrentDate;
        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("dd/MM/yyyy");
        savecurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("hh:mm a");
        savecurrentTime=currentTime.format(calendar.getTime());

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("time",savecurrentTime);
        hashMap.put("date",savecurrentDate);
        hashMap.put("state",state);

        currentUserId=firebaseAuth.getCurrentUser().getUid();
        databaseReference.child("Users").child(currentUserId).child("userState").updateChildren(hashMap);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();
        if(id==R.id.main_find_friends_menu)
        {
            sendUserToFindFriendsActivity();
        }
        else if(id==R.id.main_settings_menu)
        {
            sendUserToSettingsActivity();
        }
        else if(id==R.id.main_logout_menu)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirm Logout");
            builder.setMessage("Are you sure you want to log out?");



            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    updateUserStatus("offline");
                    firebaseAuth.signOut();
                    sendUserToLoginActivity();
                    Toast.makeText(MainActivity.this,"User logged out successfully...",Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.dismiss();
                }
            });
            builder.show();


        }

        return true;
    }






}
