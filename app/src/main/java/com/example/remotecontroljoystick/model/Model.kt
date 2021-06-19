package com.example.remotecontroljoystick.model

import java.io.PrintWriter
import java.net.Socket
import java.util.concurrent.BlockingQueue
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue

class Model {
    private lateinit var client : Socket
    private lateinit var printWriter : PrintWriter
    private var dispatchQueue = LinkedBlockingQueue<Runnable>()
    private var stopTaking = false
    private var stopAdding = false

    init {
        // dispatch loop of the active object
        Thread {
            while (!stopTaking) {
                dispatchQueue.take().run()
            }
        }.start()
    }

    private fun stopClient() {
        if (!stopAdding) {
            printWriter.close()
            client.close()
            addJobToQueue(Runnable { stopTaking = true; })
            // prevent more jobs from being added
            stopAdding = true
        }
    }

    private fun addJobToQueue(job : Runnable) {
        if (!stopAdding)
            dispatchQueue.put(job)
        else
            throw DispatchQueueWasStoppedException()
    }

    fun startClient(ip: String, port: Int) {
        if (!this::client.isInitialized) {
            addJobToQueue(Runnable {
                client = Socket(ip, port)
                printWriter = PrintWriter(client.getOutputStream())
            })
        }
    }

    fun sendCommand(command: String) {
        if (this::client.isInitialized) {
            addJobToQueue(Runnable {
                printWriter.write(command)
                printWriter.flush()
            })
        }
        else
            throw ClientNotStartedException()
    }
}

class DispatchQueueWasStoppedException : Exception()
class ClientNotStartedException : Exception()