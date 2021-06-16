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
import kotlin.math.pow
import kotlin.math.sqrt

class JoystickView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
    ) : View(context, attrs, defStyleAttr) {
    // Radius of the joystick
    private var outerCenterX = 0.0f
    private var outerCenterY = 0.0f
    private var innerCenterX = 0.0f
    private var innerCenterY = 0.0f
    private var innerRadius = 0.0f
    private var outerRadius = 0.0f
    lateinit var onChange : OnJoystickChange

    init {
        val listener = OnTouchListener(function = { view, motionEvent ->
            view.performClick()
            if (motionEvent.action == MotionEvent.ACTION_MOVE) { // user drags
                // calculate the distance between the new point and the original center
                val distance = calculateDistance(motionEvent.x, motionEvent.y)
                // if its a valid point within the circle
                if (distance < outerRadius) {
                    innerCenterX = motionEvent.x
                    innerCenterY = motionEvent.y
                }
                else { // use the parallel triangles identity
                    // calculate the maximal point of the inner circle in this direction
                    val x =  motionEvent.x - outerCenterX
                    val y =  motionEvent.y - outerCenterY
                    // calculate the edge ratio
                    val ratio = outerRadius / distance
                    // calculate the edge length of the inner triangle
                    val a = y * ratio
                    val b = x * ratio
                    innerCenterX = outerCenterX + b
                    innerCenterY = outerCenterY + a
                }
            }
            else if (motionEvent.action == MotionEvent.ACTION_UP) { // put the inner circle back in the middle
                innerCenterX = outerCenterX
                innerCenterY = outerCenterY
            }
            // redraw the view
            invalidate()
            true
        })
        this.setOnTouchListener(listener)
    }

    private fun calculateDistance(x : Float, y : Float) : Float {
        // calculate the distance
        val dx = (x - outerCenterX).pow(2)
        val dy = (y - outerCenterY).pow(2)
        return sqrt(dx + dy)
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
        outerCenterX = (width / 2).toFloat()
        outerCenterY = (height / 2).toFloat()
        innerCenterX = outerCenterX
        innerCenterY = outerCenterY
        // Calculate the radius from the smaller of the width and height.
        outerRadius = (min(width, height) / 3.0 * 0.8).toFloat()
        innerRadius = outerRadius / 2;
    }

    private fun drawJoystick(canvas : Canvas) {
        // draw outer circle
        canvas.drawCircle(outerCenterX, outerCenterY, outerRadius, outerPaint)
        // draw inner circle
        canvas.drawCircle(innerCenterX, innerCenterY, innerRadius, innerPaint)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Draw the joystick
        drawJoystick(canvas)
        val ailerone =  (innerCenterX - outerCenterX) / outerRadius
        val elivator = (innerCenterY - outerCenterY) / outerRadius
        onChange.invoke(ailerone, elivator)
    }
}