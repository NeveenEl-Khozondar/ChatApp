package com.app.socketchatdemo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GroupsModel(var id:String="",
                       val name: String="",
                       var usersId:ArrayList<String>
                       ):Parcelable