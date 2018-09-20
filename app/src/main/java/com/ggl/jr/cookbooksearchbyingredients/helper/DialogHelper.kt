package com.ggl.jr.cookbooksearchbyingredients.helper

import android.app.AlertDialog
import android.content.Context
import com.ggl.jr.cookbooksearchbyingredients.R

class DialogHelper private constructor() {
    var permissionCallback: PermissionCallback? = null
    var deleteCallback: DeleteCallback? = null

    fun getRequestPermissionDialog(context: Context): AlertDialog {
        val builder = AlertDialog.Builder(context, R.style.AlertDialogStyle)
        builder.setMessage(R.string.alert_grant_permission)
                .setPositiveButton(R.string.alert_yes) { _, _ -> permissionCallback?.onPositiveButtonClicked(ACTION_PERMISSION_REQUEST) }
                .setNegativeButton(R.string.alert_no) { dialog, _ -> dialog.cancel() }
        return builder.create()
    }

    fun getOpenSettingsDialog(context: Context): AlertDialog {
        val builder = AlertDialog.Builder(context, R.style.AlertDialogStyle)
        builder.setMessage(R.string.alert_open_settings)
                .setPositiveButton(R.string.alert_yes) { _, _ -> permissionCallback?.onPositiveButtonClicked(ACTION_OPEN_OPTIONS) }
                .setNegativeButton(R.string.alert_no) { dialog, _ -> dialog.cancel() }
        return builder.create()
    }

    fun getDeleteUserRecipeDialog(context: Context, itemId: Long, filePath: String): AlertDialog {
        val builder = AlertDialog.Builder(context, R.style.AlertDialogStyle)
        builder.setMessage(R.string.alert_delte_recipe)
                .setPositiveButton(R.string.alert_7_yes) { _, _ -> deleteCallback?.onDeletePositiveButtonClicked(itemId, filePath) }
                .setNegativeButton(R.string.alert_7_no) { dialog, _ -> dialog.cancel() }
        return builder.create()
    }

    interface PermissionCallback {
        fun onPositiveButtonClicked(action: Int)
    }

    interface DeleteCallback {
        fun onDeletePositiveButtonClicked(itemId: Long, filePath: String)
    }

    private object Holder {
        val INSTANCE = DialogHelper()
    }

    companion object {
        const val ACTION_PERMISSION_REQUEST = 1
        const val ACTION_OPEN_OPTIONS = 2
        val instance: DialogHelper by lazy { Holder.INSTANCE }
    }
}