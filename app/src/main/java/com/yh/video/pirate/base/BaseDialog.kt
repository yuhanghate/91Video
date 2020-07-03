package com.yh.video.pirate.base

import android.os.Bundle
import android.view.Gravity
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ImmersionBar

abstract class BaseDialog<D : ViewBinding, VM : BaseViewModel>:BaseActivity<D,VM>() {

    override fun initStatusTool() {
        ImmersionBar.with(this)
            .fullScreen(true)
            .init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val lp = window.attributes
        lp.gravity = Gravity.CENTER
        window.attributes = lp // 设置参数给window
        super.onCreate(savedInstanceState)
        this.setFinishOnTouchOutside(true)
    }
}