package com.github.jurassicspb.cookbooksearchbyingredients;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.github.jurassicspb.cookbooksearchbyingredients.storage.IngredientDatabase;

import java.util.List;

/**
 * Created by Мария on 08.04.2017.
 */

public class IngredientToBuyAdapter extends RecyclerView.Adapter<IngredientToBuyAdapter.ViewHolder> {
    private List<IngredientToBuy> ingrsToBuy;
    private IngredientDatabase ingrToBuyDB;

    public IngredientToBuyAdapter(List<IngredientToBuy> ingrsToBuy) {
        this.ingrsToBuy = ingrsToBuy;
    }

    @Override
    public IngredientToBuyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_ingredients_to_buy, parent, false);
        return new IngredientToBuyAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(IngredientToBuyAdapter.ViewHolder holder, int position) {
        IngredientToBuy object = ingrsToBuy.get(position);

        holder.name.setText(ingrsToBuy.get(position).getName());
        holder.weight.setText(ingrsToBuy.get(position).getWeight());
        holder.count.setText(ingrsToBuy.get(position).getAmount());

        holder.delete.setOnClickListener(v -> {
            ingrToBuyDB = new IngredientDatabase();
            ingrToBuyDB.deleteIngrToBuy(holder.getAdapterPosition());
            ingrsToBuy = ingrToBuyDB.getAllIngrToBuy();
            ingrToBuyDB.close();
            notifyDataSetChanged();
        });

        if (object.getCheckboxState() == 1) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }

        holder.checkBox.setOnClickListener(v -> {
            ingrToBuyDB = new IngredientDatabase();
            List<IngredientToBuy> newIngrToBuy = ingrToBuyDB.copyIngrToBuyFromRealm();
            if (object.getCheckboxState() == 0) {
                newIngrToBuy.set(holder.getAdapterPosition(), new IngredientToBuy(ingrsToBuy.get(holder.getAdapterPosition()).getName(),
                        ingrsToBuy.get(holder.getAdapterPosition()).getWeight(), ingrsToBuy.get(holder.getAdapterPosition()).getAmount(), 1));
                ingrToBuyDB.clearIngrToBuy();
            } else {
                newIngrToBuy.set(holder.getAdapterPosition(), new IngredientToBuy(ingrsToBuy.get(holder.getAdapterPosition()).getName(),
                        ingrsToBuy.get(holder.getAdapterPosition()).getWeight(), ingrsToBuy.get(holder.getAdapterPosition()).getAmount(), 0));
            }
            ingrToBuyDB.clearIngrToBuy();
            ingrToBuyDB.copyIngredientToBuy(newIngrToBuy);
            ingrToBuyDB.close();
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return ingrsToBuy.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView weight;
        TextView count;
        Button delete;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            weight = (TextView) itemView.findViewById(R.id.weight);
            count = (TextView) itemView.findViewById(R.id.count);
            delete = (Button) itemView.findViewById(R.id.delete);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
