package com.yh.video.pirate.utils

import com.dueeeke.videoplayer.exo.ExoMediaPlayerFactory
import com.dueeeke.videoplayer.player.AndroidMediaPlayerFactory
import com.dueeeke.videoplayer.player.VideoViewConfig
import com.dueeeke.videoplayer.player.VideoViewManager
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.yh.video.pirate.BuildConfig
import com.yh.video.pirate.repository.preferences.PreferenceUtil
import me.yokeyword.fragmentation.Fragmentation

/**
 *
 * Alication初始化
 *
 *
 */

fun initPreference(){
    PreferenceUtil.init(application)
}

fun initFragmentManger() {
    Fragmentation.builder()
        // show stack view. Mode: BUBBLE, SHAKE, NONE
        .stackViewMode(Fragmentation.NONE)
        .debug(BuildConfig.DEBUG)
        .install()
}


 fun initLog() {
    val formatStrategy = PrettyFormatStrategy.newBuilder()
        .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
        .methodCount(2)         // (Optional) How many method line to show. Default 2
        .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
        .tag("91Video")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
        .build()

    Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
        override fun isLoggable(priority: Int, tag: String?): Boolean {
            return BuildConfig.DEBUG
        }
    })
}


fun initRefreshLayout() {
    //设置全局的Header构建器
//    SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
//        MaterialHeader(context).setColorSchemeResources(R.color.primary)//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
//    }
}

/**
 * 初始化播放器内核
 */
fun initPlayer() {
    VideoViewManager.setConfig(
        VideoViewConfig.newBuilder()
            .setLogEnabled(BuildConfig.DEBUG)
        //使用使用IjkPlayer解码
//        .setPlayerFactory(IjkPlayerFactory.create())
        //使用ExoPlayer解码
        .setPlayerFactory(ExoMediaPlayerFactory.create())
        //使用MediaPlayer解码
//        .setPlayerFactory(AndroidMediaPlayerFactory.create())
        .build());
}