package com.app.socketchatdemo

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.Socket
import kotlinx.android.synthetic.main.activity_signup.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    lateinit var app: SocketCreate
    private var mSocket: Socket? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        app =application as SocketCreate
        mSocket = app.getSocket();

        mSocket!!.on(Socket.EVENT_CONNECT_ERROR){
            runOnUiThread {
                Log.e("EVENT_CONNECT_ERROR", "EVENT_CONNECT_ERROR: ")
            }
        };
        mSocket!!.on(Socket.EVENT_CONNECT_TIMEOUT, Emitter.Listener {
            runOnUiThread {
                Log.e("EVENT_CONNECT_TIMEOUT", "EVENT_CONNECT_TIMEOUT")
            }
        })
        mSocket!!.on(
            Socket.EVENT_CONNECT
        ){Log.e("onConnect", "Socket Connected!") };
        mSocket!!.on(Socket.EVENT_DISCONNECT, Emitter.Listener {
            runOnUiThread {
                Log.e("onDisconnect","Socket on Disconnect!")
            }
        })
        mSocket!!.connect()
    }
}