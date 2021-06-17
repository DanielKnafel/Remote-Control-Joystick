package com.example.remotecontroljoystick

fun interface OnJoystickChange {
    fun invoke(aileron : Float, elevator : Float)
}