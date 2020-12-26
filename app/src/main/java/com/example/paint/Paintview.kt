package com.example.paint

import android.content.Context
import android.graphics.*
import android.graphics.BitmapFactory.*
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT

class Paintview(context: Context?) : View(context) {

    val brush = Paint()
    val path = Path()
    val bitmap = decodeResource(context?.resources, R.drawable.random)

    init {
        brush.isAntiAlias = true
        brush.color = Color.CYAN
        brush.style = Paint.Style.STROKE
        brush.strokeJoin = Paint.Join.ROUND
        brush.strokeWidth = 8f
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        val pointx = event!!.getX()
        val pointy = event.getY()

        when(event.action){

            MotionEvent.ACTION_DOWN -> {path.moveTo(pointx , pointy)
                postInvalidate()
            return  true}
            MotionEvent.ACTION_MOVE -> {path.lineTo(pointx , pointy)
                postInvalidate()
            return true}
        }
        postInvalidate()
        return false
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawBitmap(bitmap, 0f, 0f , null)
        canvas?.drawPath(path , brush)
    }

}