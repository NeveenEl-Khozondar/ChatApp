package com.app.socketchatdemo

import android.os.Bundle
import android.os.PersistableBundle

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Type
import kotlin.collections.List as List




class OnlineUser :AppCompatActivity() {
    lateinit var app: SocketCreate
    private var mSocket: Socket?= null

    private val adapterUser = UserAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        app = application as SocketCreate
        mSocket = app.getSocket();


        rcDataUser.apply{
            layoutManager= LinearLayoutManager(applicationContext)
            adapter=adapterUser
        }

        mSocket!!.emit("allUser", true)
        mSocket!!.on("allUser"){ ars ->
            runOnUiThread {
                val usersList: Type =object: TypeToken<List<UserModel>>() {}.type
                val userList = Gson().fromJson<List<UserModel>>(ars[0].toString(),usersList)
                adapterUser.apply {
                    data.addAll(userList)
                    notifyDataSetChanged()
                }
            }

        }

    }

}