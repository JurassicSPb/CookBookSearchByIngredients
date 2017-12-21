package com.ggl.jr.cookbooksearchbyingredients;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ggl.jr.cookbooksearchbyingredients.storage.IngredientDatabase;

import java.util.List;

/**
 * Created by yuri on 11/30/17.
 */

public class IngredientStopAdapter extends BaseAdapter {
    private Context mContext;
    private List<IngredientStop> ingrStopAdapter;
    private IngredientDatabase ingredientStopDB;

    public IngredientStopAdapter(Context mContext, List<IngredientStop> ingrStopAdapter,
                                 IngredientDatabase ingredientStopDB) {
        this.mContext = mContext;
        this.ingrStopAdapter = ingrStopAdapter;
        this.ingredientStopDB = ingredientStopDB;
    }


    @Override
    public int getCount() {
        return ingrStopAdapter.size();
    }

    @Override
    public Object getItem(int position) {
        return ingrStopAdapter.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        TextView textView;
        ImageView imageView;
        CheckBox stopCheckbox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IngredientStop object = ingrStopAdapter.get(position);

        IngredientStopAdapter.ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            holder = new IngredientStopAdapter.ViewHolder();
            convertView = inflater.inflate(R.layout.cell_ingr_stop, null);
            holder.textView = (TextView) convertView.findViewById(R.id.textpart);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imagepart);
            holder.stopCheckbox = (CheckBox) convertView.findViewById(R.id.checkbox_stop);
            convertView.setTag(holder);
        } else {
            holder = (IngredientStopAdapter.ViewHolder) convertView.getTag();
        }
        holder.textView.setText(object.getIngredient());
        holder.imageView.setImageResource(object.getImage());
        holder.stopCheckbox.setChecked(true);


        holder.stopCheckbox.setOnClickListener(v -> {
            ingredientStopDB.deleteIngrStopPosition(object.getIngredient());
            ingrStopAdapter.remove(object);
            notifyDataSetChanged();
            Toast toast = Toast.makeText(v.getContext(), R.string.stop_remove, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        });
        return convertView;
    }
}
