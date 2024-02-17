package ru.sample.duckapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val callHandler = CallHandler()
    private fun getDuck(str: String, inputField: EditText, imageView: ImageView){
        val numValue = str.toDoubleOrNull()

        if(str.isEmpty()){
            callHandler.getRandomDuck(imageView)
        }
        else if(numValue == null){
            inputField.error = "wrong input. enter code"
        }
        else{
            callHandler.getDuckWithHttpCode(str, inputField, imageView)
        }
        return
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView = findViewById<ImageView>(R.id.image)
        callHandler.getRandomDuck(imageView)

        findViewById<Button>(R.id.button)
            .setOnClickListener {
                val inputField = findViewById<EditText>(R.id.input)
                val input = inputField.text.toString()
                getDuck(input, inputField, imageView)
            }
    }

}