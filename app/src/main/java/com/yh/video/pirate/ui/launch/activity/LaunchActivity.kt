package com.yh.video.pirate.ui.launch.activity

import android.Manifest
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.afollestad.materialdialogs.MaterialDialog
import com.gyf.immersionbar.ImmersionBar
import com.yh.video.pirate.R
import com.yh.video.pirate.base.BaseActivity
import com.yh.video.pirate.databinding.ActivityLaunchBinding
import com.yh.video.pirate.ui.launch.viewmodel.LaunchViewModel
import com.yh.video.pirate.ui.main.activity.MainActivity
import com.yh.video.pirate.utils.AppManagerUtils
import com.yh.video.pirate.utils.isVisible
import com.yh.video.pirate.utils.permissions.requestMultiplePermissions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * 启动页
 */
class LaunchActivity : BaseActivity<ActivityLaunchBinding, LaunchViewModel>() {

    override fun onLayoutId(): Int {
        return R.layout.activity_launch
    }

    override fun initStatusTool() {
        ImmersionBar.with(this)
            .fullScreen(true)
            .init()
    }

    override fun initData() {
        super.initData()
        onClick()
        initCountDown()
    }

    /**
     * 倒计时
     */
    private fun initCountDown() {
        mBinding.countDownTv.isVisible = false
        lifecycleScope.launch {
            flowOf(5, 4, 3, 2, 1, 0, -1, -2).onEach { delay(100) }.collect {
                if (it == 5) {
                    mBinding.countDownTv.isVisible = true
                    mBinding.countDownTv.start()
                }
                when {
                    it == -2 -> {
                        startMainActivity()
                    }
                    it <= 0 -> {
                        mBinding.countDownTv.text = "跳过"
                    }
                    else -> {
                        mBinding.countDownTv.text = it.toString()
                    }
                }
            }
        }
    }

     override fun onClick() {
        mBinding.countDownTv.setOnClickListener {
            if (mBinding.countDownTv.text == "跳过") {
                startMainActivity()
            }
        }
    }


    /**
     * 打开主页
     */
    private fun startMainActivity() {
        requestMultiplePermissions(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            allGranted = {
                MainActivity.start(this)
                finish()
            },
            denied = {
                showDialog()
            },
            explained = {
                showDialog()
            })
    }

    /**
     * 权限申请弹窗
     */
    private fun showDialog() {
        MaterialDialog(this).show {
            message(text = "请允许应用申请的权限")
            negativeButton(text = "确定") {
            }
            positiveButton(text = "退出") {
                AppManagerUtils.getAppManager().AppExit(application)
            }

        }
    }
}