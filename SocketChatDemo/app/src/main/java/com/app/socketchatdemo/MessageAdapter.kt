package com.app.socketchatdemo


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_send.view.*

class MessageAdapter(val dataMessage: ArrayList<Message> ): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    inner class SenderViewHolder(item: View) : RecyclerView.ViewHolder(item.rootView){
        fun bind(message: Message){
            itemView.rootView.txtMes.text = message.message
        }
    }


    inner class ReceverViewHolder(item: View) : RecyclerView.ViewHolder(item.rootView){
        fun bind(message: Message){
            itemView.rootView.txtMes.text = message.message
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType){
             0 -> {
                SenderViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.activity_send,parent,false)
                )
            }
            else -> {
                ReceverViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.activity_recver,parent,false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SenderViewHolder){     //is here equal instance of
            holder.bind(dataMessage[position])
        }else if (holder is ReceverViewHolder){
            holder.bind(dataMessage[position])
        }
    }

    override fun getItemCount(): Int = dataMessage.size

    override fun getItemViewType(position: Int): Int {  //to create more than one layout(view) in recycler
       val message = dataMessage[position]
        return when(message.id){
            Login.userModel.id ->{
                0
            }else->{
                1
            }
        }

    }
}