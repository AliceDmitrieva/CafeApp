package ru.dmitrieva.cafeapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.stetho.Stetho;

import java.util.List;

import ru.dmitrieva.cafeapp.data.Product;
import ru.dmitrieva.cafeapp.data.ProductCategory;
import ru.dmitrieva.cafeapp.fragments.CategoryFragment;
import ru.dmitrieva.cafeapp.fragments.MainFragment;
import ru.dmitrieva.cafeapp.fragments.ProductDetailFragment;
import ru.dmitrieva.cafeapp.fragments.ProfileFragment;
import ru.dmitrieva.cafeapp.fragments.ShopCartFragment;
import ru.dmitrieva.cafeapp.utils.DatabaseHelper;

import static ru.dmitrieva.cafeapp.data.Product.addProductToBuyList;
import static ru.dmitrieva.cafeapp.data.Product.getBuyList;
import static ru.dmitrieva.cafeapp.data.Product.getTotalCount;

public class MainActivity extends AppCompatActivity implements ProductsRequestAsyncTask.OnProductsRequestListener, CategoryFragment.OnProductCardClickListener, ProductDetailFragment.OnButtonOrderClickListener, ShopCartFragment.OnChangeCountTextListener {

    private static final String FRAGMENT_TAG = "fragment tag";

    private BottomNavigationView bottomNavigationView;
    private View notificationBadge;
    private TextView notificationBadgeText;
    private DatabaseHelper databaseHelper;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
                MenuItem menuItem = bottomNavigationView.getMenu().getItem(i);
                boolean isChecked = menuItem.getItemId() == item.getItemId();
                menuItem.setChecked(isChecked);
            }

            switch (item.getItemId()) {
                case R.id.item_menu: {
                    MainFragment fragment = MainFragment.newInstance(MainFragment.getProductCategoryList());
                    changeFragment(fragment);
                    return true;
                }
                case R.id.item_profile: {
                    ProfileFragment fragment = ProfileFragment.newInstance();
                    changeFragment(fragment);
                    return true;
                }
                case R.id.item_basket: {
                    ShopCartFragment fragment = ShopCartFragment.newInstance(getBuyList());
                    changeFragment(fragment);
                    return true;
                }
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        Stetho.initializeWithDefaults(this);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        BottomNavigationItemView shopCartItemView = (BottomNavigationItemView) menuView.getChildAt(2);
        notificationBadge = LayoutInflater.from(this).inflate(R.layout.noification_badge, menuView, false);
        notificationBadgeText = notificationBadge.findViewById(R.id.notification_badge_text);
        shopCartItemView.addView(notificationBadge);
        notificationBadge.setVisibility(View.INVISIBLE);

        new ProductsRequestAsyncTask(this, MainActivity.this).execute();
    }

    private void refreshBadgeView() {
        int count = getTotalCount();
        if (count != 0) {
            notificationBadge.setVisibility(View.VISIBLE);
            notificationBadgeText.setText(String.valueOf(count));
        } else {
            notificationBadge.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onProductsRequestSuccess(List<ProductCategory> result) {
        databaseHelper.addData(result);
        changeFragment(MainFragment.newInstance(result));
    }

    @Override
    public void onProductsRequestError(Exception error) {
        List<ProductCategory> data = databaseHelper.getData();
        changeFragment(MainFragment.newInstance(data));
    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_layout, fragment, FRAGMENT_TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(FRAGMENT_TAG)
                .commit();

        fragmentManager.addOnBackStackChangedListener(() -> {
            Fragment currentFragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG);
            if (currentFragment instanceof ProductDetailFragment) {
                bottomNavigationView.setVisibility(View.GONE);
            } else {
                bottomNavigationView.setVisibility(View.VISIBLE);
                if (currentFragment instanceof MainFragment) {
                    bottomNavigationView.getMenu().getItem(0).setChecked(true);
                }
                if (currentFragment instanceof ProfileFragment) {
                    bottomNavigationView.getMenu().getItem(1).setChecked(true);
                }
                if (currentFragment instanceof ShopCartFragment) {
                    bottomNavigationView.getMenu().getItem(2).setChecked(true);
                }
            }
        });
    }

    @Override
    public void onProductCardClick(Product product) {
        changeFragment(ProductDetailFragment.newInstance(product));
    }

    @Override
    public void onButtonOrderFromCardClick(Product product) {
        addProductToBuyList(product, 1, product.getPrice());
        refreshBadgeView();
    }

    @Override
    public void onButtonOrderClick(Product product) {
        addProductToBuyList(product, product.getSelectedProductCount(), product.getSelectedProductCost());
        changeFragment(ShopCartFragment.newInstance(getBuyList()));
        refreshBadgeView();
    }

    @Override
    public void onChangeCountText() {
        refreshBadgeView();
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (currentFragment instanceof MainFragment) {
            finish();
        } else {
            super.onBackPressed();
        }
    }
}




