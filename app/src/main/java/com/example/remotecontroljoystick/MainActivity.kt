package com.example.remotecontroljoystick

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import com.example.remotecontroljoystick.databinding.ActivityMainBinding
import com.example.remotecontroljoystick.utilities.OnJoystickChange
import com.example.remotecontroljoystick.view.JoystickView
import com.example.remotecontroljoystick.viewModel.MainViewModel
import java.lang.Exception
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {
    private val vm = MainViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main)
        binding.viewModel = vm

        // assign joystick on change callback
        val joystick: JoystickView = findViewById(R.id.joystick)
        joystick.onChange = OnJoystickChange { aileron, elevator ->
            vm.aileron = aileron
            vm.elevator = elevator
        }
        changeEnabledInLayout(false)
    }

    private fun changeEnabledInLayout(state : Boolean) {
        val layout : ConstraintLayout = findViewById(R.id.constraintLayout)
        for (child : View in layout.children)
            child.isEnabled = state
    }

    fun onConnectClick(view : View) {
        val ipEditText: EditText = findViewById(R.id.ipEditText)
        val portEditText: EditText = findViewById(R.id.portEditText)
        var port = 0

        try {
             port = portEditText.text.toString().toInt()
        } catch (e : NumberFormatException) {
        }

        if (vm.startClientViewModel(ipEditText.text.toString(), port))
            changeEnabledInLayout(true)
    }
}