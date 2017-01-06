package com.github.jurassicspb.cookbooksearchbyingredients.nav_drawer_extras;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.jurassicspb.cookbooksearchbyingredients.R;

/**
 * Created by Мария on 06.01.2017.
 */

public class CookingTime extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cooking_time);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        getSupportActionBar().setTitle(R.string.cooking_time);

        createGrainsTitle();
    }
    public void createGrainsTitle(){
        int marginTitle = getResources().getDimensionPixelSize(R.dimen.margin_cooking_time_title);
        int marginBody = getResources().getDimensionPixelSize(R.dimen.margin_cooking_time_body);

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        TableRow row1 = new TableRow(this);
        row1.setBackgroundResource(R.color.tabDividerRed);
        TextView tv1 = new TextView(this);
        TableRow.LayoutParams tvParams1 = new TableRow.LayoutParams();
        tvParams1.height=getResources().getDimensionPixelSize(R.dimen.tv1_cooking_time_height);
        tvParams1.width= ViewGroup.LayoutParams.MATCH_PARENT;
        tvParams1.setMargins(marginTitle, marginTitle, marginTitle, marginTitle);
        tvParams1.span=4;
        tv1.setBackgroundResource(R.color.tabBackgroundWhite);
        tv1.setText(R.string.cereal_title);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor(getResources().getColor(R.color.tabDividerRed));
        tv1.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_title));
        tv1.setTypeface(Typeface.SANS_SERIF);
        row1.addView(tv1, tvParams1);
        tableLayout.addView(row1, 0);

        TableRow row2 = new TableRow(this);
        row2.setBackgroundResource(R.color.tabDividerRed);
        TextView tv2 = new TextView(this);
        TableRow.LayoutParams tvParams2 = new TableRow.LayoutParams();
        tvParams2.height=getResources().getDimensionPixelSize(R.dimen.tv2_cooking_time_height);
        tvParams2.width= getResources().getDimensionPixelSize(R.dimen.tv2_cooking_time_width);
        tvParams2.setMargins(marginBody, 0, marginBody, marginBody);
        tv2.setBackgroundResource(R.color.tabBackgroundWhite);
        tv2.setText(R.string.cereal_title);
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextColor(getResources().getColor(R.color.tabDividerRed));
        tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body));
        tv2.setTypeface(Typeface.SANS_SERIF);
        row2.addView(tv2, tvParams2);
        tableLayout.addView(row2, 1);

    }
}
