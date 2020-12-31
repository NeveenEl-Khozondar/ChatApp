package com.app.socketchatdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_online_users.view.*

class GroupAdapter(val data: ArrayList<GroupsModel>, val onClick: OnClickItem):
        RecyclerView.Adapter<GroupAdapter.MyViewHolder>(){
    inner class MyViewHolder(item: View) : RecyclerView.ViewHolder(item)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupAdapter.MyViewHolder {
        return MyViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.activity_online_users, parent, false
                )
        )
    }

    override fun onBindViewHolder(holder: GroupAdapter.MyViewHolder, position: Int) {
        val user = data[position]

        holder.itemView.apply {
            username.text = user.name
            txtId.text = user.id
            setOnClickListener {
                onClick.onClick(user)
            }
        }

    }

    override fun getItemCount()= data.size
    interface OnClickItem {
        fun onClick(group: GroupsModel)
    }
}