package com.example.houssem.tracker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent i=new Intent(this,ProfileActivity.class);
            startActivity(i);
        }
        else{
            setContentView(R.layout.activity_main);
            BottomNavigationView bottomNav = findViewById(R.id.navigation);
            bottomNav.setOnNavigationItemSelectedListener(navListener);

            //I added this if statement to keep the selected fragment when rotating the device
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentHome()).commit();
            }
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            selectedFragment = new FragmentHome();
                            break;
                        case R.id.navigation_driver:
                            selectedFragment = new FragmentDriver();
                            break;
                        case R.id.navigation_notifications:
                            selectedFragment = new FragmentNotifications();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}
