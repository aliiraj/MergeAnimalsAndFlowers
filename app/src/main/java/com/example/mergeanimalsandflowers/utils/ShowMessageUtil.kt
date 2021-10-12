package com.example.mergeanimalsandflowers.utils

import android.view.View
import androidx.core.view.ViewCompat
import com.google.android.material.snackbar.Snackbar

object ShowMessageUtil {

    fun showSnackBar(view: View, message: String, duration: Int = Snackbar.LENGTH_SHORT){
        Snackbar.make(view, message, duration).apply {
            ViewCompat.setLayoutDirection(this.view,ViewCompat.LAYOUT_DIRECTION_RTL)
            show()
        }
    }

}