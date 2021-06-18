package com.example.remotecontroljoystick.model

import java.io.PrintWriter
import java.net.Socket
import java.util.concurrent.Executors
import java.util.concurrent.Future

class JoystickModel {
    private var client = Socket()
    private lateinit var printWriter : PrintWriter
    fun startClient(ip: String, port: Int) {
        Executors.newSingleThreadExecutor().execute(Runnable {
            client = Socket(ip, port)
            println("success ${client.inetAddress}")
            printWriter = PrintWriter(client.getOutputStream())
        })
    }
    fun sendCommand(command: String) {
            Executors.newSingleThreadExecutor().execute(Runnable {
            printWriter.write(command)
        })

    }
}