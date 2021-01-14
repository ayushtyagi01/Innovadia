package com.ayushtyagi.innovadia;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leo.simplearcloader.SimpleArcLoader;

public class videoplayer extends AppCompatActivity{

    VideoView videoview;
    SimpleArcLoader simpleArcLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplayer);
        simpleArcLoader=findViewById(R.id.loader);
        videoview = findViewById(R.id.videoview);

        simpleArcLoader.postDelayed(new Runnable() {
            @Override
            public void run() {
                simpleArcLoader.setVisibility(View.INVISIBLE);
                videoview.setVisibility(View.VISIBLE);
            }
        },7000);


        final Intent intent = getIntent();
        final String coursename = intent.getStringExtra(Courses.play_video);
        final MediaController mediaController = new MediaController(this);

        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Courses").child(coursename);
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String message = dataSnapshot.child("Video").getValue().toString();
                Uri uri = Uri.parse(message);
                videoview.setVideoURI(uri);

                mediaController.setAnchorView(videoview);
                videoview.setMediaController(mediaController);
                videoview.requestFocus();
                videoview.start();

                videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {

                    }
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

