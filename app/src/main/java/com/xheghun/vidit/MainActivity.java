package com.xheghun.vidit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xheghun.vidit.fragments.HomeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bottom_nav)
    BottomNavigationView bottomNavigation;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.record_vid)
    FloatingActionButton record_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loadFragment(new HomeFragment());


        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.update) {
                startActivity(new Intent(getApplicationContext(), UpdateActivity.class));
            }
            return true;
        });

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
