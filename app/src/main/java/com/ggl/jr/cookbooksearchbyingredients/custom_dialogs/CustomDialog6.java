package com.ggl.jr.cookbooksearchbyingredients.custom_dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import com.ggl.jr.cookbooksearchbyingredients.R;
import com.ggl.jr.cookbooksearchbyingredients.storage.MyPreferences;

/**
 * Created by Мария on 02.05.2017.
 */

public class CustomDialog6 extends Dialog{
    private Activity activity;


    public CustomDialog6(Activity a) {
        super(a, R.style.custom_dialog_theme);
        activity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyPreferences preferences = new MyPreferences(activity);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.alert_layout_6);

        setCancelable(false);

        Button show = (Button) findViewById(R.id.show);
        show.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=com.ggl.jr.cookbooksearchbyingredients"));
            activity.startActivity(intent);
        });

        Button notShow = (Button) findViewById(R.id.not_show);
        notShow.setOnClickListener(v -> {
            preferences.setFlagRating(false);
            dismiss();
        });

        Button exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(v -> {
            dismiss();
            activity.finish();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
    }
}
