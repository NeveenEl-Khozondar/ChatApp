package com.app.socketchatdemo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    val id:String = "",
    val username:String="",
    val pass:String="",
    val email:String="",
var isOnline: Boolean = false
):Parcelable {
}