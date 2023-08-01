package com.swu.coswumetic

import android.app.Application
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {

    companion object
    {
        var instance: GlobalApplication? = null
    }

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "7ebcbcef7ba62dc9975ee3b5856daaf3")
    }


    override fun onTerminate()
    {
        super.onTerminate()
        instance = null
    }

    fun getGlobalApplicationContext(): GlobalApplication
    {
        checkNotNull(instance) { "this application does not inherit com.kakao.GlobalApplication" }
        return instance!!
    }
}
