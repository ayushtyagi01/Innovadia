package com.ayushtyagi.innovadia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.text.DateFormat;
import java.util.Calendar;


public class Pro extends AppCompatActivity {
    Button btn_upload, btn_browse;
    EditText title, phone, cost;
    ImageView imgview;
    Uri uri;
    String imageUrl;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro);
        btn_browse = (Button) findViewById(R.id.btn_browse);
        btn_upload = (Button) findViewById(R.id.btn_upload);
        title = (EditText) findViewById(R.id.title);
        phone = (EditText) findViewById(R.id.Phone);
        cost = findViewById(R.id.Cost);
        imgview = findViewById(R.id.imageView3);
        progressDialog = new ProgressDialog(Pro.this);

        btn_browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,Image_Request_Code);
            }
        });
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title_text = title.getText().toString();
                String phone_text = phone.getText().toString();
                String cost_text = cost.getText().toString();
                if (title_text.isEmpty() || phone_text.isEmpty() || cost_text.isEmpty()) {
                    Toast.makeText(Pro.this, "All fields required", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(phone_text.length() == 10){
                        upload_image();
                    }
                    else {
                        Toast.makeText(Pro.this, "Enter a Valid Phone Number", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void upload_image() {
        StorageReference storageReference=FirebaseStorage.getInstance().getReference().child("Images").child(uri.getLastPathSegment());
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask =taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri urlImage=uriTask.getResult();
                imageUrl=urlImage.toString();
                Uploadtext();

            }
        });
    }

    public void Uploadtext(){
        uploadinfo info = new uploadinfo(title.getText().toString(),cost.getText().toString(),phone.getText().toString(),imageUrl);
//      String myCurrentDateTime = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        String Key = FirebaseDatabase.getInstance().getReference("Images").push().getKey();
        FirebaseDatabase.getInstance().getReference("Images").child(Key).setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Pro.this, "Data Uploaded", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Pro.this, Homepage.class);
                    startActivity(intent);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Pro.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            uri=data.getData();
            imgview.setImageURI(uri);
        }
        else{
            Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
        }

    }
}



