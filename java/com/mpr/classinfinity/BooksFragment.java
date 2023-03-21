package com.mpr.classinfinity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.mpr.classinfinity.databinding.FragmentBooksBinding;

import java.net.URL;
import java.util.ArrayList;

public class BooksFragment<BookAdapter> extends Fragment  /*implements LoaderManager.LoaderCallbacks<ArrayList<Courses>>*/ {

    public BooksFragment() {
        // Required empty public constructor
    }

    FragmentBooksBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBooksBinding.inflate(getLayoutInflater());


        return binding.getRoot();
    }

   }