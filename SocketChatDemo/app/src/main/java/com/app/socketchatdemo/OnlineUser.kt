package com.app.socketchatdemo

import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.activity_user.rcDataUser
import kotlinx.android.synthetic.main.list_user.*
import org.json.JSONObject
import java.lang.reflect.Type
import kotlin.collections.List as List




class OnlineUser :AppCompatActivity() {
    lateinit var app: SocketCreate
    private var mSocket: Socket?= null

    private val adapterUser = UserAdapter(arrayListOf(), object :UserAdapter.OnClickItem{
        override fun onClick(userModel: UserModel) {
            //When click on user and go to intent the second activity I want to transfer user data with inten(the activity went want to unch it )
          startActivity(Intent(applicationContext,MainActivity::class.java).apply {
             putExtra("User",userModel)
          })
        }

    });

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        app = application as SocketCreate
        mSocket = app.getSocket();


        rcDataUser.apply{
            adapter=adapterUser
            layoutManager= LinearLayoutManager(applicationContext)
        }

        mSocket!!.emit("allUser", true)
        mSocket!!.on("allUser"){ ars ->
            runOnUiThread {
                val usersList: Type =object: TypeToken<List<UserModel>>() {}.type
                val userList = Gson().fromJson<List<UserModel>>(ars[0].toString(),usersList)
                adapterUser.apply {
                    data.clear()
                    data.addAll(userList)
                    notifyDataSetChanged()
                }
            }

        }
        btnSeeAll.setOnClickListener {
            startActivity(Intent(this, GroupActivity::class.java))
        }
        mSocket!!.emit("updateOnline",JSONObject().apply {
            put("id",Login.userModel.id)
            put("isOnline",true)
        } )
    }

    override fun onDestroy() {
        super.onDestroy()
        mSocket!!.emit("updateOnline",JSONObject().apply {
            put("id",Login.userModel.id)
            put("isOnline",false)
        } )


    }

}