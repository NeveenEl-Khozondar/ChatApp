package com.app.socketchatdemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.Socket
import kotlinx.android.synthetic.main.activity_signup.*
import org.json.JSONObject
import java.util.*

class Signup: AppCompatActivity() {
    lateinit var idApp: String
    private var mSocket: Socket? = null
    private lateinit var create: SocketCreate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        create= application as SocketCreate
        mSocket = create.getSocket();

        mSocket!!.on(Socket.EVENT_CONNECT_ERROR) {
            runOnUiThread {
                Log.e("EVENT_CONNECT_ERROR", "EVENT_CONNECT_ERROR: ")
            }
        };
        mSocket!!.on(Socket.EVENT_CONNECT_TIMEOUT, Emitter.Listener {
            runOnUiThread {
                Log.e("EVENT_CONNECT_TIMEOUT", "EVENT_CONNECT_TIMEOUT: ")

            }
        })
        mSocket!!.on(
            Socket.EVENT_CONNECT
        ) { Log.e("onConnect", "Socket Connected!") };
        mSocket!!.on(Socket.EVENT_DISCONNECT, Emitter.Listener {
            runOnUiThread {
                Log.e("onDisconnect", "Socket onDisconnect!")

            }
        })
        mSocket!!.connect()



    }
}