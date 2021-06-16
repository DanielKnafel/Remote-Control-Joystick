package com.example.remotecontroljoystick.view

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import com.example.remotecontroljoystick.OnJoystickChange
import java.lang.Integer.min

class JoystickView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
    ) : View(context, attrs, defStyleAttr) {
    // Radius of the joystick
    private var radius = 0.0f
    private var centerX = 0.0f
    private var centerY = 0.0f
    lateinit var onChange : OnJoystickChange

    init {

        var listener = View.OnTouchListener(function = { view, motionEvent ->
            view.performClick()
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                centerX = motionEvent.x
                centerY = motionEvent.y
            }
            invalidate()
            true
        })
        this.setOnTouchListener(listener)
    }

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
        centerX = (width / 2).toFloat()
        centerY = (height / 2).toFloat()
        radius = (min(width, height) / 2.0 * 0.8).toFloat()
    }

    private fun drawJoystick(canvas : Canvas, x : Float, y : Float) {
        // draw outer circle
        canvas.drawCircle(x, y, radius, outerPaint)
        // draw inner circle
        canvas.drawCircle(x, y, radius/2, innerPaint)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Draw the joystick
        drawJoystick(canvas, centerX, centerY)
    }


}