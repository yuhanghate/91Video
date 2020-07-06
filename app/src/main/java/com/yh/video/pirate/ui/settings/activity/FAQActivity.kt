package com.yh.video.pirate.ui.settings.activity

import android.content.Context
import android.content.Intent
import com.yh.video.pirate.R
import com.yh.video.pirate.base.BaseActivity
import com.yh.video.pirate.databinding.ActivityFaqBinding
import com.yh.video.pirate.ui.settings.viewmodel.FAQViewModel

/**
 * 常见问题
 */
class FAQActivity :BaseActivity<ActivityFaqBinding,FAQViewModel>(){

    companion object{
        fun start(context: Context) {
            val intent = Intent(context, FAQActivity::class.java)
            context.startActivity(intent)
        }
    }
    override fun onLayoutId(): Int {
        return R.layout.activity_faq
    }

    override fun onStatusColor(): Int {
        return R.color.md_grey_900
    }

    override fun onClick() {
        super.onClick()
        mBinding.btnBack.setOnClickListener { onBackPressedSupport() }
    }
}