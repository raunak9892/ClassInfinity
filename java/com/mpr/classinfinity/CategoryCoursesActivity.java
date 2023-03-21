package com.mpr.classinfinity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.mpr.classinfinity.Adapter.CoursesItemAdapter;
import com.mpr.classinfinity.Fragmentss.RelavanceCourses;
import com.mpr.classinfinity.Model.Courses;
import com.mpr.classinfinity.databinding.ActivityCategoryCoursesBinding;

import java.net.URL;
import java.util.ArrayList;

public class CategoryCoursesActivity extends AppCompatActivity {

    ActivityCategoryCoursesBinding binding;
    ProgressBar spinner;
    private String JSON_QUERY_RELEVANCE = "";
    int page = 1, pageSize = 0;
    ArrayList<Courses> bookList;
    String tempStart = "https://www.udemy.com/api-2.0/courses/?page=" , tempEnd = "&page_size=20&ordering=relevance";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryCoursesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        JSON_QUERY_RELEVANCE = getIntent().getStringExtra("Category");
        String category = getIntent().getStringExtra("CategoryType");

        bookList = new ArrayList<>();

        CourseAsyncTask task = new CourseAsyncTask();
        task.execute();

        spinner = (ProgressBar) findViewById(R.id.progress_spineer);

        binding.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
                    // binding.progressSpineer.setVisibility(View.VISIBLE);

                    // https://www.udemy.com/api-2.0/courses/?page=1&search=

                    page++;
                    binding.progressSpineer2.setVisibility(View.VISIBLE);

                    String jsonStart = tempStart;
                    jsonStart += page;
                    String jsonEnd = tempEnd;
                    String link = jsonStart + jsonEnd;
                    JSON_QUERY_RELEVANCE = link;
                    Log.i("Category Courses","Link: "+link);

                    MoreCourseAsyncTask task = new MoreCourseAsyncTask();
                    task.execute();

                }
            }
        });

        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                page = 1;
                binding.progressSpineer.setVisibility(View.VISIBLE);

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


       /* binding.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
                    // binding.progressSpineer.setVisibility(View.VISIBLE);

                    // https://www.udemy.com/api-2.0/courses/?page=1&search=

                    *//*limit += 20;
                    Log.i(LOG_TAG,"LIMIT: "+limit);
                    Json_Link = tempLink;
                    Json_Link += limit;

                    binding.progressBarMoreResp.setVisibility(View.VISIBLE);
                    UtilsAsyncTaskMore task1 = new UtilsAsyncTaskMore();
                    task1.execute();

                    Log.i(LOG_TAG,"More Result Link: "+Json_Link);*//*



                }
            }
        });*/


    }

    protected void updateUi(ArrayList<Courses> booksInfos) {

        bookList = booksInfos;

        CoursesItemAdapter sliderAdapter = new CoursesItemAdapter(booksInfos, binding.recyclerView, getApplicationContext(), R.layout.courses_item_specific, 2);
        binding.recyclerView.setAdapter(sliderAdapter);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

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

            binding.progressSpineer.setVisibility(View.GONE);

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
            binding.progressSpineer2.setVisibility(View.GONE);

            if (event == null) {
                //  mEmptyStateTextView.setText("No Books Found");
                return;
            }

            bookList.addAll(event);
            updateUi(bookList);

        }
    }

}