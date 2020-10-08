package com.vishnu.todoapp.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

fun hideKeyboard(activity: Activity) {
    val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    val currentFocusedview = activity.currentFocus
    currentFocusedview?.let {
        inputMethodManager.hideSoftInputFromWindow(
            currentFocusedview.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
        )

    }
}