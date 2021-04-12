package com.ggl.jr.cookbooksearchbyingredients.user_recipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ggl.jr.cookbooksearchbyingredients.R
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import java.util.*

class UserRecipeListAdapter : RecyclerView.Adapter<UserRecipeListAdapter.ItemHolder>() {

    var itemClickListener: ItemClickListener? = null
    var deleteClickListener: DeleteClickListener? = null
    var editClickListener: EditClickListener? = null
    var userRecipes: List<UserRecipe> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_recipe_item, parent, false)).apply {
                itemView.setOnClickListener { itemClickListener?.onItemClicked(getItem(adapterPosition)) }
                delete.setOnClickListener { deleteClickListener?.onDeleteButtonClicked(getItemId(adapterPosition), getImage(adapterPosition)) }
                edit.setOnClickListener { editClickListener?.onEditButtonClicked(getItem(adapterPosition)) }
            }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) = with(holder) {
        val userRecipe = userRecipes[position]

        name.text = userRecipe.name

        Picasso.with(image.context)
                .load("file:// + ${userRecipe.image}")
                .fit()
                .centerCrop()
                .placeholder(R.drawable.timeleft64)
                .error(R.drawable.noconnection64)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(image)
    }

    override fun getItemCount() = userRecipes.size

    override fun getItemId(position: Int) = userRecipes[position].id

    private fun getItem(position: Int) = userRecipes[position]

    private fun getImage(position: Int) = userRecipes[position].image

    interface ItemClickListener {
        fun onItemClicked(item: UserRecipe)
    }

    interface DeleteClickListener {
        fun onDeleteButtonClicked(itemId: Long, filePath: String)
    }

    interface EditClickListener {
        fun onEditButtonClicked(item: UserRecipe)
    }

    class ItemHolder(
            itemView: View,
            val image: ImageView = itemView.findViewById(R.id.user_recipe_photo),
            val name: TextView = itemView.findViewById(R.id.user_recipe_name),
            val delete: Button = itemView.findViewById(R.id.delete_user_recipe),
            val edit: Button = itemView.findViewById(R.id.edit_user_recipe)
    ) : RecyclerView.ViewHolder(itemView)
}