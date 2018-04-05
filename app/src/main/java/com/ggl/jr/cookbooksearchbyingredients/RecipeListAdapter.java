package com.ggl.jr.cookbooksearchbyingredients;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Мария on 07.12.2016.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<RecipeCount> recipes;
    private List<RecipeCount> originalRecipes;
    private RecipeListAdapter.ValueFilter valueFilter;
    private OnListItemClickListener clickListener;
    private final StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
    private final StyleSpan styleSpan2 = new StyleSpan(Typeface.BOLD);
    private final RelativeSizeSpan sizeSpan = setSizeSpan();

    public RecipeListAdapter(List<RecipeCount> recipes, OnListItemClickListener clickListener, Context context) {
        this.recipes = recipes;
        originalRecipes = recipes;
        this.clickListener = clickListener;
        this.context = context;
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

        RecipeCount r = recipes.get(position);

        final SpannableStringBuilder span = new SpannableStringBuilder();

        span.append(r.getName()).append("\n").append(category).append(" ")
                .append(r.getCategory()).append("\n").append(matchingIngr).append(" ").append(String.valueOf(r.getCount()));

        span.setSpan(styleSpan, 0, r.getName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(sizeSpan, r.getName().length() + 1, span.length() - String.valueOf(r.getCount()).length() - matchingIngr.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(styleSpan2, span.length() - String.valueOf(r.getCount()).length(), span.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

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

    public RecipeCount getRecipe(int position) {
        return recipes.get(position);
    }

    private RelativeSizeSpan setSizeSpan() {
        if (Metrics.smallestWidth() >= 600) {
            return new RelativeSizeSpan(1.0f);
        } else {
            return new RelativeSizeSpan(0.9f);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView photoSmall;
        TextView recipeName;

        public ViewHolder(View itemView) {
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

            valueFilter = new RecipeListAdapter.ValueFilter();
        }

        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<RecipeCount> filterList = new ArrayList<>();
                for (int i = 0; i < originalRecipes.size(); i++) {
                    if (originalRecipes.get(i).getCategory()
                            .contains(constraint.toString())) {
                        filterList.add(originalRecipes.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = originalRecipes.size();
                results.values = originalRecipes;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0) {
                recipes = new ArrayList<>();
                Toast toast = Toast.makeText(context, R.string.no_recipes_by_category, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                recipes = (ArrayList<RecipeCount>) results.values;
            }
            notifyDataSetChanged();
        }
    }
}
