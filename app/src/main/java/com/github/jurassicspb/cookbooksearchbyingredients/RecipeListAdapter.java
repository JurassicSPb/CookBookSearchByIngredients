package com.github.jurassicspb.cookbooksearchbyingredients;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Мария on 07.12.2016.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder>{
    private List <Recipe> recipes;
    private OnListItemClickListener clickListener;

    public RecipeListAdapter (List <Recipe> recipes, OnListItemClickListener clickListener){
        this.recipes=recipes;
        this.clickListener=clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_recipelist, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Context cont = holder.recipeName.getContext();
        String matchingIngr = cont.getResources().getString(R.string.count_of_matching_ingredients);
        String category = cont.getResources().getString(R.string.category);

        String url = recipes.get(position).getImage();
        Context context = holder.photoSmall.getContext();


        Picasso.with(context)
                .load(url)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.timeleft64)
                .error(R.drawable.noconnection64)
                .into(holder.photoSmall);
        holder.recipeName.setText(recipes.get(position).getName() + "\n"
                + category + " " + recipes.get(position).getCategory() + "\n"
                + matchingIngr + " "
                + recipes.get(position).getCount());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public Recipe getRecipe(int position) {
        return recipes.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView photoSmall;
        TextView recipeName;

        public ViewHolder (View itemView){
            super(itemView);
            photoSmall = (ImageView) itemView.findViewById(R.id.photo);
            recipeName = (TextView) itemView.findViewById(R.id.recipe_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }
    }
}
