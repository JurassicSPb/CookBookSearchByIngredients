package com.ggl.jr.cookbooksearchbyingredients.user_recipes

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ggl.jr.cookbooksearchbyingredients.Metrics
import com.ggl.jr.cookbooksearchbyingredients.R
import com.ggl.jr.cookbooksearchbyingredients.helper.DialogHelper
import com.ggl.jr.cookbooksearchbyingredients.helper.ImageHelper
import com.ggl.jr.cookbooksearchbyingredients.storage.IngredientDatabase
import com.google.android.gms.ads.AdRequest
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlinx.android.synthetic.main.user_recipe_list.user_recipe_list_adview as adView
import kotlinx.android.synthetic.main.user_recipe_list.user_recipe_recyclerview as recycler
import kotlinx.android.synthetic.main.user_recipe_list.user_repice_toolbar as toolbar

class UserRecipeListActivity :
        AppCompatActivity(),
        UserRecipeListAdapter.ItemClickListener,
        UserRecipeListAdapter.DeleteClickListener,
        UserRecipeListAdapter.EditClickListener,
        DialogHelper.DeleteCallback,
        CoroutineScope {
    lateinit var database: IngredientDatabase
    private lateinit var dialogHelper: DialogHelper
    private lateinit var imageHelper: ImageHelper
    private lateinit var userRecipeAdapter: UserRecipeListAdapter
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.user_recipe_list)

        if (Metrics.smallestWidth() >= 600) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_tablets)
        } else {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_phones)
        }

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setTitle(R.string.add_user_recipe)
        }

        dialogHelper = DialogHelper.instance.apply {
            deleteCallback = this@UserRecipeListActivity
        }
        imageHelper = ImageHelper.instance

        database = IngredientDatabase()
        userRecipeAdapter = UserRecipeListAdapter()

        recycler.apply {
            layoutManager = LinearLayoutManager(this@UserRecipeListActivity)
            adapter = userRecipeAdapter.apply {
                itemClickListener = this@UserRecipeListActivity
                deleteClickListener = this@UserRecipeListActivity
                editClickListener = this@UserRecipeListActivity
                userRecipes = database.allUserRecipes
            }
        }

        adView.loadAd(AdRequest.Builder().build())
    }

    override fun onItemClicked(item: UserRecipe) {
        startActivity(createBaseIntent(item).apply {
            putExtra("viewMode", true)
        })
    }

    override fun onResume() {
        super.onResume()

        userRecipeAdapter.notifyDataSetChanged()
    }

    override fun onDeleteButtonClicked(itemId: Long, filePath: String) {
        dialogHelper.getDeleteUserRecipeDialog(this, itemId, filePath).show()
    }

    override fun onEditButtonClicked(item: UserRecipe) {
        startActivity(createBaseIntent(item).apply {
            putExtra("editMode", true)
        })
    }

    override fun onDeletePositiveButtonClicked(itemId: Long, filePath: String) {
        launch {
            database.deleteUserRecipeById(itemId)
            userRecipeAdapter.notifyDataSetChanged()

            withContext(Dispatchers.Default) {
                imageHelper.deleteFile(filePath)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.user_recipe_buttons, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        startActivity(Intent(this, UserRecipeEditActivity::class.java))

        return super.onOptionsItemSelected(item)
    }

    private fun createBaseIntent(item: UserRecipe) = Intent(this, UserRecipeEditActivity::class.java).apply {
        putExtra("id", item.id)
        putExtra("name", item.name)
        putExtra("ingredients", item.ingredients)
        putExtra("description", item.description)
        putExtra("image", item.image)
    }

    override fun onDestroy() {
        job.cancel()
        database.close()
        dialogHelper.deleteCallback = null

        super.onDestroy()
    }
}