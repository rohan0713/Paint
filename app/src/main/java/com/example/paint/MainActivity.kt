package com.example.paint

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() , OnItemSelectedListener {


    lateinit var paintview : Paintview
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        paintview = Paintview(this)
        paintview.setBackgroundResource(R.drawable.random)
        paintview.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, 1000)
        layout.addView(paintview)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.COLORS, android.R.layout.simple_spinner_item
        )
        val Brushadapter = ArrayAdapter.createFromResource(
            this,
            R.array.BrushSize, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        Brushadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        Color_Spinner.adapter = adapter
        Brush_Spinner.adapter = Brushadapter
        Color_Spinner.onItemSelectedListener = this
        Brush_Spinner.onItemSelectedListener = this

        button.setOnClickListener {
            paintview.invalidate()
            paintview.path.reset()
        }

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        if (parent?.id == R.id.Color_Spinner) {
            val text = parent.getItemAtPosition(position).toString()
            if (text == "white") {
                paintview.brush.color = ContextCompat.getColor(this, R.color.white)
            } else if (text == "Cyan") {
                paintview.brush.color = ContextCompat.getColor(this, R.color.teal_700)
            } else {
                paintview.brush.color = ContextCompat.getColor(this, R.color.teal_200)
            }
        } else if (parent?.id == R.id.Brush_Spinner) {
            val text = parent.getItemAtPosition(position).toString()
            if (text == "10") {
                paintview.brush.strokeWidth = 10f
            } else if (text == "15") {
                paintview.brush.strokeWidth = 15f
            } else {
                paintview.brush.strokeWidth = 20f
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}

class Paintview(context: Context?) : View(context) {

    val brush = Paint()
    var path = Path()

    init {
        brush.isAntiAlias = true
        brush.color = ContextCompat.getColor(context!!, R.color.white)
        brush.style = Paint.Style.STROKE
        brush.strokeJoin = Paint.Join.ROUND
        brush.strokeWidth = 10f
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        val pointX = event!!.x
        val pointY = event.y

        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                path.moveTo(pointX, pointY)
                postInvalidate()
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(pointX, pointY)
                postInvalidate()
                return true
            }
        }
        postInvalidate()
        return false
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawPath(path, brush)
    }
}
