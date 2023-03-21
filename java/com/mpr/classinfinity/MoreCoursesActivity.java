package com.mpr.classinfinity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
import com.mpr.classinfinity.Fragmentss.MostReviewdCourses;
import com.mpr.classinfinity.Fragmentss.NewestCourses;
import com.mpr.classinfinity.Fragmentss.RelavanceCourses;
import com.mpr.classinfinity.databinding.ActivityMoreCoursesBinding;

public class MoreCoursesActivity extends AppCompatActivity {

    ActivityMoreCoursesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMoreCoursesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FragmentTransaction homeTransaction = getSupportFragmentManager().beginTransaction();
        homeTransaction.replace(R.id.main_content,new RelavanceCourses());
        homeTransaction.commit();

        /* ------------------------Bottom normal Navigation Bar replacing with Bubble navigation bar----------------------*/

        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                switch (item.getItemId()){
                    /**
                     * if we are in Activity then so i used getSupportFragmentManger()
                     * else if we were in fragment then use getFragmentManager()
                     */


                    case R.id.relevance:
                        transaction.replace(R.id.main_content,new RelavanceCourses());
                        break;

                    case R.id.highRated:
                        transaction.replace(R.id.main_content,new MostReviewdCourses());
                        break;

                    case R.id.newest:
                        transaction.replace(R.id.main_content,new NewestCourses());
                        break;

                }

                transaction.commit();
                return true;

            }
        });


    }



}