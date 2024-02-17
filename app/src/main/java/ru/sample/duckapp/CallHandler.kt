package ru.sample.duckapp

import android.graphics.BitmapFactory
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.sample.duckapp.domain.Duck
import ru.sample.duckapp.infra.Api

class CallHandler {

    fun getRandomDuck(imageView: ImageView) {
        val call = Api.ducksApi.getRandomDuck()

        call.enqueue(object : Callback<Duck> {
            override fun onResponse(call: Call<Duck>, response: Response<Duck>) {
                if (response.isSuccessful) {
                    val duck: Duck? = response.body()
                    Glide.with(imageView).load(duck?.url).into(imageView)
                } else {
                    Log.e("tag", "response error code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Duck>, t: Throwable) {
                Log.e("tag", "failure : ${t.message}", t)
            }
        })
    }

    fun getDuckWithHttpCode(input: String, inputField: EditText, imageView: ImageView) {
        val call = Api.ducksApi.getDuckByHttpCode(input)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val imageBytes = response.body()?.bytes()
                    val bitmap =
                        imageBytes?.let { BitmapFactory.decodeByteArray(imageBytes, 0, it.size) }
                    imageView.setImageBitmap(bitmap)
                } else {
                    inputField.error = "duck not found"
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("tag", "failure:  ${t.message}", t)
            }
        })
    }


}