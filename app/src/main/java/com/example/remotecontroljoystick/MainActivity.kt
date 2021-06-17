package com.example.remotecontroljoystick

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import com.example.remotecontroljoystick.view.JoystickView
import com.example.remotecontroljoystick.viewModel.JoystickViewModel

class MainActivity : AppCompatActivity() {
    private val vm = JoystickViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // assign joystick on change callback
        val joystick: JoystickView = findViewById(R.id.joystick)
        joystick.onChange = OnJoystickChange { aileron, elevator ->
            vm.aileron = aileron
            vm.elevator = elevator
        }

        val throttle :SeekBar = findViewById(R.id.throttleSeekbar)
        throttle.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                vm.throttle = throttle.progress.toFloat() / 100
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                ;
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                ;
            }
        })

        val rudder :SeekBar = findViewById(R.id.rudderSeekbar)
        rudder.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                vm.rudder = rudder.progress.toFloat() / 100
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                ;
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                ;
            }
        })
    }
}