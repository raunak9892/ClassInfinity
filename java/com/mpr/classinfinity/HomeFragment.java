package com.mpr.classinfinity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mpr.classinfinity.Adapter.CategoryScrollAdapter;
import com.mpr.classinfinity.Adapter.CourseAdapterList;
import com.mpr.classinfinity.Adapter.CoursesItemAdapter;
import com.mpr.classinfinity.Model.Category;
import com.mpr.classinfinity.Model.Courses;
import com.mpr.classinfinity.databinding.FragmentHomeBinding;

import java.net.URL;
import java.util.ArrayList;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    FragmentHomeBinding binding;
    private static final int BOOK_LOADER_ID = 1;
    CourseAdapterList mAdapter;
    private String JSON_QUERY = "https://www.udemy.com/api-2.0/courses/?page_size=12";
    private ArrayList<Category> categoryArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());

        categoryArrayList = new ArrayList<>();
        categoryArrayList.add(new Category(R.drawable.buisness, "Business"));
        categoryArrayList.add(new Category(R.drawable.development, "Development"));
        categoryArrayList.add(new Category(R.drawable.marketing, "Marketing"));
        categoryArrayList.add(new Category(R.drawable.design, "Design"));
        categoryArrayList.add(new Category(R.drawable.medical, "Medical"));
        categoryArrayList.add(new Category(R.drawable.music, "Music"));
        categoryArrayList.add(new Category(R.drawable.photography, "Photography\n& Video"));
        categoryArrayList.add(new Category(R.drawable.life_style, "Lifestyle"));
        categoryArrayList.add(new Category(R.drawable.fitness, "Health &\nFitness"));
        categoryArrayList.add(new Category(R.drawable.personality, "Personal \nDevelopment"));

        HomeAsyncTask task = new HomeAsyncTask();
        task.execute();

        binding.moreCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.cancel(true);
                startActivity(new Intent(getContext(),MoreCoursesActivity.class));
            }
        });
 
        return binding.getRoot();
    }

    protected void updateUi(ArrayList<Courses> booksInfos) {

        CategoryScrollAdapter categoryScrollAdapter = new CategoryScrollAdapter(categoryArrayList, binding.recyclerView, getContext());
        LinearLayoutManager HorizontalLayout = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        binding.recyclerView.setAdapter(categoryScrollAdapter);
        binding.recyclerView.setLayoutManager(HorizontalLayout);
        categoryScrollAdapter.notifyDataSetChanged();

        LinearLayoutManager horizontalManager2 = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);

        CoursesItemAdapter adapter = new CoursesItemAdapter(booksInfos, binding.recyclerView2, getContext(),R.layout.courses_horizontal_cardview,1);
        binding.recyclerView2.setAdapter(adapter);
        binding.recyclerView2.setLayoutManager(horizontalManager2);

        adapter.notifyDataSetChanged();

    }

    private class HomeAsyncTask extends AsyncTask<URL, Void, ArrayList<Courses>> {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected ArrayList<Courses> doInBackground(URL... urls) {
            ArrayList<Courses> event = QueryUtils.fetchCoursesData(JSON_QUERY);   //also we can use  urls[0]
            Log.d("HOme Fragment Utils", JSON_QUERY);
            return event;
        }

        @Override
        protected void onPostExecute(ArrayList<Courses> event) {

            // mLoadingIndicator.setVisibility(View.GONE);
            binding.loadingSpinnerHome.setVisibility(View.GONE);

            if (event == null) {
                return;  
            }

            // mEmptyListTextView.setText("No Books Found");
            updateUi(event);

        }

    }

}