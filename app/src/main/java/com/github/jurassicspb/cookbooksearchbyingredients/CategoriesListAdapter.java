package com.github.jurassicspb.cookbooksearchbyingredients;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Мария on 14.02.2017.
 */

public class CategoriesListAdapter extends RecyclerView.Adapter<CategoriesListAdapter.ViewHolder> implements Filterable {
    private List<Recipe> recipes;
    private List<Recipe> recipesFiltered;
    private ValueFilter valueFilter;
    private OnListItemClickListener clickListener;

    public CategoriesListAdapter (List <Recipe> recipes, OnListItemClickListener clickListener){
        this.recipes=recipes;
        recipesFiltered=recipes;
        this.clickListener=clickListener;
    }

    @Override
    public CategoriesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_categories_list, parent, false);
        return new CategoriesListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CategoriesListAdapter.ViewHolder holder, int position) {
        Context cont =  holder.recipeName.getContext();
        String category = cont.getResources().getString(R.string.category);

        String url = recipes.get(position).getImage();
        Context context = holder.photoSmall.getContext();

        final SpannableString span = new SpannableString(recipes.get(position).getName() + "\n" + category + " "
                + recipes.get(position).getCategory());
        final StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
        span.setSpan(styleSpan, 0, recipes.get(position).getName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        Picasso.with(context)
                .load(url)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.timeleft64)
                .error(R.drawable.noconnection64)
                .into(holder.photoSmall);

        holder.recipeName.setText(span);

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
                ArrayList<Recipe> filterList = new ArrayList<>();
                for (int i = 0; i < recipesFiltered.size(); i++) {
                    if (recipesFiltered.get(i).getName().toUpperCase()
                            .contains(constraint.toString().toUpperCase())) {
                        filterList.add(recipesFiltered.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = recipesFiltered.size();
                results.values = recipesFiltered;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0) {
                //do nothing
            }
            else {
                recipes = (ArrayList<Recipe>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}
