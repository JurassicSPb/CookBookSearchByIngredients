package com.github.jurassicspb.cookbooksearchbyingredients.custom_dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import com.github.jurassicspb.cookbooksearchbyingredients.R;

/**
 * Created by Мария on 17.03.2017.
 */

public class CustomDialog4 extends Dialog{
    private Button btnClose;
    private Activity activity;


    public CustomDialog4 (Activity a) {
        super(a, R.style.custom_dialog_theme);
        activity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.alert_layout_4);

        setCancelable(false);

        btnClose = (Button) findViewById(R.id.close_button);
        btnClose.setOnClickListener(v -> {
            dismiss();
            new CustomDialog5(activity).show();
        });
    }
}
