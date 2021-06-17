package com.example.remotecontroljoystick.viewModel

import kotlin.properties.Delegates

class JoystickViewModel {
    private var _throttle: Float = 0.0f
    private var _rudder: Float = 0.0f
    private var _aileron: Float = 0.0f
    private var _elevator: Float = 0.0f

    var throttle: Float by Delegates.observable(_throttle) { _, old, new ->
        println("throttle changed from $old to $new")
        _throttle = new
    }
    var rudder: Float by Delegates.observable(_rudder) { _, old, new ->
        println("rudder changed from $old to $new")
        _rudder = new
    }
    var aileron: Float by Delegates.observable(_aileron) { _, old, new ->
        println("aileron changed from $old to $new")
        _aileron = new
    }
    var elevator: Float by Delegates.observable(_elevator) { _, old, new ->
        println("elevator changed from $old to $new")
        _elevator = new
    }

}