package com.github.jurassicspb.cookbooksearchbyingredients;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by Мария on 27.11.2016.
 */

public class IngredientDetailAdapter extends RecyclerView.Adapter<IngredientDetailAdapter.ViewHolder> {
    private OnListItemClickListener clickListener;

    public IngredientDetailAdapter() {}

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_detailingredient, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.detailIngredient.setText(SelectedIngredient.getSelectedIngredient().get(position));
    }

    @Override
    public int getItemCount() {
        return SelectedIngredient.getSelectedIngredient().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView detailIngredient;
        TextView separator;

        public ViewHolder(View itemView) {
            super(itemView);
            detailIngredient = (TextView) itemView.findViewById(R.id.detail_ingredient);
            separator = (TextView) itemView.findViewById(R.id.separator);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }
    }

}
