package com.github.jurassicspb.cookbooksearchbyingredients;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
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
    private List <RecipeCount> recipes;
    private OnListItemClickListener clickListener;

    public RecipeListAdapter (List <RecipeCount> recipes, OnListItemClickListener clickListener){
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

        String url = recipes.get(position).getRecipe().getImage();
        Context context = holder.photoSmall.getContext();

        RecipeCount r = recipes.get(position);

        final SpannableStringBuilder span = new SpannableStringBuilder();
        span.append(r.getRecipe().getName()).append("\n").append(category).append(" ")
                .append(r.getRecipe().getCategory()).append("\n").append(matchingIngr).append(" ").append(String.valueOf(r.getCount()));

        final StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
        final StyleSpan styleSpan2 = new StyleSpan(Typeface.BOLD);

        final RelativeSizeSpan sizeSpan;
        if (Metrics.smallestWidth() > 600) {
            sizeSpan = new RelativeSizeSpan(1.0f);
        }
        else {
            sizeSpan = new RelativeSizeSpan(0.9f);
        }
        span.setSpan(styleSpan, 0, r.getRecipe().getName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(sizeSpan, r.getRecipe().getName().length()+1, span.length()-String.valueOf(r.getCount()).length()-matchingIngr.length()-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(styleSpan2, span.length()-String.valueOf(r.getCount()).length(), span.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

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
