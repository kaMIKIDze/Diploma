package com.gromov.diploma.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.gromov.diploma.R;
import com.gromov.diploma.view.analysis.AnalysisFragment;
import com.gromov.diploma.view.category.CategoryFragment;
import com.gromov.diploma.view.info.InfoFragment;
import com.gromov.diploma.view.products.PurchaseFragment;
import com.gromov.diploma.view.setting.SettingFragment;


public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((android.support.v7.widget.Toolbar) findViewById(R.id.toolbar));
        drawerLayout = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displaySelectedFragment(new PurchaseFragment());


    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);

        } else super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_purchase:
                displaySelectedFragment(new PurchaseFragment());
                break;
            case R.id.nav_category:
                displaySelectedFragment(new CategoryFragment());
                break;
            case R.id.nav_analysis:
                displaySelectedFragment(new AnalysisFragment());
                break;
            case R.id.nav_info:
                displaySelectedFragment(new InfoFragment());
                break;
            case R.id.nav_setting:
                displaySelectedFragment(new SettingFragment());
                break;
            default:
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }

    private void displaySelectedFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame_nav, fragment)
                .commit();
    }
}
