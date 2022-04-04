package com.example.basic_recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_main.view.*

class MainAdapter(var items: MutableList<MainData>, val l: (Int) -> Unit) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() { //추상 메소드 상속

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvTitle = itemView.tv_main_title
        val tvContent = itemView.tv_main_content
        var imageView = itemView.imageView
    }

    //view holder의 역할 : items의 MainData와 view를 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_main, parent, false)

        // root 컴포넌트를 리턴 -> 여기서는 카드 뷰
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) { // 목록 중의 몇 번째

        // 카드를 눌렀을 때 디테일 뷰를 보여주기 위한 리스너
        holder.itemView.setOnClickListener {
            l(position)
        }

        items[position].let { item ->
            with(holder) {
                tvTitle.text = item.title
                tvContent.text = item.content
                Glide.with(itemView)
                    .load(item.image)
                    .override(64, 64)
                    .into(imageView)
                //override : 실제 이미지의 크기를 조절
            }
        }
    }


    override fun getItemCount(): Int = items.size // 목록의 갯수 리턴

}

