package com.example.remotecontroljoystick

interface OnJoystickChange {
    fun invoke(aileron : Float, Elevator : Float) : Void
}