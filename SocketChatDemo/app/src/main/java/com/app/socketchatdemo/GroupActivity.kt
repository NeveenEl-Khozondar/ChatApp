package com.app.socketchatdemo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.list_group.*
import kotlinx.android.synthetic.main.list_group.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

class GroupActivity: AppCompatActivity(){
    lateinit var app: SocketCreate
    private var mSocket: Socket? = null
//    private val addGroup by lazy { AddGroupDialogFragment(arrayListOf()) }
    val usersId=ArrayList<String>()
    private val adapterUser = UserAdapter(arrayListOf(), object : UserAdapter.OnClickItem {
        override fun onClick(userModel: UserModel) {
            if (!usersId.contains(userModel.id))
            usersId.add(userModel.id)
        }
    });
    private val adapterGroup = GroupAdapter(arrayListOf(), object : GroupAdapter.OnClickItem {
        override fun onClick(groupsModel: GroupsModel) {
          startActivity(Intent(this@GroupActivity,MessageGroupActivity::class.java).apply {
              putExtra("group", groupsModel)
          })
        }

    });

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.list_group)
            app = application as SocketCreate
            mSocket = app.getSocket();

            usersId.add(Login.userModel.id)
            rcDataUser.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = adapterUser
            }
            mSocket!!.emit("allUser", true)
            mSocket!!.on("allUser") { ars ->
                runOnUiThread {
                    val usersList: Type = object : TypeToken<List<UserModel>>() {}.type
                    val userList = Gson().fromJson<List<UserModel>>(ars[0].toString(), usersList)
                    adapterUser.apply {
                        data.clear()
                        data.addAll(userList)
                        notifyDataSetChanged()
                    }
                }
            }

            mSocket!!.emit("allGroup", true)
            mSocket!!.on("allGroup") { args ->
                runOnUiThread {
                    val groupList: Type = object : TypeToken<List<GroupsModel>>() {}.type
                    val groups = Gson().fromJson<List<GroupsModel>>(args[0].toString(), groupList)
                    //When user inter another time so we will clear the data
                    adapterGroup.data.clear()
                    groups.forEach { group->
                        group.usersId.map { id ->
                            if (Login.userModel.id == id)
                                       adapterGroup.apply {
                                           data.add(group)
                                           notifyDataSetChanged()
                                       }
                        }

                    }
                }
            }


            rcDataGroup.apply {
                adapter=adapterGroup
                layoutManager= LinearLayoutManager(this@GroupActivity)
            }


            btnCreateGroup.setOnClickListener {
                val nameGroup=txtNameGroup.text.toString();
                val dataGrop=JSONObject()
                dataGrop.put("name",nameGroup)
                dataGrop.put("id",UUID.randomUUID().toString())
                //To send array in persone should to define rrayofobject
                val userIdJson=JSONArray()
                for (id in usersId){
                    userIdJson.put(id)
                }
                dataGrop.put("usersId",userIdJson)

                //we need to send this group to server
                mSocket!!.emit("addGroup", dataGrop)
            }
        }


}

//private fun <E> ArrayList<E>.add(   : List<E>) {
//
//}
