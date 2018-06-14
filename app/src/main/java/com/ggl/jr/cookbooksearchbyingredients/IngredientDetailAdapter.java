package com.ggl.jr.cookbooksearchbyingredients;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Мария on 27.11.2016.
 */

public class IngredientDetailAdapter extends RecyclerView.Adapter<IngredientDetailAdapter.ViewHolder> {
    private MenuListener menuListener;

    public interface MenuListener {
        void setMenuItemVisible(boolean state);
    }

    public IngredientDetailAdapter() {
    }

    public void setMenuListener(MenuListener menuListener) {
        this.menuListener = menuListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_detailingredient, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.detailIngredient.setText(SelectedIngredient.getSelectedIngredient().get(position));
        try {
            holder.detailImage.setImageResource(Integer.valueOf(SelectedIngredient.getSelectedImage().get(position)));
        } catch (Resources.NotFoundException e) {
            holder.detailImage.setImageResource(R.drawable.ic_res_not_found);
        }

        holder.delete.setOnClickListener(v -> {
            SelectedIngredient.getSelectedIngredient().remove(holder.getAdapterPosition());
            SelectedIngredient.getSelectedImage().remove(holder.getAdapterPosition());

            if (SelectedIngredient.getSelectedIngredient().isEmpty()) {
                menuListener.setMenuItemVisible(false);
            } else {
                menuListener.setMenuItemVisible(true);
            }
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return SelectedIngredient.getSelectedIngredient().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView detailIngredient;
        ImageView detailImage;
        View separator;
        Button delete;

        public ViewHolder(View itemView) {
            super(itemView);
            detailImage = (ImageView) itemView.findViewById(R.id.detail_image);
            detailIngredient = (TextView) itemView.findViewById(R.id.detail_ingredient);
            separator = itemView.findViewById(R.id.separator);
            delete = (Button) itemView.findViewById(R.id.delete_ingr);
        }
    }
}
