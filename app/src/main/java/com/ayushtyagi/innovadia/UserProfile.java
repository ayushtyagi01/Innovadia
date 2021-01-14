package com.ayushtyagi.innovadia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {
    TextView aboutus,logout,tvuser,tvemail,tv_user,mycourses;
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        BottomNavigationView bottom_navigation=findViewById(R.id.bottom);
        aboutus=findViewById(R.id.aboutus);
        logout=findViewById(R.id.logout);
        tvuser=findViewById(R.id.tvuser);
        tv_user=findViewById(R.id.tv_user);
        tvemail=findViewById(R.id.tvemail);
        mycourses=findViewById(R.id.mycourses);

        bottom_navigation.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.search_bar:
                        Intent intent =new Intent(UserProfile.this,Pro.class);
                        startActivity(intent);
                        break;
                    case R.id.home_bar:
                        Intent i =new Intent(UserProfile.this,Homepage.class);
                        startActivity(i);
                        break;

                    case R.id.profile_bar:

                        break;
                }
            }

        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserProfile.this,LogIn.class);
                startActivity(intent);
                finish();
            }
        });
       aboutus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i=new Intent(UserProfile.this,AboutUs.class);
               startActivity(i);
           }
       });

       mycourses.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent =new Intent(UserProfile.this,Pro.class);
               startActivity(intent);
               finish();
           }
       });

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tvemail.setText(dataSnapshot.child("email").getValue().toString());
                tvuser.setText(dataSnapshot.child("username").getValue().toString());
                tv_user.setText(dataSnapshot.child("username").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
