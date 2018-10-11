package ru.dmitrieva.cafeapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.container);
        this.addPages(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });

        new GettingProductsAsyncTask().execute();
    }

    public void addPages(ViewPager pager) {
        ContentFragmentPagerAdapter adapter = new ContentFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addPage(new CategoryFragment("Пицца"));
        adapter.addPage(new CategoryFragment("Салаты"));
        adapter.addPage(new CategoryFragment("Паста"));
        adapter.addPage(new CategoryFragment("Чай"));
        adapter.addPage(new CategoryFragment("Кофе"));

        pager.setAdapter(adapter);
    }
}
