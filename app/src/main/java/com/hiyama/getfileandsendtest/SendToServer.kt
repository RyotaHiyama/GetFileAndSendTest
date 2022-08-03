package com.hiyama.getfileandsendtest

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.AsyncTask
import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketException

class SendToServer {
    //    private val SERVER_IP = "192.168.11.24" //Windows notePC
//    private val SERVER_IP = "192.168.56.1" //Virtual Box
    private val SERVER_IP = "165.242.108.120" //Ubuntu DesktopPC

    private val SERVER_PORT = 50000

    fun sendImageToServer(bmp: Bitmap) {
        val imageByte = bitmapToByte(bmp)
        connect(imageByte)
    }

    private fun bitmapToByte(bmp: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        return stream.toByteArray()
    }

    //    writer1: send byte data
//    private fun connect(data: String){
    private fun connect(data: ByteArray) {
        try {
            val inetSocketAddress = InetSocketAddress(SERVER_IP, SERVER_PORT)

            val task = @SuppressLint("StaticFieldLeak")
            object : AsyncTask<InetSocketAddress, Void, Void>() {
                override fun doInBackground(vararg inetSocketAddresses: InetSocketAddress): Void? {
                    var socket: Socket? = null
                    try {
                        socket = Socket()
                        socket.connect(inetSocketAddresses[0])

                        //send Byte
                        val writer1 = BufferedOutputStream(socket.getOutputStream())
                        writer1.write(data)
                        writer1.flush()
                        writer1.close()
                        socket.close()

                        return null
                    } catch (e: SocketException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    return null
                }
            }
            task.execute(inetSocketAddress)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}