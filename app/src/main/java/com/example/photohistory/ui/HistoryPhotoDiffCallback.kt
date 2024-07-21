package com.example.photohistory.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.photohistory.domain.models.HistoryPhoto

class HistoryPhotoDiffCallback : DiffUtil.ItemCallback<HistoryPhoto>() {
    override fun areItemsTheSame(oldItem: HistoryPhoto, newItem: HistoryPhoto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: HistoryPhoto, newItem: HistoryPhoto): Boolean {
        return oldItem == newItem && oldItem.isChecked == newItem.isChecked
    }
}