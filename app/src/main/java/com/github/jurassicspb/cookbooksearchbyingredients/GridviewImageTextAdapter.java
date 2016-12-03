package com.github.jurassicspb.cookbooksearchbyingredients;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;


/**
 * Created by Мария on 06.11.2016.
 */

public class GridviewImageTextAdapter extends BaseAdapter{
    private Context mContext;
    private final List <Ingredient> ingredientAdapter;

    public GridviewImageTextAdapter(Context c, List <Ingredient> ingredientAdapter) {
        mContext = c;
        this.ingredientAdapter = ingredientAdapter;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return ingredientAdapter.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return ingredientAdapter.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    public class ViewHolder{
        TextView textView;
        ImageView imageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
//        View grid;
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.cell_gridview, null);
            holder.textView = (TextView) convertView.findViewById(R.id.textpart);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imagepart);
            convertView.setTag(holder);
//            grid = new View(mContext);
//            grid = inflater.inflate(R.layout.cell_gridview, null);
//            TextView textView = (TextView) grid.findViewById(R.id.textpart);
//            ImageView imageView = (ImageView) grid.findViewById(R.id.imagepart);
//            textView.setText(ingredientAdapter.get(position).getIngredient());
//            imageView.setImageResource(image[position]);
        } else {
            holder = (ViewHolder) convertView.getTag();
//            grid = (View) convertView;
        }
        holder.textView.setText(ingredientAdapter.get(position).getIngredient());
        holder.imageView.setImageResource(ingredientAdapter.get(position).getImage());
        Log.d(GridviewImageTextAdapter.class.getSimpleName(), "here" + ingredientAdapter.get(position).getImage());
        return convertView;
//        return grid;
    }

}