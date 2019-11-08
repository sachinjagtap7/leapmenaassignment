package com.example.com.leapmenaassignment.View;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.example.com.leapmenaassignment.AppViewModel.ContactViewModel;
import com.example.com.leapmenaassignment.R;
import com.example.com.leapmenaassignment.View.Fragments.ContactFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class HomeActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CONTACT = 100;
    FragmentManager fragmentManager;
    ContactViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Phone Contacts");
        checkContactPermission();


    }

    private void checkContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        PERMISSION_REQUEST_CONTACT);
            } else {
                initFrgament();
            }
        } else {
            initFrgament();
        }
    }

    private void initFrgament() {
        ContactFragment fragment = new ContactFragment();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.replaceContainer, fragment);
        transaction.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CONTACT:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initFrgament();

                } else {
                    finish();
                }
                break;
        }
    }
}
