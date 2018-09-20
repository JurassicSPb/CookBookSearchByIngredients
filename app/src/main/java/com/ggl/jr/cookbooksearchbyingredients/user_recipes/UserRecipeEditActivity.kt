package com.ggl.jr.cookbooksearchbyingredients.user_recipes

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.ggl.jr.cookbooksearchbyingredients.Metrics
import com.ggl.jr.cookbooksearchbyingredients.R
import com.ggl.jr.cookbooksearchbyingredients.extensions.isClickableAndFocusable
import com.ggl.jr.cookbooksearchbyingredients.extensions.setMarquee
import com.ggl.jr.cookbooksearchbyingredients.extensions.visible
import com.ggl.jr.cookbooksearchbyingredients.helper.DialogHelper
import com.ggl.jr.cookbooksearchbyingredients.helper.DialogHelper.Companion.ACTION_OPEN_OPTIONS
import com.ggl.jr.cookbooksearchbyingredients.helper.DialogHelper.Companion.ACTION_PERMISSION_REQUEST
import com.ggl.jr.cookbooksearchbyingredients.helper.ImageHelper
import com.ggl.jr.cookbooksearchbyingredients.storage.IngredientDatabase
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.android.synthetic.main.user_recipe_edit.add_photo_container as addPhotoContainer
import kotlinx.android.synthetic.main.user_recipe_edit.add_photo_icon as addPhotoIcon
import kotlinx.android.synthetic.main.user_recipe_edit.add_photo_main as addPhotoMainImage
import kotlinx.android.synthetic.main.user_recipe_edit.add_photo_text as addPhotoText
import kotlinx.android.synthetic.main.user_recipe_edit.user_recipe_description as userRecipeDescription
import kotlinx.android.synthetic.main.user_recipe_edit.user_recipe_edit_toolbar as toolbar
import kotlinx.android.synthetic.main.user_recipe_edit.user_recipe_ingredients as userRecipeIngredients
import kotlinx.android.synthetic.main.user_recipe_edit.user_recipe_name as userRecipeName

class UserRecipeEditActivity :
        AppCompatActivity(),
        DialogHelper.PermissionCallback,
        ImageHelper.ImageLoadedFromPicassoCallback,
        ImageHelper.ErrorCallback {
    private val loadImage = Job()
    private val saveImage = Job()
    private lateinit var database: IngredientDatabase
    private lateinit var dialogHelper: DialogHelper
    private lateinit var imageHelper: ImageHelper
    private var idFromIntent = DEFAULT_ID
    private var imageFromIntent = DEFAULT_STRING_FIELD
    private var nameFromIntent = DEFAULT_STRING_FIELD
    private var ingredientsFromIntent = DEFAULT_STRING_FIELD
    private var descriptionFromIntent = DEFAULT_STRING_FIELD
    private var viewMode = false
    private var editMode = false
    private var menuItem: MenuItem? = null
    private var workFinished = true
    private var openGalleryIntent: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.user_recipe_edit)

        if (Metrics.smallestWidth() >= 600) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_tablets)
        } else {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_phones)
        }

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { _ -> onBackPressed() }

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setTitle(R.string.add_user_recipe)
        }

        dialogHelper = DialogHelper.instance.apply {
            permissionCallback = this@UserRecipeEditActivity
        }
        imageHelper = ImageHelper.instance.apply {
            imageLoadedFromPicasso = this@UserRecipeEditActivity
            errorCallback = this@UserRecipeEditActivity
        }

        addPhotoContainer.setOnClickListener { chooseImage() }

        handleIntent(intent)

        prepareViews()
    }

    private fun handleIntent(intent: Intent?) = with(intent) {
        if (this != null) {
            if (hasExtra("image")) imageFromIntent = getStringExtra("image")
            if (hasExtra("id")) idFromIntent = getLongExtra("id", DEFAULT_ID)
            if (hasExtra("viewMode")) viewMode = getBooleanExtra("viewMode", false)
            if (hasExtra("name")) nameFromIntent = getStringExtra("name")
            if (hasExtra("ingredients")) ingredientsFromIntent = getStringExtra("ingredients")
            if (hasExtra("description")) descriptionFromIntent = getStringExtra("description")
            if (hasExtra("editMode")) editMode = getBooleanExtra("editMode", false)
        }
    }

    private fun prepareViews() {
        if (imageFromIntent != DEFAULT_STRING_FIELD && imageHelper.resized == null) {
            imageHelper.loadImageFromStorage(this, imageFromIntent, addPhotoMainImage)
        }

        userRecipeName.setText(nameFromIntent, TextView.BufferType.EDITABLE)
        userRecipeIngredients.setText(ingredientsFromIntent, TextView.BufferType.EDITABLE)
        userRecipeDescription.setText(descriptionFromIntent, TextView.BufferType.EDITABLE)

        if (viewMode) {
            userRecipeName.apply {
                isClickableAndFocusable(false)
                isEnabled = false
            }
            userRecipeIngredients.apply {
                isClickableAndFocusable(false)
                isEnabled = false
            }
            userRecipeDescription.apply {
                isClickableAndFocusable(false)
                isEnabled = false
            }
            addPhotoContainer.isClickableAndFocusable(false)

            toolbar.setMarquee(R.string.user_recipe_view_mode)
        }

        if (editMode) supportActionBar?.setTitle(R.string.user_recipe_edit_mode)
    }

    override fun onPositiveButtonClicked(action: Int) {
        when (action) {
            ACTION_PERMISSION_REQUEST -> ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_WRITE_STORAGE)
            ACTION_OPEN_OPTIONS -> startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null)))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (Metrics.smallestWidth() >= 600) {
            menuInflater.inflate(R.menu.user_recipe_edit_buttons_tablets, menu)
        } else {
            menuInflater.inflate(R.menu.user_recipe_edit_buttons_phones, menu)
        }

        menuItem = menu.getItem(0)
        if (viewMode || !workFinished) {
            menuItem?.isVisible = false
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (imageHelper.resized == null && imageFromIntent == DEFAULT_STRING_FIELD ||
                userRecipeName.text.isEmpty() || userRecipeIngredients.text.isEmpty() || userRecipeDescription.text.isEmpty()) {
            Toast.makeText(this, R.string.fill_all_fields, Toast.LENGTH_SHORT).show()
            return false
        }

        launch(CommonPool, parent = saveImage) {
            launch(UI, parent = saveImage) {
                item.isVisible = false
            }

            try {
                database = IngredientDatabase()

                val userRecipe = UserRecipe().apply {
                    id = if (idFromIntent == DEFAULT_ID) database.findNextUserRecipeId() else idFromIntent
                    name = userRecipeName.text.toString()
                    ingredients = userRecipeIngredients.text.toString()
                    description = userRecipeDescription.text.toString()
                }

                val resized = imageHelper.resized
                if (resized != null && !resized.isRecycled) {
                    imageHelper.writeToFile(this@UserRecipeEditActivity)
                    userRecipe.image = imageHelper.filePath ?: DEFAULT_STRING_FIELD
                    imageHelper.deleteFile(imageFromIntent)
                } else {
                    userRecipe.image = imageFromIntent
                }
                database.copyOrUpdateUserRecipe(userRecipe)
            } finally {
                database.close()
            }

            launch(UI, parent = saveImage) {
                imageHelper.clear()
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        workFinished = savedInstanceState.getBoolean(WORK_FINISHED_KEY)
        openGalleryIntent = savedInstanceState.getParcelable(OPEN_GALLERY_INTENT_KEY)

        if (!workFinished) {
            onActivityResult(REQUEST_CODE, RESULT_OK, openGalleryIntent)
        } else {
            val resized = imageHelper.resized
            if (resized != null && !resized.isRecycled) {
                addPhotoMainImage.setImageBitmap(imageHelper.resized)
                hideAddPhotoElements()
            }
        }

        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(WORK_FINISHED_KEY, workFinished)
        if (openGalleryIntent != null) outState.putParcelable(OPEN_GALLERY_INTENT_KEY, openGalleryIntent)
    }

    override fun onImageLoaded() {
        hideAddPhotoElements()
    }

    override fun onImageLoadedError() {
        launch(UI, parent = loadImage) {
            Toast.makeText(this@UserRecipeEditActivity, R.string.choose_another_image_format, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_WRITE_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    dialogHelper.getRequestPermissionDialog(this).show()
                } else {
                    dialogHelper.getOpenSettingsDialog(this).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        openGalleryIntent = data

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.data != null) {

            launch(CommonPool, parent = loadImage) {

                launch(UI, parent = loadImage) {
                    menuItem?.isVisible = false
                    workFinished = false
                }

                val resized = imageHelper.getScaledImage(data.data, this@UserRecipeEditActivity)

                launch(UI, parent = loadImage) {
                    if (resized != null && !resized.isRecycled) {
                        addPhotoMainImage.setImageBitmap(resized)
                        hideAddPhotoElements()
                    }
                    menuItem?.isVisible = true
                    workFinished = true
                }
            }
        }
    }

    private fun hideAddPhotoElements() {
        addPhotoIcon.visible(false)
        addPhotoText.visible(false)
    }

    override fun onDestroy() {
        loadImage.cancel()
        saveImage.cancel()
        dialogHelper.permissionCallback = null
        imageHelper.imageLoadedFromPicasso = null
        imageHelper.errorCallback = null

        super.onDestroy()
    }

    override fun onBackPressed() {
        imageHelper.clear()

        super.onBackPressed()
    }

    private fun chooseImage(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            dialogHelper.getRequestPermissionDialog(this).show()
        } else {
            openGallery()
        }
        return true
    }

    private fun openGallery() {
        startActivityForResult(Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI), REQUEST_CODE)
    }

    companion object {
        private const val REQUEST_CODE = 1
        private const val REQUEST_WRITE_STORAGE = 2
        private const val DEFAULT_ID = Long.MIN_VALUE
        private const val DEFAULT_STRING_FIELD = ""
        private const val WORK_FINISHED_KEY = "workFinishedKey"
        private const val OPEN_GALLERY_INTENT_KEY = "openGalleryIntentKey"
    }
}