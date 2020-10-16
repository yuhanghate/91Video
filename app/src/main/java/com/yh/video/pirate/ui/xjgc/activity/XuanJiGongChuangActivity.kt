package com.yh.video.pirate.ui.xjgc.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.yh.video.pirate.R
import com.yh.video.pirate.base.BaseActivity
import com.yh.video.pirate.databinding.ActivityXuanJiGongChuangBinding
import com.yh.video.pirate.ui.xjgc.viewmodel.XuanJiGongChuangViewModel


class XuanJiGongChuangActivity :
    BaseActivity<ActivityXuanJiGongChuangBinding, XuanJiGongChuangViewModel>() {
    override fun onLayoutId(): Int {
        return R.layout.activity_xuan_ji_gong_chuang
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {
        super.initView()

        val webSettings: WebSettings = mBinding.webview.getSettings()
        //支持缩放，默认为true。
        webSettings.setSupportZoom(false)
        //调整图片至适合webview的大小
        webSettings.useWideViewPort = true
        // 缩放至屏幕的大小
        webSettings.loadWithOverviewMode = true
        //设置默认编码
        webSettings.defaultTextEncodingName = "utf-8"
        ////设置自动加载图片
        webSettings.loadsImagesAutomatically = true
        //允许访问文件
        webSettings.allowFileAccess = true;
        //开启javascript
        webSettings.javaScriptEnabled = true;

        mBinding.webview.setWebViewClient(object :WebViewClient(){
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                view?.loadUrl(request?.url.toString()!!)
                return true
            }
        });
        mBinding.webview.loadUrl("https://www.aliyun.com/")
    }
}