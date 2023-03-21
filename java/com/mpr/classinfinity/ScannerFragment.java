package com.mpr.classinfinity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mpr.classinfinity.databinding.FragmentScannerBinding;

public class ScannerFragment extends Fragment {


    public ScannerFragment() {
        // Required empty public constructor
    }

    FragmentScannerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentScannerBinding.inflate(getLayoutInflater());

        binding.textRecognition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),OcrTextRecognition.class);
                startActivity(i);
            }
        });


        return binding.getRoot();
    }
}