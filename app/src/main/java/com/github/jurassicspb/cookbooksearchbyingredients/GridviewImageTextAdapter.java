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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jurassicspb.cookbooksearchbyingredients.storage.IngredientDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Мария on 06.11.2016.
 */

public class GridviewImageTextAdapter extends BaseAdapter implements Filterable {
    private Context mContext;
    private List<Ingredient> ingredientAdapter;
    private List<Ingredient> ingredientAdapterFiltered;
    private ValueFilter valueFilter;

    public GridviewImageTextAdapter(Context c, List<Ingredient> ingredientAdapter) {
        mContext = c;
        this.ingredientAdapter = ingredientAdapter;
        ingredientAdapterFiltered = ingredientAdapter;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return ingredientAdapter.size();
    }

    @Override
    public Ingredient getItem(int position) {
        // TODO Auto-generated method stub
        return ingredientAdapter.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        int itemID;
        if (ingredientAdapter == null) {
            itemID = position;
        } else {
            itemID = ingredientAdapterFiltered.indexOf(ingredientAdapter.get(position));
        }
        return itemID;
    }

    public class ViewHolder {
        TextView textView;
        ImageView imageView;
        CheckBox checkBox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        Ingredient object = ingredientAdapter.get(position);

        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.cell_gridview, null);
            holder.textView = (TextView) convertView.findViewById(R.id.textpart);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imagepart);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        holder.textView.setText(ingredientAdapter.get(position).getIngredient());
        holder.imageView.setImageResource(ingredientAdapter.get(position).getImage());

        if (object.getState() == 1) {
            holder.textView.setTextColor(ContextCompat.getColor(mContext, R.color.tabLayoutTextColorSelected));
        } else {
            holder.textView.setTextColor(Color.WHITE);
        }

        if (object.getCheckboxState() == 1) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }

        holder.checkBox.setOnClickListener(v -> {
            IngredientDatabase ingrFavoritesDB = new IngredientDatabase();
            if (object.getCheckboxState() == 0) {
                IngredientFavorites newIngrFav;
                newIngrFav = new IngredientFavorites(ingredientAdapter.get(position).getIngredient(),
                        ingredientAdapter.get(position).getImage(), ingredientAdapter.get(position).getState(),
                        ingredientAdapter.get(position).getCheckboxState());

                ingrFavoritesDB.copyOrUpdateIngrFavorites(newIngrFav);
                ingrFavoritesDB.close();
                object.setCheckboxState(1);
                Toast toast = Toast.makeText(v.getContext(), R.string.checkbox_add, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                ingrFavoritesDB.deleteIngrFavoritePosition(ingredientAdapter.get(position).getIngredient());
                ingrFavoritesDB.close();
                object.setCheckboxState(0);
                Toast toast = Toast.makeText(v.getContext(), R.string.checkbox_remove, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            notifyDataSetChanged();
        });
        return convertView;
    }

    @Override
    public Filter getFilter() {

        if (valueFilter == null) {

            valueFilter = new ValueFilter();
        }

        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<Ingredient> filterList = new ArrayList<>();
                for (int i = 0; i < ingredientAdapterFiltered.size(); i++) {
                    if (ingredientAdapterFiltered.get(i).getIngredient().toUpperCase()
                            .contains(constraint.toString().toUpperCase())) {
                        filterList.add(ingredientAdapterFiltered.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = ingredientAdapterFiltered.size();
                results.values = ingredientAdapterFiltered;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0) {
                notifyDataSetInvalidated();
            } else {
                ingredientAdapter = (ArrayList<Ingredient>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}