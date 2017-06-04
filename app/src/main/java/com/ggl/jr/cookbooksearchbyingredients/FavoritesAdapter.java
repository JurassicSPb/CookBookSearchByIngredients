package com.ggl.jr.cookbooksearchbyingredients;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Мария on 27.12.2016.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    private List<Favorites> favorites;
    private OnListItemClickListener clickListener;

    public FavoritesAdapter(List<Favorites> favorites, OnListItemClickListener clickListener) {
        this.favorites = favorites;
        this.clickListener = clickListener;
    }

    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_favorites, parent, false);
        return new FavoritesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FavoritesAdapter.ViewHolder holder, int position) {
        Context cont = holder.recipeName.getContext();
        String category = cont.getResources().getString(R.string.category);

        String url = favorites.get(position).getImage();
        Context context = holder.photoSmall.getContext();

        Favorites f = favorites.get(position);

        final SpannableStringBuilder span = new SpannableStringBuilder();
        span.append(f.getName()).append("\n").append(category).append(" ").append(f.getCategory());

        final StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
        span.setSpan(styleSpan, 0, f.getName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

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
        return favorites.size();
    }

    public Favorites getRecipe(int position) {
        return favorites.get(position);
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
}
