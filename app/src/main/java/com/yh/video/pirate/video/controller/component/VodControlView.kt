package com.yh.video.pirate.video.controller.component

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.FrameLayout
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.dueeeke.videoplayer.controller.ControlWrapper
import com.dueeeke.videoplayer.controller.IControlComponent
import com.dueeeke.videoplayer.player.VideoView
import com.dueeeke.videoplayer.util.PlayerUtils
import com.yh.video.pirate.R
import com.yh.video.pirate.databinding.LayoutVideoVodControlBinding

/**
 * 点播底部控制栏
 */
class VodControlView : FrameLayout, IControlComponent, View.OnClickListener,
    OnSeekBarChangeListener {
    private var mControlWrapper: ControlWrapper? = null
    private var mIsDragging = false
    private var mIsShowBottomProgress = true

    var mBinding: LayoutVideoVodControlBinding

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


    /**
     * 是否显示底部进度条，默认显示
     */
    fun showBottomProgress(isShow: Boolean) {
        mIsShowBottomProgress = isShow
    }

    override fun attach(controlWrapper: ControlWrapper) {
        mControlWrapper = controlWrapper
    }

    override fun getView(): View {
        return this
    }

    override fun onVisibilityChanged(
        isVisible: Boolean,
        anim: Animation?
    ) {
        if (isVisible) {
            mBinding.bottomContainer.visibility = View.VISIBLE
            if (anim != null) {
                mBinding.bottomContainer.startAnimation(anim)
            }
            if (mIsShowBottomProgress) {
                mBinding.bottomProgress.visibility = View.GONE
            }
        } else {
            mBinding.bottomContainer.visibility = View.GONE
            if (anim != null) {
                mBinding.bottomContainer.startAnimation(anim)
            }
            if (mIsShowBottomProgress) {
                mBinding.bottomProgress.visibility = View.VISIBLE
                val animation = AlphaAnimation(0f, 1f)
                animation.duration = 300
                mBinding.bottomProgress.startAnimation(animation)
            }
        }
    }

    override fun onPlayStateChanged(playState: Int) {
        when (playState) {
            VideoView.STATE_IDLE, VideoView.STATE_PLAYBACK_COMPLETED -> {
                visibility = View.GONE
                mBinding.bottomProgress.progress = 0
                mBinding.bottomProgress.secondaryProgress = 0
                mBinding.bottomProgress.progress = 0
                mBinding.bottomProgress.secondaryProgress = 0
            }
            VideoView.STATE_START_ABORT, VideoView.STATE_PREPARING, VideoView.STATE_PREPARED, VideoView.STATE_ERROR -> visibility =
                View.GONE
            VideoView.STATE_PLAYING -> {
                mBinding.ivPlay.isSelected = true
                if (mIsShowBottomProgress) {
                    if (mControlWrapper!!.isShowing) {
                        mBinding.bottomProgress.visibility = View.GONE
                        mBinding.bottomContainer.visibility = View.VISIBLE
                    } else {
                        mBinding.bottomContainer.visibility = View.GONE
                        mBinding.bottomProgress.visibility = View.VISIBLE
                    }
                } else {
                    mBinding.bottomContainer.visibility = View.GONE
                }
                visibility = View.VISIBLE
                //开始刷新进度
                mControlWrapper!!.startProgress()
            }
            VideoView.STATE_PAUSED -> mBinding.ivPlay.isSelected = false
            VideoView.STATE_BUFFERING, VideoView.STATE_BUFFERED -> mBinding.ivPlay.isSelected =
                mControlWrapper!!.isPlaying
        }
    }

    override fun onPlayerStateChanged(playerState: Int) {
        when (playerState) {
            VideoView.PLAYER_NORMAL -> mBinding.fullscreen.isSelected = false
            VideoView.PLAYER_FULL_SCREEN -> mBinding.fullscreen.isSelected = true
        }
        val activity = PlayerUtils.scanForActivity(context)
        if (activity != null && mControlWrapper!!.hasCutout()) {
            val orientation = activity.requestedOrientation
            val cutoutHeight = mControlWrapper!!.cutoutHeight
            when (orientation) {
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT -> {
                    mBinding.bottomContainer.setPadding(0, 0, 0, 0)
                    mBinding.bottomProgress.setPadding(0, 0, 0, 0)
                }
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE -> {
                    mBinding.bottomContainer.setPadding(cutoutHeight, 0, 0, 0)
                    mBinding.bottomProgress.setPadding(cutoutHeight, 0, 0, 0)
                }
                ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE -> {
                    mBinding.bottomContainer.setPadding(0, 0, cutoutHeight, 0)
                    mBinding.bottomProgress.setPadding(0, 0, cutoutHeight, 0)
                }
            }
        }
    }

    override fun setProgress(duration: Int, position: Int) {
        if (mIsDragging) {
            return
        }
        if (duration > 0) {
            mBinding.seekBar.isEnabled = true
            val pos = (position * 1.0 / duration * mBinding.seekBar.max).toInt()
            mBinding.seekBar.progress = pos
            mBinding.bottomProgress.progress = pos
        } else {
            mBinding.seekBar.isEnabled = false
        }
        val percent = mControlWrapper!!.bufferedPercentage
        if (percent >= 95) { //解决缓冲进度不能100%问题
            mBinding.seekBar.secondaryProgress = mBinding.seekBar.max
            mBinding.bottomProgress.secondaryProgress = mBinding.bottomProgress.max
        } else {
            mBinding.seekBar.secondaryProgress = percent * 10
            mBinding.bottomProgress.secondaryProgress = percent * 10
        }
        mBinding.totalTime.text = PlayerUtils.stringForTime(duration)
        mBinding.currTime.text = PlayerUtils.stringForTime(position)
    }

    override fun onLockStateChanged(isLocked: Boolean) {
        onVisibilityChanged(!isLocked, null)
    }


    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.fullscreen) {
            toggleFullScreen()
        } else if (id == R.id.iv_play) {
            mControlWrapper!!.togglePlay()
        }
    }

    /**
     * 横竖屏切换
     */
    private fun toggleFullScreen() {
        val activity = PlayerUtils.scanForActivity(context)
        mControlWrapper!!.toggleFullScreen(activity)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
        mIsDragging = true
        mControlWrapper!!.stopProgress()
        mControlWrapper!!.stopFadeOut()
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        val duration = mControlWrapper!!.duration
        val newPosition = duration * seekBar.progress / mBinding.seekBar.max
        mControlWrapper!!.seekTo(newPosition)
        mIsDragging = false
        mControlWrapper!!.startProgress()
        mControlWrapper!!.startFadeOut()
    }

    override fun onProgressChanged(
        seekBar: SeekBar,
        progress: Int,
        fromUser: Boolean
    ) {
        if (!fromUser) {
            return
        }
        val duration = mControlWrapper!!.duration
        val newPosition = duration * progress / mBinding.seekBar.max
        mBinding.currTime.text = PlayerUtils.stringForTime(newPosition.toInt())
    }

    init {
        visibility = View.GONE
        mBinding = LayoutVideoVodControlBinding.inflate(LayoutInflater.from(context), this, true)
        mBinding.fullscreen.setOnClickListener(this)
        mBinding.seekBar.setOnSeekBarChangeListener(this)
        mBinding.ivPlay.setOnClickListener(this)

        //5.1以下系统SeekBar高度需要设置成WRAP_CONTENT
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            mBinding.bottomProgress.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        }
    }
}