package com.yh.video.pirate.video.controller.component

import android.content.Context
import android.content.pm.ActivityInfo
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.animation.Animation
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.dueeeke.videoplayer.controller.ControlWrapper
import com.dueeeke.videoplayer.controller.IControlComponent
import com.dueeeke.videoplayer.player.VideoView
import com.dueeeke.videoplayer.util.PlayerUtils
import com.yh.video.pirate.databinding.LayoutVideoCompleteBinding

/**
 * 自动播放完成界面
 */
class CompleteView : FrameLayout, IControlComponent {
    private var mControlWrapper: ControlWrapper? = null

    var mBinding : LayoutVideoCompleteBinding

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
    }

    override fun onPlayStateChanged(playState: Int) {
        if (playState == VideoView.STATE_PLAYBACK_COMPLETED) {
            visibility = View.VISIBLE
            mBinding.btnStopFullscreen.visibility =
                if (mControlWrapper!!.isFullScreen) View.VISIBLE else View.GONE
            bringToFront()
        } else {
            visibility = View.GONE
        }
    }

    override fun onPlayerStateChanged(playerState: Int) {
        if (playerState == VideoView.PLAYER_FULL_SCREEN) {
            mBinding.btnStopFullscreen.visibility = View.VISIBLE
        } else if (playerState == VideoView.PLAYER_NORMAL) {
            mBinding.btnStopFullscreen.visibility = View.GONE
        }
        val activity = PlayerUtils.scanForActivity(context)
        if (activity != null && mControlWrapper!!.hasCutout()) {
            val orientation = activity.requestedOrientation
            val cutoutHeight = mControlWrapper!!.cutoutHeight
            val sflp =
                mBinding.btnStopFullscreen.layoutParams as ConstraintLayout.LayoutParams
            if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                sflp.setMargins(0, 0, 0, 0)
            } else if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                sflp.setMargins(cutoutHeight, 0, 0, 0)
            } else if (orientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
                sflp.setMargins(0, 0, 0, 0)
            }
        }
    }

    override fun setProgress(duration: Int, position: Int) {}
    override fun onLockStateChanged(isLock: Boolean) {}

    init {
        visibility = View.GONE
        mBinding = LayoutVideoCompleteBinding.inflate(LayoutInflater.from(context), this, true)
        mBinding.btnReplay.setOnClickListener { mControlWrapper!!.replay(true) }
        mBinding.btnStopFullscreen.setOnClickListener(OnClickListener {
            if (mControlWrapper!!.isFullScreen) {
                val activity = PlayerUtils.scanForActivity(context)
                if (activity != null && !activity.isFinishing) {
                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    mControlWrapper!!.stopFullScreen()
                }
            }
        })
        isClickable = true
    }
}