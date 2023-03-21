package com.mpr.classinfinity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
import com.mpr.classinfinity.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FragmentTransaction homeTransaction = getSupportFragmentManager().beginTransaction();
        homeTransaction.replace(R.id.main_content,new HomeFragment());
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


                    case R.id.home:
                        transaction.replace(R.id.main_content,new HomeFragment());
                        break;

                    case R.id.setting_menu:
                        transaction.replace(R.id.main_content,new ProfileFragment());
                        break;

                    case R.id.chatBot:
                        transaction.replace(R.id.main_content,new ChatBotFragment());
                        break;

                    case R.id.scanner:
                        transaction.replace(R.id.main_content,new ScannerFragment());
                        break;

                    case R.id.books:
                        transaction.replace(R.id.main_content,new BooksFragment());
                        break;

                }

                transaction.commit();
                return true;

            }
        });

       /* binding.bubbleTabBar.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int i) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                switch (i){
                    case 0:
                        transaction.replace(R.id.main_content,new HomeFragment());
                        transaction.commit();
                        break;

                    case 1:
                        transaction.replace(R.id.main_content,new ProfileFragment());
                        transaction.commit();
                        break;

                    case 2:
                        transaction.replace(R.id.main_content,new ChatBotFragment());
                        transaction.commit();
                        break;

                    case 3:
                        transaction.replace(R.id.main_content,new ScannerFragment());
                        transaction.commit();
                        break;

                    case 4:
                        transaction.replace(R.id.main_content,new BooksFragment());
                        transaction.commit();
                        break;

                }
                 transaction.commit();
            }
        });*/

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }

}