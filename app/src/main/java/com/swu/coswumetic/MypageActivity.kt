package com.swu.coswumetic

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kakao.sdk.user.UserApiClient

class MypageActivity : AppCompatActivity() {

    lateinit var switchBtn : Button
    lateinit var accountModified : Button
    lateinit var everything : Button
    lateinit var logout : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)


        everything = findViewById(R.id.everything)
        logout = findViewById(R.id.logout)

        everything.setOnClickListener {
            val intent = Intent(this, EverythingActivity::class.java)
            startActivity(intent)
        }

        logout.setOnClickListener {
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Log.e("Hello", "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                } else {
                    Log.i("Hello", "로그아웃 성공. SDK에서 토큰 삭제됨")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.bottom_mypage
        bottomNavigationView.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@setOnItemSelectedListener true
                }

                R.id.bottom_add -> {
                    startActivity(Intent(applicationContext, AddActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@setOnItemSelectedListener true
                }

                R.id.bottom_mypage -> return@setOnItemSelectedListener true
            }
            false
        }
    }
}