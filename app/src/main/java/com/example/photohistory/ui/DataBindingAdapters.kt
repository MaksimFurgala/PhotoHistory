package com.example.photohistory.ui

import android.graphics.Color
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.photohistory.R

/**
 * Установка картинки для imageVIew через путь (Uri).
 *
 * @param view - imageView
 * @param path - путь до фото
 */
@BindingAdapter("android:setImageUri")
fun setImageUri(view: ImageView, path: String) {
    view.setImageURI(Uri.parse(path))
}

/**
 * Адаптер для установки цвета cardView в зависимости от того, выбрано фото или нет.
 *
 * @param view - cardView
 * @param isChecked - статус выбора
 */
@BindingAdapter("android:setBackgroundColorCardViewPhoto")
fun setBackgroundColorCardViewPhoto(view: CardView, isChecked: Boolean) {
    if (isChecked) {
        val backgroundColor = ContextCompat.getColor(view.context, R.color.purple_200)
        view.setCardBackgroundColor(backgroundColor)
    } else {
        view.setCardBackgroundColor(Color.WHITE)
    }
}

/**
 * Адаптер для установки цвета для текста в textView в зависимости от того, выбрано фото или нет.
 *
 * @param view - textView
 * @param isChecked - статус выбора
 */
@BindingAdapter("android:setTextColorCardViewPhoto")
fun setTextColorCardViewPhoto(view: TextView, isChecked: Boolean) {
    if (isChecked) {
        view.setTextColor(ContextCompat.getColor(view.context, R.color.white))
    } else {
        view.setTextColor(ContextCompat.getColor(view.context, R.color.black))
    }
}