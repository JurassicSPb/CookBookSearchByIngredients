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
            {"Абрикос", "", "", "", "", "40"},
            {"Апельсин", "", "", "", "", "140"},
            {"Арахис очищенный", "175", "140", "20", "", ""},
            {"Баклажан", "", "", "", "", "200"},
            {"Болгарский перец", "", "", "", "", "50 - 150"},
            {"Брусника", "140", "", "", "", ""},
            {"Варенье", "330", "270", "50", "17", ""},
            {"Вино столовое", "250", "200", "20", "5", ""},
            {"Вишня свежая", "190", "150", "30", "", ""},
            {"Вода", "250", "200", "18", "5", ""},
            {"Гвоздика молотая", "", "", "", "3", ""},
            {"Гвоздика не молотая", "", "", "", "4", ""},
            {"Гвоздика цельная", "", "", "", "", "0,06"},
            {"Голубика", "260", "", "", "", ""},
            {"Горох лущеный", "", "230", "25", "10", ""},
            {"Горох не лущеный", "", "200", "", "", ""},
            {"Горчица", "", "", "", "4", ""},
            {"Горчица порошок", "", "", "", "4", ""},
            {"Груша", "", "", "", "", "135"},
            {"Джем", "", "", "40", "15", ""},
            {"Ежевика", "190", "", "", "", ""},
            {"Желатин (порошок)", "", "", "15", "5", ""},
            {"Желатин гранулир.", "", "", "15", "5", ""},
            {"Желатин листик", "", "", "", "", "2,5"},
            {"Земляника", "", "", "", "", "8"},
            {"Изюм", "190", "165", "25", "", ""},
            {"Какао", "", "", "20", "10", ""},
            {"Капуста (кочан)", "", "", "", "", "1500 - 2000"},
            {"Картофель средний", "", "", "", "", "100"},
            {"Кислота лимонная кристаллич.", "", "", "25", "8", ""},
            {"Кислота лимонная сухая", "", "", "25", "8", ""},
            {"Клубника", "150", "120", "25", "", ""},
            {"Клюква", "145", "", "", "", ""},
            {"Корица молотая", "", "", "20", "8", ""},
            {"Кофе молотый", "", "", "20", "7", ""},
            {"Крахмал картофель.", "200", "160", "30", "9", ""},
            {"Крупа овсяная", "90", "75", "12", "", ""},
            {"Крупа гречневая", "210", "190", "15", "7", ""},
            {"Крупа манная", "200", "180", "25", "8", ""},
            {"Крупа перловая", "230", "210", "25", "8", ""},
            {"Крупа пшенная", "220", "200", "25", "", ""},
            {"Крупа ячневая", "180", "160", "20", "7", ""},
            {"Крыжовник", "210", "", "", "", ""},
            {"Лавровый лист", "", "", "", "", "0,2"},
            {"Ликер", "", "", "20", "7", ""},
            {"Лук репчатый средний", "", "", "", "", "75"},
            {"Майонез", "230", "180", "15", "4", ""},
            {"Мак", "155", "135", "15", "5", ""},
            {"Макароны", "230", "190", "", "", ""},
            {"Малина", "140", "110", "20", "", ""},
            {"Маргарин топленый", "230", "180", "14", "10", ""},
            {"Масло растит.", "240", "190", "20", "5", ""},
            {"Масло сливочное", "210", "168", "40", "15", ""},
            {"Масло топленое", "240", "185", "20", "5", ""},
            {"Мед натурал.", "325", "270", "25", "15", ""},
            {"Миндаль очищенный", "160", "130", "30", "10", ""},
            {"Молоко сгущеное", "", "", "30", "20", ""},
            {"Молоко сухое", "120", "100", "20", "5", ""},
            {"Молоко цельное", "250", "200", "20", "5", ""},
            {"Морковь средняя", "", "", "", "", "75"},
            {"Мука картофель.", "180", "150", "30", "10", ""},
            {"Мука кукурузная", "160", "130", "30", "10", ""},
            {"Мука пшеничная", "160", "130", "25", "10", ""},
            {"Огурец средний", "", "", "", "", "100"},
            {"Орех грецкий молотый", "120", "100", "20", "5", ""},
            {"Орех фундук очищенный", "170", "130", "30", "10", ""},
            {"Перец молотый", "", "", "18", "5", ""},
            {"Персик", "", "", "", "", "85"},
            {"Петрушка", "", "", "", "", "50"},
            {"Помидор", "", "", "", "", "75"},
            {"Пюре ягодное", "350", "290", "50", "17", ""},
            {"Рис", "240", "180", "25", "9", ""},
            {"Сахар кусковой", "200", "140", "", "", "9"},
            {"Сахарная пудра", "180", "140", "25", "8", ""},
            {"Сахарный песок", "230", "180", "30", "12", ""},
            {"Свекла", "", "", "", "", "50"},
            {"Сельдерей", "", "", "", "", "100"},
            {"Слива", "150", "", "", "", "30"},
            {"Сливки", "250", "200", "14", "5", ""},
            {"Сметана", "250", "210", "25", "10", ""},
            {"Сода питьевая", "", "", "28", "12", ""},
            {"Соль", "325", "270", "30", "10", ""},
            {"Сухари паниров.", "125", "100", "15", "5", ""},
            {"Томатный соус", "", "", "25", "8", ""},
            {"Уксус", "250", "200", "18", "5", ""},
            {"Фасоль", "", "220", "30", "10", ""},
            {"Хлопья кукурузные", "50", "35", "17", "2", ""},
            {"Хлопья овсяные", "100", "80", "14", "4", ""},
            {"Хлопья пшеничные", "60", "45", "9", "2", ""},
            {"Чернослив", "250", "220", "25", "", ""},
            {"Чечевица", "210", "180", "", "", ""},
            {"Яблоко", "", "", "", "", "90"},
            {"Яблоко сушеное", "70", "55", "", "", ""},
            {"Яйцо без скорлупы", "6 шт.", "5 шт.", "", "", "50"},
            {"Яичный порошок", "180", "100", "25", "10", ""},
    };
//    private String [][] weightsENG = new String[][]{
//            {"Almond (peeled)", "160", "130", "30", "10", ""},
//            {"Apple", "", "", "", "", "90"},
//            {"Apple (dried)", "70", "55", "", "", ""},
//            {"Apricot", "", "", "", "", "40"},
//            {"Baking soda", "", "", "28", "12", ""},
//            {"Bay leaf", "", "", "", "", "0,2"},
//            {"Beans", "", "220", "30", "10", ""},
//            {"Beet", "", "", "", "", "50"},
//            {"Bell pepper", "", "", "", "", "50-150"},
//            {"Berry puree", "350", "290", "50", "17", ""},
//            {"Blackberry", "190", "", "", "", ""},
//            {"Blueberry", "260", "", "", "", ""},
//            {"Breadcrumbs", "125", "100", "15", "5", ""},
//            {"Buckwheat", "210", "190", "15", "7", ""},
//            {"Butter", "210", "168", "40", "15", ""},
//            {"Butter (melted)", "240", "185", "20", "5", ""},
//            {"Cabbage", "", "", "", "", "1500-2000"},
//            {"Carrot (medium)", "", "", "", "", "75"},
//            {"Celery", "", "", "", "", "100"},
//            {"Cherry", "190", "150", "30", "", ""},
//            {"Cinnamon (ground)", "", "", "20", "8", ""},
//            {"Citric acid granular", "", "", "25", "8", ""},
//            {"Citric acid powder", "", "", "25", "8", ""},
//            {"Cocoa powder", "", "", "20", "10", ""},
//            {"Coffee (ground)", "", "", "20", "7", ""},
//            {"Corn flakes", "50", "35", "17", "2", ""},
//            {"Corn flour", "160", "130", "30", "10", ""},
//            {"Сowberry", "140", "", "", "", ""},
//            {"Cranberry", "145", "", "", "", ""},
//            {"Cream", "250", "200", "14", "5", ""},
//            {"Cucumber (medium)", "", "", "", "", "100"},
//            {"Egg powder", "180", "100", "25", "10", ""},
//            {"Egg without shell", "6 pcs.", "5 pcs.", "", "", "50"},
//            {"Eggplant", "", "", "", "", "200"},
//            {"Cloves (ground)", "", "", "", "3", ""},
//            {"Cloves (whole)", "", "", "", "", "0,06"},
//            {"Fine-ground barley", "180", "160", "20", "7", ""},
//            {"Gelatin (granulated)", "", "", "15", "5", ""},
//            {"Gelatin leaf", "", "", "", "", "2,5"},
//            {"Gelatin powder", "", "", "15", "5", ""},
//            {"Gooseberry", "210", "", "", "", ""},
//            {"Hazelnuts (peeled)", "170", "130", "30", "10", ""},
//            {"Honey", "325", "270", "25", "15", ""},
//            {"Jam", "330", "270", "50", "17", ""},
//            {"Ketchup", "", "", "25", "8", ""},
//            {"Liqueur", "", "", "20", "7", ""},
//            {"Lentil", "210", "180", "", "", ""},
//            {"Margarine (melted)", "230", "180", "14", "10", ""},
//            {"Millet cereal", "220", "200", "25", "", ""},
//            {"Milk", "250", "200", "20", "5", ""},
//            {"Milk (condensed)", "", "", "30", "20", ""},
//            {"Milk (powdered)", "120", "100", "20", "5", ""},
//            {"Mustard", "", "", "", "4", ""},
//            {"Mustard powder", "", "", "", "4", ""},
//            {"Oat flakes", "100", "80", "14", "4", ""},
//            {"Oatmeal", "90", "75", "12", "", ""},
//            {"Onion (medium)", "", "", "", "", "75"},
//            {"Orange", "", "", "", "", "140"},
//            {"Parsley", "", "", "", "", "50"},
//            {"Pasta", "230", "190", "", "", ""},
//            {"Peach", "", "", "", "", "85"},
//            {"Peanuts (peeled)", "175", "140", "20", "", ""},
//            {"Pear", "", "", "", "", "135"},
//            {"Pearl barley", "230", "210", "25", "8", ""},
//            {"Peas (split)", "", "230", "25", "10", ""},
//            {"Peas (whole)", "", "200", "", "", ""},
//            {"Pepper (ground)", "", "", "18", "5", ""},
//            {"Plum", "150", "", "", "", "30"},
//            {"Poppy seed", "155", "135", "15", "5", ""},
//            {"Potato (medium)", "", "", "", "", "100"},
//            {"Potato flour", "180", "150", "30", "10", ""},
//            {"Potato starch", "200", "160", "30", "9", ""},
//            {"Prune", "250", "220", "25", "", ""},
//            {"Raisins", "190", "165", "25", "", ""},
//            {"Raspberry", "140", "110", "20", "", ""},
//            {"Rice", "240", "180", "25", "9", ""},
//            {"Salt", "325", "270", "30", "10", ""},
//            {"Seed oil", "240", "190", "20", "5", ""},
//            {"Semolina", "200", "180", "25", "8", ""},
//            {"Sour Cream", "250", "210", "25", "10", ""},
//            {"Strawberry", "150", "120", "25", "", ""},
//            {"Sugar (granulated)", "230", "180", "30", "12", ""},
//            {"Sugar (powdered)", "180", "140", "25", "8", ""},
//            {"Sugar lumps", "200", "140", "", "", "9"},
//            {"Tomato", "", "", "", "", "75"},
//            {"Vinegar", "250", "200", "18", "5", ""},
//            {"Walnut powder", "120", "100", "20", "5", ""},
//            {"Water", "250", "200", "18", "5", ""},
//            {"Wheat flakes", "60", "45", "9", "2", ""},
//            {"Wheat flour", "160", "130", "25", "10", ""},
//            {"Wild strawberry", "", "", "", "", "8"},
//            {"Wine (table)", "250", "200", "20", "5", ""},
//            };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weights_and_mesures);

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
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        Intent intent = new Intent(this, IngedientTablayoutActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//        finish();
//    }
    public void createTitle(){
        int margin = getResources().getDimensionPixelSize(R.dimen.margin);
        int padding = getResources().getDimensionPixelSize(R.dimen.padding);

        TableLayout tabLayout = (TableLayout) findViewById(R.id.tableLayoutTitle);

        TableRow rowTitle1 = new TableRow(this);
        rowTitle1.setBackgroundResource(R.color.tabDivider);

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
        rowTitle1.addView(tvTitle1, tvParams1);

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
        rowTitle1.addView(tvTitle2, tvParams2);
        tabLayout.addView(rowTitle1);

        TableRow rowTitle2 = new TableRow(this);
        rowTitle2.setBackgroundResource(R.color.tabDivider);

        TextView tvTitle2_1 = new TextView(this);
        TableRow.LayoutParams tvTitleParams2_1 = new TableRow.LayoutParams();
        tvTitleParams2_1.height=LayoutParams.MATCH_PARENT;
        tvTitleParams2_1.width=getResources().getDimensionPixelSize(R.dimen.tv1_width);
        tvTitleParams2_1.setMargins(0,0,margin,margin);
        tvTitle2_1.setBackgroundResource(R.color.tabFirstRow);
        rowTitle2.addView(tvTitle2_1, tvTitleParams2_1);

        TextView tvTitle2_2 = new TextView(this);
        TableRow.LayoutParams tvTitleParams2_2 = new TableRow.LayoutParams();
        tvTitleParams2_2.height = getResources().getDimensionPixelSize(R.dimen.tv_body_height);
        tvTitleParams2_2.width = getResources().getDimensionPixelSize(R.dimen.tv_body_width);
        tvTitleParams2_2.setMargins(0,0,margin,margin);
        tvTitle2_2.setBackgroundResource(R.color.tabSecondRow);
        tvTitle2_2.setText(R.string.glass_250_ml);
        tvTitle2_2.setTextColor(Color.BLACK);
        tvTitle2_2.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_small));
        tvTitle2_2.setPadding(padding, padding, padding, padding);
        tvTitle2_2.setGravity(Gravity.CENTER);
        rowTitle2.addView(tvTitle2_2, tvTitleParams2_2);

        TextView tvTitle2_3 = new TextView(this);
        tvTitle2_3.setBackgroundResource(R.color.tabThirdRow);
        TableRow.LayoutParams tvTitleParams2_3 = new TableRow.LayoutParams();
        tvTitleParams2_3.height = LayoutParams.MATCH_PARENT;
        tvTitleParams2_3.width = getResources().getDimensionPixelSize(R.dimen.tv_body_width);
        tvTitleParams2_3.setMargins(0,0,margin,margin);
        tvTitle2_3.setText(R.string.glass_200_ml);
        tvTitle2_3.setTextColor(Color.BLACK);
        tvTitle2_3.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_small));
        tvTitle2_3.setPadding(padding, padding, padding, padding);
        tvTitle2_3.setGravity(Gravity.CENTER);
        rowTitle2.addView(tvTitle2_3, tvTitleParams2_3);

        TextView tvTitle2_4 = new TextView(this);
        tvTitle2_4.setBackgroundResource(R.color.tabFourthRow);
        tvTitle2_4.setText(R.string.table_spoon);
        tvTitle2_4.setTextColor(Color.BLACK);
        tvTitle2_4.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_small));
        tvTitle2_4.setPadding(padding, padding, padding, padding);
        tvTitle2_4.setGravity(Gravity.CENTER);
        rowTitle2.addView(tvTitle2_4, tvTitleParams2_3);

        TextView tvTitle2_5 = new TextView(this);
        tvTitle2_5.setBackgroundResource(R.color.tabFifthRow);
        tvTitle2_5.setText(R.string.tea_spoon);
        tvTitle2_5.setTextColor(Color.BLACK);
        tvTitle2_5.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_small));
        tvTitle2_5.setPadding(padding, padding, padding, padding);
        tvTitle2_5.setGravity(Gravity.CENTER);
        rowTitle2.addView(tvTitle2_5, tvTitleParams2_3);

        TextView tvTitle2_6 = new TextView(this);
        TableRow.LayoutParams tvTitleParams2_6 = new TableRow.LayoutParams();
        tvTitleParams2_6.height = LayoutParams.MATCH_PARENT;
        tvTitleParams2_6.width = getResources().getDimensionPixelSize(R.dimen.tv_body_width);
        tvTitleParams2_6.setMargins(0,0,0,margin);
        tvTitle2_6.setBackgroundResource(R.color.tabSixthRow);
        tvTitle2_6.setText(R.string.one_piece_in_grams);
        tvTitle2_6.setTextColor(Color.BLACK);
        tvTitle2_6.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_small));
        tvTitle2_6.setPadding(padding, padding, padding, padding);
        tvTitle2_6.setGravity(Gravity.CENTER);
        rowTitle2.addView(tvTitle2_6, tvTitleParams2_6);

        tabLayout.addView(rowTitle2);
    }
    public void createBody(){
        TableLayout tabLayout = (TableLayout) findViewById(R.id.tableLayoutPorridge);
        int margin = getResources().getDimensionPixelSize(R.dimen.margin);
        int padding = getResources().getDimensionPixelSize(R.dimen.padding);

        int length;
        if (Locale.getDefault().getLanguage().equals("ru")) {
            length=94;
        }
        else{
            length=92;
        }
        for (int i = 0; i<length; i++){
            TableRow rowBody = new TableRow(this);
            rowBody.setBackgroundResource(R.color.tabDivider);

            TextView tvBody1 = new TextView(this);
            TableRow.LayoutParams tvBodyParams1 = new TableRow.LayoutParams();
            tvBodyParams1.height=LayoutParams.MATCH_PARENT;
            tvBodyParams1.width=getResources().getDimensionPixelSize(R.dimen.tv1_width);
            tvBodyParams1.setMargins(0,0,margin,margin);
//            if (Locale.getDefault().getLanguage().equals("ru")) {
                tvBody1.setText(weightsRUS[i][0]);
//            }
//            else{
//                tvBody1.setText(weightsENG[i][0]);
//            }
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
//            if (Locale.getDefault().getLanguage().equals("ru")) {
                tvBody2.setText(weightsRUS[i][1]);
//            }
//            else{
//                tvBody2.setText(weightsENG[i][1]);
//            }
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
//            if (Locale.getDefault().getLanguage().equals("ru")) {
                tvBody3.setText(weightsRUS[i][2]);
//            }
//            else{
//                tvBody3.setText(weightsENG[i][2]);
//            }
            tvBody3.setTextColor(Color.BLACK);
            tvBody3.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_small));
            tvBody3.setPadding(padding, padding, padding, padding);
            tvBody3.setGravity(Gravity.CENTER);
            rowBody.addView(tvBody3, tvBodyParams3);

            TextView tvBody4 = new TextView(this);
            tvBody4.setBackgroundResource(R.color.tabFourthRow);
//            if (Locale.getDefault().getLanguage().equals("ru")) {
                tvBody4.setText(weightsRUS[i][3]);
//            }
//            else{
//                tvBody4.setText(weightsENG[i][3]);
//            }
            tvBody4.setTextColor(Color.BLACK);
            tvBody4.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_small));
            tvBody4.setPadding(padding, padding, padding, padding);
            tvBody4.setGravity(Gravity.CENTER);
            rowBody.addView(tvBody4, tvBodyParams3);

            TextView tvBody5 = new TextView(this);
            tvBody5.setBackgroundResource(R.color.tabFifthRow);
//            if (Locale.getDefault().getLanguage().equals("ru")) {
                tvBody5.setText(weightsRUS[i][4]);
//            }
//            else{
//                tvBody5.setText(weightsENG[i][4]);
//            }
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
//            if (Locale.getDefault().getLanguage().equals("ru")) {
                tvBody6.setText(weightsRUS[i][5]);
//            }
//            else{
//                tvBody6.setText(weightsENG[i][5]);
//            }
            tvBody6.setTextColor(Color.BLACK);
            tvBody6.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_small));
            tvBody6.setPadding(padding, padding, padding, padding);
            tvBody6.setGravity(Gravity.CENTER);
            rowBody.addView(tvBody6, tvBodyParams6);

            tabLayout.addView(rowBody, i);
        }
    }
}
