package com.example.remotecontroljoystick.viewModel

import com.example.remotecontroljoystick.model.JoystickModel
import kotlin.properties.Delegates

class JoystickViewModel {
    private var _throttle: Float = 0.0f
    private var _rudder: Float = 0.0f
    private var _aileron: Float = 0.0f
    private var _elevator: Float = 0.0f
    private var model = JoystickModel()
    fun startClientViewModel(ip: String, port: Int) {
        model.startClient(ip, port)
    }
    var throttle: Float by Delegates.observable(_throttle) { _, old, new ->
        println("throttle changed from $old to $new")
        model.sendCommand("set /controls/engines/current-engine/throttle $new\r\n")
        _throttle = new
    }
    var rudder: Float by Delegates.observable(_rudder) { _, old, new ->
        println("rudder changed from $old to $new")
        model.sendCommand("set /controls/flight/rudder $new\r\n")
        _rudder = new
    }
    var aileron: Float by Delegates.observable(_aileron) { _, old, new ->
        model.sendCommand("set /controls/flight/aileron $new\r\n")
        println("aileron changed from $old to $new")
        _aileron = new
    }
    var elevator: Float by Delegates.observable(_elevator) { _, old, new ->
        model.sendCommand("set /controls/flight/elevator $new\r\n")
        println("elevator changed from $old to $new")
        _elevator = new
    }

}