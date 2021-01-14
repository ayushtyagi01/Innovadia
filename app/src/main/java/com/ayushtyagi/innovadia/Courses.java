package com.ayushtyagi.innovadia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leo.simplearcloader.SimpleArcLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Courses extends AppCompatActivity {


    BottomNavigationView bottom_navigation;
    SimpleArcLoader simpleArcLoader;
    ListView listview;
    CardView btn_play;
    String st=" ",ims=" ";
    String coursename=" ";
    ImageView imageview;
    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> image = new ArrayList<>();
    public static  final String topicpass="maibhichalgaya";
    public static  final String coursepass="Chalgaya";
    public  static  final String play_video="jfnajfbanjna";
    public static  final String lom="lomdi_is_pro";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        listview=findViewById(R.id.listview);
        bottom_navigation=findViewById(R.id.bottom);
        btn_play=findViewById(R.id.btn_play);
        imageview=findViewById(R.id.imageview);
        simpleArcLoader=findViewById(R.id.loader);

        simpleArcLoader.postDelayed(new Runnable() {
            @Override
            public void run() {
                simpleArcLoader.setVisibility(View.INVISIBLE);
                listview.setVisibility(View.VISIBLE);
                btn_play.setVisibility(View.VISIBLE);
                bottom_navigation.setVisibility(View.VISIBLE);
            }
        },4000);


        homedisplay();
        bottom_navigation.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.search_bar:
                        Intent in =new Intent(Courses.this,Pro.class);
                        startActivity(in);
                        break;
                    case R.id.home_bar:
                        Intent intent =new Intent(Courses.this,Homepage.class);
                        startActivity(intent);
                        break;
                    case R.id.profile_bar:
                        Intent i =new Intent(Courses.this,UserProfile.class);
                        startActivity(i);
                        break;
                }
            }

        });
    }

    private void homedisplay() {

        listview=findViewById(R.id.listview);
        btn_play=findViewById(R.id.btn_play);

        ArrayList<String> title=new ArrayList<>();
        ArrayList<String> image=new ArrayList<>();
        final Intent intent=getIntent();
        coursename = intent.getStringExtra(Homepage.PassString);

        DatabaseReference reffr = FirebaseDatabase.getInstance().getReference().child("Courses").child(coursename);
        reffr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Picasso.get().load(dataSnapshot.child("Image6").getValue().toString()).into(imageview);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        int a = 1;

        while (a <=5) {
            String co = "Topic" + a;
            DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Courses").child(coursename).child(co);
            reff.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    st = dataSnapshot.child("Title").getValue().toString();
                    pass_course_text(st);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Courses").child(coursename).child(co);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ims = dataSnapshot.child("Image").getValue().toString();
                    pass_course_image(ims);

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
//                btn_play.setVisibility(View.GONE);

                String tit="Topic"+(position+1);

//                simpleArcLoader.stop();
//                simpleArcLoader.setVisibility(View.GONE);
//                listview.setVisibility(View.VISIBLE);
//                btn_play.setVisibility(View.VISIBLE);

                Intent intent=new Intent(Courses.this,CourseDetails.class);
                intent.putExtra(topicpass,tit);
                intent.putExtra(coursepass,coursename);
                startActivity(intent);


            }
        });
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(Courses.this,videoplayer.class);
                i.putExtra(play_video,coursename);
                startActivity(i);
            }
        });



    }
    private void pass_course_image(String ims) {
        image.add(ims);
        CourseAdapter courseAdapter = new CourseAdapter(this, title, image);
        listview.setAdapter(courseAdapter);
    }


    private void pass_course_text(String st) {
        title.add(st);
        CourseAdapter courseAdapter = new CourseAdapter(this, title, image);
        listview.setAdapter(courseAdapter);
    }
}