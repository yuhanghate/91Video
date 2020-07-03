package com.yh.video.pirate.ui.settings.activity

import android.content.Context
import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.gyf.immersionbar.ImmersionBar
import com.yh.video.pirate.R
import com.yh.video.pirate.base.BaseActivity
import com.yh.video.pirate.databinding.ActivityShareQrCodeBinding
import com.yh.video.pirate.ui.settings.viewmodel.ShareQrcodeViewModel
import com.yh.video.pirate.utils.QRCodeUtil
import com.yh.video.pirate.utils.dp
import kotlinx.coroutines.launch

/**
 * 二维码
 */
class ShareQrcodeActivity : BaseActivity<ActivityShareQrCodeBinding, ShareQrcodeViewModel>() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ShareQrcodeActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun onLayoutId(): Int {
        return R.layout.activity_share_qr_code
    }

    override fun initStatusTool() {
        ImmersionBar.with(this)
            .transparentStatusBar()
            .statusBarView(mBinding.statusBarV)
            .fitsSystemWindows(false)
            .navigationBarColor(android.R.color.transparent) //导航栏颜色，不写默认黑色
            .init()
    }


    override fun initView() {
        super.initView()
        mBinding.btnBack.setOnClickListener { onBackPressedSupport() }

        lifecycleScope.launch {
            QRCodeUtil.createQRCodeBitmap("https://www.baidu.com", 270.dp, 270.dp)?.let {
                mBinding.qrCodeIv.setImageBitmap(it)
            }
        }
    }


}