package com.ggl.jr.cookbooksearchbyingredients;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Мария on 14.02.2017.
 */

public class CategoriesAdapter extends BaseAdapter {
    private Context mContext;
    private List<Categories> categoriesAdapter;

    public CategoriesAdapter(Context mContext, List<Categories> categoriesAdapter) {
        this.mContext = mContext;
        this.categoriesAdapter = categoriesAdapter;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return categoriesAdapter.size();
    }

    @Override
    public Categories getItem(int position) {
        // TODO Auto-generated method stub
        return categoriesAdapter.get(position);
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
        CategoriesAdapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            holder = new CategoriesAdapter.ViewHolder();
            convertView = inflater.inflate(R.layout.cell_categories, null);
            holder.textView = (TextView) convertView.findViewById(R.id.textpart);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imagepart);
            convertView.setTag(holder);
        } else {
            holder = (CategoriesAdapter.ViewHolder) convertView.getTag();
        }
        holder.textView.setText(categoriesAdapter.get(position).getName());
        holder.imageView.setImageResource(categoriesAdapter.get(position).getImage());
        return convertView;
    }
}
