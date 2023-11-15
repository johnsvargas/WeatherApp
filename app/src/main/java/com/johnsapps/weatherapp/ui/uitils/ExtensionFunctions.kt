package com.johnsapps.weatherapp.ui.uitils

import android.content.DialogInterface
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout

fun Fragment.makeToastError(msg: String) {
    Toast.makeText(
        requireContext(),
        "Ha ocurrido un error: $msg",
        Toast.LENGTH_SHORT
    ).show()
}

fun Fragment.makeToastFast(msg: String) {
    Toast.makeText(
        requireContext(),
        msg,
        Toast.LENGTH_SHORT
    ).show()
}

fun String.isEmptyOrBlank(): Boolean {
    return this.isEmpty() || this.isBlank()
}

fun String.isEmailFormatIsNotCorrected(): Boolean {
    val expression = Regex(
        "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$"
    )
    return expression.matches(this).not()
}

fun String.isPhoneNumberIsNotCorrected(): Boolean {
    val expression = Regex("^\\d{10}$")
    return expression.matches(this).not()
}

fun TextInputLayout.resetError() {
    val input = this
    this.editText?.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            input.error = null
        }
    })
}

fun Fragment.createSimpleDialog(
    title: String,
    message: String,
    positiveButton: String,
    negativeButton: String,
    onClickPositive: (dialog: DialogInterface, which: Int) -> Unit,
    onClickNegative: (dialog: DialogInterface, which: Int) -> Unit
): AlertDialog {
    val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
    builder.setTitle(title)
        .setMessage(message)
        .setPositiveButton(
            positiveButton
        ) { dialog, which ->
            onClickPositive(dialog, which)
        }
        .setNegativeButton(
            negativeButton
        ) { dialog, which ->
            onClickNegative(dialog, which)
        }
    return builder.create()
}

