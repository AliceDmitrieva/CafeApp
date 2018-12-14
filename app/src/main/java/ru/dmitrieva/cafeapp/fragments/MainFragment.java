package ru.dmitrieva.cafeapp.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.dmitrieva.cafeapp.R;
import ru.dmitrieva.cafeapp.adapters.ContentFragmentPagerAdapter;
import ru.dmitrieva.cafeapp.data.ProductCategory;

public class MainFragment extends Fragment {

    private static final String ARGUMENT_PRODUCT_CATEGORY_LIST = "product category list";
    private static List<ProductCategory> productCategoryList;
    private ViewPager viewPager;

    public static MainFragment newInstance(List<ProductCategory> productCategoryList) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGUMENT_PRODUCT_CATEGORY_LIST, new ArrayList<>(productCategoryList));
        fragment.setArguments(args);
        return fragment;
    }

    public static void saveProductCategoryList(ArrayList list) {
        productCategoryList = list;
    }

    public static List<ProductCategory> getProductCategoryList() {
        return productCategoryList;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.main_fragment, null);
        viewPager = view.findViewById(R.id.viewpager);

        Bundle arguments = getArguments();
        if (arguments == null) {
            throw new IllegalArgumentException("arguments == null");
        }

        ArrayList list = arguments.getParcelableArrayList(ARGUMENT_PRODUCT_CATEGORY_LIST);
        if (!list.isEmpty()) {
            saveProductCategoryList(list);
        }
        ContentFragmentPagerAdapter contentFragmentPagerAdapter = new ContentFragmentPagerAdapter(getChildFragmentManager(), list);
        viewPager.setAdapter(contentFragmentPagerAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}
