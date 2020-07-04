package com.yh.video.pirate.video.controller.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import com.dueeeke.videoplayer.controller.ControlWrapper
import com.dueeeke.videoplayer.controller.IControlComponent
import com.dueeeke.videoplayer.player.VideoView
import com.dueeeke.videoplayer.player.VideoViewManager
import com.yh.video.pirate.R
import com.yh.video.pirate.databinding.LayoutVideoPrepareBinding

/**
 * 准备播放界面
 */
class PrepareView : FrameLayout, IControlComponent {
    private var mControlWrapper: ControlWrapper? = null

    var mBinding : LayoutVideoPrepareBinding =
        LayoutVideoPrepareBinding.inflate(LayoutInflater.from(context), this, true)

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
     * 设置点击此界面开始播放
     */
    fun setClickStart() {
        setOnClickListener { mControlWrapper!!.start() }
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
        when (playState) {
            VideoView.STATE_PREPARING -> {
                bringToFront()
                visibility = View.VISIBLE
                mBinding.startPlay.visibility = View.GONE
                mBinding.netWarningLayout.visibility = View.GONE
                mBinding.loading.visibility = View.VISIBLE
            }
            VideoView.STATE_PLAYING, VideoView.STATE_PAUSED, VideoView.STATE_ERROR, VideoView.STATE_BUFFERING, VideoView.STATE_BUFFERED, VideoView.STATE_PLAYBACK_COMPLETED -> visibility =
                View.GONE
            VideoView.STATE_IDLE -> {
                visibility = View.VISIBLE
                bringToFront()
                mBinding.loading.visibility = View.GONE
                mBinding.netWarningLayout.visibility = View.GONE
                mBinding.startPlay.visibility = View.VISIBLE
                mBinding.thumb.visibility = View.VISIBLE
            }
            VideoView.STATE_START_ABORT -> {
                visibility = View.VISIBLE
                mBinding.netWarningLayout.visibility = View.VISIBLE
                mBinding.netWarningLayout.bringToFront()
            }
        }
    }

    override fun onPlayerStateChanged(playerState: Int) {}
    override fun setProgress(duration: Int, position: Int) {}
    override fun onLockStateChanged(isLocked: Boolean) {}

    init {
        mBinding.statusBtn.setOnClickListener {
            mBinding.netWarningLayout.visibility = View.GONE
            VideoViewManager.instance().setPlayOnMobileNetwork(true)
            mControlWrapper!!.start()
        }
    }
}