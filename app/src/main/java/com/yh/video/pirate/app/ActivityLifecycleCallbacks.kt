package com.yh.video.pirate.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.yh.video.pirate.utils.AppManagerUtils

/**
 * 生命周期
 */
class ActivityLifecycleCallbacks: Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        AppManagerUtils.getAppManager().finishActivity(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, p1: Bundle) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivityCreated(activity: Activity, p1: Bundle?) {
        AppManagerUtils.getAppManager().addActivity(activity)
    }

    override fun onActivityResumed(activity: Activity) {
    }
}