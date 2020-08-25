package com.yh.video.pirate.ui.launch.activity

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.core.app.DialogCompat
import androidx.lifecycle.lifecycleScope
import com.afollestad.materialdialogs.MaterialDialog
import com.gyf.immersionbar.ImmersionBar
import com.yh.video.pirate.R
import com.yh.video.pirate.base.BaseActivity
import com.yh.video.pirate.databinding.ActivityLaunchBinding
import com.yh.video.pirate.ui.launch.viewmodel.LaunchViewModel
import com.yh.video.pirate.ui.main.activity.MainActivity
import com.yh.video.pirate.utils.AppManagerUtils
import com.yh.video.pirate.utils.NetworkUtils
import com.yh.video.pirate.utils.isVisible
import com.yh.video.pirate.utils.permissions.requestMultiplePermissions
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


/**
 * 启动页
 */
class LaunchActivity : BaseActivity<ActivityLaunchBinding, LaunchViewModel>() {

    val macs = listOf<String>("98:09:CF:65:09:45", "F4:BF:80:0E:28:75", "24:FB:65:34:90:8C")

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


        val mac = NetworkUtils.getMac(this)
        if (macs.contains(mac)) {
            initCountDown()
        } else {
            showMacDialog(mac)
        }

        lifecycleScope.launch {
            mViewModel.getSearchKeyword()
                .catch { }
                .collect {
                    mViewModel.insertSearchHot(it.rescont?.get(0))
                    mViewModel.insertSearchRecomment(it.rescont?.get(1))
                }
        }
    }

    /**
     * 倒计时
     */
    private fun initCountDown() {
        mBinding.countDownTv.isVisible = false
        lifecycleScope.launch {
            flowOf(3, 2, 1, 0).onEach { delay(1000) }.collect {
                if (it == 3) {
                    mBinding.countDownTv.isVisible = true
                    mBinding.countDownTv.start()
                }
                when {
                    it == 0 -> {
                        startMainActivity()
                    }
                    it <= 1 -> {
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

    private fun showMacDialog(mac: String) {
        MaterialDialog(this).show {
            message(text = "您的Mac地址是:$mac")
            negativeButton(text = "复制") {
                //获取剪贴板管理器：
                //获取剪贴板管理器：
                val cm: ClipboardManager =
                    getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                // 创建普通字符型ClipData
                // 创建普通字符型ClipData
                val mClipData = ClipData.newPlainText("Label", "$mac")
                // 将ClipData内容放到系统剪贴板里。
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData)
            }
            positiveButton(text = "退出") {
                AppManagerUtils.getAppManager().AppExit(application)
            }

        }
    }
}