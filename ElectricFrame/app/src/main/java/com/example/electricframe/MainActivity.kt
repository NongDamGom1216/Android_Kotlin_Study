package com.example.electricframe

import MediaImage
import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    lateinit var permissionChecker: PermissionCheck

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 권한 체크
        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE) // 외부 장치에 대한 읽기 권한
        permissionChecker = PermissionCheck(this, permissions)
        if (permissionChecker.check()) {
            // 초기화
            init()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (permissionChecker.checkGranted(requestCode, permissions, grantResults)) {
            // 모든 권한 획득 성공
            init()

        } else {
            // 권한 획득 실패
            Toast.makeText(this, "권한 실패", Toast.LENGTH_LONG).show()
            finish()

        }
    }


    private fun init() {
        val mediaImage = MediaImage(this)
        val adapter = PhotoPagerAdapter(this, mediaImage.getAllPhotos())
        viewpager.adapter = adapter

        // 3초마다 자동으로 슬라이드
        timer(period = 3000) {
            runOnUiThread {
                viewpager.currentItem =
                    (viewpager.currentItem + 1) % adapter.itemCount
            }
        }
    }
}



