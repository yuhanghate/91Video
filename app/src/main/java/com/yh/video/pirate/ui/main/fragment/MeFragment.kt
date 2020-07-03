package com.yh.video.pirate.ui.main.fragment

import android.content.Intent
import com.yh.video.pirate.R
import com.yh.video.pirate.base.BaseFragment
import com.yh.video.pirate.databinding.FragmentMeBinding
import com.yh.video.pirate.ui.main.viewmodel.MeViewModel
import com.yh.video.pirate.ui.payment.activity.PaymentActivity
import com.yh.video.pirate.ui.settings.activity.ShareQrcodeActivity


class MeFragment:BaseFragment<FragmentMeBinding, MeViewModel>() {
    companion object{
        fun newInstance(): MeFragment {
            return MeFragment()
        }
    }

    override fun onLayoutId(): Int {
        return R.layout.fragment_me
    }

    override fun initView() {
        super.initView()
        initRefreshLayout()
    }

    override fun onClick() {
        super.onClick()
        //隐私协议
        mBinding.agreementCl.setOnClickListener {  }
        //历史记录
        mBinding.historyCl.setOnClickListener {  }
        mBinding.shareCl.setOnClickListener { shareTo("分享","91视频下载地址:   \nhttp://xz.cmspapp36.xyz/\n" +
                "（如果链接打不开请复制到浏览器中打开）\n") }
        //版本
        mBinding.versionCl.setOnClickListener {  }
        //支付
        mBinding.btnPayment.setOnClickListener { PaymentActivity.start(requireContext()) }
        //生成分享二维码
        mBinding.btnQrCode.setOnClickListener { ShareQrcodeActivity.start(requireContext()) }
    }

    private fun shareTo(chooserTitle: String, body: String) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_TEXT, body)
        startActivity(Intent.createChooser(sharingIntent, chooserTitle))
    }
}