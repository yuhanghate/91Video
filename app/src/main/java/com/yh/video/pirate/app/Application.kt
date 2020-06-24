package com.yh.video.pirate.app

import android.app.Application
import com.yh.video.pirate.utils.*

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        initContext(this)
        initLog()
        initPreference()
        initFragmentManger()
        initRefreshLayout()
    }
}