package com.ggl.jr.cookbooksearchbyingredients.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.onTextChange(
        actionBeforeChange: (s: CharSequence?) -> Unit = {},
        actionAfterChange: (s: CharSequence?) -> Unit = {},
        actionOnChange: (s: CharSequence?) -> Unit = {}) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            actionBeforeChange(s)
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            actionOnChange(s)
        }

        override fun afterTextChanged(s: Editable?) {
            actionAfterChange(s)
        }
    })
}