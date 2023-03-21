package com.mpr.classinfinity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.TextRecognition;
import com.mpr.classinfinity.databinding.ActivityOcrTextRecognitionBinding;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

public class OcrTextRecognition extends AppCompatActivity {

    ActivityOcrTextRecognitionBinding binding;
    Bitmap bitmap;

    private static final int REQUEST_CAMERA_CODE = 100;
    private static String LOG_TAG = OcrTextRecognition.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOcrTextRecognitionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(ContextCompat.checkSelfPermission(OcrTextRecognition.this,Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(OcrTextRecognition.this,new String[]{Manifest.permission.CAMERA},REQUEST_CAMERA_CODE);
        }

        binding.capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(OcrTextRecognition.this);
            }
        });

        binding.copyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String scanned_text = binding.textViewCaptured.getText().toString();
                 copyToClipBoard(scanned_text);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK){
                Uri uri = result.getUri();
                try {

                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                    Log.i(LOG_TAG,"getting image from bitmap");
                    getTextFromImage(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } else{
            Toast.makeText(OcrTextRecognition.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
        }

    }

    protected void getTextFromImage(Bitmap bitmap){
        TextRecognizer recognizer = new TextRecognizer.Builder(OcrTextRecognition.this).build();
        if(!recognizer.isOperational()){
            Toast.makeText(OcrTextRecognition.this,"Some Error Occured",Toast.LENGTH_SHORT).show();
        }
        else{
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> textBlockSparseArray = recognizer.detect(frame);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0;i<textBlockSparseArray.size();i++){
                TextBlock textBlock = textBlockSparseArray.valueAt(i);
                stringBuilder.append(textBlock.getValue());
                stringBuilder.append("\n");
            }
            Log.i(LOG_TAG,"Reading image");
            binding.textViewCaptured.setText(stringBuilder.toString());
            binding.capture.setText("Retake");
        }

    }

    private void copyToClipBoard(String text){

        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("Copied Data",text);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(getApplicationContext(), "Text has been Copied", Toast.LENGTH_SHORT).show();

    }


}