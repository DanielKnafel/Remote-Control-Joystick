package com.example.remotecontroljoystick.model

import java.io.PrintWriter
import java.net.Socket
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

    fun stopClient() {
        if (!stopAdding) {
            addJobToQueue(Runnable {
                printWriter.close()
                client.close()
                stopTaking = true
            })
            // prevent more jobs from being added
            stopAdding = true
        }
    }

    private fun addJobToQueue(job : Runnable) {
        if (!stopAdding)
            dispatchQueue.put(job)
    }

    fun startClient(ip: String, port: Int) : Boolean {
        // connect to the server
        val t = Thread(Runnable {
            try {
                println("$ip:$port")
                client = Socket(ip, port)
                printWriter = PrintWriter(client.getOutputStream())
            } catch (e: Exception) {
            }
        })
        t.start()
        //  await connection to finish
        t.join()

        // check if connection succeeded
        if (this::client.isInitialized && client.isConnected)
            return true
        return false
    }

    fun sendCommand(command: String) {
        if (this::client.isInitialized) {
            addJobToQueue(Runnable {
                printWriter.write(command)
                printWriter.flush()
            })
        }
    }
}