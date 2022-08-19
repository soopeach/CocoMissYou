package com.soopeach.coco

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.bumptech.glide.Glide
import com.soopeach.coco.databinding.ImgItemBinding

class imgAdapter(private val data: List<Uri>): RecyclerView.Adapter<imgAdapter.viewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        itemClickListener = onItemClickListener
    }

    inner class viewHolder(private val binding: ImgItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: Uri){
            // 글라이드로 이미지 로딩
//            Glide.with(binding.imageView.context).load(data).into(binding.imageView)
            // 코일로 이미지 로딩
            binding.imageView.load(data){
                crossfade(true)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = ImgItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(data[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }
}