package com.app.socketchatdemo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*

class Splash: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)


        signin_btn.setOnClickListener{
            var i = Intent(this,Login::class.java)
            startActivity(i)
            finish()
        }

        signup_btn.setOnClickListener{
            var i2 = Intent(this,Signup::class.java)
            startActivity(i2)
            finish()
        }

    }
}