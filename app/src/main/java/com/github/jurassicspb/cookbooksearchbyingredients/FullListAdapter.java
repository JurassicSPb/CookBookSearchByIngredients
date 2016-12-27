package com.github.jurassicspb.cookbooksearchbyingredients;

/**
 * Created by Мария on 27.12.2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class FullListAdapter extends RecyclerView.Adapter<FullListAdapter.ViewHolder>{
    private List<Recipe> recipes;
    private OnListItemClickListener clickListener;

    public FullListAdapter (List <Recipe> recipes, OnListItemClickListener clickListener){
        this.recipes=recipes;
        this.clickListener=clickListener;
    }

    @Override
    public FullListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_full_list, parent, false);
        return new FullListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FullListAdapter.ViewHolder holder, int position) {
        String url = recipes.get(position).getImage();
        Context context = holder.photoSmall.getContext();

        Picasso.with(context)
                .load(url)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(holder.photoSmall);

        holder.recipeName.setText(recipes.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public Recipe getRecipe (int position) {
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
