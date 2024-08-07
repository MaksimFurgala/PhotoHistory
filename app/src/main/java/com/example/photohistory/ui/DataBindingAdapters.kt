package com.example.photohistory.ui

import android.graphics.Color
import android.net.Uri
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.photohistory.R
import com.example.photohistory.domain.models.Photo
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
 * Адаптер для установки фото внутри нескольких imageView элемента фото-истории.
 *
 * @param view - imageView
 * @param photos - список фото
 */
@BindingAdapter("setImageUriHistoryPhoto")
fun setImageUriHistoryPhoto(view: ImageView, photos: List<Photo>) {
    when (view.id) {
        R.id.iv_history_photo_preview1 -> setImageByIdImageView(view, photos, 0)
        R.id.iv_history_photo_preview2 -> setImageByIdImageView(view, photos, 1)
        else -> RuntimeException("Undefined id view.")
    }
}

private fun setImageByIdImageView(view: ImageView, photos: List<Photo>, index: Int) {
    Picasso.get()
        .load((Uri.parse(photos.getOrNull(index)?.path ?: "")))
        .placeholder(R.drawable.photo_placeholder)
        .fit()
        .centerCrop()
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

/**
 * Адаптер для ошибки при вводе наименования
 *
 * @param textInputLayout - textInputLayout наименование
 * @param isError - ошибка
 */
@BindingAdapter("errorInputName")
fun bindErrorInputName(textInputLayout: TextInputLayout, isError: Boolean) {
    val message = if (isError)
        textInputLayout.context.getString(R.string.error_input_name)
    else
        null
    textInputLayout.error = message
}

/**
 * Адаптер для вывода текста, содержащий количество выбранных фото.
 *
 * @param button - кнопка выбора
 * @param countItems - количество элементов
 */
@BindingAdapter("countSelectedItemsWithText")
fun bindCountOfSelectedItemsWithText(button: Button, countItems: Int) {
    button.text = button.context.getString(R.string.selected_photos_text_btn, countItems)
}