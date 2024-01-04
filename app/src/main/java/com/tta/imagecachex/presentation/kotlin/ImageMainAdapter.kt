package com.tta.imagecachex.presentation.kotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.tta.imagecachex.R
import com.tta.imagecachex.data.model.ImageDataEntity
import com.tta.imagecachex.databinding.ImageMainItemViewBinding
import com.tta.imagecachex.domain.main.ImageCacheX

class ImageMainAdapter() : Adapter<ImageMainAdapter.ItemViewHolder>() {

    private var itemClick: ItemClick? = null
    private var imageLoader: ImageCacheX? = null

    fun setImageLoader(imageLoader: ImageCacheX) {
        this.imageLoader = imageLoader
    }

    inner class ItemViewHolder(private val binding: ImageMainItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageData: ImageDataEntity) {
            binding.textViewId.text = imageData.id.toString()
            imageLoader?.displayImage(
                imageData.imageUrl ?: "",
                binding.imageViewMain,
                R.drawable.image_loading
            )
        }
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<ImageDataEntity>() {
        override fun areItemsTheSame(
            oldItem: ImageDataEntity,
            newItem: ImageDataEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ImageDataEntity,
            newItem: ImageDataEntity
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            ImageMainItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val image = differ.currentList[position]
        holder.bind(image)
    }

    interface ItemClick {
        fun showImageDetails(eventId: String)
    }

    fun setUpItemClick(itemClick: ItemClick) {
        this.itemClick = itemClick
    }
}