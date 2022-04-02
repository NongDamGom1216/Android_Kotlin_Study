package com.example.basic_edittext

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

// 밑에서 object를 안썼다면? -> TextWatcher를 구현하는 MyTextWatcher 클래스
class MyTextWatcher: TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
//        txtViewPassWd.text = s
//        밑에서랑 똑같이 했는데 안되는 이유 -> 멤버 변수라서 접근을 못한다
    }

    override fun afterTextChanged(p0: Editable?) {

    }
}

// 이미지 : R.mipmap.image ---> 데이터 타입 : Int

// editName, editPassword 같이 추가한 변수들 -> 멤버 변수
class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST = 0
        const val ID = "ID"
        const val PASSWD = "PASSWD"
        const val RESULT = "RESULT"
    }

    // 태그 문자열
    val TAG = "edittext::MainActivity"

    // 이미지 ID의 리스트 변수
    // 수정 가능하게 하려면 mutablelistOf 사용

//    로컬 사진 사용할 경우
    val images = listOf<Int>(R.mipmap.image, R.mipmap.image1, R.mipmap.image2)

    // 이미지 라이브러리 사용(타입 : String)
    val imageUrls = listOf<String>(
        "https://images.pexels.com/photos/2078474/pexels-photo-2078474.jpeg?cs=srgb&dl=pexels-jean-van-der-meulen-2078474.jpg&fm=jpg",
        "https://images.pexels.com/photos/1309848/pexels-photo-1309848.jpeg?cs=srgb&dl=pexels-as-r-1309848.jpg&fm=jpg",
        "https://images.pexels.com/photos/4079150/pexels-photo-4079150.jpeg?cs=srgb&dl=pexels-petr-ganaj-4079150.jpg&fm=jpg"
    )

    var current_image = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 이미지뷰의 이미지 리소스 변경: setImageResource(ID)
//        imageView.setImageResource(images[current_image])

        // 이미지 라이브러리 Glide 사용
        loadImage(imageUrls[current_image])


        // 양 쪽 버튼의 동작 원리 숙지하기!
        btnLeft.setOnClickListener {
            --current_image
            if(current_image < 0 ){
                current_image = images.size - 1
            }
//            imageView.setImageResource(images[current_image])

            Log.i(TAG, "이전 버튼 : $current_image, ${imageUrls[current_image]}")

            // 이미지 라이브러리 Glide 사용
            loadImage(imageUrls[current_image])
        }

        btnRight.setOnClickListener {
            ++current_image
            current_image = (current_image + 1) % images.size
//            imageView.setImageResource(images[current_image])

            Log.i(TAG, "다음 버튼 : $current_image, ${imageUrls[current_image]}")
            // 이미지 라이브러리 Glide 사용
            loadImage(imageUrls[current_image])
        }



        // 람다함수의 매개변수 1개 : editName
        // v : 이벤트를 발생한 뷰
        // hasFocus : 포커스을 얻었는지(true), 잃었는지 여부(false)
        editName.setOnFocusChangeListener() { v, hasFocus ->
            val edt = v as EditText
            val color = if (hasFocus) Color.TRANSPARENT else Color.LTGRAY
            edt.setBackgroundColor(color)
        }

//        editPassword.addTextChangedListener(MyTextWatcher()) // 생성자 호출해서 인스턴스 생성

        // object : 클래스를 정의, 인스턴스를 바로 생성
        // TextWatcher : 인터페이스
        // command + o 눌러서 모두 오버라이드
        editPassword.addTextChangedListener(object : TextWatcher { // TextWatcher 익명 구현체
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                txtViewPassWd.text = s // 비밀번호 칸에 입력한 문자열을 표시
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })

        btnLogin.setOnClickListener {
            val i = Intent(this, ResultActivity::class.java)
            i.putExtra(ID, editName.text.toString())
            i.putExtra(PASSWD, editPassword.text.toString())

            startActivityForResult(i, REQUEST) // REQUEST : 요청 ID, 리턴될 때 오는 정보

        }
    }


    // 호출한 액티비티에서 결과 값 받을 때
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        Log.i("MainActivity", "onActivityResult $data") // Logcat

        if(requestCode != REQUEST) return
        // data가 null이 아니면
        // intent에 담겨있는 RESULT 키를 받아서
        // let -> it으로 텍스트뷰 전달
        // xxx.apply : 주로 xxxdml 속성을 변경할 때 -> this가 바뀌고
        // xxx.let : xxx의 값으로 멤버 변수 수정할 때 -> this가 유지
        data?.getStringExtra(RESULT).let{
            txtMessage.text = it
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    // 이미지 라이브러리 Glide 사용
    fun loadImage(url: String) = Glide.with(this).load(imageUrls[current_image]).into(imageView)
}