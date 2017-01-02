package com.github.jurassicspb.cookbooksearchbyingredients.nav_drawer_extras;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.github.jurassicspb.cookbooksearchbyingredients.R;

import java.util.Locale;


/**
 * Created by Мария on 01.01.2017.
 */

public class WeightsAndMeasures extends AppCompatActivity{
    private String [][] weightsRUS = new String[][]{
            {"", "", "", "", "", ""},
            {"", "Стакан 250 мл.", "Стакан 100 мл.", "Стол. ложка", "Чайная ложка", "1 штука в грам."},
            {"Апельсин", "", "", "", "", "140"}
            ,};
    private String [][] weightsENG = new String[][]{
            {"", "", "", "", "", ""},
            {"", "Glass 250 ml.", "Glass 100 ml.", "Table spoon", "Tea spoon", "1 piece in grams"},
            {"Orange", "", "", "", "", "140"}
            ,};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weights_and_mesures);

//        if (Locale.getDefault().getLanguage().equals("ru")) {
//            setContentView(R.layout.weights_and_mesures);
//        }
//        else{
//            setContentView(R.layout.weights_and_mesures_eng);
//        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        getSupportActionBar().setTitle(R.string.weights);

        createTitle();
        createBody();

    }
    public void createTitle(){
        TableLayout tabLayout = (TableLayout) findViewById(R.id.tableLayout);
        int margin = getResources().getDimensionPixelSize(R.dimen.margin);
        TableRow rowTitle = new TableRow(this);
        rowTitle.setBackgroundResource(R.color.tabDivider);

        TextView tvTitle1 = new TextView(this);
        TableRow.LayoutParams tvParams1 = new TableRow.LayoutParams();
        tvParams1.height=getResources().getDimensionPixelSize(R.dimen.tv1_height);
        tvParams1.width=getResources().getDimensionPixelSize(R.dimen.tv1_width);
        tvParams1.setMargins(0,0,margin,margin);

        tvTitle1.setBackgroundResource(R.color.tabFirstRow);
        tvTitle1.setText(R.string.products);
        tvTitle1.setGravity(Gravity.CENTER);
        tvTitle1.setTextColor(Color.BLACK);
        tvTitle1.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size));
        rowTitle.addView(tvTitle1, tvParams1);

        TextView tvTitle2 = new TextView(this);
        TableRow.LayoutParams tvParams2 = new TableRow.LayoutParams();
        tvParams2.height=LayoutParams.MATCH_PARENT;
        tvParams2.width=LayoutParams.MATCH_PARENT;
        tvParams2.setMargins(0,0,0,margin);
        tvParams2.span=5;
        tvTitle2.setBackgroundResource(R.color.tabFirstRow);
        tvTitle2.setText(R.string.weight_in_grams);
        tvTitle2.setGravity(Gravity.CENTER);
        tvTitle2.setTextColor(Color.BLACK);
        tvTitle2.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size));
        rowTitle.addView(tvTitle2, tvParams2);

        tabLayout.addView(rowTitle, 0);
    }
    public void createBody(){
        TableLayout tabLayout = (TableLayout) findViewById(R.id.tableLayout);
        int margin = getResources().getDimensionPixelSize(R.dimen.margin);
        int padding = getResources().getDimensionPixelSize(R.dimen.padding);

        for (int i = 1; i< weightsRUS.length; i++){
            TableRow rowBody = new TableRow(this);
            rowBody.setBackgroundResource(R.color.tabDivider);

            TextView tvBody1 = new TextView(this);
            TableRow.LayoutParams tvBodyParams1 = new TableRow.LayoutParams();
            tvBodyParams1.height=LayoutParams.MATCH_PARENT;
            tvBodyParams1.width=getResources().getDimensionPixelSize(R.dimen.tv1_width);
            tvBodyParams1.setMargins(0,0,margin,margin);
            if (Locale.getDefault().getLanguage().equals("ru")) {
                tvBody1.setText(weightsRUS[i][0]);
            }
            else{
                tvBody1.setText(weightsENG[i][0]);
            }
            tvBody1.setBackgroundResource(R.color.tabFirstRow);
            tvBody1.setTextColor(Color.BLACK);
            tvBody1.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_medium));
            tvBody1.setGravity(Gravity.CENTER);

            rowBody.addView(tvBody1, tvBodyParams1);

            TextView tvBody2 = new TextView(this);
            TableRow.LayoutParams tvBodyParams2 = new TableRow.LayoutParams();
            tvBodyParams2.height = getResources().getDimensionPixelSize(R.dimen.tv_body_height);
            tvBodyParams2.width = getResources().getDimensionPixelSize(R.dimen.tv_body_width);
            tvBodyParams2.setMargins(0,0,margin,margin);
            tvBody2.setBackgroundResource(R.color.tabSecondRow);
            if (Locale.getDefault().getLanguage().equals("ru")) {
                tvBody2.setText(weightsRUS[i][1]);
            }
            else{
                tvBody2.setText(weightsENG[i][1]);
            }
            tvBody2.setTextColor(Color.BLACK);
            tvBody2.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_small));
            tvBody2.setPadding(padding, padding, padding, padding);
            tvBody2.setGravity(Gravity.CENTER);
            rowBody.addView(tvBody2, tvBodyParams2);

            TextView tvBody3 = new TextView(this);
            tvBody3.setBackgroundResource(R.color.tabThirdRow);
            TableRow.LayoutParams tvBodyParams3 = new TableRow.LayoutParams();
            tvBodyParams3.height = LayoutParams.MATCH_PARENT;
            tvBodyParams3.width = getResources().getDimensionPixelSize(R.dimen.tv_body_width);
            tvBodyParams3.setMargins(0,0,margin,margin);
            if (Locale.getDefault().getLanguage().equals("ru")) {
                tvBody3.setText(weightsRUS[i][2]);
            }
            else{
                tvBody3.setText(weightsENG[i][2]);
            }
            tvBody3.setTextColor(Color.BLACK);
            tvBody3.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_small));
            tvBody3.setPadding(padding, padding, padding, padding);
            tvBody3.setGravity(Gravity.CENTER);
            rowBody.addView(tvBody3, tvBodyParams3);

            TextView tvBody4 = new TextView(this);
            tvBody4.setBackgroundResource(R.color.tabFourthRow);
            if (Locale.getDefault().getLanguage().equals("ru")) {
                tvBody4.setText(weightsRUS[i][3]);
            }
            else{
                tvBody4.setText(weightsENG[i][3]);
            }
            tvBody4.setTextColor(Color.BLACK);
            tvBody4.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_small));
            tvBody4.setPadding(padding, padding, padding, padding);
            tvBody4.setGravity(Gravity.CENTER);
            rowBody.addView(tvBody4, tvBodyParams3);

            TextView tvBody5 = new TextView(this);
            tvBody5.setBackgroundResource(R.color.tabFifthRow);
            if (Locale.getDefault().getLanguage().equals("ru")) {
                tvBody5.setText(weightsRUS[i][4]);
            }
            else{
                tvBody5.setText(weightsENG[i][4]);
            }
            tvBody5.setTextColor(Color.BLACK);
            tvBody5.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_small));
            tvBody5.setPadding(padding, padding, padding, padding);
            tvBody5.setGravity(Gravity.CENTER);
            rowBody.addView(tvBody5, tvBodyParams3);

            TextView tvBody6 = new TextView(this);
            TableRow.LayoutParams tvBodyParams6 = new TableRow.LayoutParams();
            tvBodyParams6.height = LayoutParams.MATCH_PARENT;
            tvBodyParams6.width = getResources().getDimensionPixelSize(R.dimen.tv_body_width);
            tvBodyParams6.setMargins(0,0,0,margin);
            tvBody6.setBackgroundResource(R.color.tabSixthRow);
            if (Locale.getDefault().getLanguage().equals("ru")) {
                tvBody6.setText(weightsRUS[i][5]);
            }
            else{
                tvBody6.setText(weightsENG[i][5]);
            }
            tvBody6.setTextColor(Color.BLACK);
            tvBody6.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_small));
            tvBody6.setPadding(padding, padding, padding, padding);
            tvBody6.setGravity(Gravity.CENTER);
            rowBody.addView(tvBody6, tvBodyParams6);

            tabLayout.addView(rowBody, i);
        }

    }
}
