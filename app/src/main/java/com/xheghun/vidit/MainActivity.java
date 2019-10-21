package com.xheghun.vidit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xheghun.vidit.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    FrameLayout frameLayout;
    FloatingActionButton record_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(new HomeFragment());

        bottomNavigation = findViewById(R.id.bottom_nav);
        frameLayout = findViewById(R.id.fragment_container);

        record_btn = findViewById(R.id.record_vid);

        record_btn.setOnClickListener(v -> startActivity(new Intent(this,MediaMenuActivity.class)));

        bottomNavigation.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.home:
                    loadFragment(new HomeFragment());
                    break;
                case R.id.request:
                case R.id.notification:
                case R.id.search:
                    //loadFragment(null);
                    Toast.makeText(MainActivity.this, "Page not available", Toast.LENGTH_SHORT).show();
                    break;

            }
            return false;
        });
    }
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out)
                .replace(R.id.fragment_container,fragment).commit();
    }
}
