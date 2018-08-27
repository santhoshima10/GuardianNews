package com.example.android.guardiannews;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String LOG_TAG = "MainActivity";
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.draw_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WorldNewsFragment()).commit();
            navigationView.setCheckedItem(R.id.news_headlines_id);
        }


    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.news_headlines_id:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WorldNewsFragment()).commit();
                break;
            case R.id.travel_news_id:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TravelNewsFragment()).commit();
                break;
            case R.id.business_news_id:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BusinessNewsFragment()).commit();
                break;
            case R.id.lifestyle_news_id:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LifeStyleNewsFragment()).commit();
                break;
            case R.id.sports_news_id:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SportsNewsFragment()).commit();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
