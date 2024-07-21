package com.example.photohistory.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.photohistory.R
import com.example.photohistory.domain.models.HistoryPhoto
import com.example.photohistory.domain.models.Photo
import javax.inject.Inject

class HistoryPhotoAdapter @Inject constructor() :
    ListAdapter<HistoryPhoto, PhotoViewHolder>(HistoryPhotoDiffCallback()) {

    var onHistoryPhotoLongClickListener: ((HistoryPhoto, Int) -> Unit)? = null
    var onHistoryPhotoClickListener: ((HistoryPhoto, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_history_photo,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: PhotoViewHolder, position: Int) {
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
    }

}