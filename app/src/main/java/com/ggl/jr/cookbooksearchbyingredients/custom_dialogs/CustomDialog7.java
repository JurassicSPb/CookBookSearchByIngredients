package com.ggl.jr.cookbooksearchbyingredients.custom_dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import com.ggl.jr.cookbooksearchbyingredients.IngedientTablayoutActivity;
import com.ggl.jr.cookbooksearchbyingredients.R;

/**
 * Created by Мария on 17.06.2017.
 */

public class CustomDialog7 extends Dialog {
    private Button btnYes;
    private Button btnNo;
    private Activity activity;


    public CustomDialog7(Activity a) {
        super(a, R.style.custom_dialog_theme);
        activity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.alert_layout_7);

        setCancelable(false);

        btnYes = (Button) findViewById(R.id.choice_button_yes);
        btnYes.setOnClickListener(v -> {
            Intent intent = new Intent(activity, IngedientTablayoutActivity.class);
            activity.startActivity(intent);
            activity.finish();
        });

        btnNo = (Button) findViewById(R.id.choice_button_no);
        btnNo.setOnClickListener(v -> dismiss());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
    }
}
