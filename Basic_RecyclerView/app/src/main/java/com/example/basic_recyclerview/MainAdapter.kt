package com.example.basic_recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_main.view.*

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() { //추상 메소드 상속

    var items: MutableList<MainData> = mutableListOf(
        MainData("Title1", "Content1"),
        MainData("Title2", "Content2"),
        MainData("Title3", "Content3")
    )



    //view holder의 역할 : items의 MainData와 view를 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_main, parent, false)
        // root 컴포넌트를 리턴 -> 여기서는 카드 뷰
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) { // 목록 중의 몇 번째
        items[position].let { item ->
            with(holder) {
                tvTitle.text = item.title
                tvContent.text = item.content
            }
        }
    }

    override fun getItemCount(): Int = items.size // 목록의 갯수 리턴

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvTitle = itemView.tv_main_title
        val tvContent = itemView.tv_main_content
    }

}