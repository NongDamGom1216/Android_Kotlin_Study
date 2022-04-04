package com.example.basic_recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_detail.*
import kotlin.concurrent.timer

class MainDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_detail)

        val position = intent.getIntExtra("POSITION", 0)
        val adapter = MainDataPagerAdapter(this, MainDatas.items)
        viewPager.adapter = adapter
//        viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        viewPager.currentItem = position

        // 3초마다 슬라이드
        timer(period = 3000) {
            runOnUiThread {
                viewPager.currentItem = (viewPager.currentItem + 1) % MainDatas.items.size
            }
        }

    }
}