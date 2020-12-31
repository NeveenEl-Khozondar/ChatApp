package com.app.socketchatdemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.nkzawa.socketio.client.Socket
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class Login : AppCompatActivity(){

    lateinit var idApp: String
    private var mSocket: Socket? = null
    private lateinit var create: SocketCreate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        create= application as SocketCreate
        mSocket=create.getSocket()

        register.setOnClickListener{
            var i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }

        idApp=UUID.randomUUID().toString()

        mSocket!!.on("Login"){ args ->
            runOnUiThread {
                if(args[0].toString()== idApp)
                    if(args[1].toString().toBoolean()){
                        Toast.makeText(this, "${args[1]}", Toast.LENGTH_LONG).show()
                        userModel = Gson().fromJson(args[2].toString(),UserModel::class.java)
                        var i = Intent(this, OnlineUser::class.java)
                        startActivity(i)
                    }else{
                        Toast.makeText(this, "${args[0]}",Toast.LENGTH_LONG).show()
                    }
            }

        }
        sin_btn.setOnClickListener {
            val username=txtName.text.toString()
            val pass=password.text.toString()

            val jsonObject = JSONObject()
            jsonObject.put("username", username)
            jsonObject.put("pass", pass)
            jsonObject.put("isOnline", false)
            mSocket!!.emit("Login",idApp,idApp, jsonObject)

        }


    }
    companion object{
        lateinit var userModel: UserModel
    }
}