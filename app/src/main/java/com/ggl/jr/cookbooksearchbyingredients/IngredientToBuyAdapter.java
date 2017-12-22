package com.ggl.jr.cookbooksearchbyingredients;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ggl.jr.cookbooksearchbyingredients.storage.IngredientDatabase;

import java.util.List;

/**
 * Created by Мария on 08.04.2017.
 */

public class IngredientToBuyAdapter extends RecyclerView.Adapter<IngredientToBuyAdapter.ViewHolder> {
    private List<IngredientToBuy> ingrsToBuy;
    private IngredientDatabase ingrToBuyDB;
    private Context context;

    public IngredientToBuyAdapter(List<IngredientToBuy> ingrsToBuy, IngredientDatabase ingrToBuyDB) {
        this.ingrsToBuy = ingrsToBuy;
        this.ingrToBuyDB = ingrToBuyDB;
    }

    @Override
    public IngredientToBuyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == 0) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_ingredients_to_buy, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_ingredients_from_recipe, parent, false);
        }
        context = parent.getContext();
        return new IngredientToBuyAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(IngredientToBuyAdapter.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == 0) {
            holder.bindSingleIngredient(ingrsToBuy.get(position));
        } else {
            holder.bindIngredientsFromRecipe(ingrsToBuy.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (!ingrsToBuy.isEmpty() && ingrsToBuy.get(position).getIngredients() != null) {
            if (ingrsToBuy.get(position).getRecipeId() > 0) {
                return 1;
            }
        }
        return 0;
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
        LinearLayout ingredientFromRecipeParent;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            weight = (TextView) itemView.findViewById(R.id.weight);
            count = (TextView) itemView.findViewById(R.id.count);
            delete = (Button) itemView.findViewById(R.id.delete);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            ingredientFromRecipeParent = (LinearLayout) itemView.findViewById(R.id.ingredient_from_recipe_parent);
        }

        private void bindSingleIngredient(IngredientToBuy object) {
            name.setText(object.getName());
            weight.setText(object.getWeight());
            count.setText(object.getAmount());

            delete.setOnClickListener(v -> {
                List<IngredientToBuy> newIngrToBuy = ingrToBuyDB.copyIngrToBuyFromRealm();
                newIngrToBuy.remove(getAdapterPosition());
                ingrToBuyDB.clearIngrToBuy();
                ingrToBuyDB.copyIngredientToBuyList(newIngrToBuy);
                notifyDataSetChanged();
                Toast toast = Toast.makeText(v.getContext(), R.string.removed_successfully, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            });

            if (object.getCheckboxState() == 1) {
                checkBox.setChecked(true);
                name.setPaintFlags(name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                name.setTextColor((ContextCompat.getColor(context, R.color.DarkGray)));
            } else {
                checkBox.setChecked(false);
                if ((name.getPaintFlags() & Paint.STRIKE_THRU_TEXT_FLAG) > 0) {
                    name.setPaintFlags(name.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    name.setTextColor(Color.BLACK);
                }
            }

            checkBox.setOnClickListener(v -> {
                if (object.getCheckboxState() == 0) {
                    ingrToBuyDB.updateIngredientToBuy(object, 1);
                } else {
                    ingrToBuyDB.updateIngredientToBuy(object, 0);
                }
                notifyDataSetChanged();
            });
        }

        private void bindIngredientsFromRecipe(IngredientToBuy object) {
            ingredientFromRecipeParent.removeAllViews();

            FrameLayout.LayoutParams inp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            FrameLayout.LayoutParams rnp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            FrameLayout.LayoutParams blp;

            FrameLayout.LayoutParams cbp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            cbp.gravity = Gravity.RIGHT;

            if (Metrics.smallestWidth() >= 600) {
                inp.setMargins(context.getResources().getDimensionPixelSize(R.dimen.margin_from_recipe_tablets), 0, context.getResources().getDimensionPixelSize(R.dimen.margin_from_recipe_right_tablets), 0);

                blp = new FrameLayout.LayoutParams(context.getResources().getDimensionPixelSize(R.dimen.delete_from_recipe_tablets), context.getResources().getDimensionPixelSize(R.dimen.delete_from_recipe_tablets));
                blp.setMargins(0, context.getResources().getDimensionPixelSize(R.dimen.margin_delete_from_recipe_top), context.getResources().getDimensionPixelSize(R.dimen.margin_delete_from_recipe_right_tablets), 0);

                rnp.setMargins(context.getResources().getDimensionPixelSize(R.dimen.margin_from_recipe), context.getResources().getDimensionPixelSize(R.dimen.margin_from_recipe), context.getResources().getDimensionPixelSize(R.dimen.margin_from_recipe_right_tablets), context.getResources().getDimensionPixelSize(R.dimen.margin_from_recipe_bottom));

                cbp.setMargins(0, 0, context.getResources().getDimensionPixelSize(R.dimen.margin_checkbox_from_recipe_tablets), 0);
            } else {
                inp.setMargins(context.getResources().getDimensionPixelSize(R.dimen.margin_from_recipe), 0, context.getResources().getDimensionPixelSize(R.dimen.margin_from_recipe_right), 0);

                blp = new FrameLayout.LayoutParams(context.getResources().getDimensionPixelSize(R.dimen.delete_from_recipe), context.getResources().getDimensionPixelSize(R.dimen.delete_from_recipe));
                blp.setMargins(0, context.getResources().getDimensionPixelSize(R.dimen.margin_delete_from_recipe_top), context.getResources().getDimensionPixelSize(R.dimen.margin_delete_from_recipe_right), 0);

                rnp.setMargins(context.getResources().getDimensionPixelSize(R.dimen.margin_from_recipe), context.getResources().getDimensionPixelSize(R.dimen.margin_from_recipe), context.getResources().getDimensionPixelSize(R.dimen.margin_from_recipe_right), context.getResources().getDimensionPixelSize(R.dimen.margin_from_recipe_bottom));

                cbp.setMargins(0, 0, context.getResources().getDimensionPixelSize(R.dimen.margin_checkbox_from_recipe), 0);
            }
            blp.gravity = Gravity.RIGHT;

            FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            FrameLayout frameLayoutOuter = new FrameLayout(context);
            frameLayoutOuter.setLayoutParams(flp);

            TextView recipeName = new TextView(context);
            recipeName.setLayoutParams(rnp);
            recipeName.setText(object.getName());
            recipeName.setTextColor(Color.BLACK);
            recipeName.setLineSpacing(0.0f, 1.1f);
            recipeName.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
            recipeName.setGravity(Gravity.CENTER);

            Button delete = new Button(context);
            delete.setLayoutParams(blp);
            delete.setBackground((ContextCompat.getDrawable(context, R.drawable.ic_delete_ingrs_to_buy)));
            delete.setFocusable(false);
            delete.setFocusableInTouchMode(false);

            if (Metrics.smallestWidth() >= 600) {
                recipeName.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.name_from_recipe_tablets));
            } else {
                recipeName.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.name_from_recipe));
            }

            delete.setOnClickListener(v -> {
                if (object.getRecipeId() > 0) {
                    ingrToBuyDB.clearAllIngredientsFromRecipeById(object.getRecipeId());
                }
                List<IngredientToBuy> newIngrToBuy = ingrToBuyDB.copyIngrToBuyFromRealm();
                newIngrToBuy.remove(getAdapterPosition());
                ingrToBuyDB.clearIngrToBuy();
                ingrToBuyDB.copyIngredientToBuyList(newIngrToBuy);
                notifyDataSetChanged();
                Toast toast = Toast.makeText(v.getContext(), R.string.removed_successfully, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            });

            frameLayoutOuter.addView(recipeName);
            frameLayoutOuter.addView(delete);
            ingredientFromRecipeParent.addView(frameLayoutOuter);

            for (int i = 0; i < object.getIngredients().size(); i++) {
                IngredientsFromRecipe ingredient = object.getIngredients().get(i);

                FrameLayout frameLayoutInner = new FrameLayout(context);
                frameLayoutInner.setLayoutParams(flp);

                TextView ingredientName = new TextView(context);
                ingredientName.setLayoutParams(inp);
                ingredientName.setText(ingredient.getName());
                ingredientName.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
                ingredientName.setTextColor(Color.BLACK);
                ingredientName.setLineSpacing(0.0f, 1.1f);

                CheckBox ingredientState = new CheckBox(new ContextThemeWrapper(context, R.style.checkBoxForIngrsToBuy));
                ingredientState.setLayoutParams(cbp);
                ingredientState.setFocusable(false);
                ingredientState.setFocusableInTouchMode(false);

                if (Metrics.smallestWidth() >= 600) {
                    ingredientName.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.ingredient_from_recipe_tablets));
                    ingredientState.setScaleX(1.4f);
                    ingredientState.setScaleY(1.4f);
                } else {
                    ingredientName.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.ingredient_from_recipe));
                    ingredientState.setScaleX(1.2f);
                    ingredientState.setScaleY(1.2f);
                }

                if (ingredient.getState() == 1) {
                    ingredientState.setChecked(true);
                    ingredientName.setPaintFlags(ingredientName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    ingredientName.setTextColor((ContextCompat.getColor(context, R.color.DarkGray)));
                } else {
                    ingredientState.setChecked(false);
                    if ((ingredientName.getPaintFlags() & Paint.STRIKE_THRU_TEXT_FLAG) > 0) {
                        ingredientName.setPaintFlags(ingredientName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        ingredientName.setTextColor(Color.BLACK);
                    }
                }

                frameLayoutInner.addView(ingredientName);
                frameLayoutInner.addView(ingredientState);
                ingredientFromRecipeParent.addView(frameLayoutInner);

                int finalI = i;
                ingredientState.setOnClickListener(v -> {
                    if (ingredient.getState() == 0) {
                        ingrToBuyDB.updateIngredientFromRecipe(ingredient, 1);
                        ingrToBuyDB.updateIngredientsFromRecipe(object.getIngredients(), ingredient, finalI);
                    } else {
                        ingrToBuyDB.updateIngredientFromRecipe(ingredient, 0);
                        ingrToBuyDB.updateIngredientsFromRecipe(object.getIngredients(), ingredient, finalI);
                    }
                    notifyDataSetChanged();
                });
            }

            TextView separator = new TextView(context);
            LinearLayout.LayoutParams vp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, context.getResources().getDimensionPixelSize(R.dimen.separator_height));
            separator.setLayoutParams(vp);
            separator.setBackgroundColor((ContextCompat.getColor(context, R.color.colorPrimary)));

            ingredientFromRecipeParent.addView(separator);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
