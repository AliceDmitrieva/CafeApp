package ru.dmitrieva.cafeapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ContentFragmentPagerAdapter extends FragmentPagerAdapter {

    List<ProductCategory> categories = new ArrayList<>();

    public ContentFragmentPagerAdapter(FragmentManager fragmentManager, List<ProductCategory> categories) {
        super(fragmentManager);
        this.categories = categories;
    }

    @Override
    public Fragment getItem(int position) {
        return CategoryFragment.newInstance(categories.get(position));
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return categories.get(position).getTitle();
    }
}
