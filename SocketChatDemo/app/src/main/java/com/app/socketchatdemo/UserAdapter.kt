package com.app.socketchatdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_online_users.view.*

class UserAdapter(val data:ArrayList<UserModel>) :RecyclerView.Adapter<UserAdapter.MyViewHolder>() {
    inner class MyViewHolder(item: View):RecyclerView.ViewHolder(item)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.activity_online_users,parent, false
        )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = data[position]
        holder.itemView.apply {
            username.text = user.username
            txtId.text = user.id
        }

    }

    override fun getItemCount()=data.size
}