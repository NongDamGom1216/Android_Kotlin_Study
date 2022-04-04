package com.example.imagesearch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearch.data.Document

class ImageListAdapter(val imageList: List<Document>,
                       val onItemClick : (Document)-> Unit) :
                        RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {
                            inner class ViewHolder(itemView: View) :
                                RecyclerView.ViewHolder(itemView) {

                                fun bind(data: Document) {
                                    Glide.with(itemView).load(data.thumbnail_url).into(itemView as ImageView)
                                }
                            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context) .inflate(R.layout.item_image, parent, false)
        return ViewHolder(view as ImageView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = imageList[position]

        holder.bind(data)

        holder.itemView.setOnClickListener{ onItemClick(data) }
    }

    override fun getItemCount() = imageList.size


}