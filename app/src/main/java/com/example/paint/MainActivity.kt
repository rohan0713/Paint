package com.example.paint

import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), OnItemSelectedListener {

    lateinit var paintview: Painter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        paintview = Painter(this)

        paintview.setBackgroundResource(R.drawable.random)

        paintview.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, 1000)

        layout.addView(paintview)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.COLORS, android.R.layout.simple_spinner_item
        )
        val brushadapter = ArrayAdapter.createFromResource(
            this,
            R.array.BrushSize, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        brushadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        Color_Spinner.adapter = adapter
        Brush_Spinner.adapter = brushadapter
        Color_Spinner.onItemSelectedListener = this
        Brush_Spinner.onItemSelectedListener = this

        button.setOnClickListener {
            paintview.invalidate()
            paintview.startNew()
        }

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        if (parent?.id == R.id.Color_Spinner) {
            when(val text = parent.getItemAtPosition(position).toString()){
                "white" -> { paintview.setColor(text) }
                "Cyan" -> { paintview.setColor(text) }
                "black" -> { paintview.setColor(text) }
            }
        } else if (parent?.id == R.id.Brush_Spinner) {
            when(val text = parent.getItemAtPosition(position).toString()){
                "10" -> { paintview.setBrushSize(10f) }
                "15" -> { paintview.setBrushSize(15f) }
                "20" -> { paintview.setBrushSize(20f) }
            }
        }
    }
    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}

class Painter(context: Context?) : View(context) {
    var brush = Paint()
    var path = Path()
    var canvasPaint = Paint()
    var canvas = Canvas()
    var paintColor = 0xFF660000
    var canvasBitmap: Bitmap? = null
    var brushsize = 10f

    init {
       setupDrawing()
    }
    fun setBrushSize(newSize: Float) {
        invalidate()
        brush.strokeWidth = newSize
    }
    fun setColor(newColor: String?) {
        invalidate()
        paintColor = Color.parseColor(newColor).toLong()
        brush.color = paintColor.toInt()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(canvasBitmap!!)
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x
        val touchY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> path.moveTo(touchX, touchY)
            MotionEvent.ACTION_MOVE -> path.lineTo(touchX, touchY)
            MotionEvent.ACTION_UP -> {
                canvas.drawPath(path, brush)
                path.reset()
            }
            else -> return false
        }
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawBitmap(canvasBitmap!!, 0f, 0f, canvasPaint)
        canvas?.drawPath(path, brush)
    }

    private fun setupDrawing() {
        path = Path()
        brush = Paint()
        brush.color = paintColor.toInt()
        brush.isAntiAlias = true
        brush.strokeWidth = 5f
        brush.style = Paint.Style.STROKE
        brush.strokeJoin = Paint.Join.ROUND
        brush.strokeCap = Paint.Cap.ROUND
        canvasPaint = Paint(Paint.DITHER_FLAG)
        brush.strokeWidth = brushsize
    }
    fun startNew() {
        canvas.drawColor(0, PorterDuff.Mode.CLEAR)
        invalidate()
    }

}
