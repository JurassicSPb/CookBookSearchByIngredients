package com.github.jurassicspb.cookbooksearchbyingredients;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jurassicspb.cookbooksearchbyingredients.storage.IngredientDatabase;

import java.util.List;

/**
 * Created by Мария on 11.03.2017.
 */

public class IngredientFavoritesAdapter extends BaseAdapter {
    private Context mContext;
    private List<IngredientFavorites> ingrFavoritesAdapter;

    public IngredientFavoritesAdapter(Context mContext, List<IngredientFavorites> ingrFavoritesAdapter) {
        this.mContext = mContext;
        this.ingrFavoritesAdapter = ingrFavoritesAdapter;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return ingrFavoritesAdapter.size();
    }

    @Override
    public IngredientFavorites getItem(int position) {
        // TODO Auto-generated method stub
        return ingrFavoritesAdapter.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class ViewHolder {
        TextView textView;
        ImageView imageView;
        CheckBox checkBox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        IngredientFavorites object = ingrFavoritesAdapter.get(position);

        IngredientFavoritesAdapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            holder = new IngredientFavoritesAdapter.ViewHolder();
            convertView = inflater.inflate(R.layout.cell_ingr_favorites, null);
            holder.textView = (TextView) convertView.findViewById(R.id.textpart);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imagepart);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setTag(holder);
        } else {
            holder = (IngredientFavoritesAdapter.ViewHolder) convertView.getTag();
        }
        holder.textView.setText(object.getIngredient());
        holder.imageView.setImageResource(object.getImage());

        if (object.getState() == 1) {
            holder.textView.setTextColor(ContextCompat.getColor(mContext, R.color.tabLayoutTextColorSelected));
        } else {
            holder.textView.setTextColor(Color.WHITE);
        }

        holder.checkBox.setChecked(true);

        holder.checkBox.setOnClickListener(v -> {
            IngredientDatabase ingrFavoritesDB = new IngredientDatabase();
            ingrFavoritesDB.deleteIngrFavoritePosition(object.getIngredient());
            ingrFavoritesAdapter.remove(object);
            ingrFavoritesDB.close();
            notifyDataSetChanged();
            Toast toast = Toast.makeText(v.getContext(), R.string.checkbox_remove, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        });

        return convertView;
    }
}
