package com.yh.video.pirate.ui.main.activity

import android.content.Context
import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.yh.video.pirate.R
import com.yh.video.pirate.base.BaseActivity
import com.yh.video.pirate.databinding.ActivityMainBinding
import com.yh.video.pirate.ui.main.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * 主页
 */
class MainActivity:BaseActivity<ActivityMainBinding, MainViewModel>() {

    companion object{
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
    override fun onLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        super.initView()

        lifecycleScope.launch {
            mViewModel.pager.collect {

            }
        }
    }
}