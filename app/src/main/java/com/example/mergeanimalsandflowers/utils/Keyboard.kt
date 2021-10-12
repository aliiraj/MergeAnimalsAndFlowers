package com.example.mergeanimalsandflowers.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager


object Keyboard {
    fun closeKeyboard(activity: Activity) {
        val view = activity.currentFocus
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}