package com.ayushtyagi.innovadia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leo.simplearcloader.SimpleArcLoader;

import java.util.ArrayList;

public class Homepage extends AppCompatActivity {

    BottomNavigationView bottom_navigation;
    ListView listview;
    SimpleArcLoader simpleArcLoader;
    String st=" ",ims=" ";
    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> image = new ArrayList<>();
    public static  final String PassString="Chalija";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        simpleArcLoader=findViewById(R.id.loader);
        listview=findViewById(R.id.listview);
        bottom_navigation=findViewById(R.id.bottom);

        simpleArcLoader.postDelayed(new Runnable() {
            @Override
            public void run() {
                simpleArcLoader.setVisibility(View.INVISIBLE);
                listview.setVisibility(View.VISIBLE);
                bottom_navigation.setVisibility(View.VISIBLE);
            }
        },4000);


        homedisplay();
        bottom_navigation.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.search_bar:
                        Intent intent =new Intent(Homepage.this,Pro.class);
                        startActivity(intent);
                        break;
                    case R.id.home_bar:
                        break;

                    case R.id.profile_bar:
                        Intent i =new Intent(Homepage.this,UserProfile.class);
                        startActivity(i);
                        break;
                }
            }

        });

    }

    private void homedisplay() {
        listview=findViewById(R.id.listview);
        bottom_navigation=findViewById(R.id.bottom);
//        simpleArcLoader=findViewById(R.id.loader);

        int a = 1;

        while (a <= 5) {
            String co = "Course" + a;
            DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Courses").child(co);
            reff.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    st=dataSnapshot.child("Heading").getValue().toString();
                    passing(st);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Courses").child(co);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ims=dataSnapshot.child("Image6").getValue().toString();
                    passing_image(ims);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            a++;
        }


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                simpleArcLoader.setVisibility(View.VISIBLE);
//                simpleArcLoader.start();
//                listview.setVisibility(View.GONE);
                  String dogi = "Course"+(position+1);
//
//                simpleArcLoader.stop();
//                simpleArcLoader.setVisibility(View.GONE);
//                listview.setVisibility(View.VISIBLE);

                Intent intent = new Intent(Homepage.this, Courses.class);
                intent.putExtra(PassString,dogi);
                startActivity(intent);
            }
        });
    }

    private void passing_image(String ims) {
        image.add(ims);
        MyAdapter myAdapter = new MyAdapter(this, title, image);
        listview.setAdapter(myAdapter);
    }

    private void passing(String st) {
        title.add(st);
        MyAdapter myAdapter = new MyAdapter(this, title, image);
        listview.setAdapter(myAdapter);

    }
}

