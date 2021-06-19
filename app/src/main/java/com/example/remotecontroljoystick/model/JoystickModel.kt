package com.example.remotecontroljoystick.model

import java.io.PrintWriter
import java.net.Socket
import java.util.concurrent.BlockingQueue
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue

class JoystickModel {
    private lateinit var client : Socket
    private lateinit var printWriter : PrintWriter
    private var dispatchQueue = LinkedBlockingQueue<Runnable>()
    private var stopTaking = false
    private var stopAdding = false

    init {
        Thread {
            while (!stopTaking)
                dispatchQueue.take().run()
        }.start()
    }

    private fun stopClient() {
        if (!stopAdding) {
            printWriter.close()
            client.close()
            addJobToQueue(Runnable { stopTaking = true; })
            stopAdding = true
        }
    }

    private fun addJobToQueue(job : Runnable) {
        try {
            if (!stopAdding)
                dispatchQueue.put(job)
            else
                throw DispatchQueueStoppedException()
        } catch (e : Exception) {}
    }

    fun startClient(ip: String, port: Int) {
        addJobToQueue( Runnable {
            client = Socket(ip, port)
            println("connected to ${client.inetAddress}")
            printWriter = PrintWriter(client.getOutputStream())
        })
    }
    fun sendCommand(command: String) {
        if (this::client.isInitialized)
            addJobToQueue( Runnable { printWriter.write(command) })
        else
            throw ClientNotStartedException()
    }
}

class DispatchQueueStoppedException : Exception()
class ClientNotStartedException : Exception()