package com.example.paint

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val paintview = Paintview(this)
        paintview.setBackgroundResource(R.drawable.random)
        setContentView(paintview)

//        button.setOnClickListener {
//            paintview.invalidate()
//            paintview.path.reset()
//        }

    }
}
