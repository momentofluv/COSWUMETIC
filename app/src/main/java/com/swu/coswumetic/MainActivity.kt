package com.swu.coswumetic

import android.content.Intent
import android.os.Bundle
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.model.AuthErrorCause.*
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.swu.coswumetic.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                //Login Fail
                Log.d("sss", "로그인 실패", error)
            } else if (token != null) {
                //Login Success
                Log.d("sss", "로그인 성공", error)
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
        }

        binding.loginButton.setOnClickListener {
            UserApiClient.instance.run {
                if (isKakaoTalkLoginAvailable(this@MainActivity)) {
                    loginWithKakaoTalk(this@MainActivity, callback = callback)
                } else {
                    loginWithKakaoAccount(this@MainActivity, callback = callback)
                }
            }
        }

    }
}