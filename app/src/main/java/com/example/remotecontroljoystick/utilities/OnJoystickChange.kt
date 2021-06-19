package com.example.remotecontroljoystick.utilities

fun interface OnJoystickChange {
    fun invoke(aileron : Float, elevator : Float)
}