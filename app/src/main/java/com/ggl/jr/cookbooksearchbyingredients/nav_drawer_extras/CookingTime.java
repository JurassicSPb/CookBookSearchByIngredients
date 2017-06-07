package com.ggl.jr.cookbooksearchbyingredients.nav_drawer_extras;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ggl.jr.cookbooksearchbyingredients.Metrics;
import com.ggl.jr.cookbooksearchbyingredients.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by Мария on 06.01.2017.
 */

public class CookingTime extends AppCompatActivity {

    private String[][] porridgeRUS = new String[][]{
            {""},
            {""},
            {"Гречневая", "1 стакан", "2 стакана", "15-20 мин."},
            {"Манная", "1-2 ст. ложки", "1 стакан", "4 мин."},
            {"Овсяные хлопья", "1,5 стакана", "1 литр", "4 мин."},
            {"Перловая", "1 стакан", "3 стакана", "60 мин."},
            {"Пшеная", "1 стакан", "3 стакана", "45 мин."},
            {"Пшеничн.", "1 стакан", "3 стакана", "50 мин."},
            {"Рисовая", "1 стакан", "1,5-2 стакана", "15-20 мин."},
    };
//    private String[][] porridgeENG = new String[][]{
//            {""},
//            {""},
//            {"Boiled buckwheat", "1 glass", "2 glasses", "15-20 min."},
//            {"Boiled pearl barley", "1 glass", "3 glasses", "60 min."},
//            {"Cooked semolina", "1-2 tablesp.", "1 glass", "4 min."},
//            {"Millet porridge", "1 glass", "3 glasses", "45 min."},
//            {"Oatmeal", "1,5 glasses", "1 liter", "4 min."},
//            {"Rice porridge", "1 glass", "1,5-2 glasses", "15-20 min."},
//            {"Wheat porridge", "1 glass", "3 glasses", "50 min."},
//    };
    private String[][]meatRUS = new String[][]{
            {""},
            {""},
            {"Баранина", "1,5-2 ч.", "15-20 мин.", "1,5-2 ч."},
            {"Говядина", "2-2,5 ч.", "20 мин.", "1-1,5 ч."},
            {"Котлеты", "", "15-25 мин.", ""},
            {"Кролик", "1-1,5 ч.", "25-35 мин.", "1-1,5 ч."},
            {"Свинина", "1,5-2 ч.", "25 мин.", "1-1,5 ч."},
    };
//    private String[][]meatENG = new String[][]{
//            {""},
//            {""},
//            {"Beef", "2-2,5 h.", "20 min.", "1-1,5 h."},
//            {"Cutlet", "", "15-25 min.", ""},
//            {"Mutton", "1,5-2 h.", "15-20 min.", "1,5-2 h."},
//            {"Pork", "1,5-2 h.", "25 min.", "1-1,5 h."},
//            {"Rabbit", "1-1,5 h.", "25-35 min.", "1-1,5 h."},
//    };
    private String[][] seafoodRUS = new String[][]{
            {""},
            {""},
            {"Каль- мары", "3-4 мин.", "", ""},
            {"Креветки", "3-5 мин.", "", ""},
            {"Раки", "10-15 мин.", "", ""},
            {"Рыба кусками 150-200 г.", "12-15 мин.", "10-20 мин.", "10-20 мин."},
            {"Рыба целиком (крупная)", "1-2,5 ч.", "", ""},
            {"Рыбные котлеты", "", "10-15 мин.", ""},
    };
//    private String[][] seafoodENG = new String[][]{
//            {""},
//            {""},
//            {"Crayfish", "10-15 min.", "", ""},
//            {"Fish cutlets", "", "10-15 min.", ""},
//            {"Shrimp", "3-5 min.", "", ""},
//            {"Squid", "3-4 min.", "", ""},
//            {"Fish pieces 150-200 g.", "12-15 min.", "10-20 min.", "10-20 min."},
//            {"Whole fish (large)", "1-2,5 h.", "", ""},
//    };
    private String [][] birdRUS = new String[][]{
            {""},
            {""},
            {"Гусь", "2,5-3 ч.", "1,5-2 ч. в духовке", "1-1,5 ч."},
            {"Индейка", "1-1,5 ч.", "25 мин.", "1 ч."},
            {"Курица", "1 ч.", "20 мин.", "40-60 мин."},
            {"Куриные котлеты", "", "14-20 мин.", ""},
            {"Утка", "1-1,5 ч.", "35 мин.", "1,5 ч."},

    };
//    private String [][]birdENG = new String[][]{
//            {""},
//            {""},
//            {"Goose", "2,5-3 h.", "1,5-2 h. in oven", "1-1,5 h."},
//            {"Chicken", "1 h.", "20 min.", "40-60 min."},
//            {"Chicken cutlets", "", "14-20 min.", ""},
//            {"Turkey", "1-1,5 h.", "25 min.", "1 h."},
//            {"Duck", "1-1,5 h.", "35 min.", "1,5 h."},
//    };
    private String[][]vegetablesRUS = new String[][]{
            {""},
            {""},
            {"Кабачки", "20 мин.", "4-6 мин.", "25 мин."},
            {"Капуста", "15-25 мин.", "", "20-30 мин."},
            {"Картоф.", "20-25 мин.", "20-30 мин.", "50 мин."},
            {"Лук", "5 мин.", "5-7 мин.", ""},
            {"Морковь", "20-30 мин.", "10 мин.", "20 мин."},
            {"Свекла", "45-60 мин.", "", ""},
    };
//    private String[][]vegetablesENG = new String[][]{
//            {""},
//            {""},
//            {"Beet", "45-60 мин.", "", ""},
//            {"Cabbage", "15-25 min.", "", "20-30 min."},
//            {"Carrot", "20-30 min.", "10 min.", "20 min."},
//            {"Onion", "5 min.", "5-7 min.", ""},
//            {"Potato", "20-25 min.", "20-30 min.", "50 min."},
//            {"Zucchini", "20 min.", "5-10 min.", "25 min."},
//    };
    private String[][]otherRUS = new String[][]{
            {""},
            {""},
            {"Грибы", "35-40 мин.", "", ""},
            {"Макароны", "7-10 мин.", "", ""},
            {"Яйца всмятку", "3-3,5 мин.", "", ""},
            {"Яйца \"в мешочек\"", "4-4,5 мин.", "", ""},
            {"Яйца вкрутую", "7-10 мин.", "", ""},
    };
//    private String[][]otherENG = new String[][]{
//            {""},
//            {""},
//            {"Mushrooms", "35-40 min.", "", ""},
//            {"Pasta", "7-10 min.", "", ""},
//            {"Soft-boiled eggs", "3-3,5 min.", "", ""},
//            {"Medium-timed eggs", "4-4,5 min.", "", ""},
//            {"Hard-boiled eggs", "7-10 min.", "", ""},
//    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.cooking_time);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (Metrics.smallestWidth()>600) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_tablets);
        } else {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_phones);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        getSupportActionBar().setTitle(R.string.cooking_time);

        createPorridgeTitle();
        createPorridgeBody();
        createMeatTitle();
        createMeatBody();
        createFishTitle();
        createFishBody();
        createBirdTitle();
        createBirdBody();
        createVegetablesTitle();
        createVegetablesBody();
        createOtherTitle();
        createOtherBody();

        AdView mAdView = (AdView) findViewById(R.id.adFragment);
        AdRequest adRequest = new AdRequest.Builder()
//                 s3
                .addTestDevice("67F276A8D2BC2AF79DDA7E1FD3FCC12D")
//                 tablet
//                .addTestDevice("BCCA97?C08B759F6F304C2665B7233097")
//                 a5
//                .addTestDevice("E0FC7B9C15DCFF71E2D006CAB7808184")
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
    }

    public void createPorridgeTitle() {
        int marginTitle = getResources().getDimensionPixelSize(R.dimen.margin_cooking_time_title);
        int marginBody = getResources().getDimensionPixelSize(R.dimen.margin_cooking_time_body);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayoutPorridge);

        TableRow row1 = new TableRow(this);
        row1.setBackgroundResource(R.color.tabDividerRed);
        TextView tv1 = new TextView(this);
        TableRow.LayoutParams tvParams1 = new TableRow.LayoutParams();
        tvParams1.height = getResources().getDimensionPixelSize(R.dimen.tv1_cooking_time_height);
        tvParams1.width = ViewGroup.LayoutParams.MATCH_PARENT;
        tvParams1.setMargins(marginTitle, marginTitle, marginTitle, marginTitle);
        tvParams1.span = 4;
        tv1.setBackgroundResource(R.color.tabTitlePorridge);
        tv1.setText(R.string.porridge_title);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
        tv1.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_title));
        tv1.setTypeface(typeface);
        row1.addView(tv1, tvParams1);
        tableLayout.addView(row1, 0);

        TableRow row2 = new TableRow(this);
        row2.setBackgroundResource(R.color.tabDividerRed);

        TextView tv2 = new TextView(this);
        TableRow.LayoutParams tvParams2 = new TableRow.LayoutParams();
        tvParams2.height = getResources().getDimensionPixelSize(R.dimen.tv2_cooking_time_height);
        tvParams2.width = getResources().getDimensionPixelSize(R.dimen.tv2_cooking_time_width);
        tvParams2.setMargins(marginBody, 0, marginBody, marginBody);
        tv2.setBackgroundResource(R.color.tabSecondRowPorridge);
        tv2.setGravity(Gravity.CENTER);
        row2.addView(tv2, tvParams2);

        TextView tv3 = new TextView(this);
        TableRow.LayoutParams tvParams3 = new TableRow.LayoutParams();
        tvParams3.height = ViewGroup.LayoutParams.MATCH_PARENT;
        tvParams3.width = getResources().getDimensionPixelSize(R.dimen.tv2_cooking_time_width);
        tvParams3.setMargins(0, 0, marginBody, marginBody);
        tv3.setBackgroundResource(R.color.tabSecondRowPorridge);
        tv3.setText(R.string.grain);
        tv3.setGravity(Gravity.CENTER);
        tv3.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
        tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body));
        tv3.setTypeface(Typeface.SANS_SERIF, 1);
        row2.addView(tv3, tvParams3);

        TextView tv4 = new TextView(this);
        tv4.setBackgroundResource(R.color.tabSecondRowPorridge);
        tv4.setText(R.string.liquid);
        tv4.setGravity(Gravity.CENTER);
        tv4.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
        tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body));
        tv4.setTypeface(Typeface.SANS_SERIF, 1);
        row2.addView(tv4, tvParams3);

        TextView tv5 = new TextView(this);
        tv5.setBackgroundResource(R.color.tabSecondRowPorridge);
        tv5.setText(R.string.time_to_boil);
        tv5.setGravity(Gravity.CENTER);
        tv5.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
        tv5.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body));
        tv5.setTypeface(Typeface.SANS_SERIF, 1);
        row2.addView(tv5, tvParams3);

        tableLayout.addView(row2, 1);
    }

    public void createPorridgeBody() {
        int marginBody = getResources().getDimensionPixelSize(R.dimen.margin_cooking_time_body);

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayoutPorridge);

        for (int i = 2; i < porridgeRUS.length; i++) {
            TableRow row3 = new TableRow(this);
            row3.setBackgroundResource(R.color.tabDividerRed);

            TextView tv2 = new TextView(this);
            TableRow.LayoutParams tvParams2 = new TableRow.LayoutParams();
            tvParams2.height = getResources().getDimensionPixelSize(R.dimen.tv2_cooking_time_height);
            tvParams2.width = getResources().getDimensionPixelSize(R.dimen.tv2_cooking_time_width);
            tvParams2.setMargins(marginBody, 0, marginBody, marginBody);
            tv2.setBackgroundResource(R.color.tabBackgroundWhite);
//            if (Locale.getDefault().getLanguage().equals("ru")) {
                tv2.setText(porridgeRUS[i][0]);
//            } else {
//                tv2.setText(porridgeENG[i][0]);
//            }
            tv2.setGravity(Gravity.CENTER);
            tv2.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
            tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body_small));
            tv2.setTypeface(Typeface.SANS_SERIF, 1);
            row3.addView(tv2, tvParams2);

            TextView tv3 = new TextView(this);
            TableRow.LayoutParams tvParams3 = new TableRow.LayoutParams();
            tvParams3.height = ViewGroup.LayoutParams.MATCH_PARENT;
            tvParams3.width = getResources().getDimensionPixelSize(R.dimen.tv2_cooking_time_width);
            tvParams3.setMargins(0, 0, marginBody, marginBody);
            tv3.setBackgroundResource(R.color.tabBackgroundWhite);
//            if (Locale.getDefault().getLanguage().equals("ru")) {
                tv3.setText(porridgeRUS[i][1]);
//            } else {
//                tv3.setText(porridgeENG[i][1]);
//            }
            tv3.setGravity(Gravity.CENTER);
            tv3.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
            tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body_small));
            tv3.setTypeface(Typeface.SANS_SERIF, 1);
            row3.addView(tv3, tvParams3);

            TextView tv4 = new TextView(this);
            tv4.setBackgroundResource(R.color.tabBackgroundWhite);
//            if (Locale.getDefault().getLanguage().equals("ru")) {
                tv4.setText(porridgeRUS[i][2]);
//            } else {
//                tv4.setText(porridgeENG[i][2]);
//            }
            tv4.setGravity(Gravity.CENTER);
            tv4.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
            tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body_small));
            tv4.setTypeface(Typeface.SANS_SERIF, 1);
            row3.addView(tv4, tvParams3);

            TextView tv5 = new TextView(this);
            tv5.setBackgroundResource(R.color.tabBackgroundWhite);
//            if (Locale.getDefault().getLanguage().equals("ru")) {
                tv5.setText(porridgeRUS[i][3]);
//            } else {
//                tv5.setText(porridgeENG[i][3]);
//            }
            tv5.setGravity(Gravity.CENTER);
            tv5.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
            tv5.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body_small));
            tv5.setTypeface(Typeface.SANS_SERIF, 1);
            row3.addView(tv5, tvParams3);

            tableLayout.addView(row3, i);
        }
    }

    public void createMeatTitle() {
        int marginTitle = getResources().getDimensionPixelSize(R.dimen.margin_cooking_time_title);
        int marginBody = getResources().getDimensionPixelSize(R.dimen.margin_cooking_time_body);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayoutMeat);

        TableRow row1 = new TableRow(this);
        row1.setBackgroundResource(R.color.tabDividerRed);
        TextView tv1 = new TextView(this);
        TableRow.LayoutParams tvParams1 = new TableRow.LayoutParams();
        tvParams1.height = getResources().getDimensionPixelSize(R.dimen.tv1_cooking_time_height);
        tvParams1.width = ViewGroup.LayoutParams.MATCH_PARENT;
        tvParams1.setMargins(marginTitle, marginTitle, marginTitle, marginTitle);
        tvParams1.span = 4;
        tv1.setBackgroundResource(R.color.tabTitlePorridge);
        tv1.setText(R.string.meat);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
        tv1.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_title));
        tv1.setTypeface(typeface);
        row1.addView(tv1, tvParams1);
        tableLayout.addView(row1, 0);

        TableRow row2 = new TableRow(this);
        row2.setBackgroundResource(R.color.tabDividerRed);

        TextView tv2 = new TextView(this);
        TableRow.LayoutParams tvParams2 = new TableRow.LayoutParams();
        tvParams2.height = getResources().getDimensionPixelSize(R.dimen.tv3_cooking_time_height);
        tvParams2.width = getResources().getDimensionPixelSize(R.dimen.tv3_cooking_time_width);
        tvParams2.setMargins(marginBody, 0, marginBody, marginBody);
        tv2.setBackgroundResource(R.color.tabSecondRowPorridge);
        tv2.setGravity(Gravity.CENTER);
        row2.addView(tv2, tvParams2);

        TextView tv3 = new TextView(this);
        TableRow.LayoutParams tvParams3 = new TableRow.LayoutParams();
        tvParams3.height = ViewGroup.LayoutParams.MATCH_PARENT;
        tvParams3.width = getResources().getDimensionPixelSize(R.dimen.tv3_cooking_time_width);
        tvParams3.setMargins(0, 0, marginBody, marginBody);
        tv3.setBackgroundResource(R.color.tabSecondRowPorridge);
        tv3.setText(R.string. boiling);
        tv3.setGravity(Gravity.CENTER);
        tv3.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
        tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body));
        tv3.setTypeface(Typeface.SANS_SERIF, 1);
        row2.addView(tv3, tvParams3);

        TextView tv4 = new TextView(this);
        tv4.setBackgroundResource(R.color.tabSecondRowPorridge);
        tv4.setText(R.string.fry_up);
        tv4.setGravity(Gravity.CENTER);
        tv4.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
        tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body));
        tv4.setTypeface(Typeface.SANS_SERIF, 1);
        row2.addView(tv4, tvParams3);

        TextView tv5 = new TextView(this);
        tv5.setBackgroundResource(R.color.tabSecondRowPorridge);
        tv5.setText(R.string.stewing);
        tv5.setGravity(Gravity.CENTER);
        tv5.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
        tv5.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body));
        tv5.setTypeface(Typeface.SANS_SERIF, 1);
        row2.addView(tv5, tvParams3);

        tableLayout.addView(row2, 1);
    }

    public void createMeatBody() {
        int marginBody = getResources().getDimensionPixelSize(R.dimen.margin_cooking_time_body);

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayoutMeat);

        for (int i = 2; i < meatRUS.length; i++) {
            TableRow row3 = new TableRow(this);
            row3.setBackgroundResource(R.color.tabDividerRed);

            TextView tv2 = new TextView(this);
            TableRow.LayoutParams tvParams2 = new TableRow.LayoutParams();
            tvParams2.height = getResources().getDimensionPixelSize(R.dimen.tv3_cooking_time_height);
            tvParams2.width = getResources().getDimensionPixelSize(R.dimen.tv3_cooking_time_width);
            tvParams2.setMargins(marginBody, 0, marginBody, marginBody);
            tv2.setBackgroundResource(R.color.tabBackgroundWhite);
//            if (Locale.getDefault().getLanguage().equals("ru")) {
                tv2.setText(meatRUS[i][0]);
//            } else {
//                tv2.setText(meatENG[i][0]);
//            }
            tv2.setGravity(Gravity.CENTER);
            tv2.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
            tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body_small));
            tv2.setTypeface(Typeface.SANS_SERIF, 1);
            row3.addView(tv2, tvParams2);

            TextView tv3 = new TextView(this);
            TableRow.LayoutParams tvParams3 = new TableRow.LayoutParams();
            tvParams3.height = ViewGroup.LayoutParams.MATCH_PARENT;
            tvParams3.width = getResources().getDimensionPixelSize(R.dimen.tv3_cooking_time_height);
            tvParams3.setMargins(0, 0, marginBody, marginBody);
            tv3.setBackgroundResource(R.color.tabBackgroundWhite);
//            if (Locale.getDefault().getLanguage().equals("ru")) {
                tv3.setText(meatRUS[i][1]);
//            } else {
//                tv3.setText(meatENG[i][1]);
//            }
            tv3.setGravity(Gravity.CENTER);
            tv3.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
            tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body_small));
            tv3.setTypeface(Typeface.SANS_SERIF, 1);
            row3.addView(tv3, tvParams3);

            TextView tv4 = new TextView(this);
            tv4.setBackgroundResource(R.color.tabBackgroundWhite);
//            if (Locale.getDefault().getLanguage().equals("ru")) {
                tv4.setText(meatRUS[i][2]);
//            } else {
//                tv4.setText(meatENG[i][2]);
//            }
            tv4.setGravity(Gravity.CENTER);
            tv4.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
            tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body_small));
            tv4.setTypeface(Typeface.SANS_SERIF, 1);
            row3.addView(tv4, tvParams3);

            TextView tv5 = new TextView(this);
            tv5.setBackgroundResource(R.color.tabBackgroundWhite);
//            if (Locale.getDefault().getLanguage().equals("ru")) {
                tv5.setText(meatRUS[i][3]);
//            } else {
//                tv5.setText(meatENG[i][3]);
//            }
            tv5.setGravity(Gravity.CENTER);
            tv5.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
            tv5.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body_small));
            tv5.setTypeface(Typeface.SANS_SERIF, 1);
            row3.addView(tv5, tvParams3);

            tableLayout.addView(row3, i);
        }
    }
    public void createFishTitle() {
        int marginTitle = getResources().getDimensionPixelSize(R.dimen.margin_cooking_time_title);
        int marginBody = getResources().getDimensionPixelSize(R.dimen.margin_cooking_time_body);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayoutFish);

        TableRow row1 = new TableRow(this);
        row1.setBackgroundResource(R.color.tabDividerRed);
        TextView tv1 = new TextView(this);
        TableRow.LayoutParams tvParams1 = new TableRow.LayoutParams();
        tvParams1.height = getResources().getDimensionPixelSize(R.dimen.tv1_cooking_time_height);
        tvParams1.width = ViewGroup.LayoutParams.MATCH_PARENT;
        tvParams1.setMargins(marginTitle, marginTitle, marginTitle, marginTitle);
        tvParams1.span = 4;
        tv1.setBackgroundResource(R.color.tabTitlePorridge);
        tv1.setText(R.string.seafood);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
        tv1.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_title));
        tv1.setTypeface(typeface);
        row1.addView(tv1, tvParams1);
        tableLayout.addView(row1, 0);

        TableRow row2 = new TableRow(this);
        row2.setBackgroundResource(R.color.tabDividerRed);

        TextView tv2 = new TextView(this);
        TableRow.LayoutParams tvParams2 = new TableRow.LayoutParams();
        tvParams2.height = getResources().getDimensionPixelSize(R.dimen.tv3_cooking_time_height);
        tvParams2.width = getResources().getDimensionPixelSize(R.dimen.tv3_cooking_time_width);
        tvParams2.setMargins(marginBody, 0, marginBody, marginBody);
        tv2.setBackgroundResource(R.color.tabSecondRowPorridge);
        tv2.setGravity(Gravity.CENTER);
        row2.addView(tv2, tvParams2);

        TextView tv3 = new TextView(this);
        TableRow.LayoutParams tvParams3 = new TableRow.LayoutParams();
        tvParams3.height = ViewGroup.LayoutParams.MATCH_PARENT;
        tvParams3.width = getResources().getDimensionPixelSize(R.dimen.tv3_cooking_time_width);
        tvParams3.setMargins(0, 0, marginBody, marginBody);
        tv3.setBackgroundResource(R.color.tabSecondRowPorridge);
        tv3.setText(R.string.boiling);
        tv3.setGravity(Gravity.CENTER);
        tv3.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
        tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body));
        tv3.setTypeface(Typeface.SANS_SERIF, 1);
        row2.addView(tv3, tvParams3);

        TextView tv4 = new TextView(this);
        tv4.setBackgroundResource(R.color.tabSecondRowPorridge);
        tv4.setText(R.string.fry_up);
        tv4.setGravity(Gravity.CENTER);
        tv4.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
        tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body));
        tv4.setTypeface(Typeface.SANS_SERIF, 1);
        row2.addView(tv4, tvParams3);

        TextView tv5 = new TextView(this);
        tv5.setBackgroundResource(R.color.tabSecondRowPorridge);
        tv5.setText(R.string.stewing);
        tv5.setGravity(Gravity.CENTER);
        tv5.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
        tv5.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body));
        tv5.setTypeface(Typeface.SANS_SERIF, 1);
        row2.addView(tv5, tvParams3);

        tableLayout.addView(row2, 1);
    }
    public void createFishBody() {
        int marginBody = getResources().getDimensionPixelSize(R.dimen.margin_cooking_time_body);

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayoutFish);

        for (int i = 2; i < seafoodRUS.length; i++) {
            TableRow row3 = new TableRow(this);
            row3.setBackgroundResource(R.color.tabDividerRed);

            TextView tv2 = new TextView(this);
            TableRow.LayoutParams tvParams2 = new TableRow.LayoutParams();
            tvParams2.height = getResources().getDimensionPixelSize(R.dimen.fish_cooking_time_height);
            tvParams2.width = getResources().getDimensionPixelSize(R.dimen.tv3_cooking_time_width);
            tvParams2.setMargins(marginBody, 0, marginBody, marginBody);
            tv2.setBackgroundResource(R.color.tabBackgroundWhite);
//            if (Locale.getDefault().getLanguage().equals("ru")) {
                tv2.setText(seafoodRUS[i][0]);
//            } else {
//                tv2.setText(seafoodENG[i][0]);
//            }
            tv2.setGravity(Gravity.CENTER);
            tv2.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
            tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body_small));
            tv2.setTypeface(Typeface.SANS_SERIF, 1);
            row3.addView(tv2, tvParams2);

            TextView tv3 = new TextView(this);
            TableRow.LayoutParams tvParams3 = new TableRow.LayoutParams();
            tvParams3.height = ViewGroup.LayoutParams.MATCH_PARENT;
            tvParams3.width = getResources().getDimensionPixelSize(R.dimen.tv3_cooking_time_height);
            tvParams3.setMargins(0, 0, marginBody, marginBody);
            tv3.setBackgroundResource(R.color.tabBackgroundWhite);
//            if (Locale.getDefault().getLanguage().equals("ru")) {
                tv3.setText(seafoodRUS[i][1]);
//            } else {
//                tv3.setText(seafoodENG[i][1]);
//            }
            tv3.setGravity(Gravity.CENTER);
            tv3.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
            tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body_small));
            tv3.setTypeface(Typeface.SANS_SERIF, 1);
            row3.addView(tv3, tvParams3);

            TextView tv4 = new TextView(this);
            tv4.setBackgroundResource(R.color.tabBackgroundWhite);
//            if (Locale.getDefault().getLanguage().equals("ru")) {
                tv4.setText(seafoodRUS[i][2]);
//            } else {
//                tv4.setText(seafoodENG[i][2]);
//            }
            tv4.setGravity(Gravity.CENTER);
            tv4.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
            tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body_small));
            tv4.setTypeface(Typeface.SANS_SERIF, 1);
            row3.addView(tv4, tvParams3);

            TextView tv5 = new TextView(this);
            tv5.setBackgroundResource(R.color.tabBackgroundWhite);
//            if (Locale.getDefault().getLanguage().equals("ru")) {
                tv5.setText(seafoodRUS[i][3]);
//            } else {
//                tv5.setText(seafoodENG[i][3]);
//            }
            tv5.setGravity(Gravity.CENTER);
            tv5.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
            tv5.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body_small));
            tv5.setTypeface(Typeface.SANS_SERIF, 1);
            row3.addView(tv5, tvParams3);

            tableLayout.addView(row3, i);
        }
    }
    public void createBirdTitle() {
        int marginTitle = getResources().getDimensionPixelSize(R.dimen.margin_cooking_time_title);
        int marginBody = getResources().getDimensionPixelSize(R.dimen.margin_cooking_time_body);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayoutBird);

        TableRow row1 = new TableRow(this);
        row1.setBackgroundResource(R.color.tabDividerRed);
        TextView tv1 = new TextView(this);
        TableRow.LayoutParams tvParams1 = new TableRow.LayoutParams();
        tvParams1.height = getResources().getDimensionPixelSize(R.dimen.tv1_cooking_time_height);
        tvParams1.width = ViewGroup.LayoutParams.MATCH_PARENT;
        tvParams1.setMargins(marginTitle, marginTitle, marginTitle, marginTitle);
        tvParams1.span = 4;
        tv1.setBackgroundResource(R.color.tabTitlePorridge);
        tv1.setText(R.string.bird);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
        tv1.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_title));
        tv1.setTypeface(typeface);
        row1.addView(tv1, tvParams1);
        tableLayout.addView(row1, 0);

        TableRow row2 = new TableRow(this);
        row2.setBackgroundResource(R.color.tabDividerRed);

        TextView tv2 = new TextView(this);
        TableRow.LayoutParams tvParams2 = new TableRow.LayoutParams();
        tvParams2.height = getResources().getDimensionPixelSize(R.dimen.tv3_cooking_time_height);
        tvParams2.width = getResources().getDimensionPixelSize(R.dimen.tv3_cooking_time_width);
        tvParams2.setMargins(marginBody, 0, marginBody, marginBody);
        tv2.setBackgroundResource(R.color.tabSecondRowPorridge);
        tv2.setGravity(Gravity.CENTER);
        row2.addView(tv2, tvParams2);

        TextView tv3 = new TextView(this);
        TableRow.LayoutParams tvParams3 = new TableRow.LayoutParams();
        tvParams3.height = ViewGroup.LayoutParams.MATCH_PARENT;
        tvParams3.width = getResources().getDimensionPixelSize(R.dimen.tv3_cooking_time_width);
        tvParams3.setMargins(0, 0, marginBody, marginBody);
        tv3.setBackgroundResource(R.color.tabSecondRowPorridge);
        tv3.setText(R.string.boiling);
        tv3.setGravity(Gravity.CENTER);
        tv3.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
        tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body));
        tv3.setTypeface(Typeface.SANS_SERIF, 1);
        row2.addView(tv3, tvParams3);

        TextView tv4 = new TextView(this);
        tv4.setBackgroundResource(R.color.tabSecondRowPorridge);
        tv4.setText(R.string.fry_up);
        tv4.setGravity(Gravity.CENTER);
        tv4.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
        tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body));
        tv4.setTypeface(Typeface.SANS_SERIF, 1);
        row2.addView(tv4, tvParams3);

        TextView tv5 = new TextView(this);
        tv5.setBackgroundResource(R.color.tabSecondRowPorridge);
        tv5.setText(R.string.stewing);
        tv5.setGravity(Gravity.CENTER);
        tv5.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
        tv5.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body));
        tv5.setTypeface(Typeface.SANS_SERIF, 1);
        row2.addView(tv5, tvParams3);

        tableLayout.addView(row2, 1);
    }
    public void createBirdBody() {
        int marginBody = getResources().getDimensionPixelSize(R.dimen.margin_cooking_time_body);

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayoutBird);

        for (int i = 2; i < birdRUS.length; i++) {
            TableRow row3 = new TableRow(this);
            row3.setBackgroundResource(R.color.tabDividerRed);

            TextView tv2 = new TextView(this);
            TableRow.LayoutParams tvParams2 = new TableRow.LayoutParams();
            tvParams2.height = getResources().getDimensionPixelSize(R.dimen.fish_cooking_time_height);
            tvParams2.width = getResources().getDimensionPixelSize(R.dimen.tv3_cooking_time_width);
            tvParams2.setMargins(marginBody, 0, marginBody, marginBody);
            tv2.setBackgroundResource(R.color.tabBackgroundWhite);
//            if (Locale.getDefault().getLanguage().equals("ru")) {
                tv2.setText(birdRUS[i][0]);
//            } else {
//                tv2.setText(birdENG[i][0]);
//            }
            tv2.setGravity(Gravity.CENTER);
            tv2.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
            tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body_small));
            tv2.setTypeface(Typeface.SANS_SERIF, 1);
            row3.addView(tv2, tvParams2);

            TextView tv3 = new TextView(this);
            TableRow.LayoutParams tvParams3 = new TableRow.LayoutParams();
            tvParams3.height = ViewGroup.LayoutParams.MATCH_PARENT;
            tvParams3.width = getResources().getDimensionPixelSize(R.dimen.tv3_cooking_time_height);
            tvParams3.setMargins(0, 0, marginBody, marginBody);
            tv3.setBackgroundResource(R.color.tabBackgroundWhite);
//            if (Locale.getDefault().getLanguage().equals("ru")) {
                tv3.setText(birdRUS[i][1]);
//            } else {
//                tv3.setText(birdENG[i][1]);
//            }
            tv3.setGravity(Gravity.CENTER);
            tv3.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
            tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body_small));
            tv3.setTypeface(Typeface.SANS_SERIF, 1);
            row3.addView(tv3, tvParams3);

            TextView tv4 = new TextView(this);
            tv4.setBackgroundResource(R.color.tabBackgroundWhite);
//            if (Locale.getDefault().getLanguage().equals("ru")) {
                tv4.setText(birdRUS[i][2]);
//            } else {
//                tv4.setText(birdENG[i][2]);
//            }
            tv4.setGravity(Gravity.CENTER);
            tv4.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
            tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body_small));
            tv4.setTypeface(Typeface.SANS_SERIF, 1);
            row3.addView(tv4, tvParams3);

            TextView tv5 = new TextView(this);
            tv5.setBackgroundResource(R.color.tabBackgroundWhite);
//            if (Locale.getDefault().getLanguage().equals("ru")) {
                tv5.setText(birdRUS[i][3]);
//            } else {
//                tv5.setText(birdENG[i][3]);
//            }
            tv5.setGravity(Gravity.CENTER);
            tv5.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
            tv5.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body_small));
            tv5.setTypeface(Typeface.SANS_SERIF, 1);
            row3.addView(tv5, tvParams3);

            tableLayout.addView(row3, i);
        }
    }
    public void createVegetablesTitle() {
        int marginTitle = getResources().getDimensionPixelSize(R.dimen.margin_cooking_time_title);
        int marginBody = getResources().getDimensionPixelSize(R.dimen.margin_cooking_time_body);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayoutVegetables);

        TableRow row1 = new TableRow(this);
        row1.setBackgroundResource(R.color.tabDividerRed);
        TextView tv1 = new TextView(this);
        TableRow.LayoutParams tvParams1 = new TableRow.LayoutParams();
        tvParams1.height = getResources().getDimensionPixelSize(R.dimen.tv1_cooking_time_height);
        tvParams1.width = ViewGroup.LayoutParams.MATCH_PARENT;
        tvParams1.setMargins(marginTitle, marginTitle, marginTitle, marginTitle);
        tvParams1.span = 4;
        tv1.setBackgroundResource(R.color.tabTitlePorridge);
        tv1.setText(R.string.vegetables);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
        tv1.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_title));
        tv1.setTypeface(typeface);
        row1.addView(tv1, tvParams1);
        tableLayout.addView(row1, 0);

        TableRow row2 = new TableRow(this);
        row2.setBackgroundResource(R.color.tabDividerRed);

        TextView tv2 = new TextView(this);
        TableRow.LayoutParams tvParams2 = new TableRow.LayoutParams();
        tvParams2.height = getResources().getDimensionPixelSize(R.dimen.tv3_cooking_time_height);
        tvParams2.width = getResources().getDimensionPixelSize(R.dimen.tv3_cooking_time_width);
        tvParams2.setMargins(marginBody, 0, marginBody, marginBody);
        tv2.setBackgroundResource(R.color.tabSecondRowPorridge);
        tv2.setGravity(Gravity.CENTER);
        row2.addView(tv2, tvParams2);

        TextView tv3 = new TextView(this);
        TableRow.LayoutParams tvParams3 = new TableRow.LayoutParams();
        tvParams3.height = ViewGroup.LayoutParams.MATCH_PARENT;
        tvParams3.width = getResources().getDimensionPixelSize(R.dimen.tv3_cooking_time_width);
        tvParams3.setMargins(0, 0, marginBody, marginBody);
        tv3.setBackgroundResource(R.color.tabSecondRowPorridge);
        tv3.setText(R.string.boiling);
        tv3.setGravity(Gravity.CENTER);
        tv3.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
        tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body));
        tv3.setTypeface(Typeface.SANS_SERIF, 1);
        row2.addView(tv3, tvParams3);

        TextView tv4 = new TextView(this);
        tv4.setBackgroundResource(R.color.tabSecondRowPorridge);
        tv4.setText(R.string.fry_up);
        tv4.setGravity(Gravity.CENTER);
        tv4.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
        tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body));
        tv4.setTypeface(Typeface.SANS_SERIF, 1);
        row2.addView(tv4, tvParams3);

        TextView tv5 = new TextView(this);
        tv5.setBackgroundResource(R.color.tabSecondRowPorridge);
        tv5.setText(R.string.stewing);
        tv5.setGravity(Gravity.CENTER);
        tv5.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
        tv5.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body));
        tv5.setTypeface(Typeface.SANS_SERIF, 1);
        row2.addView(tv5, tvParams3);

        tableLayout.addView(row2, 1);
    }
    public void createVegetablesBody() {
        int marginBody = getResources().getDimensionPixelSize(R.dimen.margin_cooking_time_body);

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayoutVegetables);

        for (int i = 2; i < vegetablesRUS.length; i++) {
            TableRow row3 = new TableRow(this);
            row3.setBackgroundResource(R.color.tabDividerRed);

            TextView tv2 = new TextView(this);
            TableRow.LayoutParams tvParams2 = new TableRow.LayoutParams();
            tvParams2.height = getResources().getDimensionPixelSize(R.dimen.fish_cooking_time_height);
            tvParams2.width = getResources().getDimensionPixelSize(R.dimen.tv3_cooking_time_width);
            tvParams2.setMargins(marginBody, 0, marginBody, marginBody);
            tv2.setBackgroundResource(R.color.tabBackgroundWhite);
//            if (Locale.getDefault().getLanguage().equals("ru")) {
                tv2.setText(vegetablesRUS[i][0]);
//            } else {
//                tv2.setText(vegetablesENG[i][0]);
//            }
            tv2.setGravity(Gravity.CENTER);
            tv2.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
            tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body_small));
            tv2.setTypeface(Typeface.SANS_SERIF, 1);
            row3.addView(tv2, tvParams2);

            TextView tv3 = new TextView(this);
            TableRow.LayoutParams tvParams3 = new TableRow.LayoutParams();
            tvParams3.height = ViewGroup.LayoutParams.MATCH_PARENT;
            tvParams3.width = getResources().getDimensionPixelSize(R.dimen.tv3_cooking_time_height);
            tvParams3.setMargins(0, 0, marginBody, marginBody);
            tv3.setBackgroundResource(R.color.tabBackgroundWhite);
//            if (Locale.getDefault().getLanguage().equals("ru")) {
                tv3.setText(vegetablesRUS[i][1]);
//            } else {
//                tv3.setText(vegetablesENG[i][1]);
//            }
            tv3.setGravity(Gravity.CENTER);
            tv3.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
            tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body_small));
            tv3.setTypeface(Typeface.SANS_SERIF, 1);
            row3.addView(tv3, tvParams3);

            TextView tv4 = new TextView(this);
            tv4.setBackgroundResource(R.color.tabBackgroundWhite);
//            if (Locale.getDefault().getLanguage().equals("ru")) {
                tv4.setText(vegetablesRUS[i][2]);
//            } else {
//                tv4.setText(vegetablesENG[i][2]);
//            }
            tv4.setGravity(Gravity.CENTER);
            tv4.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
            tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body_small));
            tv4.setTypeface(Typeface.SANS_SERIF, 1);
            row3.addView(tv4, tvParams3);

            TextView tv5 = new TextView(this);
            tv5.setBackgroundResource(R.color.tabBackgroundWhite);
//            if (Locale.getDefault().getLanguage().equals("ru")) {
                tv5.setText(vegetablesRUS[i][3]);
//            } else {
//                tv5.setText(vegetablesENG[i][3]);
//            }
            tv5.setGravity(Gravity.CENTER);
            tv5.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
            tv5.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body_small));
            tv5.setTypeface(Typeface.SANS_SERIF, 1);
            row3.addView(tv5, tvParams3);

            tableLayout.addView(row3, i);
        }
    }
    public void createOtherTitle() {
        int marginTitle = getResources().getDimensionPixelSize(R.dimen.margin_cooking_time_title);
        int marginBody = getResources().getDimensionPixelSize(R.dimen.margin_cooking_time_body);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayoutOther);

        TableRow row1 = new TableRow(this);
        row1.setBackgroundResource(R.color.tabDividerRed);
        TextView tv1 = new TextView(this);
        TableRow.LayoutParams tvParams1 = new TableRow.LayoutParams();
        tvParams1.height = getResources().getDimensionPixelSize(R.dimen.tv1_cooking_time_height);
        tvParams1.width = ViewGroup.LayoutParams.MATCH_PARENT;
        tvParams1.setMargins(marginTitle, marginTitle, marginTitle, marginTitle);
        tvParams1.span = 4;
        tv1.setBackgroundResource(R.color.tabTitlePorridge);
        tv1.setText(R.string.other);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
        tv1.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_title));
        tv1.setTypeface(typeface);
        row1.addView(tv1, tvParams1);
        tableLayout.addView(row1, 0);

        TableRow row2 = new TableRow(this);
        row2.setBackgroundResource(R.color.tabDividerRed);

        TextView tv2 = new TextView(this);
        TableRow.LayoutParams tvParams2 = new TableRow.LayoutParams();
        tvParams2.height = getResources().getDimensionPixelSize(R.dimen.tv3_cooking_time_height);
        tvParams2.width = getResources().getDimensionPixelSize(R.dimen.tv3_cooking_time_width);
        tvParams2.setMargins(marginBody, 0, marginBody, marginBody);
        tv2.setBackgroundResource(R.color.tabSecondRowPorridge);
        tv2.setGravity(Gravity.CENTER);
        row2.addView(tv2, tvParams2);

        TextView tv3 = new TextView(this);
        TableRow.LayoutParams tvParams3 = new TableRow.LayoutParams();
        tvParams3.height = ViewGroup.LayoutParams.MATCH_PARENT;
        tvParams3.width = getResources().getDimensionPixelSize(R.dimen.tv3_cooking_time_width);
        tvParams3.setMargins(0, 0, marginBody, marginBody);
        tv3.setBackgroundResource(R.color.tabSecondRowPorridge);
        tv3.setText(R.string.boiling);
        tv3.setGravity(Gravity.CENTER);
        tv3.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
        tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body));
        tv3.setTypeface(Typeface.SANS_SERIF, 1);
        row2.addView(tv3, tvParams3);

        TextView tv4 = new TextView(this);
        tv4.setBackgroundResource(R.color.tabSecondRowPorridge);
        tv4.setText(R.string.fry_up);
        tv4.setGravity(Gravity.CENTER);
        tv4.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
        tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body));
        tv4.setTypeface(Typeface.SANS_SERIF, 1);
        row2.addView(tv4, tvParams3);

        TextView tv5 = new TextView(this);
        tv5.setBackgroundResource(R.color.tabSecondRowPorridge);
        tv5.setText(R.string.stewing);
        tv5.setGravity(Gravity.CENTER);
        tv5.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
        tv5.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body));
        tv5.setTypeface(Typeface.SANS_SERIF, 1);
        row2.addView(tv5, tvParams3);

        tableLayout.addView(row2, 1);
    }
    public void createOtherBody() {
        int marginBody = getResources().getDimensionPixelSize(R.dimen.margin_cooking_time_body);

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayoutOther);

        for (int i = 2; i < otherRUS.length; i++) {
            TableRow row3 = new TableRow(this);
            row3.setBackgroundResource(R.color.tabDividerRed);

            TextView tv2 = new TextView(this);
            TableRow.LayoutParams tvParams2 = new TableRow.LayoutParams();
            tvParams2.height = getResources().getDimensionPixelSize(R.dimen.fish_cooking_time_height);
            tvParams2.width = getResources().getDimensionPixelSize(R.dimen.tv3_cooking_time_width);
            tvParams2.setMargins(marginBody, 0, marginBody, marginBody);
            tv2.setBackgroundResource(R.color.tabBackgroundWhite);
//            if (Locale.getDefault().getLanguage().equals("ru")) {
                tv2.setText(otherRUS[i][0]);
//            } else {
//                tv2.setText(otherENG[i][0]);
//            }
            tv2.setGravity(Gravity.CENTER);
            tv2.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
            tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body_small));
            tv2.setTypeface(Typeface.SANS_SERIF, 1);
            row3.addView(tv2, tvParams2);

            TextView tv3 = new TextView(this);
            TableRow.LayoutParams tvParams3 = new TableRow.LayoutParams();
            tvParams3.height = ViewGroup.LayoutParams.MATCH_PARENT;
            tvParams3.width = getResources().getDimensionPixelSize(R.dimen.tv3_cooking_time_height);
            tvParams3.setMargins(0, 0, marginBody, marginBody);
            tv3.setBackgroundResource(R.color.tabBackgroundWhite);
//            if (Locale.getDefault().getLanguage().equals("ru")) {
                tv3.setText(otherRUS[i][1]);
//            } else {
//                tv3.setText(otherENG[i][1]);
//            }
            tv3.setGravity(Gravity.CENTER);
            tv3.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
            tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body_small));
            tv3.setTypeface(Typeface.SANS_SERIF, 1);
            row3.addView(tv3, tvParams3);

            TextView tv4 = new TextView(this);
            tv4.setBackgroundResource(R.color.tabBackgroundWhite);
//            if (Locale.getDefault().getLanguage().equals("ru")) {
                tv4.setText(otherRUS[i][2]);
//            } else {
//                tv4.setText(otherENG[i][2]);
//            }
            tv4.setGravity(Gravity.CENTER);
            tv4.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
            tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body_small));
            tv4.setTypeface(Typeface.SANS_SERIF, 1);
            row3.addView(tv4, tvParams3);

            TextView tv5 = new TextView(this);
            tv5.setBackgroundResource(R.color.tabBackgroundWhite);
//            if (Locale.getDefault().getLanguage().equals("ru")) {
                tv5.setText(otherRUS[i][3]);
//            } else {
//                tv5.setText(otherENG[i][3]);
//            }
            tv5.setGravity(Gravity.CENTER);
            tv5.setTextColor((ContextCompat.getColor(this, R.color.tabDividerRed)));
            tv5.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_cooking_time_body_small));
            tv5.setTypeface(Typeface.SANS_SERIF, 1);
            row3.addView(tv5, tvParams3);

            tableLayout.addView(row3, i);
        }
    }
}
