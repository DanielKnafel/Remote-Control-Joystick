package com.example.remotecontroljoystick

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceView
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import java.lang.Integer.min

class Joystick @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
    ) : SurfaceView(context, attrs, defStyleAttr) {
    // Radius of the joystick
    private var radius = 0.0f
    private var innerImageView = ImageView(context, attrs, defStyleAttr)

    private val outerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        typeface = Typeface.create( "", Typeface.BOLD)
    }
    private val innerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        color = Color.LTGRAY
        typeface = Typeface.create( "", Typeface.BOLD)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        // Calculate the radius from the smaller of the width and height.
        radius = (min(width, height) / 2.0 * 0.8).toFloat()
    }

    private var listener = View.OnTouchListener(function = { view, motionEvent ->
        view.performClick()
        if (motionEvent.action == MotionEvent.ACTION_MOVE) {
            view.y = motionEvent.rawY - view.height/2
            view.x = motionEvent.rawX - view.width/2
        }
        true
    })

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Draw the joystick
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, outerPaint)
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius/2, innerPaint)
        innerImageView.x
        innerImageView.draw(canvas)
        innerImageView.setOnTouchListener(listener)
    }
}