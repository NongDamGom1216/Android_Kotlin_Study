import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MediaImage(val ctx: AppCompatActivity) {

    fun getAllPhotos(): ArrayList<String> {
        // 모든 사진 정보 가져오기
        val cursor = ctx.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC"
        )
        val imageUris = ArrayList<String>()

        // use는 let과 유사하다. 람다함수가 끝날 때 close가 호출된다.
        cursor?.use {
            while (it.moveToNext()) {
                // 사진 경로 Uri 가지고 오기
                val uri = it.getString(it.getColumnIndexOrThrow(
                    MediaStore.Images.Media.DATA))
                Log.d("MediaImage", uri)
                imageUris.add(uri) // array 리스트에 이미지 추가
            }
        }

        return imageUris

    }
}
