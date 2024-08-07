package com.example.photohistory.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.photohistory.R
import com.example.photohistory.databinding.ItemPhotoBinding
import com.example.photohistory.domain.models.Photo
import javax.inject.Inject

class PhotoListAdapter @Inject constructor() :
    ListAdapter<Photo, PhotoViewHolder>(PhotoDiffCallback()) {

    var onPhotoLongClickListener: ((Photo, Int) -> Unit)? = null
    var onPhotoClickListener: ((Photo, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_photo,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: PhotoViewHolder, position: Int) {
        val photo = getItem(position)
        val binding = viewHolder.binding

        with(binding.root) {
            setOnClickListener {
                onPhotoClickListener?.invoke(photo, position)
            }

            setOnLongClickListener {
                onPhotoLongClickListener?.invoke(photo, position)
                true
            }
        }

        when (binding) {
            is ItemPhotoBinding -> {
                binding.photoItem = photo
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.isChecked) VIEW_TYPE_CHECKED else VIEW_TYPE_UNCHECKED
    }

    /**
     * Вспомогательный объект для хранения констант для recycler view.
     *
     * @constructor Create empty Companion
     */
    companion object {
        const val VIEW_TYPE_CHECKED = 100
        const val VIEW_TYPE_UNCHECKED = 101
        const val MAX_POOL_SIZE = 25
    }
}