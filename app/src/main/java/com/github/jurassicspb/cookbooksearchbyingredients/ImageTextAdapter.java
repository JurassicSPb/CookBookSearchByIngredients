package com.github.jurassicspb.cookbooksearchbyingredients;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Мария on 06.11.2016.
 */

public class ImageTextAdapter extends BaseAdapter{
    private Context mContext;
    private final List <Ingredient> ingredientAdapter;
    private final int[] image;

    public ImageTextAdapter(Context c, List <Ingredient> ingredientAdapter ,int[] image ) {
        mContext = c;
        this.image = image;
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
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.cell_gridview, null);
            TextView textView = (TextView) grid.findViewById(R.id.textpart);
            ImageView imageView = (ImageView) grid.findViewById(R.id.imagepart);
            textView.setText(ingredientAdapter.get(position).getIngredient());
            imageView.setImageResource(image[position]);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }

}
