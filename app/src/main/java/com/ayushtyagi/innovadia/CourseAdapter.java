package com.ayushtyagi.innovadia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CourseAdapter extends ArrayAdapter{
    ArrayList<String> listTitle;
    ArrayList<String> imageList;
    Context context;

    public CourseAdapter(@NonNull Context context, ArrayList<String> title, ArrayList<String> image) {
        super(context, R.layout.items, title);

        this.listTitle = title;
        this.imageList = image;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.coursecontent,parent,false);
        ImageView imageview=view.findViewById(R.id.imageview);
        TextView textView=view.findViewById(R.id.textview);
        Picasso.get().load(imageList.get(position)).into(imageview);
        textView.setText(listTitle.get(position));

        return view;
    }
}
