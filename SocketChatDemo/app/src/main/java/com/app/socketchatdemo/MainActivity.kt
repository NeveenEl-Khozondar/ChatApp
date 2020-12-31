package com.app.socketchatdemo

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.Socket
import kotlinx.android.synthetic.main.activity_signup.*
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var app: SocketCreate
    private var mSocket: Socket? = null
    lateinit var idApp: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        app = application as SocketCreate
        mSocket = app.getSocket();
        idApp = UUID.randomUUID().toString()
        mSocket!!.on(Socket.EVENT_CONNECT_ERROR) {
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
        ) { Log.e("onConnect", "Socket Connected!") };
        mSocket!!.on(Socket.EVENT_DISCONNECT, Emitter.Listener {
            runOnUiThread {
                Log.e("onDisconnect", "Socket on Disconnect!")
            }
        })
        mSocket!!.connect()



        log.setOnClickListener {
            var i = Intent(this, Login::class.java)
            startActivity(i)

        }
        mSocket!!.on("SignUp") { args ->
            runOnUiThread {
                Log.e("tttttt", "${args[0]}")
                if (args[0].toString() == idApp) {
                    Log.e("SignUp", "${args[0]}")
                }
            }

        }
        btnSinUp.setOnClickListener {
            var username = txtName.text.toString()
            var pass = txtPassword.text.toString()
            var email = email.text.toString()

            val jsonObject = JSONObject()

            jsonObject.put("id", idApp)
            jsonObject.put("username", username)
            jsonObject.put("pass", pass)
            jsonObject.put("email", email)
            mSocket!!.emit("SignUp", idApp, jsonObject)

            var i = Intent(this, Login::class.java)
            startActivity(i)
        }
    }
}