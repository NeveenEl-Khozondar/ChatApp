package com.app.socketchatdemo

import android.os.Bundle
import android.provider.ContactsContract
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_chat.*
import org.json.JSONObject
import java.security.acl.Group
import kotlinx.android.synthetic.main.activity_main.ed_messege

class MessageGroupActivity :AppCompatActivity(){
    lateinit var app: SocketCreate
    private var mSocket: Socket? = null
    private val mesAdapter = MessageAdapter(arrayListOf())
    //Received group data
    lateinit var group: GroupsModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        app = application as SocketCreate
        mSocket = app.getSocket();

        group = intent.getParcelableExtra("group")!!
        rcDataMes.apply {
            adapter = mesAdapter
            layoutManager = LinearLayoutManager(applicationContext)
        }

        btnSend.setOnClickListener {
            sendMessage()
        }
        mSocket!!.on("messageGroup") { arg ->
            runOnUiThread {
                //I want to check if this Id that from server does equal this channel id or not!
                //if equal will inter the condition and offer the msg and give it to adapter
                if (arg[0].toString() == group.id) {
                    val message = Gson().fromJson<Message>(arg[1].toString(), Message::class.java)
                    mesAdapter.apply {
                        dataMessage.add(message)
                        notifyDataSetChanged()
                    }
                }
            }
        }

    }
    private fun sendMessage() {
        val message = JSONObject()
        message.put("username", Login.userModel.username)
        message.put("id", Login.userModel.id)
        message.put("message", ed_messege.text.toString())
        mSocket!!.emit("messageGroup",group.id, message)
    }
    }

