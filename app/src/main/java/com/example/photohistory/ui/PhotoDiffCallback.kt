package com.example.photohistory.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.photohistory.domain.models.Photo

class PhotoDiffCallback : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem && oldItem.isChecked == newItem.isChecked
    }
}