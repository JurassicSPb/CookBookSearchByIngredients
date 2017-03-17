package com.github.jurassicspb.cookbooksearchbyingredients.custom_dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import com.github.jurassicspb.cookbooksearchbyingredients.R;
import com.github.jurassicspb.cookbooksearchbyingredients.storage.MyPreferences;

/**
 * Created by Мария on 14.03.2017.
 */

public class CustomDialog5 extends Dialog {
    private Button btnYes;
    private Button btnNo;
    private Activity activity;


    public CustomDialog5(Activity a) {
        super(a, R.style.custom_dialog_theme);
        activity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyPreferences preferences = new MyPreferences(activity);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.alert_layout_5);

        setCancelable(false);

        btnYes = (Button) findViewById(R.id.choice_button_yes);
        btnYes.setOnClickListener(v -> dismiss());

        btnNo = (Button) findViewById(R.id.choice_button_no);
        btnNo.setOnClickListener(v -> {
            preferences.setFlagAlert(false);
            dismiss();
        });
    }
}
