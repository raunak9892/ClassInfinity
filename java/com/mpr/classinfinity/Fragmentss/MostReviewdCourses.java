package com.mpr.classinfinity.Fragmentss;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mpr.classinfinity.Adapter.CoursesItemAdapter;
import com.mpr.classinfinity.Model.Courses;
import com.mpr.classinfinity.QueryUtils;
import com.mpr.classinfinity.R;
import com.mpr.classinfinity.databinding.FragmentMostReviewdCoursesBinding;
import com.mpr.classinfinity.databinding.FragmentRelavanceCoursesBinding;

import java.net.URL;
import java.util.ArrayList;


public class MostReviewdCourses extends Fragment {


    public MostReviewdCourses() {
        // Required empty public constructor
    }

    FragmentMostReviewdCoursesBinding binding;
    private String JSON_QUERY_RELEVANCE = "https://www.udemy.com/api-2.0/courses/?page=1&page_size=20&ordering=highest-rated";
    int page = 1, pageSize = 0;
    ArrayList<Courses> bookList;
    String tempStart = "https://www.udemy.com/api-2.0/courses/?page=", tempEnd = "&page_size=20&ordering=highest-rated";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMostReviewdCoursesBinding.inflate(getLayoutInflater());

        bookList = new ArrayList<>();

        CourseAsyncTask task = new CourseAsyncTask();
        task.execute();

        binding.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // binding.progressSpineer.setVisibility(View.VISIBLE);

                    // https://www.udemy.com/api-2.0/courses/?page=1&search=

                    page++;
                    binding.loadingSpinnerEnd.setVisibility(View.VISIBLE);

                    String jsonStart = tempStart;
                    jsonStart += page;
                    String jsonEnd = tempEnd;
                    String link = jsonStart + jsonEnd;
                    JSON_QUERY_RELEVANCE = link;
                    Log.i("Most reviewed Courses", "Link: " + link);

                    MoreCourseAsyncTask task = new MoreCourseAsyncTask();
                    task.execute();

                }
            }
        });

        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                page = 1;
                binding.loadingSpinner.setVisibility(View.VISIBLE);

                String jsonStart = "https://www.udemy.com/api-2.0/courses/?page=";
                tempStart = jsonStart;
                jsonStart += page;
                String jsonEnd = "&page_size=20&ordering=relevance&search=";
                jsonEnd += binding.searchEdittext.getText().toString().trim();
                tempEnd = jsonEnd;
                String link = jsonStart + jsonEnd;
                JSON_QUERY_RELEVANCE = link;

                CourseAsyncTask asyncTask = new CourseAsyncTask();
                asyncTask.execute();

            }
        });


        return binding.getRoot();
    }

    protected void updateUi(ArrayList<Courses> booksInfos) {

        bookList = booksInfos;

        CoursesItemAdapter sliderAdapter = new CoursesItemAdapter(booksInfos, binding.recyclerViewMostRev, getContext(), R.layout.courses_item_specific, 2);
        binding.recyclerViewMostRev.setAdapter(sliderAdapter);
        binding.recyclerViewMostRev.setLayoutManager(new GridLayoutManager(getContext(), 2));

    }

    private class CourseAsyncTask extends AsyncTask<URL, Void, ArrayList<Courses>> {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected ArrayList<Courses> doInBackground(URL... urls) {
            ArrayList<Courses> event = QueryUtils.fetchCoursesData(JSON_QUERY_RELEVANCE);            //also we can use  urls[0]
            Log.i("CategoryCoursesActivity", JSON_QUERY_RELEVANCE);
            return event;
        }

        @Override
        protected void onPostExecute(ArrayList<Courses> event) {

            binding.loadingSpinner.setVisibility(View.GONE);

            if (event == null) {
                //  binding.emptyNoBook.setText("No Books Found");
                return;
            }

            updateUi(event);

        }

    }

    private class MoreCourseAsyncTask extends AsyncTask<URL, Void, ArrayList<Courses>> {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected ArrayList<Courses> doInBackground(URL... urls) {
            ArrayList<Courses> event = QueryUtils.fetchCoursesData(JSON_QUERY_RELEVANCE);            //also we can use  urls[0]
            return event;
        }

        @Override
        protected void onPostExecute(ArrayList<Courses> event) {

            //  binding.loadingSpinner.setVisibility(View.GONE);
            binding.loadingSpinnerEnd.setVisibility(View.GONE);

            if (event == null) {
                //  mEmptyStateTextView.setText("No Books Found");
                return;
            }

            bookList.addAll(event);
            updateUi(bookList);

        }
    }

}