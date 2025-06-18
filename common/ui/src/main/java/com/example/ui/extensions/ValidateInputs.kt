package com.example.ui.extensions

import com.example.ui.R
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.validateInputs(): Boolean {
    var isValid = true

    val editText = this.editText?.text.toString()
    if (editText.isEmpty()) {
        this.error = context.getString(R.string.quantity_edit_error_empty)
        isValid = false
    } else {
        val quantity = editText.toIntOrNull()
        if (quantity == null || quantity <= 0) {
            this.error = context.getString(R.string.quantity_edit_error_invalid)
            isValid = false
        } else if (quantity > 1000) {
            this.error = context.getString(R.string.quantity_edit_error_invalid_max)
            isValid = false
        } else {
            this.isErrorEnabled = false
        }
    }
    return isValid
}
