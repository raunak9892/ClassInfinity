package com.mpr.classinfinity.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mpr.classinfinity.Model.Category;
import com.mpr.classinfinity.CategoryCoursesActivity;
import com.mpr.classinfinity.R;

import java.util.ArrayList;

public class CategoryScrollAdapter extends RecyclerView.Adapter<CategoryScrollAdapter.categoryViewHolder> {

    private ArrayList<Category> list;
    private RecyclerView recyclerView;
    private Context context;

    public CategoryScrollAdapter(ArrayList<Category> list, RecyclerView recyclerView, Context context) {
        this.list = list;
        this.recyclerView = recyclerView;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryScrollAdapter.categoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryScrollAdapter.categoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_course_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryScrollAdapter.categoryViewHolder holder, int position) {
        holder.setImageView(list.get(position));
        Log.i("Slider item: ", "" + list.size());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, CategoryCoursesActivity.class);
                String category = "";

                int pos = holder.getAdapterPosition();

                switch (holder.getAdapterPosition()) {
                    case 0:
                        category = "Business"; break;
                    case 1:
                        category = "Development"; break;
                    case 2:
                        category = "Marketing"; break;
                    case 3:
                        category = "Design"; break;
                    case 4:
                        category = "Medical"; break;
                    case 5:
                        category = "Music"; break;
                    case 6:
                        category = "Photography Video"; break;
                    case 7:
                        category = "Lifestyle"; break;
                    case 8:
                        category = "Health Fitness"; break;
                    case 9:
                        category = "Personal Development"; break;
                }

                String httpLink = "https://www.udemy.com/api-2.0/courses/?page=1&page_size=20&search=";
                httpLink += category;

                i.putExtra("Category", httpLink);
                i.putExtra("CategoryType", category);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class categoryViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView;

        public categoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.categoryImage);
            textView = itemView.findViewById(R.id.categoryName);
        }

        void setImageView(Category sliderItem) {

            imageView.setImageResource(sliderItem.getCategoryImage());
            textView.setText(sliderItem.getCategoryName());


        }


    }


}