package com.yh.video.pirate.video.controller.component

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.widget.*
import com.dueeeke.videoplayer.controller.ControlWrapper
import com.dueeeke.videoplayer.controller.IGestureComponent
import com.dueeeke.videoplayer.player.VideoView
import com.dueeeke.videoplayer.util.PlayerUtils
import com.yh.video.pirate.R
import com.yh.video.pirate.databinding.LayoutVideoGestureControlBinding

/**
 * 手势控制
 */
class GestureView : FrameLayout, IGestureComponent {

    var mBinding : LayoutVideoGestureControlBinding
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

    private var mControlWrapper: ControlWrapper? = null
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

    override fun onPlayerStateChanged(playerState: Int) {}
    override fun onStartSlide() {
        mControlWrapper!!.hide()
        mBinding.centerContainer!!.visibility = View.VISIBLE
        mBinding.centerContainer!!.alpha = 1f
    }

    override fun onStopSlide() {
        mBinding.centerContainer.animate()
            .alpha(0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    mBinding.centerContainer.visibility = View.GONE
                }
            })
            .start()
    }

    override fun onPositionChange(
        slidePosition: Int,
        currentPosition: Int,
        duration: Int
    ) {
        mBinding.proPercent.visibility = View.GONE
        if (slidePosition > currentPosition) {
            mBinding.ivIcon.setImageResource(R.drawable.ic_video_forward)
        } else {
            mBinding.ivIcon.setImageResource(R.drawable.ic_video_rewind)
        }
        mBinding.tvPercent.text = String.format(
            "%s/%s",
            PlayerUtils.stringForTime(slidePosition),
            PlayerUtils.stringForTime(duration)
        )
    }

    override fun onBrightnessChange(percent: Int) {
        mBinding.proPercent.visibility = View.VISIBLE
        mBinding.ivIcon.setImageResource(R.drawable.ic_brightness)
        mBinding.tvPercent.text = "$percent%"
        mBinding.proPercent.progress = percent
    }

    override fun onVolumeChange(percent: Int) {
        mBinding.proPercent.visibility = View.VISIBLE
        if (percent <= 0) {
            mBinding.ivIcon.setImageResource(R.drawable.ic_voice_off)
        } else {
            mBinding.ivIcon.setImageResource(R.drawable.ic_voice_up)
        }
        mBinding.tvPercent.text = "$percent%"
        mBinding.proPercent.progress = percent
    }

    override fun onPlayStateChanged(playState: Int) {
        visibility =
            if (playState == VideoView.STATE_IDLE || playState == VideoView.STATE_START_ABORT || playState == VideoView.STATE_PREPARING || playState == VideoView.STATE_PREPARED || playState == VideoView.STATE_ERROR || playState == VideoView.STATE_PLAYBACK_COMPLETED
            ) {
                View.GONE
            } else {
                View.VISIBLE
            }
    }

    override fun setProgress(duration: Int, position: Int) {}
    override fun onLockStateChanged(isLock: Boolean) {}

    init {
        visibility = View.GONE
        mBinding = LayoutVideoGestureControlBinding.inflate(LayoutInflater.from(context), this, true)
    }
}