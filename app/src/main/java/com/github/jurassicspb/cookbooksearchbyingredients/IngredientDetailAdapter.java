package com.github.jurassicspb.cookbooksearchbyingredients;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Мария on 27.11.2016.
 */

public class IngredientDetailAdapter extends RecyclerView.Adapter<IngredientDetailAdapter.ViewHolder> {
    private OnListItemClickListener clickListener;
    private ArrayList<String> newSelectedIngredient;

    public IngredientDetailAdapter(ArrayList<String> newSelectedIngredient) {
        this.newSelectedIngredient=newSelectedIngredient;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_detailingredient, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(IngredientDetailAdapter.class.getSimpleName(), "buybuy" + newSelectedIngredient);
        holder.detailIngredient.setText(newSelectedIngredient.get(position));
    }

    @Override
    public int getItemCount() {
        Log.d(IngredientDetailAdapter.class.getSimpleName(), "Hihihi" + SelectedIngredient.getSelectedIngredient().size());
        return SelectedIngredient.getSelectedIngredient().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView detailIngredient;

        public ViewHolder(View itemView) {
            super(itemView);
            detailIngredient = (TextView) itemView.findViewById(R.id.detail_ingredient);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }
    }

}
