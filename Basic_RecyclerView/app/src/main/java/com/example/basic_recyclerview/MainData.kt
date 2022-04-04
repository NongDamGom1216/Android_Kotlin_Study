package com.example.basic_recyclerview

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// 내가 추가한 데이터도 넘겨주기 위해
// 객체를 꾸러미화 시켜서 내가 만든 클래스를 인텐트에 담는다
@Parcelize
data class MainData(val title:String, val content:String, val image: String): Parcelable
// 인터페이스