package com.soopeach.coco

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.soopeach.coco.databinding.ImgItemBinding

class imgAdapter(private val data: List<Uri>): RecyclerView.Adapter<imgAdapter.viewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        itemClickListener = onItemClickListener
    }

    inner class viewHolder(private val binding: ImgItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: Uri){
            Glide.with(binding.imageView.context).load(data).into(binding.imageView)
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