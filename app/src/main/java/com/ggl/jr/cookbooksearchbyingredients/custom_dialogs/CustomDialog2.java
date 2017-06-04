package com.ggl.jr.cookbooksearchbyingredients.custom_dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import com.ggl.jr.cookbooksearchbyingredients.R;

/**
 * Created by Мария on 14.03.2017.
 */

public class CustomDialog2 extends Dialog {
    Button btnClose;
    Activity activity;

    public CustomDialog2(Activity a) {
        super(a, R.style.custom_dialog_theme);
        activity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.alert_layout_2);

        setCancelable(false);

        btnClose = (Button) findViewById(R.id.close_button);
        btnClose.setOnClickListener(v -> {
            dismiss();
            new CustomDialog3(activity).show();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
        new CustomDialog3(activity).show();
    }
}
