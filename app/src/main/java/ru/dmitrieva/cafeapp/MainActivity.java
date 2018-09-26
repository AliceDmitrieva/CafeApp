package ru.dmitrieva.cafeapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


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
    }

    public void addPages(ViewPager pager) {
        ContentFragmentPagerAdapter adapter = new ContentFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addPage(new CategoryFragment("Пицца"));
        adapter.addPage(new CategoryFragment("Салаты"));
        adapter.addPage(new CategoryFragment("Паста"));
        adapter.addPage(new CategoryFragment("Фирменные блюда"));
        adapter.addPage(new CategoryFragment("Закуски"));
        adapter.addPage(new CategoryFragment("Мороженое"));
        adapter.addPage(new CategoryFragment("Кофе"));
        adapter.addPage(new CategoryFragment("Чай"));

        pager.setAdapter(adapter);

    }
}
