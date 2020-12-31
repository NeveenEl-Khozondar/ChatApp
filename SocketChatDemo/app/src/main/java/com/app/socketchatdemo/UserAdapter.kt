package com.app.socketchatdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_online_users.view.*

class UserAdapter(val data:ArrayList<UserModel>,val onClik:OnClickItem)
    :RecyclerView.Adapter<UserAdapter.MyViewHolder>() {
    inner class MyViewHolder(item: View):RecyclerView.ViewHolder(item)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
                LayoutInflater.from(parent.context).inflate(
            R.layout.activity_online_users,parent, false
        )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = data[position]
        holder.itemView.apply {
            username.text = user.username
            txtId.text = user.id
            setOnClickListener {
                onClik.onClick(user)
            }
           imageOnLine.setBackgroundColor(
                   ResourcesCompat.getColor(
                           context.resources,
                           if (user.isOnline)R.color.teal_700 else R.color.black, null)
           )
        }

    }

    override fun getItemCount()=data.size

    interface OnClickItem{
        fun onClick(userModel: UserModel)

    }
}