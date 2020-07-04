package com.yh.video.pirate.video.controller.component

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.dueeeke.videoplayer.controller.ControlWrapper
import com.dueeeke.videoplayer.controller.IControlComponent
import com.dueeeke.videoplayer.player.VideoView
import com.dueeeke.videoplayer.util.PlayerUtils
import com.yh.video.pirate.R
import com.yh.video.pirate.base.BaseActivity
import com.yh.video.pirate.databinding.LayoutVideoTitleBinding

/**
 * 播放器顶部标题栏
 */
class TitleView : FrameLayout, IControlComponent {
    private var mControlWrapper: ControlWrapper? = null

    var mBinding:LayoutVideoTitleBinding

    constructor(context: Context) : super(context) {}
    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
    }

    fun setTitle(title: String?) {
        mBinding.title.text = title
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
//        if (mIsRegister) {
////            context.unregisterReceiver(mBatteryReceiver)
//            mIsRegister = false
//        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
//        if (!mIsRegister) {
//            context.registerReceiver(
//                mBatteryReceiver,
//                IntentFilter(Intent.ACTION_BATTERY_CHANGED)
//            )
//            mIsRegister = true
//        }
    }

    override fun attach(controlWrapper: ControlWrapper) {
        mControlWrapper = controlWrapper
    }

    override fun getView(): View {
        return this
    }

    override fun onVisibilityChanged(
        isVisible: Boolean,
        anim: Animation
    ) {
        //只在全屏时才有效
//        if (!mControlWrapper!!.isFullScreen) return
        if (isVisible) {
            if (visibility == View.GONE) {
                mBinding.sysTime.text = PlayerUtils.getCurrentSystemTime()
                visibility = View.VISIBLE
                startAnimation(anim)
            }
        } else {
            if (visibility == View.VISIBLE) {
                visibility = View.GONE
                startAnimation(anim)
            }
        }
    }

    override fun onPlayStateChanged(playState: Int) {
        when (playState) {
            VideoView.STATE_IDLE, VideoView.STATE_START_ABORT, VideoView.STATE_PREPARING, VideoView.STATE_PREPARED, VideoView.STATE_ERROR, VideoView.STATE_PLAYBACK_COMPLETED -> visibility =
                View.GONE
        }
    }

    override fun onPlayerStateChanged(playerState: Int) {
        if (playerState == VideoView.PLAYER_FULL_SCREEN) {
            if (mControlWrapper!!.isShowing && !mControlWrapper!!.isLocked) {
                visibility = View.VISIBLE
                mBinding.sysTime.text = PlayerUtils.getCurrentSystemTime()
            }
            mBinding.title.isSelected = true
        } else {
            visibility = View.GONE
            mBinding.title.isSelected = false
        }
        val activity = PlayerUtils.scanForActivity(context)
        if (activity != null && mControlWrapper!!.hasCutout()) {
            val orientation = activity.requestedOrientation
            val cutoutHeight = mControlWrapper!!.cutoutHeight
            if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                mBinding.titleContainer.setPadding(0, 0, 0, 0)
            } else if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                mBinding.titleContainer.setPadding(cutoutHeight, 0, 0, 0)
            } else if (orientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
                mBinding.titleContainer.setPadding(0, 0, cutoutHeight, 0)
            }
        }
    }

    override fun setProgress(duration: Int, position: Int) {}
    override fun onLockStateChanged(isLocked: Boolean) {
        if (isLocked) {
            visibility = View.GONE
        } else {
            visibility = View.VISIBLE
            mBinding.sysTime.text = PlayerUtils.getCurrentSystemTime()
        }
    }

    private class BatteryReceiver(private val pow: ImageView) :
        BroadcastReceiver() {
        override fun onReceive(
            context: Context,
            intent: Intent
        ) {
            val extras = intent.extras ?: return
            val current = extras.getInt("level") // 获得当前电量
            val total = extras.getInt("scale") // 获得总电量
            val percent = current * 100 / total
            pow.drawable.level = percent
        }

    }

    init {
        visibility = View.GONE
        mBinding = LayoutVideoTitleBinding.inflate(LayoutInflater.from(context), this, true)
        mBinding.back.setOnClickListener {
            val activity = PlayerUtils.scanForActivity(context)
            if (activity != null && mControlWrapper!!.isFullScreen) {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                mControlWrapper!!.stopFullScreen()
            } else {
                (context as BaseActivity<*,*>).onBackPressedSupport()
            }
        }
        //电量
//        mBatteryReceiver = BatteryReceiver(mBinding.ivBattery)
    }
}