package com.example.basic_toast_notification

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat


object NewMessageNotification {

    private val NOTIFICATION_TAG = "NewMessage"

    fun notify(context: Context, exampleString: String, number: Int) {
        val res = context.resources
        val picture = BitmapFactory.decodeResource(res, R.mipmap.ic_launcher)
        val title = "제목입니다."
        val text = exampleString
        val builder = NotificationCompat.Builder(context)

            // 알림 시, 진동 또는 플래쉬등의 설정
            .setDefaults(Notification.DEFAULT_ALL)
            // 시스템 영역의 아이콘, 타이틀, 메시지 내용
            .setSmallIcon(R.drawable.ic_watch)
            .setContentTitle(title)
            .setContentText(text)

            // 이 밑에부터는 모두 옵션
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setLargeIcon(picture)
            .setTicker(exampleString)
            .setNumber(number)

            // Touch 시, 행동(Intent)
            .setContentIntent(
                PendingIntent.getActivity(context, 0,

                    // 이곳에 원하는 Intent 만들기
                    // 일반적으로 원하는 Activity를 호출
                    Intent(context, MainActivity::class.java),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
            // 터치시 자동삭제
            .setAutoCancel(true)


        // 오레오 버전 이상에서는 채널을 만들어줘야 동작한다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nm = context.getSystemService(
                Context.NOTIFICATION_SERVICE) as NotificationManager
            val mChannel = NotificationChannel(
                "MY_NOTI_CHANNEL_ID",
                "MY_NOTI_CHANNEL",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            nm.createNotificationChannel(mChannel) // 채널 생성
            builder.setChannelId("MY_NOTI_CHANNEL_ID") // 빌더에 채널 id 설정
        }


        notify(context, builder.build())
    }

    // Notification 만들기
    private fun notify(context: Context, notification: Notification) {
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        nm.notify(NOTIFICATION_TAG, 0, notification)
    }

    // Notification 삭제하기 @TargetApi(Build.VERSION_CODES.ECLAIR)
    fun cancel(context: Context) {
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.cancel(NOTIFICATION_TAG, 0)
        } else {
            nm.cancel(NOTIFICATION_TAG.hashCode())
        }
    }
}