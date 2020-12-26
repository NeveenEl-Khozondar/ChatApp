package com.app.socketchatdemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.nkzawa.socketio.client.Socket
import kotlinx.android.synthetic.main.activity_signup.*
import org.json.JSONObject
import java.util.*

class Signup: AppCompatActivity() {
    lateinit var idApp: String
    private var mSocket: Socket? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        log.setOnClickListener {
            var i = Intent(this, Login::class.java)
            startActivity(i)

        }
        mSocket!!.on("SignUp"){args ->
            runOnUiThread {
                if(args[0].toString() == idApp){
                    Log.e("SignUp", "${args[0]}")
                }
            }

        }
        sup_btn.setOnClickListener {
            var username=txtName.text.toString()
            var pass=txtPassword.text.toString()
            var email = email.text.toString()

            val jsonObject = JSONObject()
            jsonObject.put("id", UUID.randomUUID().toString())
            jsonObject.put("username", username)
            jsonObject.put("pass1", pass)
            jsonObject.put("email", email)
            mSocket!!.emit("SignUp",idApp, jsonObject)

            var i = Intent(this, Login::class.java)
            startActivity(i)
        }

    }
}