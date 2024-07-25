package com.example.photohistory.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.photohistory.R
import com.example.photohistory.databinding.ItemHistoryPhotoBinding
import com.example.photohistory.domain.models.HistoryPhoto
import javax.inject.Inject

class HistoryPhotoAdapter @Inject constructor() :
    ListAdapter<HistoryPhoto, HistoryPhotoViewHolder>(HistoryPhotoDiffCallback()) {

    var onHistoryPhotoLongClickListener: ((HistoryPhoto, Int) -> Unit)? = null
    var onHistoryPhotoClickListener: ((HistoryPhoto, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryPhotoViewHolder {
        return HistoryPhotoViewHolder(
            DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_history_photo,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: HistoryPhotoViewHolder, position: Int) {
        val historyPhoto = getItem(position)
        val binding = viewHolder.binding

        with(binding.root) {
            setOnClickListener {
                onHistoryPhotoClickListener?.invoke(historyPhoto, position)
            }

            setOnLongClickListener {
                onHistoryPhotoLongClickListener?.invoke(historyPhoto, position)
                true
            }
        }

        when (binding) {
            is ItemHistoryPhotoBinding -> {
                binding.historyPhoto = historyPhoto
            }
        }
    }

}