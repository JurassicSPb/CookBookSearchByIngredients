package com.github.jurassicspb.cookbooksearchbyingredients;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.github.jurassicspb.cookbooksearchbyingredients.fragments.FishFragment;
import com.github.jurassicspb.cookbooksearchbyingredients.fragments.MeatFragment;
/**
 * Created by Мария on 12.11.2016.
 */

public class IngedientTablayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablayout_with_viewpager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        getSupportActionBar().setTitle("Список ингредиентов");

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new MeatFragment(), "Мясо");
        adapter.addFragment(new FishFragment(), "Рыба");

        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);

    }
}
