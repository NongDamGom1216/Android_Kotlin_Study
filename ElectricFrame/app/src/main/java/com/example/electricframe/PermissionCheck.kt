package com.example.electricframe

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionCheck(val activity: AppCompatActivity, // 연관된 액티비티
                      val permissions: Array<String>, // 검사할 퍼미션의 목록(배열로 받음)
                      val requestCode: Int = 1000) { // 요청 코드
    fun check(): Boolean {
        // 퍼미션을 획득한 적이 있으면 True
        val notGranted = permissions.filter {
            ContextCompat.checkSelfPermission(activity, it) != PackageManager.PERMISSION_GRANTED
        }
        if (notGranted.isEmpty()) {
            return true
        }
        // 획득한 적이 없으면
        ActivityCompat.requestPermissions(activity, permissions, requestCode)
        return false
    }

    // requestCode를 체크, permissions와 grantResults는 크기 동일
    fun checkGranted(requestCode: Int,
                     permissions: Array<out String>,
                     grantResults: IntArray) : Boolean {
        if(requestCode == this.requestCode) {
            val notGranted = permissions.filterIndexed { index, s -> // 인덱스, 아이템
                grantResults[index]!=PackageManager.PERMISSION_GRANTED
            }
            if(notGranted.isEmpty()) { // 필터링하고나서 비어있으면 권한을 모두 획득했다는 것
                return true
            }
        }
        return false
    }
}