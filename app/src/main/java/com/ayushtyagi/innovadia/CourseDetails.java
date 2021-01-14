package com.ayushtyagi.innovadia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leo.simplearcloader.SimpleArcLoader;

public class CourseDetails extends AppCompatActivity {
    TextView textView4,textview3;
    SimpleArcLoader simpleArcLoader;
    BottomNavigationView bottom_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        textView4 = findViewById(R.id.textView4);
        textview3=findViewById(R.id.textView3);
        simpleArcLoader=findViewById(R.id.loader);
        bottom_navigation=findViewById(R.id.bottom);
        
        simpleArcLoader.postDelayed(new Runnable() {
            @Override
            public void run() {
                simpleArcLoader.setVisibility(View.INVISIBLE);
                textview3.setVisibility(View.VISIBLE);
                textView4.setVisibility(View.VISIBLE);
                bottom_navigation.setVisibility(View.VISIBLE);
            }
        },2000);

//        textview3.setVisibility(View.GONE);
//        textView4.setVisibility(View.GONE);
//        simpleArcLoader.setVisibility(View.VISIBLE);
//        simpleArcLoader.start();

        Intent intent = getIntent();
        final String coursesname = intent.getStringExtra(Courses.coursepass);
        Intent i = getIntent();
        final String topicname = i.getStringExtra(Courses.topicpass);


        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Courses").child(coursesname).child(topicname);
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                simpleArcLoader.setVisibility(View.GONE);
//                simpleArcLoader.stop();
//                textView4.setVisibility(View.VISIBLE);
                textView4.setText(dataSnapshot.child("Content").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Courses").child(coursesname).child(topicname);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                textview3.setVisibility(View.VISIBLE);
                textview3.setText(dataSnapshot.child("Title").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}