package com.example.photohistory.ui

import android.graphics.Color
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.photohistory.R
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso

/**
 * Установка картинки для imageVIew через путь (Uri).
 *
 * @param view - imageView
 * @param path - путь до фото
 */
@BindingAdapter("android:setImageUri")
fun setImageUri(view: ImageView, path: String) {
    Picasso.get()
        .load(Uri.parse(path))
        .placeholder(R.drawable.photo_placeholder)
        .fit()
        .centerCrop()
        .error(R.drawable.photo_error)
        .into(view)
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

@BindingAdapter("errorInputName")
fun bindErrorInputName(textInputLayout: TextInputLayout, isError: Boolean) {
    val message = if (isError)
        textInputLayout.context.getString(R.string.error_input_name)
    else
        null
    textInputLayout.error = message
}