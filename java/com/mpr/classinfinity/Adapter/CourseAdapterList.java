package com.mpr.classinfinity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.mpr.classinfinity.Model.Courses;
import com.mpr.classinfinity.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CourseAdapterList extends ArrayAdapter<Courses> {

    public CourseAdapterList(@NonNull Context context, @NonNull ArrayList<Courses> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_books_list_response, parent, false);
        }

        Courses currentCourse = getItem(position);

        TextView bookTittle = listItemView.findViewById(R.id.book_title_textview);
        bookTittle.setText(currentCourse.getTittle());

        TextView authorName = listItemView.findViewById(R.id.author_textView);
        String Author = "None";
        if(currentCourse.getInstructorsList()!=null) {
            if (currentCourse.getInstructorsList().size()>0)
                Author = "By:- " + currentCourse.getInstructorsList().get(0).getName();
        }
        authorName.setText(Author);

        ImageView bookImg = listItemView.findViewById(R.id.book_image);
        try {
            Glide.with(getContext())
                    .load(new URL(currentCourse.getCourseThumbnail()))
                    .into(bookImg);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return listItemView;

    }

}
