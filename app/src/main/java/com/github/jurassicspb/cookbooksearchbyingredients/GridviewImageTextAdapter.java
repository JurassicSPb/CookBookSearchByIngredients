package com.github.jurassicspb.cookbooksearchbyingredients;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
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
        return position;
    }

    public class ViewHolder {
        TextView textView;
        ImageView imageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.cell_gridview, null);
            holder.textView = (TextView) convertView.findViewById(R.id.textpart);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imagepart);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(ingredientAdapter.get(position).getIngredient());
        holder.imageView.setImageResource(ingredientAdapter.get(position).getImage());
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
            ingredientAdapter=(ArrayList<Ingredient>) results.values;
            notifyDataSetChanged();
        }
    }
}