
package com.mpr.classinfinity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mpr.classinfinity.databinding.CoursesDetailsLayoutBinding;

import java.net.MalformedURLException;
import java.net.URL;

public class CourseDetailsActivity extends AppCompatActivity {

    CoursesDetailsLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CoursesDetailsLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String title = getIntent().getStringExtra("tittle");
        int id = getIntent().getIntExtra("id",0);
        String url = getIntent().getStringExtra("url");
        boolean isPaid = getIntent().getBooleanExtra("isPaid",true);
        String price = getIntent().getStringExtra("price");
        String courseThumbnail = getIntent().getStringExtra("courseThumbnail");
        String description = getIntent().getStringExtra("description");
        String instructorImage = getIntent().getStringExtra("instructorImage");
        String instructorName = getIntent().getStringExtra("instructorName");

        if(title!=null)
        binding.courseTitle.setText(title);

        if(instructorName!=null)
            binding.instrucotrName.setText(instructorName);

        if(instructorImage!=null){

                try {
                    Glide.with(getApplicationContext())
                            .load(new URL(instructorImage))
                            .into(binding.insturctorImage);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
        }

        if(courseThumbnail!=null){
                try {
                    Glide.with(getApplicationContext())
                            .load(new URL(courseThumbnail))
                            .into(binding.courseImage);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
        }

        if(description!=null)
        binding.description.setText(description);

        if(price!=null)
        binding.price.setText(price);

        binding.previewCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("bookDetails","buyLInk");
                if(url!=null) {
                    String link = "https://www.udemy.com";
                    link += url;
                    Log.i("CourseDetails Url: ",link);
                    Uri uri = Uri.parse(link);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
                else {
                    Log.i("bookDetails","buyLinjsihfi");
                    Toast.makeText(getApplicationContext(),"Sorry Buying Link is Not Available",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}