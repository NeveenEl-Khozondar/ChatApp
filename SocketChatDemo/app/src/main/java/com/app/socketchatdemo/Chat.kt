package com.app.socketchatdemo


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.ed_messege
import org.json.JSONObject

class Chat: AppCompatActivity() {
    lateinit var app: SocketCreate
    private var mSocket: Socket? = null
    private val messageAdapter = MessageAdapter(arrayListOf())

    private lateinit var userModel: UserModel
    private var idMessage =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        app = application as SocketCreate
        mSocket = app.getSocket();
        //get data by intent
        userModel=intent.getParcelableExtra("User")!! //didn't equal null
        //create id include(user id that i'm chat with & my id )
        idMessage=Login.userModel.id+userModel

        rcDataMes.apply {
            adapter = messageAdapter
            layoutManager = LinearLayoutManager(applicationContext)

        }

        btnSend.setOnClickListener {
            sendMessage()
        }



      mSocket!!.on("message"){ arg ->
          runOnUiThread {
              //0 because i send the id in 0 place
              //will inter here and offer the msg
              //if not equal meanthat the msg didn't receive
              //if the msg from me the fist condition will accurce if from the other user the second con will happen
              if (arg[0].toString()==idMessage || (userModel.id +Login.userModel.id+userModel)==arg[0].toString()){
              val message = Gson().fromJson<Message>(arg[1].toString(), Message::class.java)
              messageAdapter.apply {
                  dataMessage.add(message)
                  notifyDataSetChanged()
              }
              }
          }
      }

    }
    private fun sendMessage(){
        val message = JSONObject()
        message.put("username", Login.userModel.username)
        message.put("id", Login.userModel.id)
        message.put("message", ed_messege.toString())
        mSocket!!.emit("message",idMessage, message)
    }
}


